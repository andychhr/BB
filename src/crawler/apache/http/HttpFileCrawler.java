package crawler.apache.http;

import java.io.FileOutputStream;

import util.http.MyHttpClient;
import crawler.FileCrawler;

public class HttpFileCrawler implements FileCrawler{
	public static FileOutputStream getFile(String url,FileOutputStream fos) throws Exception{
		return MyHttpClient.basicHttpAccess(url, fos);
	}
}
