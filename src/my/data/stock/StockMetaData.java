package my.data.stock;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import my.context.MyContext;





public class StockMetaData {
	
	private static String[] STOCK_CODES;
	private static HashMap<String, String> STOCK_NAMES;
	private static HashMap<String, String> STOCK_META_DATA;
	
	
	private MyContext _context;
	
	
	//------------------------------------------------------
	
	private final static StockMetaData instance = new StockMetaData();
	private static boolean initialized = false;
	
	private StockMetaData(){
		
	}
	
	public static synchronized StockMetaData getInstance() throws Exception {
	    if (initialized) return instance;
	    instance.init();
	    initialized = true;
	    return instance;
	  }
	
	//-----------------------------------------------------
	
	private void init() throws Exception{
		
		//set context
		this._context = MyContext.getInstance();
	
		
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
	
	
	
	
}
