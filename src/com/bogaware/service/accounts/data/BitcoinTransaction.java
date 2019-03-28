package com.bogaware.service.accounts.data;

import java.util.List;

import com.bogaware.global.DateManager;

public class BitcoinTransaction extends GenericTransaction {
	public BitcoinTransaction(String transId, String accId, double amt, String dt, String transName) {
		id = transId;
		fromAccountId = accId;
		amount = amt;
		date = DateManager.simpleDateStringToDate(dt);
		name = transName;
	}
	
	public String toString () {
		return fromAccountId+amount+date+name;
	}
}
