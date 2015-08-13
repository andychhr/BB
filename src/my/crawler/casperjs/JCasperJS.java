package my.crawler.casperjs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.FileSystemException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import utils.IO.MyFile;
import utils.regex.MyRegex;
import utils.xml.MyXML;

public class JCasperJS {
	private final String casperJS_ConfigFile = "src/crawler/casperjs/config.xml";
	private String casperJS_templateDir;
	private String casperJS_instanceDir;
	
	public JCasperJS() throws FileSystemException{
		//tempalte directory
		this.casperJS_templateDir = MyXML.getTextByXpath(this.casperJS_ConfigFile, "/casperjs/template/directory");
		
		//instance directory
		this.casperJS_instanceDir =  MyXML.getTextByXpath(this.casperJS_ConfigFile, "/casperjs/temp/directory");
	}
	
	
	/**
	 * 
	 * @param template
	 * @param instance
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getInstanceFile(String templateFile, HashMap<String, String> params) throws Exception{
		String templateFileContent = MyFile.readFileIntoString(templateFile, Charset.forName("utf-8"));
		for(String xParam : params.keySet()){
			String patternStr = "\\$\\$"+xParam+"\\$\\$";
			//String patternStr = "BB_RegMacher_"+xParam;
			String replaceStr =  params.get(xParam);
			templateFileContent = MyRegex.replaceAll(templateFileContent, patternStr, replaceStr);
		}
		
		return templateFileContent;
	}
	
	
	
	
	public String JCasperJS_searchGood(String targetHostURL, String selector_searchInputBox, String selector_searchInputBoxForm,String searchItemName) throws FileSystemException{
		//
		String returnVal = "";
		//
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("currentPageURL", targetHostURL);
		params.put("selector_searchInputBox", selector_searchInputBox);
		params.put("selector_searchInputBoxForm", selector_searchInputBoxForm);
		params.put("itemName", searchItemName);
		
		try {
			String searchGoodJS =  MyXML.getTextByXpath(this.casperJS_ConfigFile, "/casperjs/template/javascripts/searchGood");
			
			String templateFile = this.casperJS_templateDir + "/"+ searchGoodJS;
			String instanceStr = this.getInstanceFile(templateFile, params);
			
			String instanceFileName = "searchGoodJS_"+ searchItemName +".js";
			
			File instanceFile = new File(this.generateInstanceFileName(instanceFileName));
			FileUtils.writeStringToFile(instanceFile, instanceStr, Charset.forName("utf-8"), false);
			
			String execLog = this.execCapserJS(this.generateInstanceFileName(instanceFileName));
			returnVal = execLog;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnVal;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//common methods
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public String generateInstanceFileName(String fileName){
		return this.casperJS_instanceDir +  "/" + fileName;
	}
	
	public String execCapserJS(String jsInstantFileName) throws InterruptedException, Exception {
		String execMsg = "";
		
		//
		//Process proc = Runtime.getRuntime().exec("/usr/local/Cellar/casperjs/1.0.4/libexec/bin/casperjs " + jsInstantFileName);
		ProcessBuilder pb = new ProcessBuilder("/usr/local/Cellar/casperjs/1.0.4/libexec/bin/casperjs", jsInstantFileName);
		Map<String, String> env = pb.environment();
		String param_path  = env.get("PATH");
		param_path += ":/usr/local/bin";
		env.replace("PATH", param_path);
//		System.out.format("%s=%s%n", "PATH", env.get("PATH"));
		
		//Map<String, String> env = System.getenv();
//		for (String envName : env.keySet()) {
//			System.out.format("%s=%s%n", envName, env.get(envName));
//		}
		
		Process proc = pb.start();
		
//		Process proc = Runtime.getRuntime().exec("/usr/local/Cellar/casperjs/1.0.4/bin/casperjs " + jsInstantFileName);
		if (proc.waitFor() > 0) {
			throw new Exception("CasperJS file " + jsInstantFileName + " ; exit value is " + proc.waitFor());
		}

//		String productInfoStr = "";
//		String infoIndicator = "Debug>>>";

		InputStream msg = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(msg);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				execMsg.concat(line);
//			if (line.startsWith(infoIndicator)) {
//				productInfoStr = line.substring(infoIndicator.length()).trim();
//				System.out.println(productInfoStr);
//			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//release resource
			try {
				br.close();
				isr.close();
				msg.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				br =null;
				isr =null;
				msg = null;
			}
		}
		
		return execMsg;
	}

	
	
}

