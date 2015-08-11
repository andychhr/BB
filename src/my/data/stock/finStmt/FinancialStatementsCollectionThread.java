package my.data.stock.finStmt;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;

import javax.rmi.CORBA.Util;

import org.apache.commons.io.FileUtils;

import my.context.MyContext;
import my.crawler.apache.http.HttpFileCrawler;
import my.data.MetaData;
import my.util.file.MyFile;
import my.util.thread.MyThread;
import my.util.xml.MyXML;

public class FinancialStatementsCollectionThread implements Runnable {
	private String[] _stockcode;
	private HashMap<String, String> _context;
	
	//constructor
	public FinancialStatementsCollectionThread(String []stockcodes, HashMap<String, String> context){
		this._stockcode = stockcodes;
		this._context = context;
	}
	
	//overide method run
	@Override
	public void run() {
		try {
			this.collection(this._stockcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	
	/**
	 * Stock financial statement for each stok 
	 * @param stockcode
	 * @return
	 * @throws Exception
	 */
	public void collection(String stockcode) throws Exception {
		//
//		HashMap<String, File> finStmts = new HashMap<String, File>();
//		finStmts.clear();
		
		// for financial statements local store home location check
		String stmtStoreHomeLocation = this._context.get("FinanStmtStoreURI").trim();
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
	
	


	/**
	 * 
	 * @param stockcodes
	 * @throws Exception
	 */
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


	}
	
	// =========================================================================================================
	// create Mapping file
	// =========================================================================================================
	public File getMappingFile() throws Exception{
		if(MyContext.FIN_STMT_STORE_MAPPING_FILE == null || MyContext.FIN_STMT_STORE_MAPPING_FILE.isEmpty()){
			MyContext.setContext();
		}

		if (MyFile.validateFile(MyContext.FIN_STMT_STORE_MAPPING_FILE)) {
			return new File(MyContext.FIN_STMT_STORE_MAPPING_FILE);
		} else {
			return null;
		}
	}
	
	public void writeToMappingFile(File mapFile) throws Exception{
		if(mapFile!=null && MyXML.validateDirectory(mapFile.getAbsolutePath())){
			MyXML.writeToFile(doc, outfileName);
		}
	}
	
	
	// =========================================================================================================
	// =========================================================================================================

	public String getStmtName(String oriStmtName) {
		int x = oriStmtName.indexOf("Stmt");
		return oriStmtName.substring(0, x);
	}

}
