package my.util.charset;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.nio.charset.Charset;

import my.util.io.MyInputStream;


public class MyCharset {
	
	/**
	 * Convert charset from old to new for InputStream
	 * @param is
	 * @param fromCharset
	 * @param toCharset
	 * @return
	 * @throws IOException
	 */
	public static String convertCharset(InputStream is, String fromCharset, String toCharset) throws IOException {
		
		//get string from input stream
		String inputStr = MyInputStream.readStringFromInputStream(is, Charset.forName(fromCharset));
		
		return MyCharset.convertCharset(inputStr, fromCharset, toCharset);
		
	}
	
	
	/**
	 * Convert charset from old to new for String
	 * @param inputStr
	 * @param fromCharset
	 * @param toCharset
	 * @return
	 * @throws IOException
	 */
	public static String convertCharset(String inputStr, String fromCharset, String toCharset) throws IOException {

		//use output writer to write input string  into new output stream with new charset
		OutputStream baos =  new ByteArrayOutputStream();
		OutputStreamWriter outWriter = new OutputStreamWriter(baos, toCharset);
		outWriter.write(inputStr);
		outWriter.flush();
		String finalContextWithRightCharset = baos.toString();
		
		//clear resouce
		outWriter.close();
		baos.close();
		
		//return
		return finalContextWithRightCharset;
	}
	

}
