package com.fastword.wordbook.wordbook;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Iterator;
import java.util.List;

public class ScreenReceiver extends BroadcastReceiver {



    @Override

    public void onReceive(Context context, Intent intent) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info;
        info = activityManager.getRunningTasks(7);
        Iterator iter = info.iterator();

        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) iter.next();
        if(! runningTaskInfo.topActivity.getClassName().equals("com.fastword.wordbook.wordbook.LockScreenActivity")) {

            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {



                Intent i = new Intent(context, LockScreenActivity.class);

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);

            }

        }

    }

}


