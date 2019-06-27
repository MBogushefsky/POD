package com.bogaware.service;

import com.bogaware.twilio.TwilioMessageManager;

public class CommandService {
	public static String executeCommand(String command) {
		String[] commandSplit = command.split(":");
		if(commandSplit[0].equalsIgnoreCase("sendto")) {
			TwilioMessageManager.sendMessageByPhoneNumber(commandSplit[1], commandSplit[2]);
			return "Sent!";
		}
		return "No command found";
	}
}
