package com.bogaware.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Report {
	/*private Date currentDate = Calendar.getInstance().getTime();
	private double checkingBalance;
	private double savingBalance;
	private double expectedCheckingAmount = 0;
	private double expectedSavingAmount = 0;
	private ArrayList<Transaction> transactions;
	private ArrayList<Transaction> validTransactions;
	private NumberFormat formatter = NumberFormat.getCurrencyInstance();
	
	enum Duration {
		DAY, WEEK, MONTH, YEAR
	}
	
	public Report() {
		transactions = new ArrayList<Transaction>();
		validTransactions = new ArrayList<Transaction>();
	}
	
	public double getTotalBalance() {
		return this.checkingBalance + this.savingBalance;
	}
	
	public void setBalance(double balance, boolean isChecking) {
		if(isChecking) {
			this.checkingBalance = balance;
		} else {
			this.savingBalance = balance;
		}
	}
	
	public void addTransaction(Transaction trans) {
		this.transactions.add(trans);
	}
	
	public void sortTransactions() {
		ArrayList<Transaction> resultTransactions = new ArrayList<Transaction>();
		//TODO NOT COMPLETED
	}
	
	public void addExpectedAmountForChecking(double expectedTransAmount, boolean isChecking) {
		if(isChecking) {
			this.expectedCheckingAmount += expectedTransAmount;
		} else {
			this.expectedSavingAmount += expectedTransAmount;
		}
	}

	public void generate() {
		double totalWorkingBalance = this.checkingBalance + this.savingBalance;
		double checkingWorkingBalance = this.checkingBalance;
		double checkingExpectedBalance = this.checkingBalance + this.expectedCheckingAmount;
		double savingWorkingBalance = this.savingBalance;
		double savingExpectedBalance = this.savingBalance + this.expectedSavingAmount;
		double totalExpectedBalance = checkingExpectedBalance + savingExpectedBalance;
		double pastMonthCheckingSpending = pastDurationCheckingAmount(Duration.MONTH, true);
		double pastMonthCheckingEarning = pastDurationCheckingAmount(Duration.MONTH, false);
		double pastMonthCheckingNetWorth = pastMonthCheckingEarning + pastMonthCheckingSpending;
		double pastWeekCheckingSpending = pastDurationCheckingAmount(Duration.WEEK, true);
		double pastWeekCheckingEarning = pastDurationCheckingAmount(Duration.WEEK, false);
		double pastWeekCheckingNetWorth = pastWeekCheckingEarning + pastWeekCheckingSpending;
		double averageMonthlyCheckingSpending = getAvgCheckingUsageByDuration(true, Duration.MONTH, 12);
		double averageMonthlyCheckingEarning = getAvgCheckingUsageByDuration(false, Duration.MONTH, 12);
		double averageMonthlyCheckingUsage = averageMonthlyCheckingSpending + averageMonthlyCheckingEarning;
		double averageWeeklyCheckingSpending = getAvgCheckingUsageByDuration(true, Duration.WEEK, 36);
		double averageWeeklyCheckingEarning = getAvgCheckingUsageByDuration(false, Duration.WEEK, 36);
		double averageWeeklyCheckingUsage = averageWeeklyCheckingSpending + averageWeeklyCheckingEarning;
		double averageDailyCheckingSpending = getAvgCheckingUsageByDuration(true, Duration.DAY, 91);
		double averageDailyCheckingEarning = getAvgCheckingUsageByDuration(false, Duration.DAY, 91);
		double averageDailyCheckingUsage = averageDailyCheckingSpending + averageDailyCheckingEarning;
		double averageMonthlyOfGas = getAvgGasUsageByMonth(12);
		double lastPayCheckAmount = getLastPaycheck().getAmount();
		int lastPayCheckEstimatedHours = getLastPaycheckEstimatedHours(lastPayCheckAmount);
		double yearToDateAmount = getYearToDate(); //1935.00 since 7/31/2017
		double projectedSavings = 0;
		ArrayList<Transaction> debitMonthlyOccurances = new ArrayList<Transaction>();
		ArrayList<Transaction> creditMonthlyOccurances = new ArrayList<Transaction>();
		ArrayList<Transaction> top5CategoriesInPastMonth = new ArrayList<Transaction>();
		ArrayList<Transaction> top5PurchasesInPastMonth = getTop5PurchasesInPastMonth();
		
		
		StringBuilder sb = new StringBuilder();
        sb.append("Calculation,Value\n");
        sb.append(formatToField("Total Working Balance", totalWorkingBalance));
        sb.append(formatToField("Total Expected Balance", totalExpectedBalance));
        sb.append(formatToField("Checkings Working Balance", checkingWorkingBalance));
        sb.append(formatToField("Checkings Expected Balance", checkingExpectedBalance));
        sb.append(formatToField("Savings Working Balance", savingWorkingBalance));
        sb.append(formatToField("Savings Expected Balance", savingExpectedBalance));
        sb.append("------\n");
        sb.append(formatToField("Year to Date Amount", yearToDateAmount));
        sb.append(formatToField("Projected Savings", projectedSavings));
        sb.append("------\n");
        sb.append(formatToField("Past Month Spending", pastMonthCheckingSpending));
        sb.append(formatToField("Past Month Earnings", pastMonthCheckingEarning));
        sb.append(formatToField("Past Month Net Worth", pastMonthCheckingNetWorth));
        sb.append(formatToField("Average Monthly Spending (Last year)", averageMonthlyCheckingSpending));
        sb.append(formatToField("Average Monthly Earnings (Last year)", averageMonthlyCheckingEarning));
        sb.append(formatToField("Average Monthly Savings (Last year)", averageMonthlyCheckingUsage));
        sb.append("------\n");
        sb.append(formatToField("Past Week Spending", pastWeekCheckingSpending));
        sb.append(formatToField("Past Week Earnings", pastWeekCheckingEarning));
        sb.append(formatToField("Past Week Net Worth", pastWeekCheckingNetWorth));
        sb.append(formatToField("Average Weekly Spending (Last half-year)", averageWeeklyCheckingSpending));
        sb.append(formatToField("Average Weekly Earnings (Last half-year)", averageWeeklyCheckingEarning));
        sb.append(formatToField("Average Weekly Savings (Last half-year)", averageWeeklyCheckingUsage));
        sb.append("------\n");
        sb.append(formatToField("Average Daily Spending (Last quarter)", averageDailyCheckingSpending));
        sb.append(formatToField("Average Daily Earnings (Last quarter)", averageDailyCheckingEarning));
        sb.append(formatToField("Average Daily Savings (Last quarter)", averageDailyCheckingUsage));
        sb.append("------\n");
        sb.append(formatToField("Average Gas Cost per Month (Last year of purchases of at least $25)", averageMonthlyOfGas));
        sb.append(formatToField("Last Pay Check Amount", lastPayCheckAmount));
        
        //sb.append(formatToField("Last Pay Check Estimated Hours", lastPayCheckEstimatedHours));


        writeBuilderToFile(sb);
	}
	
	public String formatToField(String name, double value) {
        return name + ",\"" + formatter.format(value) + "\"\n";
	}
	
	public double getYearToDate() {
		double result = 1935.00;
		Date dateFromBack = new Date("7/31/2017");
		for(int i = 0; i < transactions.size(); i++) {
			Transaction tempTransaction = (Transaction) transactions.get(i);
			if(tempTransaction.getIsChecking() && tempTransaction.getCategory() == References.Category.PAYROLL && tempTransaction.getAppliedDate().after(dateFromBack)) {
				result += tempTransaction.getAmount();
			}
		}
		return result;
	}
	
	public double getAvgCheckingUsageByDuration(boolean isSpending, Duration duration, int goBackNum) {
		double[] rangeAmounts = new double[goBackNum];
		for(int k = 0; k < goBackNum; k++) {
			double durationAmount = 0;
			ArrayList<Transaction> resultList = getCheckingTransactionsFromDurationNumber(duration, (k + 1));
			for(int i = 0; i < resultList.size(); i++) {
				Transaction tempTransaction = (Transaction) resultList.get(i);
				if(tempTransaction.getIsDebit() == isSpending) {
					durationAmount += tempTransaction.getAmount();
				}
			}
			rangeAmounts[k] = durationAmount;
		}
		return getAverage(rangeAmounts, goBackNum);
	}
	
	public double getAvgGasUsageByMonth(int goBackNum) {
		double[] rangeAmounts = new double[goBackNum];
		for(int k = 0; k < goBackNum; k++) {
			double durationAmount = 0;
			ArrayList<Transaction> resultList = getCheckingTransactionsFromDurationNumber(Duration.MONTH, (k + 1));
			for(int i = 0; i < resultList.size(); i++) {
				Transaction tempTransaction = (Transaction) resultList.get(i);
				if(tempTransaction.getCategory() == References.Category.GAS) {					
					durationAmount += tempTransaction.getAmount();
				}
			}
			rangeAmounts[k] = durationAmount;
		}
		return getAverage(rangeAmounts, goBackNum);
	}
	
	public ArrayList<Transaction> getCheckingTransactionsFromDurationNumber(Duration duration, int numberBack){
		ArrayList<Transaction> resultList = new ArrayList<Transaction>();
		Date dateFromBack = null;
		Date dateToBack = null;
		switch(duration) {
			case YEAR:
				dateFromBack = new Date(currentDate.getYear() - numberBack, currentDate.getMonth(), currentDate.getDate());
				dateToBack = new Date(currentDate.getYear() - (numberBack - 1), currentDate.getMonth(), currentDate.getDate());
				break;
			case MONTH:
				dateFromBack = new Date(currentDate.getYear(), currentDate.getMonth() - numberBack, currentDate.getDate());
				dateToBack = new Date(currentDate.getYear(), currentDate.getMonth() - (numberBack - 1), currentDate.getDate());
				break;
			case WEEK:
				dateFromBack = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate() - (numberBack * 7));
				dateToBack = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate() - ((numberBack - 1) * 7));
				break;
			case DAY:
				dateFromBack = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate() - numberBack);
				dateToBack = new Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate() - (numberBack - 1));
				break;
			default:
				Main.exitProgram("Error finding duration", 1);
		}
		for(int i = 0; i < transactions.size(); i++) {
			Transaction tempTransaction = (Transaction) transactions.get(i);
			if(tempTransaction.getIsChecking() && tempTransaction.getAppliedDate().after(dateFromBack) && (tempTransaction.getAppliedDate().before(dateToBack) || tempTransaction.getAppliedDate().equals(dateToBack))) {
				resultList.add(tempTransaction);
			}
		}
		return resultList;
	}
	
	public double getAverage(double[] list, int length) {
		double amount = 0;
		for(double x : list) {
			amount += x;
		}
		return amount/length;
	}
	
	public Transaction getLastPaycheck() {
		for(int i = 0; i < transactions.size(); i++) {
			Transaction tempTransaction = (Transaction) transactions.get(i);
			if(tempTransaction.getIsChecking() && tempTransaction.getCategory() == References.Category.PAYROLL) {
				return tempTransaction;
			}
		}
		return null;
	}
	
	public int getLastPaycheckEstimatedHours(double lastPaycheck) {
		double result = lastPaycheck;
		result -= (lastPaycheck * Main.FEDERAL_INCOME_TAX);
		result -= (lastPaycheck * Main.OASDI);
		result -= (lastPaycheck * Main.MEDICARE);
		result -= (lastPaycheck * Main.ARIZONA_STATE_INCOME_TAX_WITHHOLDING);
		int hours = (int) (result/Main.PAY_RATE);
		return hours;
	}
	
	public double pastDurationCheckingAmount(Duration duration, boolean isSpending) {
		ArrayList<Transaction> pastDurationTransactions = getPastDuration(duration, currentDate); 
		double result = 0;
		for(int i = 0; i < pastDurationTransactions.size(); i++) {
			Transaction tempTransaction = (Transaction) pastDurationTransactions.get(i);
			if(tempTransaction.getIsChecking() && tempTransaction.getIsDebit() == isSpending) {
				result += tempTransaction.getAmount();
			}
		}
		return result;
	}
	
	public ArrayList<Transaction> getPastDuration(Duration duration, Date date){
		ArrayList<Transaction> resultList = new ArrayList<Transaction>();
		ArrayList<Transaction> runningList = new ArrayList<Transaction>(transactions);
		Date dateFromBack = null;
		switch(duration) {
			case YEAR:
				dateFromBack = new Date(date.getYear() - 1, date.getMonth(), date.getDate());
				break;
			case MONTH:
				dateFromBack = new Date(date.getYear(), date.getMonth() - 1, date.getDate());
				break;
			case WEEK:
				dateFromBack = new Date(date.getYear(), date.getMonth(), date.getDate() - 7);
				break;
			case DAY:
				dateFromBack = new Date(date.getYear(), date.getMonth(), date.getDate() - 1);
				break;
			default:
				return null;
		}
		for(int i = 0; i < runningList.size(); i++) {
			Transaction tempTransaction = (Transaction) runningList.get(i);
			if(tempTransaction.getIsChecking() && tempTransaction.getAppliedDate().after(dateFromBack)) {
				resultList.add(tempTransaction);
			}
		}
		return resultList;
	}
	
	public ArrayList<Transaction> getTop5PurchasesInPastMonth(){
		ArrayList<Transaction> resultList = new ArrayList<Transaction>();
		ArrayList<Transaction> runningList = getPastDuration(Duration.MONTH, currentDate);
		int top1 = getTopPurchase(runningList);
		resultList.add(runningList.get(top1));
		runningList.remove(top1);
		int top2 = getTopPurchase(runningList);
		resultList.add(runningList.get(top2));
		runningList.remove(top2);
		int top3 = getTopPurchase(runningList);
		resultList.add(runningList.get(top3));
		runningList.remove(top3);
		int top4 = getTopPurchase(runningList);
		resultList.add(runningList.get(top4));
		runningList.remove(top4);
		int top5 = getTopPurchase(runningList);
		resultList.add(runningList.get(top5));
		runningList.remove(top5);
		
		System.out.println(((Transaction) resultList.get(0)));
		System.out.println(((Transaction) resultList.get(1)));
		System.out.println(((Transaction) resultList.get(2)));
		System.out.println(((Transaction) resultList.get(3)));
		System.out.println(((Transaction) resultList.get(4)));
		return resultList;
	}
	
	public int getTopPurchase(ArrayList<Transaction> list) {
		int topIndex = 0;
		double topAmount = 0;
		for(int i = 0; i < list.size(); i++) {
			Transaction tempTransaction = (Transaction) list.get(i);
			if(tempTransaction.getIsChecking() && tempTransaction.getIsDebit() && tempTransaction.getAmount() < topAmount) {
				topIndex = i;
				topAmount = tempTransaction.getAmount();
			}
		}
		return topIndex;
	}
	
	public Transaction analyzeMonthlyReoccurrances(boolean isDebit) {
		for(int i = 0; i < transactions.size(); i++) {
			Transaction tempTransaction = transactions.get(i);
			if(tempTransaction.getIsDebit() == isDebit) {
				//TODO SOON
			}
		}
		return null;
		
	}
	
	public void writeBuilderToFile(StringBuilder sb) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("report.csv"));
	        pw.write(sb.toString());
	        pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setCurrentDate(String dateString) {
		Date dateFromString = new Date(dateString); 
		System.err.println(dateString);
		currentDate = dateFromString;
	}*/
}
