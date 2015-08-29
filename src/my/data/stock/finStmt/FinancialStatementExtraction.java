package my.data.stock.finStmt;

import my.data.stock.StockDataPreprocess;

public class FinancialStatementExtraction 
	extends StockDataPreprocess<FinStmtDataObj> {

		
		
		public FinancialStatementExtraction(FinStmtDataObj stockDataObject) throws Exception {
			super(stockDataObject);
		}
		
		public void extact(){
			try {
				this.preprocess("extract");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
