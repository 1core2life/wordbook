package com.fastword.wordbook.wordbook;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;

public class LockScreenService extends Service {


    static private ScreenReceiver mReceiver = null;

    public LockScreenService() {

    }


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }



    @Override
    public void onCreate() {

        super.onCreate();

        mReceiver = new ScreenReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);

        registerReceiver(mReceiver, filter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        super.onStartCommand(intent, flags, startId);

        if(intent != null){

            if(intent.getAction()==null){

                if(mReceiver==null){

                    mReceiver = new ScreenReceiver();

                    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
                    filter.addAction(Intent.ACTION_BOOT_COMPLETED);

                    registerReceiver(mReceiver, filter);

                }

            }

        }


        return START_STICKY;

    }

    @Override

    public void onDestroy(){

        super.onDestroy();

        Log.i("test","*** LockScreenService killed");


        unregisterReceiver(mReceiver);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent in = new Intent(this, RestartService.class);
            this.startForegroundService(in);

        }
        else {
            Intent in = new Intent(this, LockScreenService.class);
            this.startService(in);
        }

    }




}
