package data.stock.finStmt;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;

import crawler.apache.http.HttpFileCrawler;
import data.MetaData;

public class FinancialStatement implements MetaData {
	public FinancialStatement() {

	}

	public FinancialStatement(String stockcode) {

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
	
	
	public HashMap<String, HashMap<String,FileOutputStream>> collection(String stockcode) throws Exception {
		// TODO Auto-generated method stub
		HttpFileCrawler.getFile(url, fos);
	}
	
	public void collection(String ...stockcodes) {
		// TODO Auto-generated method stub
		foreach(String sc : stockcodes){
			
		}

	}
	
	public void collection(String stockcode,Date date) {
		// TODO Auto-generated method stub
		
	}

}
