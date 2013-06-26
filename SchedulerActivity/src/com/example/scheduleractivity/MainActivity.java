package com.example.scheduleractivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_layout);
		scheduleAlarm();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void scheduleAlarm() {
		// Create an offset from the current time in which the alarm will go
		// off.

		Calendar date = new GregorianCalendar();
		if (date.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
				&& date.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {

			date.set(Calendar.HOUR_OF_DAY, 9);
			date.set(Calendar.MINUTE, 40);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);

			Intent intentAlarm = new Intent(this, TaskReceiver.class);
			AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, date
					.getTimeInMillis(), AlarmManager.INTERVAL_HALF_HOUR,
					PendingIntent.getBroadcast(this, 1, intentAlarm,
							PendingIntent.FLAG_UPDATE_CURRENT));
		}

	}

}
