package com.example.scheduleractivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;

public abstract class WakeLocker {
	private static PowerManager.WakeLock wakeLock;

	@SuppressLint("Wakelock")
	public static void acquire(Context ctx) {
		if (wakeLock != null)
			wakeLock.release();

		PowerManager pm = (PowerManager) ctx
				.getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
		wakeLock.acquire();
	}

	public static void release() {
		if (wakeLock != null)
			wakeLock.release();
		wakeLock = null;
	}
}
