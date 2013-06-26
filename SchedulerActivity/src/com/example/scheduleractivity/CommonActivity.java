package com.example.scheduleractivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CommonActivity {
	public static long getTodayStartTime() {
		Calendar date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 1); 
		return date.getTimeInMillis();
	}
	
	public static long getTodayOfficeTime() {
		Calendar date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 40);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0); 
		return date.getTimeInMillis();
	}	
}
