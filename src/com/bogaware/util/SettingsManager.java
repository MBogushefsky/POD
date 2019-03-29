package com.bogaware.util;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import com.bogaware.global.DatabaseManager;
import com.bogaware.global.Properties;
import com.bogaware.global.TaskManager;
import com.bogaware.service.Application;

public class SettingsManager {
	public static Properties properties;
	public static TaskManager taskManager;
	public static DecimalFormat usdFormat = new DecimalFormat("#.00");

	public SettingsManager() {
		properties = new Properties();
		SettingsManager.properties.getAPISettings();
		taskManager = new TaskManager();
	}
	
	
	
	
	
	public static void printArrayList(Object objList) {
		ArrayList list = (ArrayList) objList;
		String result = "";
		for(int i = 0; i < list.size(); i++) {
			result += list.get(i) + "\n";
		}
		System.out.println(result);
	}
	
	
}
