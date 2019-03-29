package com.bogaware.util;

import java.util.Base64;

public class Base64Manager {
	public static String encodeBase64(String input) {
		byte[] bytesEncoded = Base64.getEncoder().encode(input.getBytes());
		return new String(bytesEncoded);
	}
	
	public static String decodeBase64(String input) {
		byte[] valueDecoded= Base64.getDecoder().decode(input);
		return new String(valueDecoded);
	}
}
