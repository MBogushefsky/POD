package com.bogaware.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.bogaware.global.DatabaseManager;
import com.bogaware.service.accounts.BankAccount;
import com.bogaware.service.accounts.CoinbaseAccount;
import com.bogaware.service.accounts.PaypalAccount;
import com.bogaware.service.accounts.data.BankTransaction_OLD;
import com.bogaware.service.accounts.data.BitcoinTransaction;
import com.bogaware.util.SettingsManager;

public class Application {
	public static User login(String username, String password) {
		System.out.print("Logging in... ");
		User user = getUserFromDb(username, password);
		if(user != null) {
			/*user.setBankAccounts(getBankAccountsFromUser(user));
			user.setPaypalAccounts(getPaypalAccountsFromUser(user));
			user.setBitcoinAccounts(getBitcoinAccountsFromUser(user));*/
			return user;
		} else {
			System.out.println("Incorrent username and/or password. \n");
			return null;
		}
	}
	
	public static User getUserFromPhoneNumber(String phoneNumber) {
		User user = getUserByPhoneNumberFromDb(phoneNumber);
		if(user != null) {
			/*user.setBankAccounts(getBankAccountsEssentialFromUser(user));
			user.setPaypalAccounts(getPaypalAccountsEssentialFromUser(user));
			user.setBitcoinAccounts(getBitcoinAccountsEssentialFromUser(user));*/
			return user;
		} else {
			System.out.println("No user found with that phone number. \n");
			return null;
		}
	}

