package my.data.stock;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import my.context.MyContext;
import my.crawler.apache.http.HttpFileCrawler;
import my.data.stock.finStmt.FinancialStatementsCollectionThread;
import my.util.file.MyFile;

public abstract class StockDataCollectionThread<T extends SotckDataCollection> implements Runnable{
	
	protected String []_stockcodes;
	protected HashMap<String, String> _context;
	protected String _localStoreHomeDir;
	
	protected static Map<String, String> _RequestNeedToBeResubmit;
	protected static Map<String,String> _FailedRequestNeedToBeReviewed;
	
	public StockDataCollectionThread(String []stockcodes, HashMap<String, String> context, String storeHomeDirContextName){
		this._stockcodes = stockcodes;
		this._context = context;
		this._localStoreHomeDir = this._context.get(storeHomeDirContextName);
	}

	@Override
	public void run() {
		//
		for(String xsc : this._stockcodes){
			
			Map<String, String> sc_urls_localFiles = this.getURL_File(xsc);
			
			try {
				this.collection(xsc, sc_urls_localFiles);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	protected abstract void setRequestNeedToBeResubmit(Map<String, String> RequestNeedToBeResubmit);
	
	protected abstract void setFailedRequestNeedToBeReviewed(Map<String, String> FailedRequestNeedToBeReviewed);
	
	
	
	
	public String getLocalStoreHomeDir(){
		return this._localStoreHomeDir;
	}
	
	
	
	/**
	 * 
	 * @param stockcodes
	 * @return HashMap<String:stockcode, ArrayList<String>: URLs for this stock code>
	 */
	protected abstract Map<String, String> getURL_File(String stockcode);
	
	
	
	/**
	 * 
	 * @param sc_urls
	 * @return
	 */
	//public abstract HashMap<String,String> getLocalStoreFiles(HashMap<String, ArrayList<String>> sc_urls);
	
	

	public void collection(String stockcode, Map<String, String> urls_fileName) throws Exception {
		//String dataSourceService = this._context.get("CurrentDataSource");	//get template URLs
		for (String xURL : urls_fileName.keySet()) {
			// get local file absolute path
			String xStmtLocalFilePath = urls_fileName.get(xURL);
			
			System.out.println("Stock is :" + stockcode + " ; xURL is "+ xURL + " ;  local file abs path is "+ xStmtLocalFilePath);

			// get content via http and save file into local files
			FinancialStatementsCollectionThread.getAndSaveContent(xURL,xStmtLocalFilePath);
		}	
	}
	
	
	
	
	public static void getAndSaveContent(String url, String localFilePath){
		String content = "";
		//crawl content from web
		try{
			content = HttpFileCrawler.getContent(url);
		}catch(Exception ex){	//Need to handle http issues
			//
			synchronized(T._RESUBMIT_REQ){
				if(!T._RESUBMIT_REQ.containsKey(url)){
					T._RESUBMIT_REQ.put(url, localFilePath);
				}else{
					T._FAILED_REQ.put(url, localFilePath);
				}
			}
			//
			ex.printStackTrace();
		}
		try{
			// craete local file if not exists
			File xStmtFile = MyFile.createFileIfNotExits(localFilePath);	//create file if not exists
			// Save fileoutputstream to file in local
			synchronized(xStmtFile){
				MyFile.WriteStringToFile(localFilePath, content, Charset.forName(MyContext.Charset), false);	//write content string to file
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// clear resouce
			content = null;
		}
		
	}
	

}
