package my.data.stock.finStmt;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import my.context.MyContext;
import my.crawler.apache.http.HttpFileCrawler;
import my.data.stock.StockMetaData;
import my.util.file.MyFile;
import my.util.xml.MyXML;


public class FinStmtDataObj extends StockMetaData {
	
	
	
	private  static String _FinStmt_STORE_HOME_DIR;
	
	
	
	
	
	
	public FinStmtDataObj() throws Exception{
		super.getInstance();
		setLocalStoreHomeDir();
		
		//StockMetaData.COLLECTION_CONTEXT = this.getCollectionContext(StockMetaData.STOCK_CODES);
	}
	
	
//	public Map<String, Map<String, String>> getCollectionContext(String []stockcodes) throws Exception{
//		Map<String, Map<String, String>> collContext = new HashMap<String, Map<String, String>>();
//		collContext.clear();
//		
//		for(String sc : stockcodes){
//			collContext.put(sc, this.getURL_File(sc));
//		}
//		
//		return collContext;
//	}

	
	
	
	/***************************************************************************
	 * 
	 * 
	 *************************************************************************/
	
	
	
	
	public void setLocalStoreHomeDir() throws Exception{
		MyContext.getInstance();
		_FinStmt_STORE_HOME_DIR = MyContext.getStockContext().get("FinanStmtStoreURI");
	}
	
	
	
	public String getLocalStoreHomeDir(){
		return _FinStmt_STORE_HOME_DIR;
	}
	
	
	
	

	/***************************************************************************
	 * 
	 * 
	 *************************************************************************/
	
	
	@Override
	public Map<String, String> getURL_File(String stockcode) throws Exception {
		//
		HashMap<String, String> url_file = new HashMap<String,String>();
		url_file.clear();
		
		
//		StockMetaData.getInstance();
		//get template URLs
		for(String key : FinStmtDataObj.getStockContext().keySet()){
			if(key.contains("StmtURI")){
				String xStmtURI = FinStmtDataObj.getStockContext().get(key);	//get x statement URIs
				xStmtURI=xStmtURI.replace("$sc$", stockcode).trim();	//replace "$sc$" to real stock code
				String xURL = StockMetaData.getStockContext().get("CurrentDataSource") + xStmtURI;	// get real statement url
//				System.out.println("xURL for stockcode:"+stockcode + "is " + xURL);
				
				//get local file absolute path
				String xStmtLocalFilePath = this.getLocalStoreHomeDir() + "/" +stockcode+"/" + getStmtName(key) + ".csv"; 
				
				url_file.put(xURL, xStmtLocalFilePath);

			}
		}	
		
		//
		return url_file;
	}
	
	
	
	
	
	
	
	
	
	
	public void collection(String stockcode) throws Exception {	

		Map<String, String> sc_urls_localFiles = null;
		try {
			sc_urls_localFiles = getURL_File(stockcode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String xURL : sc_urls_localFiles.keySet()) {
			// get content from web
			String content = HttpFileCrawler.getContent(xURL);

			// save content into local files
			String localFilePath = sc_urls_localFiles.get(xURL);
			saveToLocalFile(content, localFilePath);

			// clear resource
			content = null;
		}
		
		System.out.println("collection is done for stock:"+stockcode);

	}
	
	
	
	
	
	
	/***************************************************************************
	 * 
	 * Extract fields from xls file
	 * 
	 *************************************************************************/
	
	public void extract(String stockcode) throws Exception{
		getAndSaveFinStmtFieldValues(stockcode, _FinStmt_STORE_HOME_DIR);
	}
	
	

	
	public void getAndSaveFinStmtFieldValues(String sc,
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
					if(elementName == null){
						System.out.println("element name is null");
					}
					String []xfieldVals = finStmtFieldValues.get(xField);
					String elementVal ="";
					try{
						elementVal = getStr(xfieldVals[i]);
					}catch(java.lang.ArrayIndexOutOfBoundsException outOfBoundEx){
						elementVal ="--";
					}catch(Exception ex){
						ex.printStackTrace();
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
	
	
	
	
	
	
	
	/***************************************************************************
	 * 
	 * filter stock code
	 * 
	 *************************************************************************/
	
	public static HashMap<String, String> getFinStmtDataXMLFiles(){
		HashMap<String, String> dataXMLs =  new HashMap<String, String> ();
		dataXMLs.clear();
		
		for(String xSC : STOCK_CODES){
			dataXMLs.put(xSC, _FinStmt_STORE_HOME_DIR+"/"+xSC+"/"+xSC+"xml");
		}
		
		return dataXMLs;
	}
	
	public String getFieldValue(){
		
	}
	
	
	
	
	
	
	
	

	/***************************************************************************
	 * 
	 * 
	 *************************************************************************/
	

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
					+ "}	\n"
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
			MyFile.WriteStringToFile("src/my/data/stock/finStmt/FinancialStatementFieldsMap.java",
					classFileContent);
		}
	}
	
	
	
	
	
	
	
	/***************************************************************************
	 * 
	 * 
	 *************************************************************************/
	
	
	
	public void analysis(String stockcode){
		
	
	}
		
		
	
		
//		public void collection_GetAndSaveContent(String url, String localFilePath) {
//			String content = "";
//			// crawl content from web
//			content = this.collection_getContent(url, localFilePath);
//			
//			
//			//Save to Local file
//			this.saveToLocalFile(content, localFilePath);
//
//		}
		
		
		

//		public String collection_getContent(String url,String localFilePath){
//			String content = "";
//			try {
//				content = HttpFileCrawler.getContent(url);
//			} catch (Exception ex) { // Need to handle http issues
//				//
//				synchronized (this._RESUBMIT_RECORDS) {
//					if (!this._RESUBMIT_REQ.containsKey(url)) {
//						this._RESUBMIT_REQ.put(url, localFilePath);
//					} else {
//						this._FAILED_REQ.put(url, localFilePath);
//					}
//				}
//				//
//				ex.printStackTrace();
//			}
//			
//			return content;
//		}
		
		
	
	
	
	
	
	

	// ===============================================================
	// ============================================================

		public static String getStmtName(String oriStmtName) {
			int x = oriStmtName.indexOf("Stmt");
			return oriStmtName.substring(0, x);
		}
	
	
	
	
}
