package com.bogaware.twilio;

import org.quartz.JobDataMap;

import com.bogaware.plugins.AccountBalancesPlugin;
import com.bogaware.plugins.AllAccountsStatusPlugin;
import com.bogaware.plugins.BitfinexBitcoinPricePlugin;
import com.bogaware.plugins.GoodMorningPlugin;
import com.bogaware.plugins.HealthyMessagePlugin;
import com.bogaware.plugins.HelpPlugin;
import com.bogaware.plugins.LastDaysSpendingPlugin;
import com.bogaware.plugins.Plugin;
import com.bogaware.plugins.QuoteOfTheDayPlugin;
import com.bogaware.plugins.WeatherPlugin;
import com.bogaware.util.SettingsManager;

public class MessageHandler {
	private Plugin plugin;
	private String fromPhoneNumber = "";
	private String requestMessage = "";
	private String responseMessage = "";
	private String frequencyDescription = "";
	
	public MessageHandler(String fromPhoneNumber, String requestText) {
		this.fromPhoneNumber = fromPhoneNumber;
		this.requestMessage = requestText.toLowerCase();
		getPlugin();
		checkForAlert();
	}
	
	public void getPlugin() {
		if(requestMessage.equalsIgnoreCase("h")) {
			plugin = new HelpPlugin(requestMessage);
		} else if(requestMessage.startsWith("qotd")) {
			plugin = new QuoteOfTheDayPlugin(requestMessage);
		} else if(requestMessage.startsWith("gm")){
			plugin = new GoodMorningPlugin(requestMessage);
			frequencyDescription = "dailyAt8";
		} else if(requestMessage.startsWith("hm")){
			plugin = new HealthyMessagePlugin(requestMessage);
			frequencyDescription = "dailyAt11";
		} else if(requestMessage.startsWith("bp")){
			plugin = new BitfinexBitcoinPricePlugin(requestMessage);
		} else if(requestMessage.startsWith("lds")) {
			plugin = new LastDaysSpendingPlugin(requestMessage);
		} else if(requestMessage.startsWith("ab")) {
			plugin = new AccountBalancesPlugin(requestMessage);
		}
		/*else if(requestMessage.startsWith("tmp")){
			plugin = new WeatherPlugin(requestMessage);
		} else if(requestMessage.startsWith("bs")){
			plugin = new AllAccountsStatusPlugin(fromPhoneNumber, requestMessage);
			frequencyDescription = "dailyAt20";
		}*/ 
		else {
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
