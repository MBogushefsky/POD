package com.bogaware.service.accounts.data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bogaware.global.DateManager;
import com.plaid.client.response.TransactionsGetResponse.Transaction.Location;
import com.plaid.client.response.TransactionsGetResponse.Transaction.PaymentMeta;


public class BankTransaction_OLD extends GenericTransaction {
	private BankTransactionPaymentMeta paymentMeta;
	private BankTransactionLocation location; 
	private List<String> categories = new ArrayList<String>();
	
	public BankTransaction_OLD(String transId, String accId, double amt, String dt, String transName, PaymentMeta transPaymentMeta, Location transLocation, List<String> cate) {
		id = transId;
		fromAccountId = accId;
		amount = amt;
		date = DateManager.simpleDateStringToDate(dt);
		name = transName;
		if(transPaymentMeta != null) {
			paymentMeta = new BankTransactionPaymentMeta(transPaymentMeta.getByOrderOf(), transPaymentMeta.getPayee(), transPaymentMeta.getPayer(), transPaymentMeta.getPaymentMethod(), transPaymentMeta.getPaymentProcessor(), transPaymentMeta.getPpdId(), transPaymentMeta.getReason(), transPaymentMeta.getReferenceNumber());
		} else {
			paymentMeta = null;
		}
		if(transLocation != null) {
			location = new BankTransactionLocation(transLocation.getAddress(), transLocation.getCity(), transLocation.getState(), transLocation.getZip(), transLocation.getLat(), transLocation.getLon(), transLocation.getStoreNumber());
		} else {
			location = null;
		}
		categories = cate;
	}
	
	public String toString () {
		return fromAccountId+amount+date+name+(categories.size() > 0 ? (String) categories.get(0): "");
	}
}
