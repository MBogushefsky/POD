package com.bogaware.plugins;

public class HealthyMessagePlugin extends Plugin {
	
	public HealthyMessagePlugin(String inputText) {
		super(inputText);
		generateResponse();
	}
	public void generateResponse() {
		response = "Drink water and Eat Healthy, even if it's small changes!";
	}

}
