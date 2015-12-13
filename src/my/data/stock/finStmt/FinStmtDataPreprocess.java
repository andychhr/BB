package my.data.stock.finStmt;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
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
import my.util.data.MyData;
import my.util.file.MyFile;
import my.util.xml.MyXML;

public class FinStmtDataPreprocess {
	
	
	//private static Map(String,List<String>) rptDates;

	
	
	public FinStmtDataPreprocess() throws Exception{
		
		
		//StockMetaData.COLLECTION_CONTEXT = this.getCollectionContext(StockMetaData.STOCK_CODES);
	}

	//======================================================================
	//======================================================================
	//======================================================================
	public static TreeMap<String, String> getQnFieldValuesFromXML(String sc, String fieldName, int Qn) throws Exception{
		TreeMap<String, String>  allValues = FinStmtDataPreprocess.getFieldValuesFromXML(sc, fieldName);
		 allValues = FinStmtDataPreprocess.getQnData(allValues, Qn);
		 return allValues;
	}
	
	
	
	public static Map<String, TreeMap<String,String>> getQnFieldValuesFromXML(String fieldName, int Qn) throws Exception{
		Map<String,TreeMap<String,String>> allSC_FieldValues= new HashMap<String,TreeMap<String,String>>();
		for (String xsc : FinStmtDataObj.getStockCodes()) {
			if(xsc==null){
				continue;
			}
			
			TreeMap<String, String> values = FinStmtDataPreprocess.getQnFieldValuesFromXML(xsc, fieldName, Qn);
			allSC_FieldValues.put(xsc, values);
		}
		
		return allSC_FieldValues;
	}
	
	
	
	
	public static String isGoodStock(String sc, int analysisDuration, int Qn,
			String fieldName, boolean isRatioField, double fieldMinValue, boolean isCompareByRatio)
			throws Exception {
		String stockFilterType = "bad";
		TreeMap<String, String> QnValues = FinStmtDataPreprocess.getQnFieldValuesFromXML(sc,fieldName,Qn);
		List<String> rptdates = new ArrayList<String>(QnValues.keySet());
		
		//if fieldName map to an absolute value
		int analysisReportDates = analysisDuration;
		if(!isRatioField && isCompareByRatio){
			analysisReportDates =  analysisDuration +1;
		}

		if (rptdates.size() < analysisDuration) {
			stockFilterType = "lessValues";
		}else{
			Collections.reverse(rptdates);
			List<String> analysisRptDates = rptdates.subList(0, analysisReportDates);
			for (int i=0;i<analysisDuration;i++) {
				String xRptDate=""; 
				String xVal=""; 
				if(isRatioField && isCompareByRatio){	//field value is ratio value, and  compare by ratio
					xRptDate = analysisRptDates.get(i);
					xVal = QnValues.get(xRptDate);
				}else if(!isRatioField && isCompareByRatio){	//field value is not ratio value, and should be compare by ratio
					
					xRptDate = analysisRptDates.get(i);
					String xVal_this = QnValues.get(analysisRptDates.get(i));
					String xVal_next = QnValues.get(analysisRptDates.get(i + 1));
					if (MyData.isDouble(xVal_this) && MyData.isDouble(xVal_next)) {
						double thisVal = Double.parseDouble(xVal_this);
						double nextVal = Double.parseDouble(xVal_next);
						if (nextVal - 0.0 < 0.000000001) {
							return "invalidValues";
						}
						xVal = String.valueOf((thisVal - nextVal) * 100 / nextVal);
					}

					
				}else if(!isRatioField && !isCompareByRatio){
					xRptDate = analysisRptDates.get(i);
					xVal = QnValues.get(xRptDate);
				}
				
				
				
				if(MyData.isDouble(xVal)){	//if value is double
					if(fieldMinValue < Double.parseDouble(xVal) && i< analysisDuration-1){	//if value is > min value
						continue;
					}else if(fieldMinValue < Double.parseDouble(xVal) && i== analysisDuration-1){	//if all values are > min value
						stockFilterType = "good";
					}else{
						stockFilterType = "bad";
						break;
						
					}
					
				}else{
					stockFilterType = "invalidValues";
					break;
				}
			}
		}
		return stockFilterType;
	}
	
	
	
	

