package my.data.stock.finStmt;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;

import javax.rmi.CORBA.Util;

import my.context.MyContext;
import my.crawler.apache.http.HttpFileCrawler;
import my.data.MetaData;
import my.util.file.MyFile;
import my.util.thread.MyThread;

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
	

	public HashMap<String, File> collection(String stockcode) throws Exception {
		//
		HashMap<String, File> finStmts = new HashMap<String, File>();
		finStmts.clear();
		
		// TODO Auto-generated method stub
		String dataSourceService = this._context.get("CurrentDataSource");
		HashMap<String, String>urls = new HashMap<String, String>();
		urls.clear();
		for(String key : this._context.keySet()){
			if(key.contains("StmtURI")){
				String xStmtURI = this._context.get(key);	//get x statement URIs
				xStmtURI=xStmtURI.replace("$sc$", stockcode).trim();	//replace "$sc$" to real stock code
				String xURL = dataSourceService + xStmtURI;
				System.out.println("xURL for stockcode:"+stockcode + "is " + xURL);
				urls.put(key, xURL);
			}
		}
		
		//for financial statements local store home location check		
		String stmtStoreHomeLocation = this._context.get("FinanStmtStoreURI").trim();
		stmtStoreHomeLocation += "/" + stockcode;

		// 
		String content = "";
		for (String stmt : urls.keySet()) {
			// url
			String xurl = urls.get(stmt);
			//get content via http
			try {
				content = HttpFileCrawler.getContent(xurl); // get context
			}catch(Exception ex){
				ex.printStackTrace();
			}

			try{
				// Save fileoutputstream to file in local
				String xStmt = stmtStoreHomeLocation + "/" + this.getStmtName(stmt) + ".csv"; // get file abs location
				File xStmtFile = MyFile.createFileIfNotExits(xStmt);	//create file if not exists
				MyFile.WriteStringToFile(xStmtFile, content, Charset.forName(MyContext.Charset), false);	//write content string to file

				// getStmtName
				finStmts.put(this.getStmtName(stmt), xStmtFile);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// clear resouce
				content = null;
			}

		}
		
		
		//rturn
		return finStmts;
		
	}
	
	


	public HashMap<String, HashMap<String, File>> collection(String ...stockcodes) throws Exception{
		//return object
		HashMap<String, HashMap<String, File>> scsFinanStmts = new HashMap<String, HashMap<String, File>>();
		
		//loop to get statments for each stock
		for(String sc : stockcodes){
			if(sc == null){
				continue;
			}
			
			System.out.println("Debug>>>processing stock code "+sc);
			HashMap<String, File> xscStmts =  new HashMap<String, File>();
			try {
				xscStmts = this.collection(sc);	//collection stock financial statements
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
			if(xscStmts.size() == 0){
				throw new Exception("no financial statements for stock "+ sc);
			}else{
				scsFinanStmts.put(sc, xscStmts);
			}
		}
		
		//return 
		return scsFinanStmts;

	}
	
	  //=========================================================================================================
	  //=========================================================================================================
		
		
		
		
		public String getStmtName(String oriStmtName){
			int x = oriStmtName.indexOf("Stmt");
			return oriStmtName.substring(0, x);
		}

}
