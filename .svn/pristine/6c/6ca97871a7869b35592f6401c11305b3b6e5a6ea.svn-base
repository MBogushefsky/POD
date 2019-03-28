package com.bogaware.service.accounts;

import java.util.ArrayList;
import java.util.Date;
import com.bogaware.global.Global;
import com.bogaware.service.accounts.data.BankTransaction;
import com.bogaware.service.accounts.data.GenericTransaction;

public class GenericAccount {
	public int owner;
	public String name;
	public String id;
	public int institution;
	public int type;
	public double balance;
	public Date lastUpdated;
	public ArrayList<GenericTransaction> transactions;
	
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
	
	public double getBalance() {
		return balance;
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	public String toString() {
		return name + ": $" + Global.usdFormat.format(balance);
	}
}
