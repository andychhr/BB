package my.util.data;

import java.util.regex.Pattern;

public class MyData {
	
	public static boolean isDouble(String str) {
		//Pattern pattern = Pattern.compile("^[-//+]?//d+(//.//d*)?|//.//d+$");
		Pattern pattern = Pattern.compile("^[-\\+]{0,1}?\\d+(\\.\\d*)?|\\.\\d+$");
		return pattern.matcher(str).matches();
	}

}
