package com.demo.demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    NotificationManager manager;
    Notification myNotication;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();

        notifyUser(context,intent.getStringExtra("title"));




    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
     void notifyUser(Context context, String title) {

        Intent intent = new Intent("com.rj.notitfications.SECACTIVITY");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setAutoCancel(true);
        builder.setTicker("Medicine time");
        builder.setContentTitle("Medicine Reminder");
        builder.setContentText("This reminder is for "+title);
        builder.setSmallIcon(R.drawable.medical);
        builder.setContentIntent(pendingIntent);
        builder.setVibrate(new long[]{100,200,300});
        builder.setOngoing(false);
        builder.setNumber(100);
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);

    }
}
