package my.data.stock.finStmt;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import my.context.MyContext;
import my.crawler.apache.http.HttpFileCrawler;
import my.data.stock.StockDataCollectionThread;
import my.util.file.MyFile;


public class FinancialStatementsCollectionThread extends StockDataCollectionThread {
//	private String[] _stockcode;
//	private HashMap<String, String> _context;
	
	//constructor
	public FinancialStatementsCollectionThread(String []stockcodes, HashMap<String, String> context, String storeHomeDirContextName){
		super(stockcodes, context, storeHomeDirContextName);
//		this._stockcode = stockcodes;
//		this._context = context;
	}
	
	
	
	
	

	@Override
	public HashMap<String, ArrayList<String>> getURLs(String[] stockcodes) {
		
		String dataSourceService = this._context.get("CurrentDataSource");	//get template URLs
		for(String key : this._context.keySet()){
			if(key.contains("StmtURI")){
				String xStmtURI = this._context.get(key);	//get x statement URIs
				xStmtURI=xStmtURI.replace("$sc$", stockcode).trim();	//replace "$sc$" to real stock code
				String xURL = dataSourceService + xStmtURI;	// get real statement url
				System.out.println("xURL for stockcode:"+stockcode + "is " + xURL);
				
				//get local file absolute path
				String xStmtLocalFilePath = this._localStoreHomeDir + "/" + this.getStmtName(key) + ".csv"; // get file abs location
				
				//get content via http and save file into local files
				FinancialStatementsCollectionThread.getAndSaveContent(xURL, xStmtLocalFilePath);
			}
		}	
	}

	@Override
	public HashMap<String, String> getLocalStoreFiles(
			HashMap<String, ArrayList<String>> sc_urls) {
		// TODO Auto-generated method stub
		return null;
	}

	

	/*

	//**
	 * 
	 * @param stockcodes
	 * @throws Exception
	 *//*
	public void collection(String ...stockcodes) throws Exception{
		//loop to get statments for each stock
		for(String sc : stockcodes){
			if(sc == null){
				continue;
			}
			
			System.out.println("Debug>>>processing stock code "+sc);
			try {
				this.collection(sc);	//collection stock financial statements
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}

		FinancialStatement.removeCompletedThread(FinancialStatement._threadUnitsSize_Collection);
	}
	
	

	
	*//**
	 * Stock financial statement for each stok 
	 * @param stockcode
	 * @return
	 * @throws Exception
	 *//*
	public void collection(String stockcode) throws Exception {
		//
//		HashMap<String, File> finStmts = new HashMap<String, File>();
//		finStmts.clear();
		
		// for financial statements local store home location check
		String stmtStoreHomeLocation = FinancialStatement.getLocalFinStmtsStoreHomeDir();
		stmtStoreHomeLocation += "/" + stockcode;
		
		
		String dataSourceService = this._context.get("CurrentDataSource");	//get template URLs
		for(String key : this._context.keySet()){
			if(key.contains("StmtURI")){
				String xStmtURI = this._context.get(key);	//get x statement URIs
				xStmtURI=xStmtURI.replace("$sc$", stockcode).trim();	//replace "$sc$" to real stock code
				String xURL = dataSourceService + xStmtURI;	// get real statement url
				System.out.println("xURL for stockcode:"+stockcode + "is " + xURL);
				
				//get local file absolute path
				String xStmtLocalFilePath = stmtStoreHomeLocation + "/" + this.getStmtName(key) + ".csv"; // get file abs location
				
				//get content via http and save file into local files
				FinancialStatementsCollectionThread.getAndSaveContent(xURL, xStmtLocalFilePath);
			}
		}	
	}
	
	
	public void collection(String stockcode,String stmtStoreHomeLocation, HashMap<String, String> urls_fileName) throws Exception {
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
	
	
	
	
	public static void getAndSaveContent(String url, String localFilePath){
		String content = "";
		//crawl content from web
		try{
			content = HttpFileCrawler.getContent(url);
		}catch(Exception ex){	//Need to handle http issues
			//
			synchronized(FinancialStatement.RequestNeedToBeResubmit){
				if(!FinancialStatement.RequestNeedToBeResubmit.containsKey(url)){
					FinancialStatement.RequestNeedToBeResubmit.put(url, localFilePath);
				}else{
					FinancialStatement.FailedRequestNeedToBeReviewed.put(url, localFilePath);
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
	
	

*/

	
	
	// =========================================================================================================
	// =========================================================================================================

	public String getStmtName(String oriStmtName) {
		int x = oriStmtName.indexOf("Stmt");
		return oriStmtName.substring(0, x);
	}

}
