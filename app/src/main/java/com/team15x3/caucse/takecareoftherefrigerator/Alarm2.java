package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

public class Alarm2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        finish();
        Bitmap mLargeIconForNoti =
                BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        PendingIntent mPendingIntent = PendingIntent.getActivity(Alarm2.this, 0,
                new Intent(getApplicationContext(), Alarm.class),
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(Alarm2.this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("알림제목")
                        .setContentText("알림내용")
                        .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                        .setLargeIcon(mLargeIconForNoti)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .setContentIntent(mPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}

