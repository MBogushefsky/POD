package com.bogaware.service.accounts.data;

import java.util.Date;

public class GenericTransaction {
	public String id;
	public String fromAccountId;
	public double amount;
	public Date date;
	public String name;	
	
	public String getName(){
		return name;
	}
	
	public Date getDate(){
		return date;
	}
}
