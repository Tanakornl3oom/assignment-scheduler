package com.example.l3oom.scheduler;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;

public class SampleSchedulingService extends IntentService {


    private int NOTIFICATION_ID =1;

    public SampleSchedulingService() {
        super("SchedulingService");
    }

    NotificationManager mNotificationManager;

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification("hello");
        SampleAlarmReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {

        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.getContext());
                dialog.setTitle("SchedulingService");
                dialog.setIcon(android.R.drawable.btn_star_big_on);
                dialog.setMessage("awake");
                dialog.setPositiveButton("Close", null);
                dialog.show();
        }
        });

        KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("Scheduler");
        keyguardLock.disableKeyguard();
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "Scheduler");
        wakeLock.acquire();

    }

}
