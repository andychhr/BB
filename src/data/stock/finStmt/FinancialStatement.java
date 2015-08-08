package data.stock.finStmt;

import java.io.FileOutputStream;
import java.util.Date;

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
	
	
	public void collection(String stockcode, String url, FileOutputStream fos) {
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