	private static User getUserFromDb(String username, String password) {
		User resultUser = null;
		try {
			ResultSet rs = DatabaseManager.queryFromDatabase("SELECT * FROM users WHERE USERNAME=\'" + username + "\' and PASSWORD=\'" + password + "\';");
			while (rs.next())
			{
				int id = rs.getInt("ID");
				String user = rs.getString("USERNAME");
				String pass = rs.getString("PASSWORD");
				String fName = rs.getString("FIRST_NAME");
				String lName = rs.getString("LAST_NAME");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE_NUMBER");
				resultUser = new User(id, user, pass, fName, lName, email, phone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultUser;
	}
	
	private static User getUserByPhoneNumberFromDb(String phoneNumber) {
		User resultUser = null;
		try {
			ResultSet rs = DatabaseManager.queryFromDatabase("SELECT * FROM users WHERE PHONE_NUMBER=\'" + phoneNumber + "\';");
			while (rs.next())
			{
				int id = rs.getInt("ID");
				String user = rs.getString("USERNAME");
				String pass = rs.getString("PASSWORD");
				String fName = rs.getString("FIRST_NAME");
				String lName = rs.getString("LAST_NAME");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE_NUMBER");
				resultUser = new User(id, user, pass, fName, lName, email, phone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultUser;
	}
	
	private static ArrayList<BankAccount> getBankAccountsFromUser(User user){
		ArrayList<BankAccount> result = new ArrayList<BankAccount>();
		try {
			ResultSet rs = DatabaseManager.queryFromDatabase("SELECT * FROM bankaccounts WHERE ACCOUNT_OWNER=" + user.getUserId() + ";");
			while (rs.next())
			{
				int id = rs.getInt("ID");
				BankAccount tempAccount = new BankAccount(rs.getInt("ACCOUNT_OWNER"), rs.getString("ACCOUNT_NAME"), rs.getInt("INSTITUTION_ID"), rs.getString("ACCOUNT_ID"), rs.getInt("TYPE"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getDouble("AVAILABLE_BALANCE"), rs.getDouble("CURRENT_BALANCE"), rs.getString("ACCESS_TOKEN"), rs.getString("LAST_UPDATED"));
				tempAccount.setAllTransactions(getTransactionsFromBankAccount(rs.getString("ACCOUNT_ID")));
				result.add(tempAccount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static ArrayList<BankAccount> getBankAccountsEssentialFromUser(User user){
		ArrayList<BankAccount> result = new ArrayList<BankAccount>();
		try {
			ResultSet rs = DatabaseManager.queryFromDatabase("SELECT * FROM bankaccounts WHERE ACCOUNT_OWNER=" + user.getUserId() + ";");
			while (rs.next())
			{
				int id = rs.getInt("ID");
				BankAccount tempAccount = new BankAccount(rs.getInt("ACCOUNT_OWNER"), rs.getString("ACCOUNT_NAME"), rs.getInt("INSTITUTION_ID"), rs.getString("ACCOUNT_ID"), rs.getInt("TYPE"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getDouble("AVAILABLE_BALANCE"), rs.getDouble("CURRENT_BALANCE"), rs.getString("ACCESS_TOKEN"), rs.getString("LAST_UPDATED"));
				result.add(tempAccount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static ArrayList<BankTransaction_OLD> getTransactionsFromBankAccount(String accoundId) {
		ArrayList<BankTransaction_OLD> result = new ArrayList<BankTransaction_OLD>();
		try {
			ResultSet rs = DatabaseManager.queryFromDatabase("SELECT * FROM banktransactions WHERE ACCOUNT_ID=\'" + accoundId + "\' ORDER BY APPLIED_DATE DESC;");
			while (rs.next())
			{
				int id = rs.getInt("ID");
				BankTransaction_OLD tempTrans = new BankTransaction_OLD(rs.getString("TRANSACTION_ID"), rs.getString("ACCOUNT_ID"), rs.getDouble("AMOUNT"), rs.getString("APPLIED_DATE"), rs.getString("NAME"), null, null, null);
				result.add(tempTrans);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static ArrayList<PaypalAccount> getPaypalAccountsFromUser(User user) {
		return new ArrayList<PaypalAccount>();
	}
	
	private static ArrayList<PaypalAccount> getPaypalAccountsEssentialFromUser(User user) {
		return new ArrayList<PaypalAccount>();
	}
	
	private static ArrayList<CoinbaseAccount> getBitcoinAccountsFromUser(User user) {
		ArrayList<CoinbaseAccount> result = new ArrayList<CoinbaseAccount>();
		try {
			ResultSet rs = DatabaseManager.queryFromDatabase("SELECT * FROM bitcoinaccounts WHERE ACCOUNT_OWNER=" + user.getUserId() + ";");
			while (rs.next())
			{
				int id = rs.getInt("ID");
				CoinbaseAccount tempAccount = new CoinbaseAccount(rs.getInt("ACCOUNT_OWNER"), rs.getString("ACCOUNT_EMAIL"), rs.getInt("INSTITUTION_ID"), rs.getString("ACCOUNT_ID"), rs.getInt("TYPE"), rs.getString("API_KEY"), rs.getString("API_SECRET"), rs.getDouble("BALANCE"), rs.getString("LAST_UPDATED"));
				tempAccount.setAllTransactions(getTransactionsFromBitcoinAccount(rs.getString("ACCOUNT_ID")));
				result.add(tempAccount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static ArrayList<CoinbaseAccount> getBitcoinAccountsEssentialFromUser(User user) {
		return new ArrayList<CoinbaseAccount>();
	}
	
	private static ArrayList<BitcoinTransaction> getTransactionsFromBitcoinAccount(String accoundId) {
		ArrayList<BitcoinTransaction> result = new ArrayList<BitcoinTransaction>();
		try {
			ResultSet rs = DatabaseManager.queryFromDatabase("SELECT * FROM bitcointransactions WHERE ACCOUNT_ID=\'" + accoundId + "\' ORDER BY APPLIED_DATE DESC;");
			while (rs.next())
			{
				int id = rs.getInt("ID");
				BitcoinTransaction tempTrans = new BitcoinTransaction(rs.getString("TRANSACTION_ID"), rs.getString("ACCOUNT_ID"), rs.getDouble("AMOUNT"), rs.getString("APPLIED_DATE"), rs.getString("NAME"));
				result.add(tempTrans);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void updateUser(User loggedInUser) {
		return;
	}
	
	public static void exitProgram(String errorMessage, int code) {
		System.err.println(errorMessage);
		System.exit(code);
	}
}
