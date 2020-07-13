package com.rmm.easyreminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHandler {

    private static final String CHANNEL_ID = "001";

    static void sendNotification (Context context, int notification_id, String text)
    {
        // Checks the version of the sdk, because since 'x' version it is necessarily to create a channel for sending notifications
        createChannel (context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder (context, CHANNEL_ID);

        // Basic notification data
        builder.setSmallIcon (R.drawable.ic_notification);
        builder.setPriority (NotificationCompat.PRIORITY_DEFAULT);

        builder.setContentTitle (text);
        builder.setContentText (context.getResources().getString(R.string.app_name));

//        builder.setStyle(new NotificationCompat.BigTextStyle ().bigText(""));

        // Tap action

//        builder.setAutoCancel(true);

        Intent intent = new Intent (context, RemindersActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity (context, 0, intent, 0);
        builder.setContentIntent (pendingIntent);

        // Notification sending
        NotificationManagerCompat.from(context).notify (notification_id, builder.build());
    }

    private static void createChannel (Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "name";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("qwe", name, importance);

//            getSystemService (NotificationManager.class).createNotificationChannel(channel);
            NotificationManagerCompat.from (context).createNotificationChannel(channel);
        }
    }
}
