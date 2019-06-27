package com.bogaware.plugins;

import java.util.ArrayList;

import com.bogaware.service.Application;
import com.bogaware.service.User;
import com.bogaware.service.accounts.BankAccount;
import com.bogaware.service.accounts.CoinbaseAccount;
import com.bogaware.service.accounts.PaypalAccount;

public class AllAccountsStatusPlugin extends Plugin {
	private String fromPhoneNumber = "";
	public AllAccountsStatusPlugin(String fromPhoneNumberInput, String inputText) {
		super(inputText);
		this.fromPhoneNumber = fromPhoneNumberInput;
		generateResponse();
	}
	public void generateResponse() {
		User retrievedUser = Application.getUserFromPhoneNumber(fromPhoneNumber);
		if(retrievedUser != null) {
			response = "--Account Statuses--";
			/*ArrayList<BankAccount> bankAccounts = retrievedUser.getBankAccounts();
			if(bankAccounts.size() > 0) {
				for(BankAccount acc : bankAccounts) {
					response += acc + "\n";
				}
			} else {
				response += "Bank Accounts: $0.00\n";
			}
			ArrayList<PaypalAccount> paypalAccounts = retrievedUser.getPaypalAccounts();
			if(paypalAccounts.size() > 0) {
				//TODO NEEDS TO BE COMPLETED
			} else {
				response += "Paypal: $0.00\n";
			}
			ArrayList<CoinbaseAccount> bitcoinAccounts = retrievedUser.getBitcoinAccounts();
			if(bitcoinAccounts.size() > 0) {
				//TODO NEEDS TO BE COMPLETED
			} else {
				response += "Bitcoin: $0.00";
			}*/
		} else {
			response = "User not found. ";
		}
	}
}
