package my.data.stock.finStmt;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import my.context.MyContext;
import my.data.Analysis;
import my.data.MetaData;
import my.data.stock.StockMetaData;
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
	
	
	

	
	

	//=========================================================================================================
	//Collection
	//=========================================================================================================
	
	//for multi thread collection 
	public void collection() throws Exception{
		FinStmtDataObj obj = new FinStmtDataObj();
		FinancialStatementCollection fsc = new FinancialStatementCollection(obj);
		fsc.collection();
	}
	
	
	
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
	
	
	public static void createFinStmtMapFile(boolean needToUpdateMapFile) throws Exception{
		if (needToUpdateMapFile) {
			
			HashMap<String, String[]> finContents = readFinStmtFilesContent(
					"600036",
					FinancialStatement._context.get("FinanStmtStoreURI"));
			
			String classFileContent = "package my.data.stock.finStmt;\n"
					+ "import java.util.HashMap;\n"
					+ "public class FinancialStatementFieldsMap {\n"
					+ "private final static FinancialStatementFieldsMap instance = new FinancialStatementFieldsMap(); \n"
					+ "private static boolean initialized = false;	\n"
					+ "private static HashMap<String,String> FieldsMap; \n"
					+ "private FinancialStatementFieldsMap(){} \n"
					+ "public static FinancialStatementFieldsMap getInstance(){ \n"
					+ "if (initialized) return instance; \n"
					+ "instance.init(); \n"
					+ "initialized = true;	\n"
					+ "return instance;	\n"
					+ "public static HashMap<String,String> getFieldsMap(){	\n"
					+ "return FinancialStatementFieldsMap.FieldsMap;	\n"
					+ "}	\n"
					+ "private void init(){	\n"
					+ "if(!initialized){	\n"
					+ "FinancialStatementFieldsMap.FieldsMap = new HashMap<String,String>(); \n";

			int ii = 0;
			for (String xFields : finContents.keySet()) {
				String xkey = "\"" + xFields.trim() + "\"";
				String xval = "\"f" + (ii++) + "\"";
				classFileContent += "FinancialStatementFieldsMap.FieldsMap.put("
						+ xkey + "," + xval + ");\n";
			}

			classFileContent = classFileContent + "\n}\n}\n}";

			// write into local file system
			MyFile.WriteStringToFile("FinancialStatementFieldsMap.java",
					classFileContent);
		}
	}
	
	
	
	
	
	public static void getAndSaveFinStmtFieldValues(String sc,
			String localStoreHomeDir) throws Exception {
		// init fields map file
		FinancialStatementFieldsMap.getInstance();

		//get fields and their values
		HashMap<String, String[]> finStmtFieldValues = readFinStmtFilesContent(sc, localStoreHomeDir);
		 
		// create root elements
		org.dom4j.Document doc = org.dom4j.DocumentHelper.createDocument();
		org.dom4j.Element root = doc.addElement("stock").addAttribute("code",sc);

		String[] dateField = finStmtFieldValues.get("报告日期");
		int len = dateField.length;
		for (int i = 0; i < len; i++) {
			org.dom4j.Element el_datex = root.addElement("rptdate")
					.addAttribute("date", dateField[i]);
			for (String xField : finStmtFieldValues.keySet()) {
				if (xField.equals("报告日期")) {
					continue;
				} else {
					String elementName = FinancialStatementFieldsMap.getFieldsMap().get(xField);
					String []xfieldVals = finStmtFieldValues.get(xField);
					String elementVal ="";
					try{
						elementVal = getStr(xfieldVals[i]);
					}catch(java.lang.ArrayIndexOutOfBoundsException outOfBoundEx){
						elementVal ="--";
					}
					
				

					el_datex.addElement(elementName).addText(elementVal);
				}
			}
		}
		 
		 
		 //write to local file
		 MyXML.writeToFile(doc, localStoreHomeDir+"/"+sc + "/"+sc+".xml");
		 
	     
	}
	
	public static String getStr(String xstr){
        return (xstr==null) ? "":xstr;
    }
	
	
	
	
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
	
	
	
	public static HashMap<String, String[]> readFinStmtFilesContent(String sc,
			String localStoreHomeDir) throws Exception {

		HashMap<String, String[]> fields = new HashMap<String, String[]>();
		fields.clear();

		Map<String, String> files = getFinStmtFileList(sc, localStoreHomeDir);
		for (String xFile : files.keySet()) {
			if(!xFile.endsWith(".csv")){
				continue;
			}
			String xContent = MyFile.readFileToString(files.get(xFile),
					Charset.forName(MyContext.Charset));
			fields = getFieldsValue(xContent, fields);
		}

		return fields;
	}
	
	
	
	public static Map<String,String> getFinStmtFileList(String sc, String localStoreHomeDir) throws Exception{
		return MyFile.getAllFilesUnderDirectory(localStoreHomeDir+"/"+sc);
	}
	
	
	
	public static HashMap<String, String[]> getFieldsValue(String inStr,
			HashMap<String, String[]> fieldsValues) {
		String[] str_is_line = inStr.trim().split("\n");
		for (String xline : str_is_line) {
			int firstComma = xline.indexOf(",");
			String fieldName_key = xline.substring(0, firstComma).trim();
			String fieldValue = xline.substring(firstComma + 1).trim();
			if (fieldsValues.containsValue(fieldName_key)) {
				// key is exists
				continue;
			} else {
				// key is not exists
				fieldsValues.put(fieldName_key, fieldValue.split(","));
			}
		}

		return fieldsValues;
	}
	
	
	
	

	@Override
	public void analysis() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collection(Date date) {
		// TODO Auto-generated method stub
		
	}
	
	


}
