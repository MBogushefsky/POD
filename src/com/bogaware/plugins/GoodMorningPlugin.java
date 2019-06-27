package com.bogaware.plugins;

public class GoodMorningPlugin extends Plugin {
	
	public GoodMorningPlugin(String inputText) {
		super(inputText);
		generateResponse();
	}
	public void generateResponse() {
		response = "Good morning! Ready for work today? Have a great day:)";
	}

}
