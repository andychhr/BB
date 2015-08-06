package data.stock;

import java.util.ArrayList;
import java.util.Date;

import data.MetaData;
import data.Analysis;

public class Stock implements MetaData, Analysis {
	public String _code;
	public ArrayList<FinancialStatement> _finStmts;
	public ArrayList<DailyTrade> _dailyTrades;
	public ArrayList<Products> _products;
	
	@Override
	public void collection() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void collection(Date date) {
		// TODO Auto-generated method stub
		
	}
}
