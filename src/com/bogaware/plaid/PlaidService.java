package com.bogaware.plaid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bogaware.global.Functions;
import com.bogaware.service.accounts.BankAccount;
import com.bogaware.util.RestManager;
import com.bogaware.util.SettingsManager;
import com.plaid.client.PlaidClient;
import com.plaid.client.request.AccountsBalanceGetRequest;
import com.plaid.client.request.IncomeGetRequest;
import com.plaid.client.request.InstitutionsSearchRequest;
import com.plaid.client.request.ItemAccessTokenUpdateVersionRequest;
import com.plaid.client.request.ItemPublicTokenCreateRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.SandboxPublicTokenCreateRequest;
import com.plaid.client.request.common.Product;
import com.plaid.client.response.Account;
import com.plaid.client.response.AccountsBalanceGetResponse;
import com.plaid.client.response.Institution;
import com.plaid.client.response.InstitutionsSearchResponse;
import com.plaid.client.response.ItemAccessTokenUpdateVersionResponse;
import com.plaid.client.response.ItemPublicTokenCreateResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;

import retrofit2.Call;
import retrofit2.Response;

public class PlaidService {

	private static final String PLAID_ENV = "development.plaid.com";
	private static final String PLAID_CLIENT_ID = "59948fcabdc6a40e7648fbc6";
	private static final String PLAID_PUBLIC_KEY = "ce84441114a95a4795f66b9bddb36f";
	private static final String PLAID_SECRET = "c53d41f30231b6e72d0cd41ef02e3e";
	private static PlaidClient plaidClient;
	private static String linkSessionId;
	private static String publicToken;
	private static String accessToken = "access-development-dddd1097-d441-49ee-a72c-ace4e758257b";
		
	public PlaidService() {
		plaidClient = PlaidClient.newBuilder()
				  .clientIdAndSecret(PLAID_CLIENT_ID, PLAID_SECRET)
				  .publicKey(PLAID_PUBLIC_KEY) 
				  .developmentBaseUrl() 
				  .build();
		
	}
	
	public List<Account> getAccounts() {
		Response<AccountsBalanceGetResponse> response = null;
		try {
			response = plaidClient.service().accountsBalanceGet(new AccountsBalanceGetRequest(accessToken)).execute();
			return response.body().getAccounts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getInstitutionBankAccounts() {
		List<Account> responseList = null;
		ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		Response<AccountsBalanceGetResponse> response = null;
		try {
			response = plaidClient.service().accountsBalanceGet(new AccountsBalanceGetRequest(accessToken)).execute();
			if (response.isSuccessful()) {
				responseList = response.body().getAccounts();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String resultString = "";
		for(Account acc : responseList) {
			//bankAccounts.add(new BankAccount(userId, acc.getName(), acc., accId, accType, user, pass, availBalance, currBalance, accessTok, lastUp))
			//System.out.println(acc.getName());
			resultString += acc.getName();
		}
		return resultString;
	}
	
	/*private String getLinkSessionId() {
		JSONObject requestJson = new JSONObject();
		requestJson.put("link_version", "2.0.50");
		requestJson.put("public_key", PLAID_PUBLIC_KEY);
		String responseString = Functions.callPost("https://" + PLAID_ENV + "/link/client/get", requestJson.toJSONString(), null, null);
		JSONObject jsonReponse = RestManager.stringToJson(responseString);
		String linkSessionId = (String) jsonReponse.get("link_session_id");
		System.out.println("Session: " + linkSessionId);
		return linkSessionId;
	}
	
	private String getPublicToken(String linkSessionId, int instit, String user, String pass) {
		JSONObject requestJson = new JSONObject();
		JSONObject credentialJson = new JSONObject();
		credentialJson.put("username", user);
		credentialJson.put("password", pass);
		JSONArray productJson = new JSONArray();
		productJson.add("transactions");
		requestJson.put("credentials", credentialJson);
		requestJson.put("initial_products", productJson);
		requestJson.put("institution_id", "ins_" + instit);
		requestJson.put("link_session_id", linkSessionId);
		requestJson.put("options", new JSONObject());
		requestJson.put("public_key", PLAID_PUBLIC_KEY);
		String response = Functions.callPost("https://" + PLAID_ENV + "/link/item/create", requestJson.toJSONString(), null, null);
		System.out.println("RESPONSE: " + response);
		JSONObject jsonReponse = RestManager.stringToJson(response);
		String publicToken = (String) jsonReponse.get("public_token");
		System.out.println("Public Token: " + publicToken);
		return publicToken;
	}*/
	
	private String getAccessToken() {
		String accessToken = null;
		Response<ItemPublicTokenExchangeResponse> response = null;
		try {
			response = plaidClient.service().itemPublicTokenExchange(new ItemPublicTokenExchangeRequest(publicToken)).execute();
			if (response.isSuccessful()) {
				accessToken = response.body().getAccessToken();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Access Token: " + accessToken);
		return accessToken;
	}
}
