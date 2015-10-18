package my.data.stock.finStmt;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import my.context.MyContext;
import my.crawler.apache.http.HttpFileCrawler;
import my.data.stock.StockDataPreprocess;
import my.data.stock.StockMetaData;
import my.util.file.MyFile;
import my.util.xml.MyXML;


public class FinStmtDataObj extends StockMetaData {
	
	
	
	private static String _FinStmt_STORE_HOME_DIR;
	
	private static HashMap<String,String> _FIELDS_MAP;
	
	
	
	
	public FinStmtDataObj() throws Exception{
		super.getInstance();
		setLocalStoreHomeDir();
		
		FinancialStatementFieldsMap.getInstance();
		_FIELDS_MAP = FinancialStatementFieldsMap.getFieldsMap();
		
		//StockMetaData.COLLECTION_CONTEXT = this.getCollectionContext(StockMetaData.STOCK_CODES);
	}
	
	
	public static String get_FinStmt_STORE_HOME_DIR(){
		return FinStmtDataObj._FinStmt_STORE_HOME_DIR;
	}
	
	public static HashMap<String,String>  get_FIELDS_MAP(){
		return FinStmtDataObj._FIELDS_MAP;
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
		System.out.println("Started to collect data for stock:"+stockcode);

		Map<String, String> sc_urls_localFiles = null;
		try {
			sc_urls_localFiles = getURL_File(stockcode);
		} catch (Exception e) {
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
//		HashMap<String, String[]> finStmtFieldValues = new HashMap<String,String[]>();
//		finStmtFieldValues.clear();
		HashMap<String, String[]> finStmtFieldValues = FinStmtDataPreprocess.readFinStmtFilesContentFromXLS(sc, localStoreHomeDir);
		 
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
		 
	     //clear resource 
		 finStmtFieldValues = null;
	}
	
	
	public static String getStr(String xstr){
        return (xstr==null) ? "":xstr;
    }
	
	
//
//	public static HashMap<String, String[]> readFinStmtFilesContentFromXLS(String sc,
//			String localStoreHomeDir) throws Exception {
//
//		HashMap<String, String[]> fields = new HashMap<String, String[]>();
//		fields.clear();
//
//		Map<String, String> files = getFinStmtFileList(sc, localStoreHomeDir);
//		for (String xFile : files.keySet()) {
//			if(!xFile.endsWith(".csv")){
//				continue;
//			}
//			String xContent = MyFile.readFileToString(files.get(xFile),
//					Charset.forName(MyContext.Charset));
//			fields = getFieldsValueFromXLS(xContent, fields);
//		}
//
//		return fields;
//	}
	
	
	
	
//	/**
//	 * 
//	 * @param sc
//	 * @param localStoreHomeDir
//	 * @return Map<fieldName, fieldValues>
//	 * @throws Exception
//	 */
//	
//	public static Map<String,String> getFinStmtFileList(String sc, String localStoreHomeDir) throws Exception{
//		return MyFile.getAllFilesUnderDirectory(localStoreHomeDir+"/"+sc);
//	}

	
//	
//	public static HashMap<String, String[]> getFieldsValueFromXLS(String inStr,
//			HashMap<String, String[]> fieldsValues) {
//		String[] str_is_line = inStr.trim().split("\n");
//		for (String xline : str_is_line) {
//			int firstComma = xline.indexOf(",");
//			String fieldName_key = xline.substring(0, firstComma).trim();
//			String fieldValue = xline.substring(firstComma + 1).trim();
//			if (fieldsValues.keySet().contains(fieldName_key)) {
//				// key is exists
//				continue;
//			} else {
//				// key is not exists
//				fieldsValues.put(fieldName_key, fieldValue.split(","));
//			}
//		}
//
//		return fieldsValues;
//	}
	
	
	
	
	
	//======================================================================
	//======================================================================
	//======================================================================
	
	
	
	
	
	/***************************************************************************
	 * 
	 * filter stock code
	
	 * 
	 * 
	 *************************************************************************/
	/*
	class FinStmtVO{
		//净资产收益率
		public double ROE;
		
	}
	
	
	public 
	
	
	
	public static ArrayList<FinStmtVO> filterStep1_GoodRecords;
	public static ArrayList<String> filterStep1_lackRecords;	//Records number less then required analysis number
	public static ArrayList<FinStmtVO> filterStep1_badRecords;
	
	
	public 
	
	
	
	public static void initBasicFilters(int requiredNum){
		
		for(String xSC : STOCK_CODES){
			filterStep1_GoodRecords = new ArrayList<FinStmtVO>();
			//filterStep1GoodRecords.clear();
			
			filterStep1_lackRecords = new ArrayList<String>();
			filterStep1_badRecords =  new ArrayList<FinStmtVO>();
			
			//
			filterStep1(xSC, filter_num);
		}
	}
	
	
	
	
	public static void cleanDate(String sc, String fieldName, ArrayList<String> values, int requiredNum){
		if(values.size() < requiredNum){
			synchronized(filterStep1_lackRecords){
				filterStep1_lackRecords.add(sc);
				//set values to null to avoid analysis this stock anymore
				values = null;
			}
		}
		
//		for(int i=0;i<requiredNum;i++){
//			String xStrVal = values.get(i).trim();
//			if(xStrVal==null){
//				throw new Exception("stock:"+ sc+ " field :"+fieldName+" is null for Rec#"+i);
//			}else if(xStrVal.isEmpty() ||xStrVal.equals("--")){
//				//when i is first element, search forword and find first one record whose value is ok
//				if(i==0){
//					for(int k=2; k<requiredNum;k++){
//						String xTryVal =  values.get(k).trim();
//						
//					}
//				}
//				
//			}
//				xStrVal = "0";
//			}
	}
	
	
	
	//@TODO
	public static void fillMissingValues(ArrayList<String> values) throws Exception{
		for(int i=0;i<values.size();i++){
			String xStrVal = values.get(i).trim();
			if(xStrVal==null){
				throw new Exception("value is null for Rec#"+i);
			}else if(xStrVal.isEmpty() ||xStrVal.equals("--")){
				//when i is first element, search forword and find first one record whose value is ok
				if(i==0){
					for(int k=2; k<values.size();k++){
						String xTryVal =  values.get(k).trim();
						
					}
				}
				
			}
				xStrVal = "0";
			}
	}
	
	public static void filterStep1(String sc, int filter_num){
		//
		
		//ROE
		HashMap<String, String>  values = FinStmtDataPreprocess.getFieldValuesFromXML(sc, "净资产收益率");
		ArrayList<String> yearlyValues = FinStmtDataPreprocess.getQnData(values, 4);
		
		//
		if(yearlyValues.size() < filter_num){
			synchronized(filterStep1_LessFilterNumRecords){
				filterStep1_LessFilterNumRecords.add(sc);
			}
		}
		
		for(int i=0;i<filter_num;i++){
			String xStrVal = yearlyValues.get(i).trim();
			if(xStrVal==null){
				throw new Exception("stock:"+ sc+ " field 净资产收益率 is null for Rec#"+i);
			}else if(xStrVal.isEmpty() ||xStrVal.equals("--")){
				xStrVal = "0";
			}
			
			
			FinStmtVO xVO= new FinStmtVO();
			xVO.ROE  = Double.parseDouble(xStrVal);
			if(xVO.ROE < 0){
				synchronized(filterStep1_GoodRecords){
					filterStep1_badFilterNumRecords.add(xVO);
				}
			}else if()
			
		}
		
	}
	
	*/
	
	
	
	
	
	
	
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
