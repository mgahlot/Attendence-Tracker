package com.example.scheduleractivity;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

public class SendEmailAsyncTask extends AsyncTask<Void, Void, Void>{

	List<Employee> reportList;
	
	public List<Employee> getReportList() {
		if(reportList == null) {
			return new ArrayList<Employee>();
		}
		return reportList;
	}

	public void setReportList(List<Employee> reportList) {
		this.reportList = reportList;
	}	

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			if(sendEmail().send()) {
				System.out.println("Email is sent successfully");
			} else {
				System.out.println("Email is not sent successfully");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Mail sendEmail() {
		Mail email = new Mail();
		if(getReportList().size() > 0) {
			email.set_body(getEmailFormat(getReportList()));
		} else {
			email.set_body(getDefaultFormat());
		}				
		String[] str = new String[4];
		str[0] = "mg10590@gmail.com";
		str[1] = "sandeep.shitole@capgemini.com";
		str[2] = "chinmay.arankalle@capgemini.com";
		str[3] = "ankit.jajoo@capgemini.com";
		email.set_to(str);
		return email;
	}
	
	/**
	 * This method is used to create HTML format email for daily report
	 * @param employeeList
	 * @return String
	 */
	public String getEmailFormat(List<Employee> employeeList) {
		StringBuilder formatMessage = new StringBuilder();
		formatMessage.append("<table border='1' cellspacing='0' cellpadding='5' width='95%' align='center'>");
		formatMessage.append("<thead>");
		formatMessage.append("<tr align='center'>");
		formatMessage.append("<td width='7%'> Sr. No </td>");
		formatMessage.append("<td width='13%'> Contact No </td>");
		formatMessage.append("<td width='16%'> Name </td>");
		formatMessage.append("<td width='42%'> Message </td>");
		formatMessage.append("<td width='8%'> Time </td>");
		formatMessage.append("<td width='8%'> Status </td>");
		formatMessage.append("</tr>");
		formatMessage.append("</thead>");
		formatMessage.append("<tbody>");
		int serialNo = 0;
		for(Employee att : employeeList) {
			formatMessage.append("<tr align='center'>");
			formatMessage.append("<td>" + ++serialNo + "</td>");
			formatMessage.append("<td nowrap>" + att.getContactNo() + "</td>");
			formatMessage.append("<td>" + att.getContactName() + "</td>");
			formatMessage.append("<td align='left'>" + att.getMessage() + "</td>");
			formatMessage.append("<td nowrap>" + att.getMessageTime() + "</td>");
			formatMessage.append("<td>" + att.getStatus() + "</td>");
			formatMessage.append("</tr>");
		}
		formatMessage.append("</tbody>");
		formatMessage.append("</table>");
		return formatMessage.toString();
	}

	/**
	 * This method is used to create HTML format email for daily report
	 * @param employeeList
	 * @return String
	 */
	public String getDefaultFormat() {
		StringBuilder formatMessage = new StringBuilder();
		formatMessage.append("<h1>Great !!! No one is late or on leave today. :) :) :)</h1>");		
		return formatMessage.toString();
	}
}