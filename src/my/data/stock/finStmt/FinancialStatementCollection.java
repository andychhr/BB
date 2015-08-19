package my.data.stock.finStmt;

import java.util.HashMap;
import java.util.Map;

import my.data.stock.SotckDataCollection;
import my.data.stock.StockMetaData;

public class FinancialStatementCollection extends
		SotckDataCollection<FinStmtDataObj> {

	
	
	public FinancialStatementCollection(FinStmtDataObj stockDataObject) {
		super(stockDataObject);
	}

	
	
	
	
	@Override
	protected Map<String, String> getURL_File(String stockcode) throws Exception {
		//
		HashMap<String, String> url_file = new HashMap<String,String>();
		url_file.clear();
		
		
		StockMetaData.getInstance();
		//get template URLs
		for(String key : StockMetaData.getStockContext().keySet()){
			if(key.contains("StmtURI")){
				String xStmtURI = StockMetaData.getStockContext().get(key);	//get x statement URIs
				xStmtURI=xStmtURI.replace("$sc$", stockcode).trim();	//replace "$sc$" to real stock code
				String xURL = StockMetaData.getStockContext().get("CurrentDataSource") + xStmtURI;	// get real statement url
				System.out.println("xURL for stockcode:"+stockcode + "is " + xURL);
				
				//get local file absolute path
				String xStmtLocalFilePath = this._STOCK_META_DATA_OBJ.getLocalStoreHomeDir() + "/" + this.getStmtName(key) + ".csv"; // get file abs location
				
				url_file.put(xURL, xStmtLocalFilePath);
				//get content via http and save file into local files
				//getAndSaveContent(xURL, xStmtLocalFilePath);
			}
		}	
		
		//
		return url_file;
	}
	
	
	
	
	
	// ===============================================================
	// ============================================================

		public String getStmtName(String oriStmtName) {
			int x = oriStmtName.indexOf("Stmt");
			return oriStmtName.substring(0, x);
		}

}
