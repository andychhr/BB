package my.data.stock.finStmt;



import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;





import my.context.MyContext;
import my.data.Analysis;
import my.data.MetaData;
import my.util.file.MyFile;
import my.util.xml.MyXML;



public class FinancialStatement implements MetaData, Analysis {
	
	//
	public static HashMap<String, String> _context;
	
	HashMap<String,String> _crwalerFailedDueToHttpIssueReocrds;
	
	// =========================================================================================================
	// Contructors
	// =========================================================================================================
	
	public FinancialStatement() throws Exception{
		FinancialStatement._context = new HashMap<String, String>();
		FinancialStatement._context.clear();
		
		MyContext.getInstance();
		FinancialStatement._context = MyContext.getStockContext();
	}
	
	/**
	 * for all stock codes' financial statements
	 * @param context
	 */
	public FinancialStatement(HashMap<String, String> context) {
		FinancialStatement._context = new HashMap<String, String>();
		FinancialStatement._context.clear();
		
		FinancialStatement._context = context;
	}

	
	/**
	 * For single stock code financial statements
	 * @param stockcode
	 * @param context
	 */
	public FinancialStatement(String stockcode, HashMap<String, String> context) {
		FinancialStatement._context = new HashMap<String, String>();
		FinancialStatement._context.clear();
		
		FinancialStatement._context = context;
	}
	
	
	
	
	// =========================================================================================================
	// Contructors
	// =========================================================================================================

	public static String getLocalFinStmtsStoreHomeDir() throws Exception{
		String LocalFinStmtsStoreHomeDir =  FinancialStatement._context.get("FinanStmtStoreURI").trim();
		if(MyFile.validateDirectory(LocalFinStmtsStoreHomeDir)){
			return LocalFinStmtsStoreHomeDir;
		}else{
			throw new Exception("this._context.get(\"FinanStmtStoreURI\").trim(); value is null or empty, pls. check this._context has value or not!");
		}
	}
	
	
	

	
	
	/***************************************************************************
	 * Collect financial statements data from web and save data into xls files
	 * 
	 *************************************************************************/
	//for multi thread collection 
	public void collect() throws Exception{
		FinStmtDataObj obj = new FinStmtDataObj();
		FinancialStatementCollection fsc = new FinancialStatementCollection(obj);
		fsc.collection();
	}
	
	/***************************************************************************
	 * Extract data from xls files into xml file for each stock
	 * @throws Exception 
	 * 
	 *************************************************************************/
	
