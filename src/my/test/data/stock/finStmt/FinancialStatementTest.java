package my.test.data.stock.finStmt;


import java.util.HashMap;

import my.context.MyContext;
import my.data.stock.StockMetaData;
import my.data.stock.finStmt.FinStmtDataObj;
import my.data.stock.finStmt.FinancialStatement;
import my.data.stock.finStmt.FinancialStatementCollection;

import org.junit.Test;

public class FinancialStatementTest {
	
	@Test
	public void testFinStmtFieldsVal() throws Exception {
		FinancialStatement fs = new FinancialStatement();
		FinancialStatement.getAndSaveFinStmtFieldValues("600036", FinancialStatement._context.get("FinanStmtStoreURI"));
		
	}

	/*
	@Test
	public void testCollectionString() throws Exception {
//		
//		HashMap<String, String> configs = new HashMap<String, String>();
//		if(MyContext.StokCodeMetaFileURI==null ||MyContext.StokCodeMetaFileURI.isEmpty() ){
//			MyContext.getInstance();
//			configs = MyContext.getStockContext();
//		}
//
//		if(StockMetaData.getStockCodes() == null || StockMetaData.getStockCodes().length < 2000){
//			StockMetaData.getInstance();
//		}
//		
//		System.out.println("stock array lenght is "+StockMetaData.getStockCodes().length );
//		
//		FinancialStatement fs = new FinancialStatement(configs);
//		//fs.collection(StockMetaData.STOCK_CODES);
//		fs.collection();
//		
//		
		
		FinStmtDataObj obj = new FinStmtDataObj();
//		String[] scs = FinStmtDataObj.getStockCodes();
//		HashMap<String,String> scContext = FinStmtDataObj.getStockContext(); 
		FinancialStatementCollection fsc = new FinancialStatementCollection(obj);
		fsc.collection();
		
	}
	
	
	*/
	
	

}
