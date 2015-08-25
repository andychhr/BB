package my.data.stock.finStmt;

import java.util.HashMap;
import java.util.Map;

import my.context.MyContext;
import my.crawler.apache.http.HttpFileCrawler;

import my.data.stock.StockMetaData;


public class FinStmtDataObj extends StockMetaData {
	
	
	
	private String _FinStmt_STORE_HOME_DIR;
	
	
	
	
	
	
	public FinStmtDataObj() throws Exception{
		super.getInstance();
		this.setLocalStoreHomeDir();
		
		//StockMetaData.COLLECTION_CONTEXT = this.getCollectionContext(StockMetaData.STOCK_CODES);
	}
	
	
//	public Map<String, Map<String, String>> getCollectionContext(String []stockcodes) throws Exception{
//		Map<String, Map<String, String>> collContext = new HashMap<String, Map<String, String>>();
//		collContext.clear();
//		
//		for(String sc : stockcodes){
//			collContext.put(sc, this.getURL_File(sc));
//		}
//		
//		return collContext;
//	}
	
	

	@Override
	public Map<String, String> getURL_File(String stockcode) throws Exception {
		//
		HashMap<String, String> url_file = new HashMap<String,String>();
		url_file.clear();
		
		
//		StockMetaData.getInstance();
		//get template URLs
		for(String key : FinStmtDataObj.getStockContext().keySet()){
			if(key.contains("StmtURI")){
				String xStmtURI = FinStmtDataObj.getStockContext().get(key);	//get x statement URIs
				xStmtURI=xStmtURI.replace("$sc$", stockcode).trim();	//replace "$sc$" to real stock code
				String xURL = StockMetaData.getStockContext().get("CurrentDataSource") + xStmtURI;	// get real statement url
//				System.out.println("xURL for stockcode:"+stockcode + "is " + xURL);
				
				//get local file absolute path
				String xStmtLocalFilePath = this.getLocalStoreHomeDir() + "/" + this.getStmtName(key) + ".csv"; // get file abs location
				
				url_file.put(xURL, xStmtLocalFilePath);
				//get content via http and save file into local files
				//getAndSaveContent(xURL, xStmtLocalFilePath);
			}
		}	
		
		//
		return url_file;
	}
	
	
	
	
	
	
	@Override
	public void collection(String stockcode) throws Exception {

		

		Map<String, String> sc_urls_localFiles = null;
		try {
			sc_urls_localFiles = this.getURL_File(stockcode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String xURL : sc_urls_localFiles.keySet()) {
			// get content from web
			String content = HttpFileCrawler.getContent(xURL);

			// save content into local files
			String localFilePath = sc_urls_localFiles.get(xURL);
			this.saveToLocalFile(content, localFilePath);

			// clear resource
			content = null;
		}

	}
	
	
	
	
	@Override
	public void analysis(String stockcode){
	
	}
		
		
	
		
//		public void collection_GetAndSaveContent(String url, String localFilePath) {
//			String content = "";
//			// crawl content from web
//			content = this.collection_getContent(url, localFilePath);
//			
//			
//			//Save to Local file
//			this.saveToLocalFile(content, localFilePath);
//
//		}
		
		
		

//		public String collection_getContent(String url,String localFilePath){
//			String content = "";
//			try {
//				content = HttpFileCrawler.getContent(url);
//			} catch (Exception ex) { // Need to handle http issues
//				//
//				synchronized (this._RESUBMIT_RECORDS) {
//					if (!this._RESUBMIT_REQ.containsKey(url)) {
//						this._RESUBMIT_REQ.put(url, localFilePath);
//					} else {
//						this._FAILED_REQ.put(url, localFilePath);
//					}
//				}
//				//
//				ex.printStackTrace();
//			}
//			
//			return content;
//		}
		
		
	
	
	
	@Override
	public void setLocalStoreHomeDir() throws Exception{
		MyContext.getInstance();
		this._FinStmt_STORE_HOME_DIR = MyContext.getStockContext().get("FinanStmtStoreURI");
	}
	
	
	@Override
	public String getLocalStoreHomeDir(){
		return this._FinStmt_STORE_HOME_DIR;
	}
	
	

	// ===============================================================
	// ============================================================

		public String getStmtName(String oriStmtName) {
			int x = oriStmtName.indexOf("Stmt");
			return oriStmtName.substring(0, x);
		}
	
	
	
	
}
