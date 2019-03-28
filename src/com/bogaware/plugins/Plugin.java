package com.bogaware.plugins;

public class Plugin {
	protected String input;
	protected String response;
	public Plugin(String inputText) {
		input = inputText;
		response = "No plugin found";
	}
	public String getResponse() {
		return response;
	}
}