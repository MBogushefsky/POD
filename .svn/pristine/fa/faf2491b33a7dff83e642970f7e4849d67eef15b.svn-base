package com.bogaware.global;

import java.util.HashMap;

import com.bogaware.service.Main;

public class PhoneManager {
	public static void sendTwilioMessage(String phone, String message) {
		String data = "Body=" + Global.decodeURL(message) + "&From=" + Global.decodeURL(Global.properties.getProperty("TWILIO_PHONE_NUMBER")) + "&To=" + Global.decodeURL(phone);
		String authorizationString = Global.encodeBase64(Global.properties.getProperty("TWILIO_ACCOUNT_SID") + ":" + Global.properties.getProperty("TWILIO_AUTH_TOKEN"));
		RestManager.callPost("https://api.twilio.com/2010-04-01/Accounts/" + Global.properties.getProperty("TWILIO_ACCOUNT_SID") + "/Messages.json", data, new String[][] {{"Authorization", "Basic " + authorizationString}, {"Content-Type", "application/x-www-form-urlencoded"}});
	}
	
	public static String getFieldOfTwilioMessage(String requestString, String field) {
		String[] splitMessage = requestString.split("&");
		HashMap<String, String> messageMap = new HashMap<String, String>();
		for (String string : splitMessage) {
			String[] splitField = string.split("=");
			String fieldName = Global.decodeURL(splitField[0]);
			String fieldValue = Global.decodeURL(splitField[1]);
			if (splitField.length > 1) {
				messageMap.put(fieldName, fieldValue);
			}
		}
		return messageMap.get(field);
	}
}
