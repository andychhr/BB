package my.data.stock;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import my.context.MyContext;
import my.crawler.apache.http.HttpFileCrawler;
import my.data.stock.SotckDataCollection.StockDataCollectionThread;
import my.util.file.MyFile;


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
//			for (String xurl : this._RESUBMIT_REQ.keySet()) {
				//
				//handler.getAndSaveContent(xurl,this._RESUBMIT_REQ.get(xurl));
				this.preprocess(actionName, (String[])this._RESUBMIT_RECORDS.toArray());
				
//			}
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
	
	
	
	/**
	 * 
	 * @param sc_urls
	 * @return
	 */
	// public abstract HashMap<String,String>
	// getLocalStoreFiles(HashMap<String, ArrayList<String>> sc_urls);

//	public void preprocess(String stockcode, Map<String, String> urls_fileName) throws Exception {
//		// String dataSourceService =
//		// this._context.get("CurrentDataSource"); //get template URLs
//		for (String xURL : urls_fileName.keySet()) {
//			// get local file absolute path
//			String xLocalFilePath = urls_fileName.get(xURL);
//
////			System.out.println("Stock is :" + stockcode + " ; xURL is "
////					+ xURL + " ;  local file abs path is "
////					+ xLocalFilePath);
//
//			// get content via http and save file into local files
//			//this.getAndSaveContent(xURL,xLocalFilePath);
//			
//			this.process();
//		}
//	}
	

	public void luanchThread(String actionName, String ...sub_scs) throws Exception{
		//
		new Thread(new StockDataActionThread(actionName,sub_scs)).start();

		//
		this.addNewThread();
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param stockcodes
	 * @return HashMap<String:stockcode, ArrayList<String>: URLs for this
	 *         stock code>
	 * @throws Exception 
	 */
	protected abstract Map<String, String> getURL_File(String stockcode) throws Exception;
	
	
	
	public abstract void process();
	
	
	
	
//	public void process_GetAndSaveContent(String url, String localFilePath) {
//		String content = "";
//		// crawl content from web
//		content = this.getContent(url, localFilePath);
//		
//		
//		//Save to Local file
//		this.saveToLocalFile(content, localFilePath);
//
//	}
	
	
//	public String getContent(String url,String localFilePath){
//		String content = "";
//		try {
//			content = HttpFileCrawler.getContent(url);
//		} catch (Exception ex) { // Need to handle http issues
//			//
//			synchronized (this._RESUBMIT_REQ) {
//				if (!this._RESUBMIT_REQ.containsKey(url)) {
//					this._RESUBMIT_REQ.put(url, localFilePath);
//				} else {
//					this._FAILED_REQ.put(url, localFilePath);
//				}
//			}
//			//
//			ex.printStackTrace();
//		}
//		
//		return content;
//	}
	
	
//	public void saveToLocalFile(String content ,String localFilePath){
//		try {
//			// craete local file if not exists
//			File xStmtFile = MyFile.createFileIfNotExits(localFilePath); 
//			
//			// Save fileoutputstream to file in local
//			synchronized (xStmtFile) {
//				//write content string to file
//				MyFile.WriteStringToFile(localFilePath, content,Charset.forName(MyContext.Charset), false); 
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			// clear resouce
//			content = null;
//		}
//	}
	
	
	
	

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(_actionName.equals("collection")){
				this.collection();
			}else if(_actionName.equals("analysis")){
				this.analysis();
			}
			
		}

		
		public void collection() {
			//
			for (String xsc : this._stockcodes) {
				if(xsc == null){
					continue;
				}
  
				Map<String, String> sc_urls_localFiles = null;
				try {
					sc_urls_localFiles = StockDataPreprocess.this._STOCK_META_DATA_OBJ.getURL_File(xsc);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					StockDataPreprocess.this._STOCK_META_DATA_OBJ.process(xsc, sc_urls_localFiles);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//decrease thread number
			StockDataPreprocess.this.removeCompletedThread();
			
		}

		
		public void analysis() {
			// TODO Auto-generated method stub
			
		}

		
	
		

	}
	
	
	
}
