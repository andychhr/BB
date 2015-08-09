package my.util.charset;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import my.util.io.MyInputStream;


public class MyCharset {
	
	final static int BUFFER_SIZE = 4096;

	
	public static String convertCharset(InputStream is, String fromCharset, String toCharset) throws IOException {
		
		//get string from input stream
		String inputStr = MyInputStream.readStringFromInputStream(is, Charset.forName(fromCharset));
		
		return MyCharset.convertCharset(inputStr, fromCharset, toCharset);
		
	}
	
	
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
	
	
	
	
	
	
	

	/**
	 * 将InputStream转换成String
	 * 
	 * @param in
	 *            InputStream
	 * @return String
	 * @throws Exception
	 * 
	 */
	public static String InputStreamToString(InputStream in) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "utf-8");
	}

	/**
	 * 将InputStream转换成某种字符编码的String
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String InputStreamTOString(InputStream in, String encoding)
			throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "utf-8");
	}

	/**
	 * 将String转换成InputStream
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream StringToInputStream(String in) throws Exception {

		ByteArrayInputStream is = new ByteArrayInputStream(
				in.getBytes("utf-8"));
		return is;
	}

	/**
	 * 将InputStream转换成byte数组
	 * 
	 * @param in: InputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] InputStreamToByte(InputStream in) throws IOException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return outStream.toByteArray();
	}

	/**
	 * 将byte数组转换成InputStream
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream byteToInputStream(byte[] in) throws Exception {

		ByteArrayInputStream is = new ByteArrayInputStream(in);
		return is;
	}

	/**
	 * 将byte数组转换成String
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String byteToString(byte[] in) throws Exception {

		InputStream is = byteToInputStream(in);
		return InputStreamToString(is);
	}
	
	
	public byte[] getBytes(char[] chars) {
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = CharBuffer.allocate(chars.length);
		cb.put(chars);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);

		return bb.array();
	}

	public char[] getChars(byte[] bytes) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);

		return cb.array();
	}
	

//	/**
//	 * NOT WORK!
//	 * 
//	  * 字符串编码转换的实现方法
//	  * @param str  待转换编码的字符串
//	  * @param oldCharset 原编码
//	  * @param newCharset 目标编码
//	  * @return
//	  * @throws UnsupportedEncodingException
//	  */
//	public static String changeCharset(String str, String oldCharset, String newCharset) {
//		if (str != null) {
//			try {
//				// 用旧的字符编码解码字符串。解码可能会出现异常。
//				byte[] bs = str.getBytes(oldCharset);
//				// 用新的字符编码生成字符串
//				return new String(bs, newCharset);
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
//		return null;
//	}

}
