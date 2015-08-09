package my.data.stock.finStmt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import my.crawler.apache.http.HttpFileCrawler;
import my.data.MetaData;
import my.util.charset.MyCharset;
import my.util.file.MyFile;

import org.apache.commons.io.FileUtils;

public class FinancialStatement implements MetaData {
	
	HashMap<String, String> _context;
	
	public FinancialStatement(HashMap<String, String> context) {
		this._context = new HashMap<String, String>();
		this._context.clear();
		
		this._context = context;
	}

	public FinancialStatement(String stockcode, HashMap<String, String> context) {
		this._context = new HashMap<String, String>();
		this._context.clear();
		
		this._context = context;
	}

	@Override
	/**
	 * 
	 */
	public void collection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void collection(Date date) {
		// TODO Auto-generated method stub

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
				content = HttpFileCrawler.getFile(xurl); // get context
			}catch(Exception ex){
				ex.printStackTrace();
			}

			try{
				// Save fileoutputstream to file in local
				String xStmt = stmtStoreHomeLocation + "/" + this.getStmtName(stmt) + ".csv"; // get file abs location
				File xStmtFile = MyFile.createFileIfNotExits(xStmt);	//create file if not exists
				MyFile.WriteStringToFile(xStmtFile, content);	//write content string to file

				// getStmtName
				finStmts.put(this.getStmtName(stmt), xStmtFile);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// clear resouce
				
			}

		}
		
		
		//rturn
		return finStmts;
		
	}
	
	public void collection(String ...stockcodes) {
		// TODO Auto-generated method stub
		for(String sc : stockcodes){
			
		}

	}
	
	public void collection(String stockcode,Date date) {
		// TODO Auto-generated method stub
		
	}
	
	
	public String getStmtName(String oriStmtName){
		int x = oriStmtName.indexOf("Stmt");
		return oriStmtName.substring(0, x);
	}

}
