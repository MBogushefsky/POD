package com.bogaware.plugins.inventory;

import org.json.simple.JSONObject;

import com.bogaware.global.Global;
import com.bogaware.global.RestManager;
import com.bogaware.plugins.Plugin;

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
