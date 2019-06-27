package com.bogaware.plugins;

import java.util.List;

import com.bogaware.plaid.PlaidService;
import com.plaid.client.response.Account;

public class AccountBalancesPlugin extends Plugin {
	
	public AccountBalancesPlugin(String inputText) {
		super(inputText);
		generateResponse();
	}
	public void generateResponse() {
		PlaidService plaidService = new PlaidService();
		String resultString = "";
		List<Account> accounts = plaidService.getAccounts();
		for(Account account : accounts) {
			resultString += account.getSubtype() + ": " + account.getBalances().getCurrent() + "\n";
		}
		response = resultString;
	}
}