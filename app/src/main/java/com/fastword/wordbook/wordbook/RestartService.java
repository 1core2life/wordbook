package com.fastword.wordbook.wordbook;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class RestartService extends Service {
    public RestartService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @TargetApi(android.os.Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        NotificationCompat.Builder builder;
        NotificationChannel channel;
        String CHANNEL_ID = "service_channel";

        channel = new NotificationChannel(CHANNEL_ID,
                "Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT);

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
                .createNotificationChannel(channel);

        builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        builder.setContentTitle("")
                .setContentText("")
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        startForeground(startId, builder.build());


        Intent in = new Intent(this, LockScreenService.class);
        startService(in);

        stopForeground(true);
        stopSelf();

        return START_NOT_STICKY;

    }
}
