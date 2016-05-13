package my.engine;

import java.util.ArrayList;
import java.util.HashMap;


public class MultiThreadProcess <DataObjectT extends DataObject, unitObjT> {


/*

interface PreprocessEventListener{
	void collection();
	void analysis();
	
	
	void process();
}


abstract class PreprocessEventHandler implements PreprocessEventListener{

	@Override
	public void collection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void analysis() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void process(){
		
	}
}

*/








	// ================================================
	// Preprocess
	// ================================================
	// for multi thread preprocess

	protected Integer _threadUnitsSize; // total number of threads
	protected long _threadUnitSize = 100;		//default size for earch thread is 100
	
	protected DataObjectT _dataObj;
	
	public MultiThreadDataProcess(DataObjectT dataObj, long threadUnitSize){

		this._dataObj = dataObj;  //set data object which need to be processed
		
		this.setThreadUnitSize(threadUnitSize);  // set thread unit size for each thread.
	}

	public void addNewThread() {
		synchronized (this._threadUnitsSize) {
			this._threadUnitsSize++;

		}
	}

	public void removeCompletedThread() {
		synchronized (this._threadUnitsSize) {
			this._threadUnitsSize--;
		}
	}
	
	//
	private void setThreadUnitSize(long threadUnitSize){
		this._threadUnitSize = threadUnitSize;
	}
	
	
	private void getThreadUnitSize(){
		return this._threadUnitSize;
	}
		
		
	private ArrayList<unitObjT> _rerunRecords;
	private ArrayList<unitObjT> _failedRecords;

	
	public void initRerunRecords() {
		this._rerunRecords = new ArrayList<unitObjT>();
		this._rerunRecords.clear();
	}

	public void initFailedRecords() {

		this._failedRecords = new ArrayList<unitObjT>();
		this._failedRecords.clear();
	}
	
	
	
	/**
	 * Proprecess for all stock codes with muti-threads
	 */
	public void process(String actionName) throws Exception {
		this.initRerunRecords();
		this.initFailedRecords();

		// get all unit records
		ArrayList<unitObjT> allRecords = this._dataObj.getAllRecords();
		if (allRecords == null ) {  //if no records in data ojbect
			throw new Exception("No record can be found from input data object");
		}else if(allRecords.size < this._dataObj.getMinRequiredRecordsNumber()){	//records number < min required records number
			 new Exception("Unit object number of input data object is less than the min required record number of input data object.");
		}

		// start collections for all stockcodes
		this.processRun(this._dataObj.getAllRecords);

		// resubmit request if any failures
		if (this._rerunRecords.size() > 0) {
			Object[] recs = this._rerunRecords.toArray();
			String[] resubmitReq = new String[recs.length];
			System.arraycopy(recs, 0, resubmitReq, 0, recs.length);
			this.preprocess(actionName, resubmitReq);
		}

		// clear resource
		this._rerunRecords = null;
	}

	
	
	public void processRun(unitObjT... dataRecords) throws NumberFormatException, Exception {
		//
		unitObjT[] subRecords = new unitObjT[this._threadUnitSize];
		for (int i = 0; i < dataRecords.length; i++) {
			// add stock codes into sub stock codes
			subRecords[i % this._threadUnitSize] = dataRecords[i];
			//
			if (i % this._threadUnitSize == 0) {
				if (i > 0) {
					this.luanchThread(sub_scs); // luanch thread
				}
				sub_scs = new unitObjT[this._threadUnitSize];
			}

			if ((i % this.this._threadUnitSize != 0 && i == dataRecords.length - 1)
					|| (i == 0 && i == dataRecords.length - 1)) { // reach the end
				this.luanchThread(sub_scs); // luanch thread
			}
		}

		//
		int loopTimes = 60;
		while (loopTimes > 0) {
			if (this._threadUnitsSize > 0) {
				Thread.sleep(1 * 30 * 1000); // every 1 min check once
				loopTimes--;
			} else {
				loopTimes = -1; // exit loop
			}
		}

		// ----------------------------------------------------------
		System.out.println("Done");
	}
	
	

	public void luanchThread(String actionName, String ...sub_scs) throws Exception{
		//
		new Thread(new StockDataActionThread(actionName,sub_scs)).start();

		//
		this.addNewThread();
	}

	
	
	
	
	
	
	
	
	

	/** ===============================================================
	 *Thread class
	 * 
	 * @author hanrchen
	 *
	 * @param <T>
	 */
	
	class StockDataActionThread
			implements Runnable {

		protected String[] _stockcodes;
		protected String _localStoreHomeDir;
		
		protected String _actionName;



		public StockDataActionThread(String actionName, String[] stockcodes) throws Exception {
			this._stockcodes = stockcodes;
			this._localStoreHomeDir = _STOCK_META_DATA_OBJ.getLocalStoreHomeDir();
			
			
			this._actionName = actionName.trim().toLowerCase();
		}

		
		@Override
		public void run() {
			if(this._actionName == null || this._actionName.isEmpty()){
				try {
					throw new Exception("the action name should not be null or empty. ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			this.actionHandler(this._actionName, this._stockcodes);
			
//			if(_actionName.equals("collect")){
//				this.collection(this._stockcodes);
//			}else if(_actionName.equals("extract")){
//				this.analysis(this._stockcodes);
//			}
//				else if(_actionName.equals("analysis")){
//				this.analysis(this._stockcodes);
//			}
			
		}

		
		
		/**
		 * data action handler
		 * @param actionName
		 * @param stockcodes
		 */
		public void actionHandler(String actionName, String... stockcodes) {
			//
			for (String xSC : stockcodes) {
				if (xSC == null) {
					continue;
				}

				try {

					if (_actionName.equals("collect")) {
						_STOCK_META_DATA_OBJ.collection(xSC);
					} else if (_actionName.equals("extract")) {
						_STOCK_META_DATA_OBJ.extract(xSC);
					} else if (_actionName.equals("analysis")) {
						_STOCK_META_DATA_OBJ.analysis(xSC);
					}
					
				} catch (Exception e) {
					e.printStackTrace();

					//
					synchronized (StockDataPreprocess.this._rerunRecords) {
						if (!StockDataPreprocess.this._rerunRecords.contains(xSC)) {
							StockDataPreprocess.this._rerunRecords.add(xSC);
							continue;
						} else {
							StockDataPreprocess.this._failedRecords.add(xSC);
							continue;
						}
					}
				}
			}

			// decrease thread number
			StockDataPreprocess.this.removeCompletedThread();

		}
		
		
		


	}
	
	
	/** ===============================================================
	 * 
	 */
	

	


}
