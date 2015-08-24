package my.crawler.apache.http;

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
	public static String getContent(String url) throws Exception{
		return MyHttpClient.basicHttpAccess(url);
	}
}
