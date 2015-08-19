package my.data.stock.finStmt;

import my.context.MyContext;
import my.data.stock.StockMetaData;

public class FinStmtDataObj extends StockMetaData {
	
	
	
	private String _FinStmt_STORE_HOME_DIR;
	
	
	
	public FinStmtDataObj() throws Exception{
		super.getInstance();
		this.setLocalStoreHomeDir();
	}
	
	

	
	
	
	@Override
	public void setLocalStoreHomeDir() throws Exception{
		MyContext.getInstance();
		this._FinStmt_STORE_HOME_DIR = MyContext.getStockContext().get("FinanStmtStoreURI");
	}
	
	
	@Override
	public String getLocalStoreHomeDir(){
		return this._FinStmt_STORE_HOME_DIR;
	}
	
	
	
	
	
}
