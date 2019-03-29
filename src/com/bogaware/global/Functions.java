package com.bogaware.global;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.bogaware.service.Main;
import com.bogaware.service.User;
import com.bogaware.service.accounts.BankAccount;
import com.bogaware.service.accounts.data.BankTransaction;
import com.bogaware.util.SettingsManager;
import com.plaid.client.PlaidClient;
import com.plaid.client.request.AuthGetRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.Account;
import com.plaid.client.response.AuthGetResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.plaid.client.response.TransactionsGetResponse;
import com.plaid.client.response.TransactionsGetResponse.Transaction;

import retrofit2.Response;

public class Functions {
	
	public static String dbUrl = "jdbc:mysql://" + SettingsManager.properties.getProperty("DATABASE_HOST") + ":" + SettingsManager.properties.getProperty("DATABASE_PORT") + "/" + SettingsManager.properties.getProperty("DATABASE_SCHEMA");
	public static Connection conn;
	
	public static void connectToDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, SettingsManager.properties.getProperty("DATABASE_USERNAME"), SettingsManager.properties.getProperty("DATABASE_PASSWORD"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet queryFromDatabase(String query) {
		//System.out.println(query);
		try
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			//TODO NEEDS TO CLOSE STATEMENT
			return rs;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static double getCurrentBalanceFromPhoneNumber(String phone) {
		double result = 0;
		try {
			ResultSet rs = queryFromDatabase("SELECT * FROM cashpsychic.accounts WHERE ACCOUNT_OWNER=1;");
			while (rs.next())
			{
				int id = rs.getInt("ID");
				result = rs.getDouble("CURRENT_BALANCE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static ArrayList<BankAccount> getAllCheckingAccounts(){
		ArrayList<BankAccount> result = new ArrayList<BankAccount>();
		/*ArrayList<BankAccount> checkingAccounts = Main.accounts;
		for(BankAccount acc : checkingAccounts){
			if(acc.getType() == Main.bankAccountTypes.get("checking").intValue()){
				result.add(acc);
			}
		}*/
		return result;
	}
	
	public static ArrayList<BankAccount> getAllSavingsAccounts(){
		ArrayList<BankAccount> result = new ArrayList<BankAccount>();
		/*ArrayList<BankAccount> savingsAccounts = Main.accounts;
		for(BankAccount acc : savingsAccounts){
			if(acc.getType() == Main.bankAccountTypes.get("savings").intValue()){
				result.add(acc);
			}
		}*/
		return result;
	}
	
	public static ArrayList<String> jsonArrayToArrayList(JSONArray array) {
		ArrayList<String> result = new ArrayList<String>();     
		if (array != null) { 
		   for (int i=0;i<array.size();i++){ 
			   result.add((String) array.get(i));
		   } 
		} 
		return result;
	}
	
	public static String callGet(String url, String[][] header, String regex) {
		HttpClient httpClient = new DefaultHttpClient();
		String result = "";
		try {
			HttpGet httpRequest = new HttpGet(url);
			// Execute HTTP request
			String[][] defaultHeaders = new String[][] {		//Set the default headers for each JIRA REST call
				{"Content-Type", "application/x-www-form-urlencoded"}, 
				{"Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"}, 
			};
			if(header == null) {
				header = new String[][] {};
			}
			String[][] allHeaders = append(defaultHeaders, header);		//Combine default and passed-in headers
			if (allHeaders != null) {	//Add all headers to the request header
				for (String[] param : allHeaders) {
					if (param[0] != null && param[1] != null) {
						httpRequest.setHeader(param[0], param[1]);
					}
				}
			}
			
			HttpResponse response = httpClient.execute(httpRequest);	//Execute request
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine() + " - GET");
			System.out.println("----------------------------------------");
			
			// Get hold of the response entity
			HttpEntity entity = response.getEntity();		//Get entity of response

			byte[] buffer = new byte[1024];
			if (entity != null) {
				InputStream inputStream = entity.getContent();
				try {
					int bytesRead = 0;
					BufferedInputStream bis = new BufferedInputStream(inputStream);
					while ((bytesRead = bis.read(buffer)) != -1) {
						String chunk = new String(buffer, 0, bytesRead);
						result += chunk;
					}
					if(result.length() > 0 && regex != null) {
						Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
						Matcher matcher = pattern.matcher(result);
						while (matcher.find()) {
							result = matcher.group(1);
						}
					}
				} catch (Exception e) {
					httpRequest.abort();
					e.printStackTrace();
				} finally {
					try {
						inputStream.close();
					} catch (Exception ignore) {
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String callPost(String url, String data, String[][] header, String regex) {
		HttpClient client = new DefaultHttpClient();
		String result = "";
		try {
			HttpPost post = new HttpPost(url);
			StringEntity input;
			input = new StringEntity(data);
			input.setContentType("application/json");
			
			String[][] defaultHeaders = new String[][] {
				{"Content-Type", "application/json"}
			};
			if(header == null) {
				header = new String[][] {};
			}
			String[][] allHeaders = append(defaultHeaders, header);
			if (allHeaders != null) {
				for (String[] param : allHeaders) {
					if (param[0] != null && param[1] != null) {
						post.setHeader(param[0], param[1]);
					}
				}
			}
			
			post.setEntity(input);
			HttpResponse response = client.execute(post);
			
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine() + " - POST");
			System.out.println("----------------------------------------");
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			/*if(result.length() > 0) {
				if(response.getStatusLine().getStatusCode() == 200) {
					if(regex != null) {
						Pattern pattern = Pattern.compile(regex);
						Matcher matcher = pattern.matcher(result);
						while (matcher.find()) {
							result = matcher.group(1);
						}
					}
				}
				
			}*/
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String[][] append(String[][] a, String[][] b) {		//Combine two two-dimensional arrays
		String[][] result = new String[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
