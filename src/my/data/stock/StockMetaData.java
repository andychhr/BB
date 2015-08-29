package my.data.stock;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import my.context.MyContext;
import my.util.file.MyFile;





public class StockMetaData {
	
	protected static String[] STOCK_CODES;
	protected static HashMap<String, String> STOCK_NAMES;
	protected static HashMap<String, String> STOCK_META_DATA;
	

	
	protected static String DEFAULT_STORE_HOME_DIR;
	
	public static Map<String, Map<String, String>>  COLLECTION_CONTEXT;
	
	
	//------------------------------------------------------
	
	protected final static StockMetaData instance = new StockMetaData();
	private static boolean initialized = false;
	
	
	protected StockMetaData(){
		
	}
	
	
	
	public static synchronized StockMetaData getInstance() throws Exception {
	    if (initialized) return instance;
	    instance.init();
	    initialized = true;
	    return instance;
	  }
	
	//-----------------------------------------------------
	
	
	
	protected void init() throws Exception{
		
		//set context
		MyContext.getInstance();
	
		this.getStockCoreData();
	}
	
	
	
	
	private void getStockCoreData() throws Exception{
		
		//read file into string
		File f = new File(MyContext.StokCodeMetaFileURI);
		String content = FileUtils.readFileToString(f, Charset.forName(MyContext.Charset));
		if(content.isEmpty() || content == null){
			throw new Exception("Stock Meta data file is not exists : "+MyContext.StokCodeMetaFileURI);
		}else{
			String[] rows = content.split("\n");
			StockMetaData.STOCK_CODES = new String[rows.length];	//init stock code array
			STOCK_NAMES = new HashMap<String, String>();	//init stock name hashmap
			STOCK_META_DATA = new HashMap<String, String>();	//init stock name hashmap
			for(int i=1;i<rows.length;i++){	//start from row 2 since row 1 is header row
				String xRow = rows[i].trim();	// get row 
				String fields[] = xRow.trim().split("\t");
				//String scStr = String.copyValueOf(fields[0].trim().toCharArray());
				String scStr = fields[0].trim();
				if(StockMetaData.validateStockCode(scStr)){	//string is a validate stock code 
					StockMetaData.STOCK_CODES[i-1] = fields[0].trim();	//set stockcode
					STOCK_NAMES.put(StockMetaData.STOCK_CODES[i-1], fields[1].trim());	
					STOCK_META_DATA.put(StockMetaData.STOCK_CODES[i-1], xRow);
				}else{
					continue;	//string is not a stock code, then continue;
				}
				
				//clear resource
				fields=null;
				xRow = null;
			}
			
			//clear resource
			rows = null;
			content = null;

		}
	}
	
	
	
	
	/**
	 * Check if the sting is a stock code, string is a validate stock code
	 * @param scStr
	 * @return
	 */
 	public static boolean validateStockCode(String scStr){
		Pattern p = Pattern.compile("\\d{6}");	// 6 digitals
		Matcher m = p.matcher(scStr);
		if(m.find()){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	
	
	public static String[] getStockCodes(){
		return STOCK_CODES;
	}
	
	
	
	public static HashMap<String, String> getStockNames(){
		return STOCK_NAMES;
	}
	
	
	
	public static HashMap<String, String> getStockMetaData(){
		return STOCK_META_DATA;
	}
	
	
	
	public static HashMap<String, String> getStockContext() throws Exception{
		return MyContext.getStockContext();
	}
	

	
	
	
	public void setLocalStoreHomeDir() throws Exception{
		DEFAULT_STORE_HOME_DIR = MyContext.DATA_DIR;
	}
	
	
	public String getLocalStoreHomeDir(){
		return DEFAULT_STORE_HOME_DIR;
	}
	
	
	
	
	
	
	
	// TODO
	public Map<String, String> getURL_File(String stockcode) throws Exception {
		return null;
	}

	
	
	
	// TODO
	public void collection(String stockcode) throws Exception {

	}
	
	
	public void extract(String stockcode) throws Exception {
		System.out.println("in StockMetaData extract method");
	}

	
	
	public void analysis(String stockcode) {
		// TODO Auto-generated method stub

	}
	
	
	
	
	public static void saveToLocalFile(String content, String localFilePath){
		try {
			// craete local file if not exists
			File xStmtFile = MyFile.createFileIfNotExits(localFilePath); 
			
			// Save fileoutputstream to file in local
			synchronized (xStmtFile) {
				//write content string to file
				MyFile.WriteStringToFile(localFilePath, content,Charset.forName(MyContext.Charset), false); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// clear resouce
			content = null;
		}
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



	
	
	
	
	
	
}
