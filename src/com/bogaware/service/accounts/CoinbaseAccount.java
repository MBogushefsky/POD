package com.bogaware.service.accounts;

import java.util.ArrayList;
import java.util.Date;

import com.bogaware.global.DateManager;
import com.bogaware.service.accounts.data.BankTransaction_OLD;
import com.bogaware.service.accounts.data.BitcoinTransaction;
import com.bogaware.service.accounts.data.GenericTransaction;
import com.bogaware.util.SettingsManager;

public class CoinbaseAccount extends GenericAccount{
	private String apiKey;
	private String apiSecret;
	private String email;
	private double btcBalance;
	
	public CoinbaseAccount(int accOwner, String accEmail, int institId, String accId, int accType, String accApiKey, String accApiSecret, double accBtcBalance, String lastUp) {
		this.owner = accOwner;
		this.name = accEmail;
		this.email = accEmail;
		this.institution = institId;
		this.id = accId;
		this.type = accType;
		this.apiKey = accApiKey;
		this.apiSecret = accApiSecret;
		this.btcBalance = accBtcBalance;
		this.balance = getTotalUSDValueAmount();
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
	
	public double getBTCBalance() {
		return btcBalance;
	}
	
	public double getTotalUSDValueAmount() {
		return btcBalance + 40;			//TODO NEED TO CONVERT TO USD VALUE
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	public ArrayList<GenericTransaction> getAllTransactions(){
		return transactions;
	}
	
	public void setAllTransactions(ArrayList<BitcoinTransaction> bitcoinTrans) {
		ArrayList<GenericTransaction> result = new ArrayList<GenericTransaction>();
		for(BitcoinTransaction trans : bitcoinTrans) {
			result.add(trans);
		}
		transactions = result;
	}
	
	public String toString() {
		return name + ": $" + SettingsManager.usdFormat.format(getTotalUSDValueAmount());
	}
}