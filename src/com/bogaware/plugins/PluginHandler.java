package com.bogaware.plugins;

import org.quartz.JobDataMap;

import com.bogaware.plugins.inventory.AllAccountsStatusPlugin;
import com.bogaware.plugins.inventory.BitfinexBitcoinPricePlugin;
import com.bogaware.plugins.inventory.GoodMorningPlugin;
import com.bogaware.plugins.inventory.QuoteOfTheDayPlugin;
import com.bogaware.plugins.inventory.WeatherPlugin;
import com.bogaware.util.SettingsManager;

public class PluginHandler {
	private Plugin plugin;
	private String fromPhoneNumber = "";
	private String requestMessage = "";
	private String responseMessage = "";
	private String frequencyDescription = "";
	
	public PluginHandler(String fromPhoneNumberInput, String requestText) {
		this.fromPhoneNumber = fromPhoneNumberInput;
		this.requestMessage = requestText.toLowerCase();
		getPlugin();
		checkForAlert();
	}
	
	public void getPlugin() {
		if(requestMessage.startsWith("qotd")) {
			plugin = new QuoteOfTheDayPlugin(requestMessage);
		} else if(requestMessage.startsWith("gma")){
			plugin = new GoodMorningPlugin(requestMessage);
			frequencyDescription = "dailyAt8";
		} else if(requestMessage.startsWith("bp")){
			plugin = new BitfinexBitcoinPricePlugin(requestMessage);
		} else if(requestMessage.startsWith("tmp")){
			plugin = new WeatherPlugin(requestMessage);
		} else if(requestMessage.startsWith("bs")){
			plugin = new AllAccountsStatusPlugin(fromPhoneNumber, requestMessage);
			frequencyDescription = "dailyAt20";
		} else {
			plugin = new Plugin(requestMessage);
		}
		this.responseMessage = plugin.getResponse();
	}
	
	public void checkForAlert() {
		String[] requestSplit = requestMessage.split("-");
		if(requestSplit.length > 1) {
			if(requestSplit[1].equalsIgnoreCase("alert")){
				JobDataMap dataMap = new JobDataMap();
				dataMap.put("PhoneNumber", fromPhoneNumber);
				dataMap.put("RequestMessage", requestSplit[0]);
				SettingsManager.taskManager.scheduleTask("job", "trigger", frequencyDescription, dataMap);
				responseMessage = "Task scheduled. ";
			}
		}
	}
	
	public String getTextResponse() {
		return responseMessage;
	}
}
