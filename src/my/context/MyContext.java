package my.context;

import java.util.HashMap;
import java.io.File;

import my.util.xml.MyXML;

public class MyContext {

	private final static MyContext instance = new MyContext();
	private static boolean initialized = false;

	private MyContext() {

	}

	private void init() throws Exception {
		setContext();
	}

	public static synchronized MyContext getInstance() throws Exception {
		if (initialized)
			return instance;
		instance.init();
		initialized = true;
		return instance;
	}

	// ============================================
	// project config file
	// ============================================

	public static final String projectConfigFile = "./BB.xml";

	public static String WORKSAPCE_DIR;
	public static String DATA_DIR;

	// ============================================
	// project level config
	// ============================================
	// project charset
	public static String Charset;

	// ============================================
	// stock config
	// ============================================

	// stock configs
	public static String StokCodeMetaFileURI;

	// stock data source
	public static String CurrentDataSource;

	// ---------------------------
	// stock financial statements

	//
	public static String STOCK_CODES_NUM_PER_THREAD; // need conver to int

	//
	public static String FIN_STMT_STORE_MAPPING_FILE;

	// balanceStmt
	public static String BalanceStmtURI;

	// incomeStmt
	public static String IncomeStmtURI;

	// cashFlowStmt
	public static String CashFlowStmtURI;

	// summary 财务报表摘要
	public static String SummayStmtURI;

	// 主要财务指标
	public static String KeyIndexStmtURI;

	// 盈利能力
	public static String ProfitStmtURI;

	// 偿还能力
	public static String PaybackStmtURI;

	// 成长能力
	public static String GrowthStmtURI;

	// 营运能力
	public static String OperationStmtURI;

	// stock financial statements store home location
	public static String FinanStmtStoreURI;

	//

	/**
	 * 
	 * @throws Exception
	 */
	private static void setContext() throws Exception {

		// check config file
		MyXML.validateXML(MyContext.projectConfigFile);

		/********************************
		 * for whole project
		 *********************************/
		MyContext.Charset = MyXML.getTextByXpath(MyContext.projectConfigFile,"/BeatBear/charset").trim();

		MyContext.WORKSAPCE_DIR = (new File("")).getAbsoluteFile().getParent();
		MyContext.DATA_DIR = WORKSAPCE_DIR + "/data/";

		/********************************
		 * for meta data
		 *********************************/
		// load config file content
		MyContext.StokCodeMetaFileURI = MyContext.WORKSAPCE_DIR + MyXML.getTextByXpath(MyContext.projectConfigFile,"//stock/codeMetaFileURI").trim();

		/********************************
		 * for crawler
		 *********************************/
		// currentSource/service
		MyContext.CurrentDataSource = MyXML.getTextByXpath(MyContext.projectConfigFile,"//dataSourceList/currentSource/service").trim();

		/********************************
		 * for financial statments
		 *********************************/
		setCrawlerURL_FinStmts();
		
		
		

		// stock//finStmts//MultiThread/StockCodesNumPerThread
		MyContext.STOCK_CODES_NUM_PER_THREAD = MyXML.getTextByXpath(MyContext.projectConfigFile,"//stock//finStmts//MultiThread/StockCodesNumPerThread").trim();

		// stock//finStmts//FinStmtStoreMappingFile
		MyContext.FIN_STMT_STORE_MAPPING_FILE = MyContext.WORKSAPCE_DIR+ MyXML.getTextByXpath(MyContext.projectConfigFile,"//stock//finStmts//FinStmtStoreMappingFile").trim();
	}

	
	
	/********************************
	 * for financial statments
	 * 
	 * @throws Exception
	 *********************************/
	private static void setCrawlerURL_FinStmts() throws Exception {

		// currentSource//finStmts/balanceStmt
		MyContext.BalanceStmtURI = MyXML.getTextByXpath(
				MyContext.projectConfigFile,
				"//currentSource//finStmts/balanceStmt").trim();

		// currentSource//finStmts/incomeStmt
		MyContext.IncomeStmtURI = MyXML.getTextByXpath(MyContext.projectConfigFile,
				"//currentSource//finStmts/incomeStmt").trim();

		// currentSource//finStmts/cashFlowStmt
		MyContext.CashFlowStmtURI = MyXML.getTextByXpath(
				MyContext.projectConfigFile,
				"//currentSource//finStmts/cashFlowStmt").trim();

		// currentSource//finStmts//summary
		MyContext.SummayStmtURI = MyXML.getTextByXpath(
				MyContext.projectConfigFile,
				"//currentSource//finStmts//summary").trim();

		// currentSource//finStmts//keyIndex
		MyContext.KeyIndexStmtURI = MyXML.getTextByXpath(
				MyContext.projectConfigFile,
				"//currentSource//finStmts//keyIndex").trim();

		// currentSource//finStmts//profit
		MyContext.ProfitStmtURI = MyXML.getTextByXpath(
				MyContext.projectConfigFile,
				"//currentSource//finStmts//profit").trim();

		// currentSource//finStmts//payback
		MyContext.PaybackStmtURI = MyXML.getTextByXpath(
				MyContext.projectConfigFile,
				"//currentSource//finStmts//payback").trim();

		// currentSource//finStmts//growth
		MyContext.GrowthStmtURI = MyXML.getTextByXpath(
				MyContext.projectConfigFile,
				"//currentSource//finStmts//growth").trim();

		// currentSource//finStmts//operation
		MyContext.OperationStmtURI = MyXML.getTextByXpath(
				MyContext.projectConfigFile,
				"//currentSource//finStmts//operation").trim();

		
		
		
		
		// stock//finStmts//finStmtStoreURI
		MyContext.FinanStmtStoreURI = MyContext.WORKSAPCE_DIR + MyXML.getTextByXpath(
				MyContext.projectConfigFile,
				"//stock//finStmts//finStmtStoreURI").trim();

	}

	public static HashMap<String, String> getStockContext() throws Exception {
		// set context
		//MyContext.setContext();

		//
		HashMap<String, String> hmConfigs = new HashMap<String, String>();
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

		//
		hmConfigs.put("STOCK_CODES_NUM_PER_THREAD",
				MyContext.STOCK_CODES_NUM_PER_THREAD);
		//
		hmConfigs.put("FIN_STMT_STORE_MAPPING_FILE",
				MyContext.FIN_STMT_STORE_MAPPING_FILE);

		// return
		return hmConfigs;
	}

}