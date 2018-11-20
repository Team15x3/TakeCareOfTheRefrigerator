package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Received!",Toast.LENGTH_LONG).show();
        NotificationManager notifier = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.com_facebook_button_icon,"text",System.currentTimeMillis());

        Intent intent2 = new Intent(context,InsertFoodActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, InsertFoodActivity.class).
                        setFlags(intent.FLAG_ACTIVITY_NEW_TASK).
                        putExtra("no",Integer.parseInt(intent.getExtras().getString("no"))).
                        putExtra("sendID",intent.getExtras().getString("name")),
                PendingIntent.FLAG_UPDATE_CURRENT);

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.vibrate = new long[]{200,200,500,500};
        notification.number ++;

        notifier.notify(1,notification);
    }
}