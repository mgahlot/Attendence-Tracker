package com.example.scheduleractivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

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
		date.set(Calendar.HOUR_OF_DAY, 9);
		date.set(Calendar.MINUTE, 40);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0); 
		return date.getTimeInMillis();
	}	
	
	public static String millisToDate(long currentTime) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(currentTime);
	    return calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + " " + (calendar.get(Calendar.AM_PM)==0 ? "AM" : "PM");
	}
	
	public static Set<String> getAllEmployeeFirstName() {
		Set<String> nameList = new HashSet<String>();
		nameList.add("Manoj");
		nameList.add("Amit");
		nameList.add("Vishal");
		nameList.add("Ramamohan");
		nameList.add("Simson");
		nameList.add("Tapan");
		nameList.add("Gaurav");
		nameList.add("Bala");
		nameList.add("Priya");
		nameList.add("Nipa");
		nameList.add("Vidhya");
		nameList.add("Trupti");
		nameList.add("Subir");
		nameList.add("Neha");
		nameList.add("Ramakant");
		nameList.add("Sandeep");
		nameList.add("Jitendra");
		nameList.add("Amrish");
		nameList.add("Rahul");		
		nameList.add("Ankit");
		nameList.add("Nivedita");
		nameList.add("Yogesh");
		nameList.add("Subal");
		nameList.add("Sujith");
		nameList.add("Sneha");
		nameList.add("Poonam");
		nameList.add("Mudasir");
		nameList.add("Shailesh");
		nameList.add("Ameya");		
		nameList.add("Swapnil");
		nameList.add("Prachi");
		nameList.add("Chaitali");
		nameList.add("Manish");
		nameList.add("Magesh");
		nameList.add("Rohit");
		nameList.add("Nimmi");
		nameList.add("Swapna");
		nameList.add("Simarjeet");
		nameList.add("Junaid");		
		nameList.add("Tejas");
		nameList.add("Vijay");
		nameList.add("saishree");
		nameList.add("Priyanka");
		nameList.add("Kale");
		nameList.add("Mangesh");
		nameList.add("Jwalant");
		nameList.add("Iti");
		nameList.add("Anusha");
		nameList.add("Chinmay");
		nameList.add("Prachi");
		nameList.add("Nishant");
		nameList.add("Pratima");
		nameList.add("Sarabdeep");
		nameList.add("Vaishali");
		nameList.add("Pravin");
		nameList.add("Roshan");
		nameList.add("Rajashree");
		nameList.add("Vishal");
		nameList.add("Vaibhav");
		nameList.add("Kayyum");
		nameList.add("Mohammad");
		nameList.add("Kajgikar");
		nameList.add("Pratik");
		nameList.add("Debajit");
		nameList.add("Ankur");
		nameList.add("Rajaram");
		nameList.add("Narendra");
		nameList.add("Kirti");	
		return nameList;		
	}
}
