package com.bogaware.plugins.inventory;

import org.json.simple.JSONObject;

import com.bogaware.global.Global;
import com.bogaware.global.RestManager;
import com.bogaware.plugins.Plugin;

public class CoinbaseAccountBalancePlugin extends Plugin {
	private String apiAddress = "https://api.coinbase.com/v2/accounts";

	public CoinbaseAccountBalancePlugin(String inputText) {
		super(inputText);
	}
	
	public void generateResponse() {
		JSONObject json = RestManager.stringToJson(RestManager.callGet(apiAddress));		//AUTHORIZATION
		response = "BTC Price: $" + (String) json.get("mid");
	}
}
