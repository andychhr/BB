package my.test.data.stock.finStmt;


import org.junit.Test;

import my.data.stock.finStmt.FinStmtDataObj;
import my.data.stock.finStmt.FinancialStatement;
import my.data.stock.finStmt.FinancialStatementCollection;
import my.data.stock.finStmt.FinancialStatementExtraction;

public class FinancialStatementTest {
	
	/*
	@Test
	public void testExtract() throws Exception {
		FinStmtDataObj obj = new FinStmtDataObj();
		FinancialStatementExtraction fse = new FinancialStatementExtraction(obj);
		fse.extact();
	}
		
*/
	@Test
	public void testGetFieldValues() throws Exception{
		String sc = "600036";
		FinStmtDataObj obj = new FinStmtDataObj();
		obj.getFieldValuesFromXML(sc, "净利润(万元)");
	}

	
	
	
	/*
	@Test
	public void testCreateFinStmtMapFile() throws Exception {
		FinStmtDataObj fs = new FinStmtDataObj();
		FinStmtDataObj.createFinStmtMapFile(false );
	}
	*/
	
	
	
	
	/*
	@Test
	public void testFinStmtFieldsVal() throws Exception {
		FinStmtDataObj obj = new FinStmtDataObj();
		obj.extract("000001");
		//FinStmtDataObj.getAndSaveFinStmtFieldValues("000001", FinancialStatement._context.get("FinanStmtStoreURI"));
		
	}
	*/

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
