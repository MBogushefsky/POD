package com.bogaware.global;

import java.util.ArrayList;
import java.util.Calendar;

import com.plaid.client.response.TransactionsGetResponse.Transaction;

public class UserManager {
	public String addUserToDatabase(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
		try {
			DatabaseManager.updateFromDatabase("INSERT INTO users SET USERNAME=\'" + username + "\', PASSWORD=\'" + password + "\', FIRST_NAME=\'" + firstName + "\', LAST_NAME=\'" + lastName + "\', EMAIL=\'" + email + "\', PHONE_NUMBER=\'" + phoneNumber + "\', LAST_UPDATED=\'" + DateManager.calendarToSimpleDateString(Calendar.getInstance().getTime()) + "\';");
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		return "Success";
	}
}
