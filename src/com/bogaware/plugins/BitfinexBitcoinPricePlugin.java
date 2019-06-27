package com.bogaware.plugins;

import org.json.simple.JSONObject;

import com.bogaware.util.RestManager;
import com.bogaware.util.SettingsManager;

public class BitfinexBitcoinPricePlugin extends Plugin {
	private String apiAddress = "https://api.bitfinex.com/v1/pubticker/btcusd";
	
	public BitfinexBitcoinPricePlugin(String inputText) {
		super(inputText);
		generateResponse();
	}
	public void generateResponse() {
		JSONObject json = RestManager.stringToJson(RestManager.callGet(apiAddress));
		response = "BTC Price: $" + (String) json.get("mid");
	}
}
