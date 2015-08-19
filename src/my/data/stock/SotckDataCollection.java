package my.data.stock;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import my.context.MyContext;
import my.crawler.apache.http.HttpFileCrawler;
import my.data.stock.finStmt.FinancialStatementsCollectionThread;
import my.util.file.MyFile;

public abstract class SotckDataCollection {
	protected String[] _stockcodes;
	protected HashMap<String, String> _context;
	protected String _storeHomeDirContextName;

	public SotckDataCollection(String[] stockcodes,
			HashMap<String, String> context, String storeHomeDirContextName) {
		this._stockcodes = stockcodes;
		this._context = context;
		this._storeHomeDirContextName = storeHomeDirContextName;
	}

	// =========================================================================================================
	// Collection
	// =========================================================================================================

	// for multi thread collection

	public static Integer _threadUnitsSize_Collection; // total threads numbers
	int _threadSize = 100;
	
	
	

	
	public static void addNewThread(Integer _threadUnitsSize) {
		synchronized (_threadUnitsSize) {
			_threadUnitsSize++;

		}
	}
		
	
	
	public static void removeCompletedThread(Integer _threadUnitsSize) {
		synchronized (_threadUnitsSize) {
			_threadUnitsSize--;
		}
	}
		

	
	private HashMap<String, String> _RESUBMIT_REQ;
	private HashMap<String, String> _FAILED_REQ;
	
	
	
	
	
	public void initRESUBMIT_REQ(){
		this._RESUBMIT_REQ = new HashMap<String, String>();
		this._RESUBMIT_REQ.clear();
	}
	
	
	
	
	
	public void initFAILED_REQ(){

		this._FAILED_REQ = new HashMap<String, String>();
		this._FAILED_REQ.clear();
	}
	
	

	
	
	/**
	 * Collect for all stock codes with muti-threads
	 */
	public void collection() throws Exception {
		this.initRESUBMIT_REQ();
		this.initFAILED_REQ();

		// get all stock codes
		if (StockMetaData.getStockCodes() == null
				|| StockMetaData.getStockCodes().length < 2000) {
			StockMetaData.getStockMetaData();
		}

		// start collections for all stockcodes
		this.collection(StockMetaData.getStockCodes());

		// resubmit request if any failures
		if (this._RESUBMIT_REQ.size() > 0) {
			for (String xurl : this._RESUBMIT_REQ.keySet()) {
				StockDataCollectionThread.getAndSaveContent(xurl,this._RESUBMIT_REQ.get(xurl));
			}
		}

		// clear resource
		this._RESUBMIT_REQ = null;
	}

	
	
	
	
	
	public void collection(String... stockcodes) throws InterruptedException {
		//
		this._threadSize = Integer.parseInt(this._context.get("STOCK_CODES_NUM_PER_THREAD"));

		//
		SotckDataCollection._threadUnitsSize_Collection = 0;
		//
		String[] sub_scs = new String[this._threadSize];
		for (int i = 0; i < stockcodes.length; i++) {
			// add stock codes into sub stock codes
			sub_scs[i % this._threadSize] = stockcodes[i];
			//
			if (i % this._threadSize == 0) {
				if (i > 0) {
					this.luanchThread(sub_scs); // luanch thread
				}
				sub_scs = new String[this._threadSize];
			}

			if ((i % this._threadSize != 0 && i == stockcodes.length - 1)
					|| (i == 0 && i == stockcodes.length - 1)) { // reach the
																	// end
				this.luanchThread(sub_scs); // luanch thread
			}
		}

		//
		int loopTimes = 120;
		while (loopTimes > 0) {
			if (SotckDataCollection._threadUnitsSize_Collection > 0) {
				Thread.sleep(1 * 60 * 1000); // every 1 min check once
				loopTimes--;
			} else {
				loopTimes = -1; // exit loop
			}
		}

		// ----------------------------------------------------------
		System.out.println("Done");
	}

	
	
	
	public void luanchThread(String ...sub_scs){
		StockDataCollectionThread xThread = new <? extends StockDataCollectionThread>(sub_scs,this._context, this._storeHomeDirContextName);	//init thread
		new Thread(xThread).start();	//start thread
		
		//
		SotckDataCollection.addNewThread(SotckDataCollection._threadUnitsSize_Collection);
	}

	
	
	
	/** ===============================================================
	 *Thread class
	 * 
	 * @author hanrchen
	 *
	 * @param <T>
	 */
	
	abstract class StockDataCollectionThread
			implements Runnable {

		protected String[] _stockcodes;
		protected HashMap<String, String> _context;
		protected String _localStoreHomeDir;

//		protected static Map<String, String> _RequestNeedToBeResubmit;
//		protected static Map<String, String> _FailedRequestNeedToBeReviewed;

		public StockDataCollectionThread(String[] stockcodes,
				HashMap<String, String> context, String storeHomeDirContextName) {
			this._stockcodes = stockcodes;
			this._context = context;
			this._localStoreHomeDir = this._context.get(storeHomeDirContextName);
		}

		@Override
		public void run() {
			//
			for (String xsc : this._stockcodes) {

				Map<String, String> sc_urls_localFiles = this.getURL_File(xsc);

				try {
					this.collection(xsc, sc_urls_localFiles);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

//		protected abstract void setRequestNeedToBeResubmit(
//				Map<String, String> RequestNeedToBeResubmit);
//
//		protected abstract void setFailedRequestNeedToBeReviewed(
//				Map<String, String> FailedRequestNeedToBeReviewed);
//
//		public String getLocalStoreHomeDir() {
//			return this._localStoreHomeDir;
//		}

		/**
		 * 
		 * @param stockcodes
		 * @return HashMap<String:stockcode, ArrayList<String>: URLs for this
		 *         stock code>
		 */
		protected abstract Map<String, String> getURL_File(String stockcode);

		/**
		 * 
		 * @param sc_urls
		 * @return
		 */
		// public abstract HashMap<String,String>
		// getLocalStoreFiles(HashMap<String, ArrayList<String>> sc_urls);

		public void collection(String stockcode,
				Map<String, String> urls_fileName) throws Exception {
			// String dataSourceService =
			// this._context.get("CurrentDataSource"); //get template URLs
			for (String xURL : urls_fileName.keySet()) {
				// get local file absolute path
				String xStmtLocalFilePath = urls_fileName.get(xURL);

				System.out.println("Stock is :" + stockcode + " ; xURL is "
						+ xURL + " ;  local file abs path is "
						+ xStmtLocalFilePath);

				// get content via http and save file into local files
				FinancialStatementsCollectionThread.getAndSaveContent(xURL,
						xStmtLocalFilePath);
			}
		}

		public static void getAndSaveContent(String url, String localFilePath) {
			String content = "";
			// crawl content from web
			try {
				content = HttpFileCrawler.getContent(url);
			} catch (Exception ex) { // Need to handle http issues
				//
				synchronized (_RESUBMIT_REQ) {
					if (!T._RESUBMIT_REQ.containsKey(url)) {
						T._RESUBMIT_REQ.put(url, localFilePath);
					} else {
						T._FAILED_REQ.put(url, localFilePath);
					}
				}
				//
				ex.printStackTrace();
			}
			try {
				// craete local file if not exists
				File xStmtFile = MyFile.createFileIfNotExits(localFilePath); // create
																				// file
																				// if
																				// not
																				// exists
				// Save fileoutputstream to file in local
				synchronized (xStmtFile) {
					//write content string to file
					MyFile.WriteStringToFile(localFilePath, content,Charset.forName(MyContext.Charset), false); 
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// clear resouce
				content = null;
			}

		}

	}

}
