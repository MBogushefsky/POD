package com.bogaware.service;
import java.util.ArrayList;
import java.util.List;

import com.bogaware.global.Functions;
import com.bogaware.service.accounts.BankAccount;
import com.bogaware.service.accounts.data.BankTransaction_OLD;
import com.bogaware.util.SettingsManager;
import com.plaid.client.PlaidClient;
import com.plaid.client.response.Account;
import com.plaid.client.response.TransactionsGetResponse.Transaction;

public class CommandCenter {
	
	public CommandCenter() {
		
		/*System.out.println("Client ID: " + Main.prop.getProperty("PLAID_CLIENT_ID"));
		System.out.println("Secret: " + Main.prop.getProperty("PLAID_SECRET"));
		System.out.println("Public Key: " + Main.prop.getProperty("PLAID_PUBLIC_KEY"));*/
		
	}
	
	public ArrayList<BankAccount> getBankAccounts() {
		List<Account> responseList = null;
		/*Response<AuthGetResponse> response = null;
		try {
			response = plaidClient.service().authGet(new AuthGetRequest(accessToken)).execute();
			if (response.isSuccessful()) {
				responseList = response.body().getAccounts();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return accountsToBankAccounts(responseList);
	}
	
	public ArrayList<BankAccount> accountsToBankAccounts(List<Account> accounts){
		ArrayList<BankAccount> result = new ArrayList<BankAccount>();
		/*for(Account acc : accounts){
			BankAccount tempAccount = new BankAccount(acc.getAccountId(), acc.getSubtype(), acc.getBalances().getAvailable(), acc.getBalances().getCurrent(), acc.getName(), acc.getMask());
			result.add(tempAccount);
		}*/
		return result;
	}
	
	public ArrayList<BankTransaction_OLD> getAllTransactions(){
		//return getTransactionsByAccounts(Main.accounts);
		return null;
	}
	
	public ArrayList<BankTransaction_OLD> transactionsToBankTransactions(List<Transaction> transactions){
		ArrayList<BankTransaction_OLD> result = new ArrayList<BankTransaction_OLD>();
		/*int count = 0;
		for(Transaction trans : transactions){
			BankTransaction tempAccount = new BankTransaction(trans.getAccountId(), trans.getAmount(), trans.getDate(), trans.getName(), trans.getPaymentMeta(), trans.getLocation(), trans.getCategory());
			result.add(tempAccount);
			count++;
		}
		System.out.println(Integer.MAX_VALUE+ ", "+transactions.size()+ ", "+count);*/
		return result;
	}
}
