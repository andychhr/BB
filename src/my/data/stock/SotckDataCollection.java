package my.data.stock;

import java.util.HashMap;


public class SotckDataCollection {
	protected String []_stockcodes;
	protected HashMap<String, String> _context;
	protected String _storeHomeDirContextName;
	
	public SotckDataCollection(String []stockcodes, HashMap<String, String> context, String storeHomeDirContextName){
		this._stockcodes = stockcodes;
		this._context = context;
		this._storeHomeDirContextName = storeHomeDirContextName;
	}

	//=========================================================================================================
	//Collection
	//=========================================================================================================
	
	//for multi thread collection 
	
	public static Integer _threadUnitsSize_Collection;	//total threads numbers
	int _threadSize = 100;
	
	
	
	public static void addNewThread(Integer _threadUnitsSize) {
        synchronized (_threadUnitsSize) {
        	_threadUnitsSize++;

        }
    }

    public static void removeCompletedThread(Integer _threadUnitsSize) {
        synchronized (_threadUnitsSize) {
        	_threadUnitsSize--;
        }
    }
    
    
    public static HashMap<String, String> _RESUBMIT_REQ;
    public static HashMap<String, String> _FAILED_REQ;
    
    
	@Override
	/**
	 * Collect for all stock codes with muti-threads
	 */
	public void collection() throws Exception {
		
		
		//
		SotckDataCollection._RESUBMIT_REQ = new HashMap<String, String>();
		SotckDataCollection._RESUBMIT_REQ.clear();
		SotckDataCollection._FAILED_REQ = new HashMap<String, String>();
		SotckDataCollection._FAILED_REQ.clear();
		
		//get all stock codes
		if(StockMetaData.getStockCodes() == null || StockMetaData.getStockCodes().length < 2000){
			StockMetaData.getStockMetaData();
		}
		
		//start collections for all stockcodes
		this.collection(StockMetaData.getStockCodes());
		
		
		//resubmit request if any failures
		if(SotckDataCollection._RESUBMIT_REQ.size() > 0 ){
			for(String xurl : SotckDataCollection._RESUBMIT_REQ.keySet() ){
				StockDataCollectionThread.getAndSaveContent(xurl,  SotckDataCollection._RESUBMIT_REQ.get(xurl));				
			}
		}
		
		
		//clear resource
		SotckDataCollection._RESUBMIT_REQ = null;
		
	}
	
	
	
	public void collection(String ...stockcodes) throws InterruptedException {
		//
		this._threadSize = Integer.parseInt(this._context.get("STOCK_CODES_NUM_PER_THREAD"));
		
		//
		SotckDataCollection._threadUnitsSize_Collection = 0;
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
			if (SotckDataCollection._threadUnitsSize_Collection > 0) {
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
		StockDataCollectionThread xThread = new <? extends StockDataCollectionThread>(sub_scs,this._context, this._storeHomeDirContextName);	//init thread
		new Thread(xThread).start();	//start thread
		
		//
		SotckDataCollection.addNewThread(SotckDataCollection._threadUnitsSize_Collection);
	}
}
