package my.data.stock;


import java.util.ArrayList;
import java.util.HashMap;


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







public abstract class StockDataPreprocess <T extends StockMetaData>{
	
	
	protected T _STOCK_META_DATA_OBJ;
	protected String[] _stockcodes;
	protected HashMap<String, String> _context;
	
	
	
	
	
	public StockDataPreprocess(T stockDataObject) throws Exception{
		T.getInstance();
		this._STOCK_META_DATA_OBJ = stockDataObject;
		this._context = T.getStockContext();
		
	}
	
	
	
	
	
	// =========================================================================================================
	// Preprocess
	// =========================================================================================================

	// for multi thread preprocess

	protected Integer _threadUnitsSize; // total threads numbers
	int _threadSize = 100;		//default thread size is 100

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
		
		
	private ArrayList<String> _RESUBMIT_RECORDS;
	private ArrayList<String> _FAILED_RECORDS;

	public void initRESUBMIT_REQ() {
		this._RESUBMIT_RECORDS = new ArrayList<String>();
		this._RESUBMIT_RECORDS.clear();
	}

	public void initFAILED_REQ() {

		this._FAILED_RECORDS = new ArrayList<String>();
		this._FAILED_RECORDS.clear();
	}
	
	
	
	
	/**
	 * Proprecess for all stock codes with muti-threads
	 */
	public void preprocess(String actionName) throws Exception {
		this.initRESUBMIT_REQ();
		this.initFAILED_REQ();

		// get all stock codes
		if (T.getStockCodes() == null || T.getStockCodes().length < 2000) {
			T.getStockMetaData();
		}

		// start collections for all stockcodes
		this.preprocess(actionName,T.getStockCodes());

		// resubmit request if any failures
		if (this._RESUBMIT_RECORDS.size() > 0) {
				this.preprocess(actionName, (String[])this._RESUBMIT_RECORDS.toArray());
		}

		// clear resource
		this._RESUBMIT_RECORDS = null;
	}

	
	
	public void preprocess(String actionName, String... stockcodes) throws NumberFormatException, Exception {
		//
		this._threadSize = Integer.parseInt(StockMetaData.getStockContext().get("STOCK_CODES_NUM_PER_THREAD"));

		//
		this._threadUnitsSize = 0;
		//
		String[] sub_scs = new String[this._threadSize];
		for (int i = 0; i < stockcodes.length; i++) {
			// add stock codes into sub stock codes
			sub_scs[i % this._threadSize] = stockcodes[i];
			//
			if (i % this._threadSize == 0) {
				if (i > 0) {
					this.luanchThread(actionName, sub_scs); // luanch thread
				}
				sub_scs = new String[this._threadSize];
			}

			if ((i % this._threadSize != 0 && i == stockcodes.length - 1)
					|| (i == 0 && i == stockcodes.length - 1)) { // reach the end
				this.luanchThread(actionName, sub_scs); // luanch thread
			}
		}

		//
		int loopTimes = 120;
		while (loopTimes > 0) {
			if (this._threadUnitsSize > 0) {
				Thread.sleep(1 * 60 * 1000); // every 1 min check once
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
					synchronized (StockDataPreprocess.this._RESUBMIT_RECORDS) {
						if (!StockDataPreprocess.this._RESUBMIT_RECORDS.contains(xSC)) {
							StockDataPreprocess.this._RESUBMIT_RECORDS.add(xSC);
							continue;
						} else {
							StockDataPreprocess.this._FAILED_RECORDS.add(xSC);
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
