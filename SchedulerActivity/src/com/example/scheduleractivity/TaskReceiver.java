package com.example.scheduleractivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TaskReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("=== Receiver received the alarm ===");
		WakeLocker.acquire(context);
		Intent sendIntent = new Intent(context, FirstActivity.class);
		sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);		
		context.startActivity(sendIntent);
		WakeLocker.release();
	}

}
