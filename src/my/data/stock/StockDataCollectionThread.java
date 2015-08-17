package my.data.stock;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import my.context.MyContext;
import my.crawler.apache.http.HttpFileCrawler;
import my.data.stock.finStmt.FinancialStatementsCollectionThread;
import my.util.file.MyFile;

public abstract class StockDataCollectionThread implements Runnable{
	private String []_stockcodes;
	private HashMap<String, String> _context;
	private String _localStoreHomeDir;
	
	public StockDataCollectionThread(String []stockcodes, HashMap<String, String> context, String storeHomeDirContextName){
		this._stockcodes = stockcodes;
		this._context = context;
		this._localStoreHomeDir = this._context.get(storeHomeDirContextName);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		HashMap<String, ArrayList<String>> sc_urls = this.getURLs(this._stockcodes);
		HashMap<String,String> urls_localFile = this.getLocalStoreFiles(sc_urls);
		for(String xsc : this._stockcodes){
			try {
				this.collection(xsc, this._localStoreHomeDir, urls_localFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public String getLocalStoreHomeDir(){
		return this._localStoreHomeDir;
	}
	
	
	/**
	 * 
	 * @param stockcodes
	 * @return HashMap<String:stockcode, ArrayList<String>: URLs for this stock code>
	 */
	public abstract HashMap<String, ArrayList<String>> getURLs(String []stockcodes);
	
	
	
	/**
	 * 
	 * @param sc_urls
	 * @return
	 */
	public abstract HashMap<String,String> getLocalStoreFiles(HashMap<String, ArrayList<String>> sc_urls);
	
	

	public void collection(String stockcode,String storeHomeLocation, HashMap<String, String> urls_fileName) throws Exception {
		//String dataSourceService = this._context.get("CurrentDataSource");	//get template URLs
		for (String xURL : urls_fileName.keySet()) {
			//
			System.out.println("xURL for stockcode:" + stockcode + " is "+ xURL);

			// get local file absolute path
			String xStmtLocalFilePath = urls_fileName.get(xURL);
					//stmtStoreHomeLocation + "/"+ this.getStmtName(xURL) + ".csv"; // get file abs location

			// get content via http and save file into local files
			FinancialStatementsCollectionThread.getAndSaveContent(xURL,xStmtLocalFilePath);

		}	
	}
	
	
	
	
	public static void getAndSaveContent(String url, String localFilePath, HashMap<String, String> RequestNeedToBeResubmit, HashMap<String,String> FailedRequestNeedToBeReviewed){
		String content = "";
		//crawl content from web
		try{
			content = HttpFileCrawler.getContent(url);
		}catch(Exception ex){	//Need to handle http issues
			//
			synchronized(RequestNeedToBeResubmit){
				if(!RequestNeedToBeResubmit.containsKey(url)){
					RequestNeedToBeResubmit.put(url, localFilePath);
				}else{
					FailedRequestNeedToBeReviewed.put(url, localFilePath);
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
