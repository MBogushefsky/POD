package com.bogaware.global;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateManager {
	private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	public static Date currentDate = Calendar.getInstance().getTime();

	public static Date simpleDateStringToDate(String dateString) {
		Date result = new Date();
		try {
			result = dateformat.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String calendarToSimpleDateString(Date date) {
		int month = date.getMonth();
		int day = date.getDate();
		int year = date.getYear();
		String currentMonth;
		String currentDay;
		String currentYear;
		month = month + 1;
		year = year + 1900;
		if(month < 10){
			currentMonth = "0" + month;
		} else {
			currentMonth = "" + month;
		}
		if(day < 10){
			currentDay = "0" + day;
		} else {
			currentDay = "" + day;
		}
		currentYear = "" + year;
		return currentYear + "-" + currentMonth + "-" + currentDay;
	}
	
	public static String calendarToSimpleDateString(int month, int day, int year){
		return calendarToSimpleDateString(new Date(month, day, year));
	}
	
}
