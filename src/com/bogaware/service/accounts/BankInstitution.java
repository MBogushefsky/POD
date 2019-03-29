package com.bogaware.service.accounts;

import java.util.ArrayList;
import java.util.Date;

import com.bogaware.service.BankAccountManager;

public class BankInstitution {
	private int id;
	private int owner;
	private String name;
	private int plaid_institution_id;
	private String username;
	private String password;
	private String accessToken;
	private Date lastUpdated;
	private ArrayList<BankAccount> bankAccounts;
	public BankInstitution(int id, int owner, String name, int plaid_institution_id, String username, String password, String accessToken, Date lastUpdated) {
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.plaid_institution_id = plaid_institution_id;		
		this.username = username; 
		this.password = password;
		this.accessToken = accessToken; 
		this.lastUpdated = lastUpdated;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public void getBankAccounts() {
		BankAccountManager bankMgr = new BankAccountManager(owner, id, accessToken);
		bankMgr.getInstitutionBankAccounts();
	}
	
	public String toString() {
		return "Name: " + this.name;
	}
}
