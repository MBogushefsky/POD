package com.bogaware.plugins;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bogaware.twilio.TwilioMessageManager;
import com.bogaware.util.RestManager;

public class QuoteOfTheDayPlugin extends Plugin {
	private String apiAddress = "http://quotes.rest/qod.json";
	public QuoteOfTheDayPlugin(String inputText) {
		super(inputText);
		generateResponse();
	}
	public void generateResponse() {
		JSONObject json = RestManager.stringToJson(RestManager.callGet(apiAddress));
		json = (JSONObject) json.get("contents");
		JSONArray array = (JSONArray) json.get("quotes");
		json = (JSONObject) array.get(0);
		response = "\"" + json.get("quote") + "\"\n";
		response += "- " + json.get("author");
	}
}
