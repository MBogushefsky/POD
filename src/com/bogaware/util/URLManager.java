package com.bogaware.util;

public class URLManager {

	public static String decodeURL(String url) {
		String result = null;
		try {
			result = java.net.URLDecoder.decode(url, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