	public void extract() throws Exception{
		FinStmtDataObj obj = new FinStmtDataObj();
		FinancialStatementExtraction fse = new FinancialStatementExtraction(obj);
		fse.extact();
	}
	
	
	/***************************************************************************
	 * 
	 * 
	 *************************************************************************/
	
	
	//=========================================================================================================
	// =========================================================================================================
	// create Mapping file
	// =========================================================================================================
//	public File getMappingFile() throws Exception{
//		if(MyContext.FIN_STMT_STORE_MAPPING_FILE == null || MyContext.FIN_STMT_STORE_MAPPING_FILE.isEmpty()){
//			MyContext.getInstance();
//		}
//
//		if (MyFile.validateFile(MyContext.FIN_STMT_STORE_MAPPING_FILE)) {
//			return new File(MyContext.FIN_STMT_STORE_MAPPING_FILE);
//		} else {
//			throw new Exception("MyContext.FIN_STMT_STORE_MAPPING_FILE is not exists, pls. check if file is there");
//		}
//	}
//	
//	public void writeToMappingFile(File mapFile) throws Exception{
//		//validate map file path is correct
//		if(mapFile!=null && MyXML.validateDirectory(mapFile.getAbsolutePath())){
//			
//			//get financial statements local store home dir
//			String finStmtsLocalStoreDirPath = FinancialStatement.getLocalFinStmtsStoreHomeDir();
//			
//			//validate this dir
//			if(MyFile.validateDirectory(finStmtsLocalStoreDirPath)){
//				
//				//get docs into org.dom4j.Document object
//				org.dom4j.Document doc = this.getDocList(finStmtsLocalStoreDirPath);
//				
//				//write to map xml file 
//				MyXML.writeToFile(doc, mapFile.getAbsolutePath());
//			}
//		}
//	}
	
	
//	public org.dom4j.Document getDocList(String finanStmtsLocalStoreHomeDirPath) throws Exception{
//		//add root element
//		org.dom4j.Document doc = org.dom4j.DocumentHelper.createDocument();
//		org.dom4j.Element root = doc.addElement("FinancialStatements");
//		
//		//add child elements
//		if(MyFile.validateDirectory(finanStmtsLocalStoreHomeDirPath)){
//			File finanStmtsLocalStoreHomeDir = new File(finanStmtsLocalStoreHomeDirPath);
//			
//			//Stock codes' level dirs
//			File[] scDirs = finanStmtsLocalStoreHomeDir.listFiles();	//stock codes' dirs
//			for(File xscDir : scDirs){
//				//ADD element for stock code level
//				org.dom4j.Element e_sc = root.addElement("stock")
//						.addAttribute("code", xscDir.getName())
//						.addAttribute("path", xscDir.getAbsolutePath());
//				
//				if(MyFile.validateDirectory(xscDir.getAbsolutePath())){
//					File[] stmtsFiles = xscDir.listFiles();
//					for(File xStmt : stmtsFiles){
//						e_sc.addElement(xStmt.getName()).addText(xStmt.getAbsolutePath());
//					}
//				}
//			}
//		}
//		
//		return doc;
//	}
//	
//	
//	public HashMap<String, HashMap<String, String>> loadMapFileToHashMap(String mapFilePath) throws Exception{
//		//return value
//		 HashMap<String, HashMap<String, String>> hmMap = new  HashMap<String, HashMap<String, String>>();
//		 hmMap.clear();
//		 
//		 //
//		if(mapFilePath!=null && MyXML.validateDirectory(mapFilePath)){
//			Document document = null;
//			try {
//				SAXReader saxReader = new SAXReader(); // 
//				document = saxReader.read(mapFilePath); // 
//				List<? extends Node> list = document.selectNodes("//stock");	//get node by xpath 
//				Iterator<?> iter_sc = list.iterator();	// stock code level iterator
//				while (iter_sc.hasNext()) {	// loop to get all elements' attribute
//					Element xel = (Element) iter_sc.next();
//					String xAttrVal = xel.attributeValue("code");	// get stock code
//					if(xAttrVal == null){
//						throw new Exception("Element ://stockcode cannot find attribute: stockcode");
//					}else if(StockMetaData.validateStockCode(xAttrVal))	{	// attributes value is a stock code
//						Iterator<?> iter_stmt = xel.elementIterator();	//statment level iterator
//						 HashMap<String, String> xSC_stmts = new  HashMap<String, String> ();
//						while (iter_sc.hasNext()) {
//							Element xStmtElement = (Element) iter_sc.next();
//							String xStmtName = xStmtElement.getName();
//							String xStmtFilePath = xStmtElement.getTextTrim();
//							xSC_stmts.put(xStmtName, xStmtFilePath);
//						}
//						
//						hmMap.put(xAttrVal, xSC_stmts);
//					}
//						
//				
//					
//				}
//				
//				//clear resource
//				list = null;
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			} finally {
//				document = null;
//			}
//		}
//		
//		
//		return hmMap;
//	}


	
	// =========================================================================================================
	// =========================================================================================================
	// Analysis meta data
	// =========================================================================================================
	
	
	
	
	
	
	
	
//	public static HashMap<String, String[]> getFinStmtFields(String sc,
//			String localStoreHomeDir) throws Exception {
//
//		HashMap<String, String[]> fieldsValues = new HashMap<String, String[]>();
//		//
//		HashMap<String, String> contents = readFinStmtFilesContent(sc,
//				localStoreHomeDir);
//		for (String xField : contents.keySet()) {
//			String longStr = contents.get(xField);
//			String[] values = longStr.split(",");
//			//
//			fieldsValues.put(xField, values);
//		}
//
//		return fieldsValues;
//	}
	
	
	
	
	
	

	@Override
	public void analysis() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collection(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collection() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	


}
