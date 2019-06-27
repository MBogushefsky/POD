package com.bogaware.plugins;

public class HelpPlugin extends Plugin {
	public HelpPlugin(String inputText) {
		super(inputText);
		generateResponse();
	}
	public void generateResponse() {
		response = "qotd = Quote of the Day\ngma = Good Morning Text\nbp = Bitcoin Price\n";
	}

}
