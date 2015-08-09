package my.context;

import java.util.HashMap;

import my.util.xml.MyXML;

public class MyContext {
	public static String projectConfigFile = "BB.xml";
	
	//project charset
	public static String Charset;
	
	
	
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
	
	//stock financial statements store home location 
	public static String FinanStmtStoreURI;
	
	
	//
	
	
	/**
	 * 
	 * @throws Exception
	 */
	public static void setContext() throws Exception{
		//check config file
		MyXML.validateXML(MyContext.projectConfigFile);

		/********************************
		 * for whole project
		 *********************************/
		MyContext.Charset = MyXML.getValByXpath(MyContext.projectConfigFile, "/BeatBear/charset").trim();
		
		/********************************
		 * for meta data
		 *********************************/
		//load config file content
		MyContext.StokCodeMetaFileURI = MyXML.getValByXpath(MyContext.projectConfigFile, "//stock/codeMetaFileURI").trim();
		
		/********************************
		 * for crawler
		 *********************************/
		//currentSource/service
		MyContext.CurrentDataSource = MyXML.getValByXpath(MyContext.projectConfigFile, "//dataSourceList/currentSource/service").trim();
		
		/********************************
		 * for financial statments
		 *********************************/
		//currentSource//finStmts/balanceStmt
		MyContext.BalanceStmtURI = MyXML.getValByXpath(MyContext.projectConfigFile, "//currentSource//finStmts/balanceStmt").trim();
		
		//currentSource//finStmts/incomeStmt
		MyContext.IncomeStmtURI = MyXML.getValByXpath(MyContext.projectConfigFile, "//currentSource//finStmts/incomeStmt").trim();
		
		//currentSource//finStmts/cashFlowStmt
		MyContext.CashFlowStmtURI = MyXML.getValByXpath(MyContext.projectConfigFile, "//currentSource//finStmts/cashFlowStmt").trim();
		
		//currentSource//finStmts//summary
		MyContext.SummayStmtURI = MyXML.getValByXpath(MyContext.projectConfigFile, "//currentSource//finStmts//summary").trim();
		
		//currentSource//finStmts//keyIndex
		MyContext.KeyIndexStmtURI = MyXML.getValByXpath(MyContext.projectConfigFile, "//currentSource//finStmts//keyIndex").trim();
		
		//currentSource//finStmts//profit
		MyContext.ProfitStmtURI= MyXML.getValByXpath(MyContext.projectConfigFile, "//currentSource//finStmts//profit").trim();
		
		//currentSource//finStmts//payback
		MyContext.PaybackStmtURI= MyXML.getValByXpath(MyContext.projectConfigFile, "//currentSource//finStmts//payback").trim();
		
		//currentSource//finStmts//growth
		MyContext.GrowthStmtURI= MyXML.getValByXpath(MyContext.projectConfigFile, "//currentSource//finStmts//growth").trim();
		
		//currentSource//finStmts//operation
		MyContext.OperationStmtURI= MyXML.getValByXpath(MyContext.projectConfigFile, "//currentSource//finStmts//operation").trim();
		
		//stock//finStmts//finStmtStoreURI
		MyContext.FinanStmtStoreURI= MyXML.getValByXpath(MyContext.projectConfigFile, "//stock//finStmts//finStmtStoreURI").trim();
		
		
		//Context.
	}
	
	
	public static HashMap<String,String> getStockContext() throws Exception{
		//set context
		MyContext.setContext();
		
		//
		HashMap<String,String> hmConfigs = new HashMap<String,String>();
		hmConfigs.clear();
		
		//
		hmConfigs.put("StokCodeMetaFileURI", MyContext.StokCodeMetaFileURI);
		hmConfigs.put("CurrentDataSource", MyContext.CurrentDataSource);
		hmConfigs.put("BalanceStmtURI", MyContext.BalanceStmtURI);
		hmConfigs.put("IncomeStmtURI", MyContext.IncomeStmtURI);
		hmConfigs.put("CashFlowStmtURI", MyContext.CashFlowStmtURI);
		hmConfigs.put("SummayStmtURI", MyContext.SummayStmtURI);
		hmConfigs.put("KeyIndexStmtURI", MyContext.KeyIndexStmtURI);
		hmConfigs.put("ProfitStmtURI", MyContext.ProfitStmtURI);
		hmConfigs.put("PaybackStmtURI", MyContext.PaybackStmtURI);
		hmConfigs.put("GrowthStmtURI", MyContext.GrowthStmtURI);
		hmConfigs.put("OperationStmtURI", MyContext.OperationStmtURI);
		hmConfigs.put("FinanStmtStoreURI", MyContext.FinanStmtStoreURI);
		
		
		//return
		return hmConfigs;
		
		
	}
}