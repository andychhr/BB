package util.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;

import util.io.MyInputStream;


public class MyHttpClient {
	/**
	 * @param _host
	 * @param _path
	 * @param _parameters
	 * @return
	 * @throws URISyntaxException
	 */
	public URI getHttpURI(String _host, String _path,
			Map<String, String> _parameters) throws URISyntaxException {
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
	 * @param fos
	 * @return
	 * @throws Exception
	 */
	public static FileOutputStream basicHttpAccess(String url,FileOutputStream fos) throws Exception {
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
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				entity.writeTo(fos);
				return fos;
				
				//Header h = entity.getContentType();
				// if(h != null){
				// System.out.println(h.toString());
				// if(h.toString().equalsIgnoreCase("Content-Type: application/octet-stream")){
				// if(entity.isStreaming()){
				// FileOutputStream fos = new
				// FileOutputStream(defaultStreamFile);
				// entity.writeTo(fos);
				// return fos;
				// }
				// }else if(h.toString().contains())
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			httppost = null;
			httpclient = null;
		}
		return null;
	}

	public static String basicHttpAccess(String url, Charset _charSet)
			throws Exception {
		String _responseContent = "";

		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(10 * 30 * 1000) // 30 sec
				.setConnectTimeout(10 * 30 * 1000) // 30sec
				.build();
		HttpClient httpclient = HttpClientBuilder.create()
				.setDefaultRequestConfig(config).build();
		HttpPost httppost = new HttpPost(url);
		httppost.setConfig(config);

		try {
			// Request parameters and other properties.
			// List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			// params.add(new BasicNameValuePair("param-1", "12345"));
			// params.add(new BasicNameValuePair("param-2", "Hello!"));
			// httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			// Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				/*
				 * Header h = entity.getContentType(); if(h != null){
				 * System.out.println(h.toString()); }
				 */
				InputStream instream = entity.getContent();
				try {
					// read string from input stream
					_responseContent = MyInputStream.readStringFromInputStream(
							instream, _charSet); // read String from
													// InputStream,
													// StandardCharsets.UTF_8
					// return _responseContent;
				} finally {
					instream.close();
					response = null;
				}
			} else {
				throw new Exception("response.getEntity() return null");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// 递归调用
			_responseContent = MyHttpClient.basicHttpAccess(url, _charSet);
		} finally {
			httppost = null;
			httpclient = null;
		}

		return _responseContent;
	}

}
