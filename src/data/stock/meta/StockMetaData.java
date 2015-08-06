package data.stock.meta;

import java.util.Date;

public interface StockMetaData {
	public String _code;
	
	/****************************************************************************
	 * 
	 * data collection
	 * 
	 *****************************************************************************/
	/**
	 * 
	 */
	public void collection();
	
	/**
	 * 
	 * @param date
	 */
	public void collection(Date date);
	
	
	/****************************************************************************
	 * 
	 * data analysis
	 * 
	 *****************************************************************************/
	
}