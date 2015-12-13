package my.test.data.stock.finStmt;

import my.data.stock.finStmt.FinStmtDataObj;
import my.data.stock.finStmt.FinancialStatementCollection;

import org.junit.Test;


public class FinStmtCollectionTest {


	
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
	
	

}
