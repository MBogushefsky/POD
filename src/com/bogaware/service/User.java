package com.bogaware.service;

import java.util.ArrayList;

import com.bogaware.service.accounts.BankAccount;
import com.bogaware.service.accounts.BankInstitution;
import com.bogaware.service.accounts.CoinbaseAccount;
import com.bogaware.service.accounts.PaypalAccount;

public class User {
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private ArrayList<BankInstitution> bankInstitutions = new ArrayList<BankInstitution>();
	private ArrayList<PaypalAccount> paypalAccounts = new ArrayList<PaypalAccount>();
	private ArrayList<CoinbaseAccount> bitcoinAccounts = new ArrayList<CoinbaseAccount>();
	
	public User(int userId, String user, String pass, String fName, String lName, String userEmail, String phone) {
		id = userId;
		username = user;
		password = pass;
		firstName = fName;
		lastName = lName;
		email = userEmail;
		phoneNumber = phone;
	}
	
	public int getUserId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public void setBankInstitutions(ArrayList<BankInstitution> inputBankInstits){
		this.bankInstitutions = inputBankInstits;
	}
	
	public ArrayList<BankInstitution> getBankInstitutions(){
		return bankInstitutions;
	}
	
	public void setPaypalAccounts(ArrayList<PaypalAccount> inputAccount){
		this.paypalAccounts = inputAccount;
	}
	
	public ArrayList<PaypalAccount> getPaypalAccounts(){
		return paypalAccounts;
	}
	
	public void setBitcoinAccounts(ArrayList<CoinbaseAccount> inputAccount){
		this.bitcoinAccounts = inputAccount;
	}
	
	public ArrayList<CoinbaseAccount> getBitcoinAccounts(){
		return bitcoinAccounts;
	}
	
	public String getStatus() {
		String resultStatus = "";
		resultStatus += "Username: " + username + "\n";
		/*resultStatus += "Bank Accounts: \n";
		for(int i = 0; i < bankAccounts.size(); i++) {
			resultStatus += "Account " + (i + 1) + ": " + bankAccounts.get(i) + "\n";
		}*/
		resultStatus += "Paypal Accounts: \n";
		for(int i = 0; i < paypalAccounts.size(); i++) {
			resultStatus += "Account " + (i + 1) + ": \n";
		}
		resultStatus += "Bitcoin Accounts: \n";
		for(int i = 0; i < bitcoinAccounts.size(); i++) {
			resultStatus += "Account " + (i + 1) + ": \n";
		}
		return resultStatus;
	}
}
