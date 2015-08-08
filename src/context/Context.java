package context;

import java.util.HashMap;

import util.xml.MyXML;

public class Context {
	public static String projectConfigFile = "BB.xml";
	
	//stock configs
	public static String StokCodeMetaFileURI;
	
	//stock data source
	public static String CurrentDataSource;
	
	//balanceStmt
	public static String BalanceStmtURI;
	
	//incomeStmt
	public static String IncomeStmtURI;
	
	//cashFlowStmt
	public static String CashFlowStmtURI;
	
	//summary 财务报表摘要
	public static String SummayStmtURI;
	
	//主要财务指标
	public static String KeyIndexStmtURI;
	
	//盈利能力
	public static String ProfitStmtURI;
	
	//偿还能力
	public static String PaybackStmtURI;
	
	//成长能力
	public static String GrowthStmtURI;
	
	//营运能力
	public static String OperationStmtURI;
	
	//
	
	//
	
	
	/**
	 * 
	 * @throws Exception
	 */
	public static void setContext() throws Exception{
		//check config file
		MyXML.validateXML(Context.projectConfigFile);
		
		/********************************
		 * for meta data
		 *********************************/
		//load config file content
		Context.StokCodeMetaFileURI = MyXML.getValByXpath(Context.projectConfigFile, "//stock/codeMetaFileURI").trim();
		
		/********************************
		 * for crawler
		 *********************************/
		//currentSource/service
		Context.CurrentDataSource = MyXML.getValByXpath(Context.projectConfigFile, "//dataSourceList/currentSource/service").trim();
		
		/********************************
		 * for financial statments
		 *********************************/
		//currentSource//finStmts/balanceStmt
		Context.BalanceStmtURI = MyXML.getValByXpath(Context.projectConfigFile, "//currentSource//finStmts/balanceStmt").trim();
		
		//currentSource//finStmts/incomeStmt
		Context.IncomeStmtURI = MyXML.getValByXpath(Context.projectConfigFile, "//currentSource//finStmts/incomeStmt").trim();
		
		//currentSource//finStmts/cashFlowStmt
		Context.CashFlowStmtURI = MyXML.getValByXpath(Context.projectConfigFile, "//currentSource//finStmts/cashFlowStmt").trim();
		
		//currentSource//finStmts//summary
		Context.SummayStmtURI = MyXML.getValByXpath(Context.projectConfigFile, "//currentSource//finStmts//summary").trim();
		
		//currentSource//finStmts//keyIndex
		Context.KeyIndexStmtURI = MyXML.getValByXpath(Context.projectConfigFile, "//currentSource//finStmts//keyIndex").trim();
		
		//currentSource//finStmts//profit
		Context.ProfitStmtURI= MyXML.getValByXpath(Context.projectConfigFile, "//currentSource//finStmts//profit").trim();
		
		//currentSource//finStmts//payback
		Context.PaybackStmtURI= MyXML.getValByXpath(Context.projectConfigFile, "//currentSource//finStmts//payback").trim();
		
		//currentSource//finStmts//growth
		Context.GrowthStmtURI= MyXML.getValByXpath(Context.projectConfigFile, "//currentSource//finStmts//growth").trim();
		
		//currentSource//finStmts//operation
		Context.OperationStmtURI= MyXML.getValByXpath(Context.projectConfigFile, "//currentSource//finStmts//operation").trim();
		
		//Context.
		
		//Context.
	}
	
	
	public HashMap<String,String> getStockContext() throws Exception{
		//set context
		Context.setContext();
		
		//
		HashMap<String,String> hmConfigs = new HashMap<String,String>();
		hmConfigs.clear();
		
		
		
	}
}