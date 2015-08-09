package my.util.http;


import my.util.charset.MyCharset;
import my.util.io.MyInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;


public class MyHttpClient {
	/**
	 * @param _host
	 * @param _path
	 * @param _parameters
	 * @return
	 * @throws URISyntaxException
	 */
	public URI getHttpURI(String _host, String _path,Map<String, String> _parameters) throws URISyntaxException {
		// http://quotes.money.163.com/f10/zycwzb_600157.html#08a01
		URIBuilder uriBuilder = new URIBuilder();
		uriBuilder.setScheme("http");
		uriBuilder.setHost(_host); // "www.google.com"
		uriBuilder.setPath(_path); // "/search"
		for (String param : _parameters.keySet()) {
			uriBuilder.setParameter(param, _parameters.get(param)); // .setParameter("q","httpclient")
		}

		// uri = new URI("http",_host, _path, "" );
		URI uri = uriBuilder.build();

		return uri;
	}

	public HttpGet getHttpGet(URI uri) {
		HttpGet httpget = new HttpGet(uri);
		return httpget;
	}

	/**
	 * Get file
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String basicHttpAccess(String url) throws Exception {
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(10 * 30 * 1000) // 30 sec
				.setConnectTimeout(10 * 30 * 1000) // 30sec
				.build();
		HttpClient httpclient = HttpClientBuilder.create()
				.setDefaultRequestConfig(config).build();
		HttpPost httppost = new HttpPost(url);
		httppost.setConfig(config);
		try {
			// Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {	//check HttpStatus
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					
					@SuppressWarnings("deprecation")
					String origCharset = EntityUtils.getContentCharSet(entity);	//get charset
					//get content from entity
					InputStream instream = entity.getContent();
					String content = MyCharset.convertCharset(instream, origCharset, MyContext.Charset);
					
					//clear resouce
					instream.close();
					
					//return
					return content;
				}
			}else{
				throw new Exception("url:"+url + " http request fetch failed. http response status code is "+response.getStatusLine().getStatusCode() );
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//clear resouce
			httppost = null;
			httpclient = null;
		}
		return null;
	}

	

}
