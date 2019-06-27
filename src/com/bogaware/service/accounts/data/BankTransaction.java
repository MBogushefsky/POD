package com.bogaware.service.accounts.data;

import java.util.Date;

public class BankTransaction {
	
	private String details;
	private Date postingDate;
	private String description;
	private double amount;
	private String type;
	private double balance;

	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public Date getPostingDate() {
		return postingDate;
	}
	
	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
