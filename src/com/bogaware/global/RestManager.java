package com.bogaware.global;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RestManager {
	public static String callGet(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		String result = "";
		try {
			HttpGet httpRequest = new HttpGet(url);
			
			HttpResponse response = httpClient.execute(httpRequest);	
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine() + " - GET");
			System.out.println("----------------------------------------");
			HttpEntity entity = response.getEntity();		

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
	
	public static String callPost(String url, String data, String[][] header) {
		HttpClient client = new DefaultHttpClient();
		String result = "";
		try {
			HttpPost post = new HttpPost(url);
			StringEntity input;
			input = new StringEntity(data);
			if(header == null) {
				header = new String[][] {};
			}
			for (String[] param : header) {
				if (param[0] != null && param[1] != null) {
					post.setHeader(param[0], param[1]);
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
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static JSONObject stringToJson(String response) {
		JSONObject result = null;
		JSONParser parser = new JSONParser();
		try {
			result = (JSONObject) parser.parse(response);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static Object getJsonFromPath(JSONObject json, String path) {
		String[] pathSplit = path.split("/");
		for(int i = 0; i < pathSplit.length; i++) {
			if(!pathSplit[i].equals("") && json.containsKey(pathSplit[i])) {
				Object tempObj = json.get(pathSplit[i]);
				if (tempObj instanceof String) {
					return tempObj;
				} else if(tempObj instanceof JSONObject) {
					json = (JSONObject) json.get(pathSplit[i]);
				}
			}
		}
		return (Object) json;
	}
}
