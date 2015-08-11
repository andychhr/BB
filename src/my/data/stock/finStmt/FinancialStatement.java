package my.data.stock.finStmt;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import my.context.MyContext;
import my.crawler.apache.http.HttpFileCrawler;
import my.data.MetaData;
import my.data.stock.StockMetaData;
import my.util.file.MyFile;


public class FinancialStatement implements MetaData {
	
	//
	HashMap<String, String> _context;
	
	HashMap<String,String> _crwalerFailedDueToHttpIssueReocrds;
	
	// =========================================================================================================
	// Contructors
	// =========================================================================================================
	
	/**
	 * for all stock codes' financial statements
	 * @param context
	 */
	public FinancialStatement(HashMap<String, String> context) {
		this._context = new HashMap<String, String>();
		this._context.clear();
		
		this._context = context;
	}

	
	/**
	 * For single stock code financial statements
	 * @param stockcode
	 * @param context
	 */
	public FinancialStatement(String stockcode, HashMap<String, String> context) {
		this._context = new HashMap<String, String>();
		this._context.clear();
		
		this._context = context;
	}

	
	

	//=========================================================================================================
	//Collection
	//=========================================================================================================
	
	//for multi thread collection 
	
	public static Integer _threadUnitsSize;	//total threads numbers
	int _threadSize = Integer.parseInt(_context.get("STOCK_CODES_NUM_PER_THREAD"));
	
	

    public static void addNewThread() {
        synchronized (FinancialStatement._threadUnitsSize) {
        	FinancialStatement._threadUnitsSize++;

        }
    }

    public void removeCompletedThread() {
        synchronized (FinancialStatement._threadUnitsSize) {
        	FinancialStatement._threadUnitsSize--;
        }
    }
    
    
    public static HashMap<String, String> RequestNeedToBeResubmit;
    public static HashMap<String, String> FailedRequestNeedToBeReviewed;
    
    
	@Override
	/**
	 * Collect for all stock codes with muti-threads
	 */
	public void collection() throws Exception {
		//
		FinancialStatement.RequestNeedToBeResubmit = new HashMap<String, String>();
		FinancialStatement.RequestNeedToBeResubmit.clear();
		FinancialStatement.FailedRequestNeedToBeReviewed = new HashMap<String, String>();
		FinancialStatement.FailedRequestNeedToBeReviewed.clear();
		
		//get all stock codes
		if(StockMetaData.STOCK_CODES == null || StockMetaData.STOCK_CODES.length < 2000){
			StockMetaData.getStockMetaData();
		}
		
		//start collections for all stockcodes
		this.collection(StockMetaData.STOCK_CODES);
		
		
		//resubmit request if any failures
		if(FinancialStatement.RequestNeedToBeResubmit.size() > 0 ){
			for(String xurl : FinancialStatement.RequestNeedToBeResubmit.keySet() ){
				FinancialStatementsCollectionThread.getAndSaveContent(xurl,  FinancialStatement.RequestNeedToBeResubmit.get(xurl));				
			}
		}
		
		
		//clear resource
		FinancialStatement.RequestNeedToBeResubmit = null;
		
	}
	
	
	
	public void collection(String ...stockcodes) throws InterruptedException {
		//
		String[] sub_scs = new String[this._threadSize];
		for (int i = 0; i < stockcodes.length; i++) {
			// add stock codes into sub stock codes
			sub_scs[i % this._threadSize] = stockcodes[i];
			//
			if (i % this._threadSize == 0) {
				if (i > 0) {
					this.luanchThread(sub_scs); //luanch thread
				}
				sub_scs = new String[this._threadSize];
			}

			if ((i % this._threadSize != 0 && i == stockcodes.length - 1)
					|| (i == 0 && i == stockcodes.length - 1)) { // reach the end
				this.luanchThread(sub_scs);	//luanch thread
			}
		}

		//
		int loopTimes = 120;
		while (loopTimes > 0) {
			if (FinancialStatement._threadUnitsSize > 0) {
				Thread.sleep(1 * 60 * 1000); // every 1 min check once
				loopTimes--;
			} else {
				loopTimes = -1; // exit loop
			}
		}

		// ----------------------------------------------------------
		System.out.println("Done");
	}
	
	public void luanchThread(String ...sub_scs){
		FinancialStatementsCollectionThread xThread = new FinancialStatementsCollectionThread(sub_scs,this._context);	//init thread
		new Thread(xThread).start();	//start thread
	}
	
	
	
	
	
	
	//=========================================================================================================
		

	@Override
	public void collection(Date date) {
		// TODO Auto-generated method stub

	}
	
	
	
	
	public void collection(String stockcode,Date date) {
		// TODO Auto-generated method stub
		
	}   
	
	
	//=========================================================================================================
    
	


}
