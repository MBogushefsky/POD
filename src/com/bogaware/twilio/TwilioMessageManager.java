package com.bogaware.twilio;

import java.util.HashMap;

import com.bogaware.service.Main;
import com.bogaware.util.Base64Manager;
import com.bogaware.util.RestManager;
import com.bogaware.util.SettingsManager;
import com.bogaware.util.URLManager;

public class TwilioMessageManager {
	
	private final static String TWILIO_ACCOUNT_SID = "AC9e514a290db6f306f4b481f5b750d6e5";
	private final static String TWILIO_AUTH_TOKEN = "304470892a18c3fc28c1bc4736fb2dba";
	
	public static void sendMessageByPhoneNumber(String phone, String message) {
		String data = "Body=" + URLManager.decodeURL(message) + "&From=" + URLManager.decodeURL("+15732276562") + "&To=" + URLManager.decodeURL(phone);
		String authorizationString = Base64Manager.encodeBase64(TWILIO_ACCOUNT_SID + ":" + TWILIO_AUTH_TOKEN);
		RestManager.callPost("https://api.twilio.com/2010-04-01/Accounts/" + TWILIO_ACCOUNT_SID + "/Messages.json", data, new String[][] {{"Authorization", "Basic " + authorizationString}, {"Content-Type", "application/x-www-form-urlencoded"}});
	}
	
	public static String getFieldOfTwilioMessage(String requestString, String field) {
		String[] splitMessage = requestString.split("&");
		HashMap<String, String> messageMap = new HashMap<String, String>();
		for (String string : splitMessage) {
			String[] splitField = string.split("=");
			String fieldName = URLManager.decodeURL(splitField[0]);
			String fieldValue = URLManager.decodeURL(splitField[1]);
			if (splitField.length > 1) {
				messageMap.put(fieldName, fieldValue);
			}
		}
		return messageMap.get(field);
	}
}
