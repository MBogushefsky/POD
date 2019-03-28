package com.bogaware.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bogaware.global.BankAccountManager;
import com.bogaware.global.BankInstitutionManager;
import com.bogaware.global.CoinbaseAccountManager;
import com.bogaware.global.Global;
import com.bogaware.global.Output;
import com.bogaware.global.PhoneManager;
import com.bogaware.global.Properties;
import com.bogaware.global.RestManager;
import com.bogaware.service.accounts.BankAccount;
import com.bogaware.service.accounts.BankInstitution;
import com.bogaware.service.accounts.data.BankTransaction;
import com.coinbase.api.Coinbase;
import com.coinbase.api.CoinbaseBuilder;

public class Main {

	private static Report report;

	/*
	 * CACHED OBJECTS BELOW
	 */
	public static User loggedInUser;
	public static ArrayList<BankAccount> linkedAccounts = new ArrayList<BankAccount>();
	public static HashMap<String, Integer> bankAccountTypes = new HashMap<String, Integer>(); // TODO ADD TO DB
	public static ArrayList<BankTransaction> storedTransactions = new ArrayList<BankTransaction>();

	public static void main(String[] args) {
		Global global = new Global();
		
		bankAccountTypes.put("checking", 0);
		bankAccountTypes.put("savings", 1);

		Scanner input = new Scanner(System.in);

		System.out.println("Welcome to Cash Psychic!");

		loggedInUser = Application.login("mbogushefsky", "password");
		System.out.println("Please login to continue: ");
		while (loggedInUser == null) {
			System.out.print("Username: ");
			String inputtedUsername = input.nextLine();
			System.out.print("Password: ");
			String inputtedPassword = input.nextLine();
			loggedInUser = Application.login(inputtedUsername, inputtedPassword);
		}
		System.out.println("Logged In.");

		System.out.println("What would you like to do? ");
		String actionInput = "";
		while (actionInput != null) {
			System.out.print("User Status(1), Bank Account Statuses(2), Paypal Account Statuses(3), Bitcoin Account Statuses(4), Link Account(5), Exit Program(6): ");
			actionInput = input.nextLine();
			if (actionInput.equalsIgnoreCase("1")) {
				System.out.println(loggedInUser.getStatus());
			} else if (actionInput.equalsIgnoreCase("2")) {
				BankInstitutionManager bankInstitMgr = new BankInstitutionManager();
				ArrayList<BankInstitution> bankInstits = bankInstitMgr.getBankInstitutions(loggedInUser.getUserId());
				Global.printArrayList(bankInstits);
			
				/*String resultStatus = "";
				ArrayList<BankAccount> bankAccounts = loggedInUser.getBankAccounts();
				for(int i = 0; i < bankAccounts.size(); i++) {
					resultStatus += "Account " + (i + 1) + ": " + bankAccounts.get(i) + "\n";
				}
				System.out.println(resultStatus);*/
			} else if (actionInput.equalsIgnoreCase("3")) {
				System.out.println("Paypal Account Statuses Goes Here... ");
			} else if (actionInput.equalsIgnoreCase("4")) {
				System.out.println("Bitcoin Account Statuses Goes Here... ");
			} else if (actionInput.equalsIgnoreCase("5")) {
				System.out.print("Link bank account(1), paypal account(2), or bitcoin account(3)?: ");
				actionInput = "3";//input.nextLine();
				if (actionInput.equalsIgnoreCase("1")) {
					System.out.println("Linking Chase account");

					System.out.print("Chase Username: ");
					String inputtedChaseUser = input.nextLine();
					System.out.print("Chase Password: ");
					String inputtedChasePass = input.nextLine();

					//String inputtedChaseUser = "mbogushefsky8";
					//String inputtedChasePass = "wumbyBCeZej2BJFeYoRpoThu";
					/*BankAccountManager bankMgr = new BankAccountManager();
					bankMgr.linkAccount(inputtedChaseUser, inputtedChasePass, 3);*/
				} else if (actionInput.equalsIgnoreCase("2")) {
					// Go to https://www.paypal.com/businessmanage/credentials/apiAccess for access
					/*
					 * Map<String, String> customConfigurationMap = new HashMap<String, String>();
					 * customConfigurationMap.put("mode", "live"); // Load the map with all
					 * mandatory parameters customConfigurationMap.put("acct1.UserName",
					 * "mbogushefsky_api1.gmail.com"); customConfigurationMap.put("acct1.Password",
					 * "MCJBGH3J4DPX724C"); customConfigurationMap.put("acct1.Signature",
					 * "Ai1PaghZh5FmBLCDCTQpwG8jB264AjrY7W7wPrPoIILEqXYUzOfGWAhS");
					 * customConfigurationMap.put("acct1.AppId", "APP-80W284485P519543T");
					 * 
					 * PayPalAPIInterfaceServiceService paypalInterface = new
					 * PayPalAPIInterfaceServiceService(customConfigurationMap); try {
					 * paypalInterface.getBalance(new GetBalanceReq()); } catch (Exception e) {
					 * e.printStackTrace(); }
					 */
					// TODO NEED TO TAKE OUT MAVEN SOURCE!!!
				} else if (actionInput.equalsIgnoreCase("3")) {
					System.out.println("Linking Coinbase account (API v1 - all accounts with balance/user/transactions)");
					System.out.print("Coinbase API Key: ");
					String inputApiKey = "8ETsLCiizcfMrKdr"; //input.nextLine();
					System.out.print("Coinbase API Secret: ");
					String inputApiSecret = "2yWEyjPOrWNeBYwncPaSyHSkMpVoOEg1"; //input.nextLine();
					// 8ETsLCiizcfMrKdr
					// 2yWEyjPOrWNeBYwncPaSyHSkMpVoOEg1
					CoinbaseAccountManager bitcoinMgr = new CoinbaseAccountManager();
					bitcoinMgr.linkAccount(inputApiKey, inputApiSecret, 1);
					System.out.println(bitcoinMgr.getUserId());
					System.out.println(bitcoinMgr.getName());
					System.out.println(bitcoinMgr.getAccountEmail());
					System.out.println(bitcoinMgr.getBTCBalance());
					System.out.println(bitcoinMgr.getUSDBalance());
					System.out.println(bitcoinMgr.getTotalUSDValueAmount());
				}
			} else if (actionInput.equalsIgnoreCase("6")) {
				System.out.println("Program exiting...");
				Application.exitProgram("User initiated exit. ", 0);
			} else {
				System.out.println("Incorrect input given. ");
			}
		}

		// command.linkAccount
		/*
		 * accounts = command.getBankAccounts();
		 * 
		 * ArrayList<BankTransaction> transactions =
		 * command.getTransactionsByAccounts(Functions.getAllCheckingAccounts());
		 * for(BankAccount x : accounts){ System.out.println(x.getName()); } int count =
		 * 0;
		 */
		// for(BankTransaction x : transactions){ System.out.println(x.getName() + " on:
		// " + x.getDate().toString() + ", count: " +count); count++; }
		/*
		 * ArrayList<Transaction> checkingTransactions =
		 * command.getCheckingTransactions();
		 */

		// Use builder to create a client

		// System.out.println((Account) accounts.get(0));
		// System.out.println((Account) accounts.get(1));

		/*
		 * report = new Report();
		 * 
		 * String[] files = getAccountFiles();
		 * 
		 * importTransactions(files[0], true); importTransactions(files[1], false);
		 * report.generate();
		 */
	}

