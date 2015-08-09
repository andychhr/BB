package my.crawler.apache.http;

import java.io.FileOutputStream;

import my.crawler.FileCrawler;
import my.util.http.MyHttpClient;

public class HttpFileCrawler implements FileCrawler{
	
	/**
	 * 
	 * @param url
	 * @param fos
	 * @return
	 * @throws Exception
	 */
	public static String getFile(String url) throws Exception{
		return MyHttpClient.basicHttpAccess(url);
	}
}
