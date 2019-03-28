package com.bogaware.global;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bogaware.service.Application;
import com.bogaware.service.Main;
import com.coinbase.api.Coinbase;
import com.coinbase.api.CoinbaseBuilder;
import com.coinbase.api.entity.Account;
import com.coinbase.api.entity.Transaction;
import com.coinbase.api.exception.CoinbaseException;

public class CoinbaseAccountManager {
	private Coinbase coinbaseClient;
	
	public CoinbaseAccountManager() {
		coinbaseClient = new CoinbaseBuilder().build();
	}
	
	public void linkAccount(String apiKey, String apiSecret, int instit) {
		switch(instit) {
		case 1:
			coinbaseClient = new CoinbaseBuilder().withApiKey(apiKey, apiSecret).build();
		}
		List<Account> responseList = getAccountsFromLink();
		if(responseList != null) {
			for(Account acc : responseList){
				try {
					String currencyUnit = acc.getBalance().getCurrencyUnit().getCode();
					if(currencyUnit.equalsIgnoreCase("BTC")) {
						addAccountToDatabase(Main.loggedInUser.getUserId(), instit, acc.getId(), coinbaseClient.getUser().getEmail(), 0, acc.getBalance().getAmount().doubleValue(), apiKey, apiSecret);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private List<Account> getAccountsFromLink() {
		List<Account> responseList = null;
		try {
			responseList = coinbaseClient.getAccounts().getAccounts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}
	
	private void addAccountToDatabase(int accOwner, int instit, String accountId, String email, int type, double balance, String apiKey, String apiSecret) {
		try {
			DatabaseManager.updateFromDatabase("INSERT INTO bitcoinaccounts SET ACCOUNT_OWNER=" + accOwner + ", ACCOUNT_EMAIL=\'" + DatabaseManager.stringToDBSafeString(email) + "\', INSTITUTION_ID=" + instit + ", ACCOUNT_ID=\'" + accountId + "\', TYPE=" + type + ", BALANCE=" + balance + ", API_KEY=\'" + apiKey + "\', API_SECRET=\'" + apiSecret + "\', LAST_UPDATED=\'" + DateManager.calendarToSimpleDateString(Calendar.getInstance().getTime()) + "\';");
			ArrayList<Transaction> transactions = getAllTransactionsFromAccount(accountId);
			ArrayList<String> transactionStatements = new ArrayList<String>();
			for(Transaction trans : transactions) {
				String transNote = "";
				double transAmount = trans.getAmount().getAmount().doubleValue();
				if(transAmount > 0) {
					transNote = "Recieved BTC from " + trans.getSender().getName();
				} else if(transAmount < 0) {
					transNote = "Sent BTC to " + trans.getRecipientAddress();
				}
				String transactionStatement = "INSERT INTO bitcointransactions SET ACCOUNT_ID=\'" + accountId + "\', TRANSACTION_ID=\'" + trans.getId() + "\', NAME=\'" + DatabaseManager.stringToDBSafeString(transNote) + "\', AMOUNT=" + transAmount + ", APPLIED_DATE=\'" + DateManager.calendarToSimpleDateString(trans.getCreatedAt().toDate()) + "\';";
				transactionStatements.add(transactionStatement);
			}
			DatabaseManager.updateFromDatabase(transactionStatements);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<Transaction> getAllTransactionsFromAccount(String accountId) {
		ArrayList<Transaction> result = new ArrayList<Transaction>();
		try {
			result = (ArrayList<Transaction>) coinbaseClient.getTransactions().getTransactions();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public String getUserId() {
		try {
			return coinbaseClient.getUser().getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getName() {
		try {
			return coinbaseClient.getUser().getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	public String getAccountEmail() {
		try {
			return coinbaseClient.getUser().getEmail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public double getBTCBalance() {
		try {
			return coinbaseClient.getBalance("BTC").getAmount().doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public double getUSDBalance() {
		try {
			return coinbaseClient.getBalance("USD").getAmount().doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public double getTotalUSDValueAmount() {
		try {
			return coinbaseClient.getBalance("USD").getAmount().doubleValue() + (coinbaseClient.getBalance("BTC").getAmount().doubleValue() * coinbaseClient.getSpotPrice(coinbaseClient.getBalance("BTC").getCurrencyUnit().USD).getAmount().doubleValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
