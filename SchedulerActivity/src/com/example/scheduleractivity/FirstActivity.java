package com.example.scheduleractivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.widget.TextView;

@SuppressLint("ShowToast")
public class FirstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("=== FirstActivity received the alarm ===");
		setContentView(R.layout.first_layout);
		filterDailySMS(); 
	}

	/**
	 * This method is used to return all sms received in mobile from 12:00 AM to 09:40 AM
	 * @return Map<String, String>
	 */
	public Map<String, String> getTodaySMS() {
		Map<String, String> messageMap = new HashMap<String, String>();
		Uri uriSMSURI = Uri.parse("content://sms/inbox");
		String startTime = String.valueOf(CommonActivity.getTodayStartTime());
		String endTime = String.valueOf(CommonActivity.getTodayOfficeTime());
		Cursor cur = getContentResolver().query(uriSMSURI, null,
				"date between " + startTime + " and " + endTime, null, "date asc");
		while (cur.moveToNext()) {
			String address = cur.getString(cur.getColumnIndexOrThrow("address"));
			String sms = cur.getString(cur.getColumnIndexOrThrow("body"));			
			messageMap.put(address, sms);			
		}
		return messageMap;
	}

	/**
	 * This method is used to return id of the contact group
	 * @return List<String>
	 */
	public List<String> getContactGroupIds() {
		List<String> groupIds = new ArrayList<String>();
		final String[] GROUP_PROJECTION = new String[] { ContactsContract.Groups._ID };
		Cursor cursor = getContentResolver().query(
				ContactsContract.Groups.CONTENT_URI, GROUP_PROJECTION,
				"TITLE='YouSee'", null, null);
		while (cursor.moveToNext()) {
			groupIds.add(cursor.getString(cursor
					.getColumnIndex(ContactsContract.Groups._ID)));
		}
		return groupIds;
	}

	/**
	 * This method is used to get all contacts of the group
	 * @return Map<String, String>
	 */
	public Map<String, String> getAllContactsOfGroup() {
		List<String> groupIds = getContactGroupIds();
		Map<String, String> contactMap = new HashMap<String, String>();
		
		String[] projection = new String[] {
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID };

		for (String groupId : groupIds) {
			Cursor cursor = getContentResolver()
					.query(ContactsContract.Data.CONTENT_URI,
							projection,
							ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
									+ "=" + groupId, null, null);
			while (cursor.moveToNext()) {
				String id = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID));
				Cursor pCur = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = ?", new String[] { id }, null);

				while (pCur.moveToNext()) {
					String name = pCur
							.getString(pCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
					String number = pCur
							.getString(pCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
					
					if(!contactMap.containsKey(number)) {
						contactMap.put(number, name);
					}					
				}
				pCur.close();
			}
		}
		return contactMap;
	}

	/**
	 * This method is used to filter message
	 */
	public void filterDailySMS() {
		TextView view = new TextView(this);
		List<Employee> employeeList = new ArrayList<Employee>();
		Map<String, String> messageMap = getTodaySMS();
		if(messageMap.size() > 0) {
			Map<String, String> contactMap = getAllContactsOfGroup();
			for(String messageKey : messageMap.keySet()) {
				if(contactMap.containsKey(messageKey) || contactMap.containsKey(addPrefix(messageKey))) {
					employeeList.add(createEmployee(messageKey, contactMap.get(messageKey), messageMap.get(messageKey)));				
				} else {
					if (Pattern.compile("LEAVE| LATE| SICK| NOT COMING| TO COME| NOT FEELING WELL| NOT COMING TO| REACH BY| REACHING BY", Pattern.CASE_INSENSITIVE)
							.matcher(messageMap.get(messageKey))
							.find()) {
						employeeList.add(createEmployee(messageKey, contactMap.get(messageKey), messageMap.get(messageKey)));
					}
					
				}
			}
		}		
		// to send an email
		SendEmailAsyncTask sendEmail = new SendEmailAsyncTask();
		sendEmail.setReportList(employeeList);
		sendEmail.execute();
		view.setText(employeeList.toString());
		setContentView(view);
	}
	
	/**
	 * This method is used to create Employee instance
	 * @param number
	 * @param name
	 * @param message
	 * @return Employee
	 */
	public Employee createEmployee(String number, String name, String message) {
		Employee emp = new Employee();
		emp.setContactNo(addPrefix(number));
		if (name == null) {
			emp.setContactName(getContact(number));
		} else {
			emp.setContactName(name);
		}
		emp.setMessage(message);
		emp.setStatus(checkStatus(message));
		return emp;
	}
	
	/**
	 * This method is used to get contact details of given number
	 * @param number
	 * @return String
	 */
	public String getContact(String number) {
		String contactName = null;

		// define the columns I want the query to return
		String[] projection = new String[] {
				ContactsContract.PhoneLookup.DISPLAY_NAME,
				ContactsContract.PhoneLookup._ID };

		// encode the phone number and build the filter URI
		Uri contactUri = Uri.withAppendedPath(
				ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(number));

		// query to get contact details
		Cursor cursor = getContentResolver().query(contactUri, projection,
				null, null, null);

		if (cursor.moveToFirst()) {
			// Get values from contacts database:
			contactName = cursor.getString(cursor
					.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
			System.out.println(cursor.getString(cursor
					.getColumnIndex(ContactsContract.PhoneLookup._ID)));
			System.out.println("Contact Id " + number);
			System.out.println("Contact Name " + contactName);
		} else {
			System.out.println("No Contact Found " + number);
			return "Unknown"; // contact not found

		}
		return contactName;
	}

	/**
	 * This method is used to set status
	 * @param message
	 * @return String
	 */
	public String checkStatus(String message) {
		String status = null;
		if (Pattern
				.compile("LEAVE| NOT FEELING WELL| SICK| NOT COMING| TO COME| NOT COMING TO",
						Pattern.CASE_INSENSITIVE).matcher(message).find()) {
			status = "LEAVE";
		} else if(Pattern
				.compile("LATE| REACH BY| REACHING BY",
						Pattern.CASE_INSENSITIVE).matcher(message).find()) {
			status = "LATE";
		} else {
			status = "UNKNOWN";
		}
		return status;
	}
	
	/**
	 * This method is used to add prefix as "+91" in the number
	 * @param number 
	 * @return String
	 */
	public String addPrefix(String number) {
		if(number!=null && number.length() == 10 && number.matches("^\\d+$")) {
			return "+91" + number;
		} else {
			return number;
		}
	}
}
