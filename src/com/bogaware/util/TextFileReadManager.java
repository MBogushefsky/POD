package com.bogaware.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class TextFileReadManager {

	public static HashMap<String, String> readPropertiesFile() {
		HashMap<String, String> properties = new HashMap<String, String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("config/settings.properties"));
		    String line = br.readLine();
		    while (line != null) {
		    	if(line.length() > 0) {
		    		String[] parts = line.split("=");
		    		properties.put(parts[0], parts[1]);
		    	}
		        line = br.readLine();
		    }
		    br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
}
