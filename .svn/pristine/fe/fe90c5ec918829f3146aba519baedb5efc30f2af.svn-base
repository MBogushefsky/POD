package com.bogaware.global;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bogaware.service.Main;
import com.bogaware.service.accounts.BankAccount;
import com.plaid.client.PlaidClient;
import com.plaid.client.request.AccountsBalanceGetRequest;
import com.plaid.client.request.AuthGetRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.Account;
import com.plaid.client.response.AccountsBalanceGetResponse;
import com.plaid.client.response.AuthGetResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.plaid.client.response.TransactionsGetResponse;
import com.plaid.client.response.TransactionsGetResponse.Transaction;

import retrofit2.Response;

public class BankAccountManager {
	private int userId;
	private int institutionLinkId;
	private String accessToken;
	private static PlaidClient plaidClient;
	
	public BankAccountManager(int userId, int institutionLinkId, String accessToken) {
		this.userId = userId;
		this.institutionLinkId = institutionLinkId;
		this.accessToken = accessToken;
		plaidClient = PlaidClient.newBuilder()
				  .clientIdAndSecret(Global.properties.getProperty("PLAID_CLIENT_ID"), Global.properties.getProperty("PLAID_SECRET"))
				  .publicKey(Global.properties.getProperty("PLAID_PUBLIC_KEY")) 
				  .developmentBaseUrl() 
				  .build();
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

	public void linkAccount(String user, String pass, int instit) {
		String accessToken = "access-development-790eaacd-9e8b-49f6-9eb4-4a063e071139"; //Functions.getAccessToken(instit, user, pass);	TODO NEED TO IMPLEMENT
		List<Account> responseList = getAccountsFromLink(accessToken);
		if(responseList != null) {
			for(Account acc : responseList){
				addAccountToDatabase(Main.loggedInUser.getUserId(), instit, acc.getAccountId(), acc.getName(), acc.getBalances().getAvailable(), acc.getBalances().getCurrent(), user, pass, accessToken);
			}
		}
	}
	
	private List<Account> getAccountsFromLink(String accessToken) {
		List<Account> responseList = null;
		Response<AuthGetResponse> response = null;
		System.out.println(accessToken);
		try {
			response = plaidClient.service().authGet(new AuthGetRequest(accessToken)).execute();
			if (response.isSuccessful()) {
				responseList = response.body().getAccounts();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}
	
	private void addAccountToDatabase(int accOwner, int instit, String accountId, String name, double availBalance, double currBalance, String user, String pass, String accessToken) {
		try {
			//TODO CANNOT FLOOR DOLLAR VALUES
			DatabaseManager.updateFromDatabase("INSERT INTO bankaccounts SET ACCOUNT_OWNER=" + accOwner + ", ACCOUNT_NAME=\'" + DatabaseManager.stringToDBSafeString(name) + "\', INSTITUTION_ID=" + instit + ", ACCOUNT_ID=\'" + accountId + "\', TYPE=0, AVAILABLE_BALANCE=" + availBalance + ", CURRENT_BALANCE=" + currBalance + ", USERNAME=\'" + user + "\', PASSWORD=\'" + pass + "\', ACCESS_TOKEN=\'" + accessToken + "\', LAST_UPDATED=\'" + DateManager.calendarToSimpleDateString(Calendar.getInstance().getTime()) + "\';");
			//Get Transactions
			ArrayList<Transaction> transactions = getAllTransactionsFromAccount(accessToken, accountId);
			ArrayList<String> transactionStatements = new ArrayList<String>();
			for(Transaction trans : transactions) {			
				String transactionStatement = "INSERT INTO banktransactions SET ACCOUNT_ID=\'" + trans.getAccountId() + "\', TRANSACTION_ID=\'" + trans.getTransactionId() + "\', NAME=\'" + DatabaseManager.stringToDBSafeString(trans.getName()) + "\', AMOUNT=" + trans.getAmount() + ", APPLIED_DATE=\'" + trans.getDate() + "\';";
				transactionStatements.add(transactionStatement);
			}
			DatabaseManager.updateFromDatabase(transactionStatements);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<Transaction> getAllTransactionsFromAccount(String accessToken, String accountId) {
		ArrayList<Transaction> result = new ArrayList<Transaction>();
		ArrayList<String> accountIdList = new ArrayList<String>();
		accountIdList.add(accountId);
		for(int i = 0; i < 5; i++){
			Date startYearDate = (Date)DateManager.currentDate.clone();
			Date endYearDate = (Date)DateManager.currentDate.clone();
			startYearDate.setYear(DateManager.currentDate.getYear() - (i + 1));
			endYearDate.setYear(DateManager.currentDate.getYear() - i);
			TransactionsGetResponse yearResponse = getTransactionsByAccountWithinRange(accessToken, accountIdList, startYearDate, endYearDate);
			if(yearResponse.getTotalTransactions() > yearResponse.getTransactions().size()) {
				System.out.println("Going to split trans");
				Date startHalfYearDate = (Date)startYearDate.clone();
				Date endHalfYearDate = (Date)startYearDate.clone();
				endHalfYearDate.setDate(endHalfYearDate.getDate() + 182);
				TransactionsGetResponse firstHalfYearResponse = getTransactionsByAccountWithinRange(accessToken, accountIdList, startHalfYearDate, endHalfYearDate);
				if(firstHalfYearResponse.getTotalTransactions() > firstHalfYearResponse.getTransactions().size()) {
					System.out.println("ERROR FIRST HALF");
				} else {
					result.addAll(firstHalfYearResponse.getTransactions());
				}
				endHalfYearDate = (Date)startYearDate.clone();
				endHalfYearDate.setYear(endHalfYearDate.getYear() + 1);
				startHalfYearDate.setDate(startHalfYearDate.getDate() + 183);
				TransactionsGetResponse secondHalfYearResponse = getTransactionsByAccountWithinRange(accessToken, accountIdList, startHalfYearDate, endHalfYearDate);
				if(secondHalfYearResponse.getTotalTransactions() > secondHalfYearResponse.getTransactions().size()) {
					System.out.println("ERROR SECOND HALF");
				} else {
					result.addAll(secondHalfYearResponse.getTransactions());
				}
			} else {
				result.addAll(yearResponse.getTransactions());
			}
		}
		return result;
	}
	
	private TransactionsGetResponse getTransactionsByAccountWithinRange(String accessToken, ArrayList<String> accountIdInstance, Date startDate, Date endDate) {
		TransactionsGetResponse result = null;
		try {
			System.out.println("From: " + DateManager.calendarToSimpleDateString(startDate) + " To: " + DateManager.calendarToSimpleDateString(endDate));
			TransactionsGetRequest transactionRequest = new TransactionsGetRequest(accessToken, startDate, endDate);
			transactionRequest = transactionRequest.withCount(500);
			transactionRequest = transactionRequest.withAccountIds(accountIdInstance);
			Response<TransactionsGetResponse> response = plaidClient.service().transactionsGet(transactionRequest).execute();
			if (response.isSuccessful()) {
				result = response.body();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	private String getLinkSessionId() {
		JSONObject requestJson = new JSONObject();
		requestJson.put("link_version", "2.0.50");
		requestJson.put("public_key", Global.properties.getProperty("PLAID_PUBLIC_KEY"));
		String response = Functions.callPost("https://" + Global.properties.getProperty("PLAID_ENV") + "/link/client/get", requestJson.toJSONString(), null, null);
		JSONObject jsonReponse = RestManager.stringToJson(response);
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
		requestJson.put("public_key", Global.properties.getProperty("PLAID_PUBLIC_KEY"));
		String response = Functions.callPost("https://" + Global.properties.getProperty("PLAID_ENV") + "/link/item/create", requestJson.toJSONString(), null, null);
		JSONObject jsonReponse = RestManager.stringToJson(response);
		String publicToken = (String) jsonReponse.get("public_token");
		System.out.println("Public Token: " + publicToken);
		return publicToken;
	}
	
	private String getAccessToken(int instit, String user, String pass){
		String publicToken = getPublicToken(getLinkSessionId(), instit, user, pass);
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
	
	public static void printBanksAccounts(ArrayList<BankAccount> bankAccounts) {
		if(bankAccounts != null && bankAccounts.size() > 0) {
			System.out.println(bankAccounts.size() + " bank account(s) linked. ");
			for(int i = 0; i < bankAccounts.size(); i++) {
				BankAccount tempAccount = bankAccounts.get(i);
				System.out.println("Account " + (i + 1) + ": " + tempAccount.getName() + " with a current balance of $" + tempAccount.getCurrentBalance() + ". Last updated: " + DateManager.calendarToSimpleDateString(tempAccount.getLastUpdated()));
			}
		} else {
			System.out.println("No bank account linked. ");
		}
	}
}
