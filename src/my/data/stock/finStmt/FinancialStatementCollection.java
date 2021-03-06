package my.data.stock.finStmt;

import my.data.stock.StockDataPreprocess;


public class FinancialStatementCollection extends StockDataPreprocess<FinStmtDataObj> {

	
	
	public FinancialStatementCollection(FinStmtDataObj stockDataObject) throws Exception {
		super(stockDataObject);
	}

	
	public void collection(){
		try {
			this.preprocess("collect");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void extract(){
		try {
			this.preprocess("extract");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
