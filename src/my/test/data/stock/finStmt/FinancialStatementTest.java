package my.test.data.stock.finStmt;


import java.util.HashMap;

import my.context.MyContext;
import my.data.stock.StockMetaData;
import my.data.stock.finStmt.FinancialStatement;

import org.junit.Test;

public class FinancialStatementTest {

	@Test
	public void testCollectionString() throws Exception {
		HashMap<String, String> configs = new HashMap<String, String>();
		if(MyContext.StokCodeMetaFileURI==null ||MyContext.StokCodeMetaFileURI.isEmpty() ){
			configs = MyContext.getStockContext();
		}

		if(StockMetaData.STOCK_CODES == null || StockMetaData.STOCK_CODES.length < 2000){
			StockMetaData.getStockMetaData();
		}
		
		System.out.println("stock array lenght is "+StockMetaData.STOCK_CODES.length );
		
		FinancialStatement fs = new FinancialStatement(configs);
		//fs.collection(StockMetaData.STOCK_CODES);
		fs.collection();
		
		
	}

}
