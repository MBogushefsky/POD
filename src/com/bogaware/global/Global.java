package com.bogaware.global;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;

public class Global {
	public static Properties properties;
	public static TaskManager taskManager;
	public static DecimalFormat usdFormat = new DecimalFormat("#.00");

	public Global() {
		properties = new Properties();
		Global.properties.getAPISettings();
		taskManager = new TaskManager();
	}
	
	public static String decodeURL(String url) {
		String result = null;
		try {
			result = java.net.URLDecoder.decode(url, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String encodeBase64(String input) {
		byte[] bytesEncoded = Base64.getEncoder().encode(input.getBytes());
		return new String(bytesEncoded);
	}
	
	public static String decodeBase64(String input) {
		byte[] valueDecoded= Base64.getDecoder().decode(input);
		return new String(valueDecoded);
	}
	
	public static void printArrayList(Object objList) {
		ArrayList list = (ArrayList) objList;
		String result = "";
		for(int i = 0; i < list.size(); i++) {
			result += list.get(i) + "\n";
		}
		System.out.println(result);
	}
}