	//======================================================================
	//======================================================================
	//======================================================================
	
	


	/**
	 * 
	 * @param sc
	 * @param localStoreHomeDir
	 * @return Map<fieldName, fieldValues>
	 * @throws Exception
	 */
	public static Map<String,String> getFinStmtFileList(String sc, String localStoreHomeDir) throws Exception{
		return MyFile.getAllFilesUnderDirectory(localStoreHomeDir+"/"+sc);
	}
	
	
	
	public static TreeMap<String, String[]> readFinStmtFilesContentFromXLS(String sc,
			String localStoreHomeDir) throws Exception {

		TreeMap<String, String[]> fields = new TreeMap<String, String[]>();
		fields.clear();

		Map<String, String> files = getFinStmtFileList(sc, localStoreHomeDir);
		for (String xFile : files.keySet()) {
			if(!xFile.endsWith(".csv")){
				continue;
			}
			String xContent = MyFile.readFileToString(files.get(xFile),
					Charset.forName(MyContext.Charset));
			fields = getFieldsValueFromXLS(xContent, fields);
		}

		return fields;
	}
	
	

	public static TreeMap<String, String[]> getFieldsValueFromXLS(String inStr,
			TreeMap<String, String[]> fieldsValues) {
		String[] str_is_line = inStr.trim().split("\n");
		for (String xline : str_is_line) {
			int firstComma = xline.indexOf(",");
			String fieldName_key = xline.substring(0, firstComma).trim();
			String fieldValue = xline.substring(firstComma + 1).trim();
			if (fieldsValues.keySet().contains(fieldName_key)) {
				// key is exists
				continue;
			} else {
				// key is not exists
				fieldsValues.put(fieldName_key, fieldValue.split(","));
			}
		}

		return fieldsValues;
	}
	

	//======================================================================
	//======================================================================
	//======================================================================
	
	
	public static TreeMap<String, String> getFinStmtDataXMLFiles(){
		TreeMap<String, String> dataXMLs =  new TreeMap<String, String> ();
		dataXMLs.clear();
		
		for(String xSC : FinStmtDataObj.getStockCodes()){
			dataXMLs.put(xSC, FinStmtDataObj.get_FinStmt_STORE_HOME_DIR()+"/"+xSC+"/"+xSC+".xml");
		}
		
		return dataXMLs;
	}
	
	
	
	/**
    *
    * @param TreeMap<String, String>: TreeMap<Rerport date, value>
    * @param Qn: Q1,Q2,Q3,Q4
    * @return
    */
   public static TreeMap<String, String> getQnData(TreeMap<String, String> fieldValues, int Qn){
	   TreeMap<String, String> vals =  new TreeMap<String, String>();

       //
       //int maxLen = dateStr.length > valxStr.length ?  valxStr.length :  dateStr.length;
       //int maxLen = fieldValues.size();

       //
       String Q4 = "\\d{4}-12-\\d{2}";
       String Q3 = "\\d{4}-09-\\d{2}";
       String Q2 = "\\d{4}-06-\\d{2}";
       String Q1 = "\\d{4}-03-\\d{2}";

       String pat = Q4;
       switch(Qn){
           case 1:
               pat = Q1;
               break;
           case 2:
               pat = Q2;
               break;
           case 3:
               pat = Q3;
               break;
           case 4:
               pat = Q4;
               break;
       }
       
       //match string by regex
       Pattern pt = Pattern.compile(pat);
       for(String xDate : fieldValues.keySet()){
           String xValue = fieldValues.get(xDate).trim();
           //
           Matcher m = pt.matcher(xDate);
           if(m.find()){
               vals.put(xDate,xValue);
           }
       }
       
       //return 
       return vals;
   }

	
	