	// TODO Some debit transactions have > 0 values

	/*
	 * public static String[] getAccountFiles() { File[] files = new
	 * File(System.getProperty("user.dir")).listFiles(); String checkingsFile = "";
	 * String savingsFile = ""; for (File file : files) { String fileName =
	 * file.getName(); String extension = ""; int i = fileName.lastIndexOf('.'); if
	 * (i > 0) { extension = fileName.substring(i+1); } if(file.isFile() &&
	 * extension.equalsIgnoreCase("csv")) { if (checkingsFile.equals("")) {
	 * checkingsFile = fileName; printFileContext(checkingsFile); } else
	 * if(savingsFile.equals("")) { savingsFile = fileName;
	 * printFileContext(savingsFile); } } } return new String[]{checkingsFile,
	 * savingsFile}; }
	 * 
	 * public static void printFileContext(String file) { String bankName = "";
	 * String accountNum = ""; String dateTaken = ""; if(file.length() == 31) {
	 * bankName = file.substring(0, 5); accountNum = file.substring(5, 9); dateTaken
	 * = file.substring(19, 27); System.out.println("Account File Found [Bank: " +
	 * bankName + " | Account: " + accountNum + " | Date: " + dateTaken + "]"); } }
	 * 
	 * public static void importTransactions(String fileName, boolean isChecking) {
	 * BufferedReader br = null; String line = ""; try { br = new BufferedReader(new
	 * FileReader(fileName)); br.readLine(); boolean balanceIndicated = false;
	 * boolean currentDateFound = false; while ((line = br.readLine()) != null) {
	 * String[] row = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
	 * double amount = Double.parseDouble(row[3]); if(!currentDateFound) {
	 * report.setCurrentDate(row[1]); currentDateFound = true; }
	 * if(!balanceIndicated) { if(!row[5].equals("") && !row[5].equals(" ")) {
	 * report.setBalance(Double.parseDouble(row[5]), isChecking);
	 * 
	 * balanceIndicated = true; } else { report.addExpectedAmountForChecking(amount,
	 * isChecking); } } Transaction tempTrans = new Transaction(isChecking, row[0],
	 * row[1], row[2], amount, row[4], row[5]); report.addTransaction(tempTrans); }
	 * } catch (Exception e) { e.printStackTrace(); } finally { if (br != null) {
	 * try { br.close(); } catch (IOException e) { e.printStackTrace(); } } }
	 * System.out.println(report.getTotalBalance()); }
	 */

}
