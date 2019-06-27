package com.bogaware.service.accounts;
import java.util.ArrayList;
import java.util.Date;

import com.bogaware.global.DateManager;
import com.bogaware.service.accounts.data.BankTransaction_OLD;
import com.bogaware.service.accounts.data.BitcoinTransaction;
import com.bogaware.service.accounts.data.GenericTransaction;
import com.bogaware.util.SettingsManager;

public class BankAccount extends GenericAccount {
	private String username;
	private String password;
	private double availableBalance;
	private double currentBalance;
	private String accessToken;
	private String publicToken;
	private String linkSessionId;
	
	public BankAccount(int accOwner, String accountName, int institId, String accId, int accType, String user, String pass, double availBalance, double currBalance, String accessTok, String lastUp) {
		this.owner = accOwner;
		this.name = accountName;
		this.institution = institId;
		this.id = accId;
		this.type = accType;
		this.username = user;
		this.password = pass;
		this.availableBalance = availBalance;
		this.currentBalance = currBalance;
		this.balance = currBalance;
		this.accessToken = accessTok;
		this.lastUpdated = DateManager.simpleDateStringToDate(lastUp);
	}
	
	public String getAccountId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getInstitutionId() {
		return institution;
	}
	
	public int getType(){
		return type;
	}
	
	public double getCurrentBalance() {
		return currentBalance;
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	public ArrayList<GenericTransaction> getAllTransactions(){
		return transactions;
	}
	
	public void setAllTransactions(ArrayList<BankTransaction_OLD> bankTrans) {
		ArrayList<GenericTransaction> result = new ArrayList<GenericTransaction>();
		for(BankTransaction_OLD trans : bankTrans) {
			result.add(trans);
		}
		transactions = result;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public String toString() {
		return name + ": $" + SettingsManager.usdFormat.format(currentBalance);
	}
}
