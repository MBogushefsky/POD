package com.bogaware.plugins.inventory;

import org.json.simple.JSONObject;

import com.bogaware.global.RestManager;
import com.bogaware.plugins.Plugin;
import com.bogaware.util.SettingsManager;

public class WeatherPlugin extends Plugin {
private String apiAddress = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22phoenix%2C%20az%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
	
	public WeatherPlugin(String inputText) {
		super(inputText);
		generateResponse();
	}
	public void generateResponse() {
		JSONObject json = RestManager.stringToJson(RestManager.callGet(apiAddress));
		String temperatureString = (String)RestManager.getJsonFromPath(json, "/query/results/channel/item/condition/temp");
		response = "Temperature is " + temperatureString;
	}
}
