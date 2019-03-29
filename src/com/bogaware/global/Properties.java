package com.bogaware.global;

import java.sql.ResultSet;
import java.util.HashMap;

import com.bogaware.service.Application;
import com.bogaware.service.Main;
import com.bogaware.util.SettingsManager;

public class Properties {
	public HashMap<String, String> properties = new HashMap<String, String>();
	
	public Properties() {
		/*BufferedReader br;
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
		}*/
		properties.put("DATABASE_HOST", "104.198.105.8");
		properties.put("DATABASE_PORT", "3306");
		properties.put("DATABASE_USERNAME", "root");
		properties.put("DATABASE_PASSWORD", "o6Ywezyh");
		properties.put("DATABASE_SCHEMA", "cashpsychic");
		properties.put("FEDERAL_INCOME_TAX", "0.0559");
		properties.put("OASDI", "0.062");
		properties.put("MEDICARE", "0.0145");
		properties.put("ARIZONA_STATE_INCOME_TAX_WITHHOLDING", "0.042");
		properties.put("PAY_RATE", "15");
	}
	
	public void getAPISettings() {
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			ResultSet rs = DatabaseManager.queryFromDatabase("SELECT * FROM " + SettingsManager.properties.getProperty("DATABASE_SCHEMA") + ".settings;");
			while (rs.next())
			{
				int id = rs.getInt("ID");
				String field = rs.getString("FIELD");
				String value = rs.getString("VALUE");
				result.put(field, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		properties = result;
	}
	
	public String getProperty(String propertyName) {
		if(properties.containsKey(propertyName)) {
			return ((String) properties.get(propertyName)).trim();
		} else {
			Application.exitProgram("Property could not be retrived. ", 0);
			return null;
		}
	}
}
