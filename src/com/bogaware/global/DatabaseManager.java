package com.bogaware.global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.bogaware.service.Main;
import com.bogaware.service.User;

public class DatabaseManager {
	public static String dbUrl = "jdbc:mysql://" + Global.properties.getProperty("DATABASE_HOST") + ":" + Global.properties.getProperty("DATABASE_PORT") + "/" + Global.properties.getProperty("DATABASE_SCHEMA");
	public static Connection conn;
	
	public static Connection getDBConnection() {
		if(conn == null) {
			try {
				conn = DriverManager.getConnection(dbUrl, Global.properties.getProperty("DATABASE_USERNAME"), Global.properties.getProperty("DATABASE_PASSWORD"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	public static ResultSet queryFromDatabase(String query) {
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = getDBConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			return rs;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean updateFromDatabase(String updateStatement) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
		System.out.println(updateStatement);
		try {
			Statement sta = conn.createStatement();		
			sta.execute(updateStatement);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean updateFromDatabase(ArrayList<String> updateStatement) {
		try {
			Statement sta = conn.createStatement();
			for(int i = 0; i < updateStatement.size(); i++) {
				System.out.println("Executing statement " + (i + 1) + " of " + (updateStatement.size() + 1));
				sta.execute(updateStatement.get(i));
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String stringToDBSafeString(String string) {
		return string.replace("\'", "\\'");
	}
}