	/**
	 * 
	 * @param sc
	 * @param fieldName: field name same as web, will be convert to real name with Map file
	 * @return field name's all values in xml
	 * @throws Exception
	 */
	public static TreeMap<String, String> getFieldValuesFromXML(String sc, String fieldName) throws Exception{
		//return value
				TreeMap<String, String> date_fieldVal = new TreeMap<String, String>();
				date_fieldVal.clear();
				
				
		//get actual field name from map file
		String actualFieldName = "";
		if(FinStmtDataObj.get_FIELDS_MAP().containsKey(fieldName)){
			actualFieldName = FinStmtDataObj.get_FIELDS_MAP().get(fieldName);
		}else{
			throw new Exception("FinancialStatementFieldsMap does not contain this fieldName:"+fieldName);
		}
		
		//xml file path
		String filePath =   FinStmtDataObj.get_FinStmt_STORE_HOME_DIR()+"/"+sc+"/"+sc+".xml";
		
		// check input file
		File xml = MyXML.validateXML(filePath);

		//
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader(); 
			document = saxReader.read(xml); 
			List<? extends Node> list = document.selectNodes("//rptdate");
			Iterator<?> iter = list.iterator();
			while (iter.hasNext()) {
				Element dateElement = (Element) iter.next();
				String rptDate = dateElement.attributeValue("date").trim();
				
//				String currentPath = dateElement.getPath();
//				String xPath = currentPath + "[@date='"+rptDate+"']/"+ actualFieldName;
//				String xFieldVal = dateElement.selectSingleNode(xPath).getText().trim();
				Element xElmt = dateElement.element(actualFieldName);
				String xFieldVal = xElmt.getTextTrim();
				
				date_fieldVal.put(rptDate, xFieldVal);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			document = null;
		}
				
		
		return date_fieldVal;
		
		
		
		
	}
	
//	
//	public static sortFinStmtMapKeysDesc(Map<String,String> finStmtMap){
//		List mapKeys = new ArrayList(finStmtMap.keySet());
//		Collections.sort(mapKeys);
//	}
	
	
	
	
	
	
	//======================================================================
	//======================================================================
	//======================================================================
	


	/***************************************************************************
	 * 
	 * 
	 *************************************************************************/
	

	public static void createFinStmtMapFile(boolean needToUpdateMapFile) throws Exception{
		if (needToUpdateMapFile) {
			
			TreeMap<String, String[]> finContents = FinStmtDataPreprocess.readFinStmtFilesContentFromXLS(
					"600036",
					FinancialStatement._context.get("FinanStmtStoreURI"));
			
			String classFileContent = "package my.data.stock.finStmt;\n"
					+ "import java.util.TreeMap;\n"
					+ "public class FinancialStatementFieldsMap {\n"
					+ "private final static FinancialStatementFieldsMap instance = new FinancialStatementFieldsMap(); \n"
					+ "private static boolean initialized = false;	\n"
					+ "private static TreeMap<String,String> FieldsMap; \n"
					+ "private FinancialStatementFieldsMap(){} \n"
					+ "public static FinancialStatementFieldsMap getInstance(){ \n"
					+ "if (initialized) return instance; \n"
					+ "instance.init(); \n"
					+ "initialized = true;	\n"
					+ "return instance;	\n"
					+ "}	\n"
					+ "public static TreeMap<String,String> getFieldsMap(){	\n"
					+ "return FinancialStatementFieldsMap.FieldsMap;	\n"
					+ "}	\n"
					+ "private void init(){	\n"
					+ "if(!initialized){	\n"
					+ "FinancialStatementFieldsMap.FieldsMap = new TreeMap<String,String>(); \n";

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
	

	//======================================================================
	//======================================================================
	//======================================================================
	
	
	
}
