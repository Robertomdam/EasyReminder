package com.rmm.easyreminder.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.rmm.easyreminder.R;

/**
 * @author Roberto
 * Class that handles the app's notification process.
 */
public class NotificationHandler {

    private static final String CHANNEL_ID   = "001";
    private static final String CHANNEL_NAME = "CHANNEL-" + CHANNEL_ID;

    private static final int    CHANNEL_IMPORTANCE        = NotificationManager.IMPORTANCE_DEFAULT;
    private static final int    NOTIFICATION_IMPORTANCE   = NotificationCompat.PRIORITY_DEFAULT;

    private Context mContext;

    /**
     * Sets initial values and creates the app's unique channel.
     * @param context
     */
    NotificationHandler (Context context) {

        mContext = context;

        // Checks the version of the sdk, because since 'x' version it is necessarily to create a channel for sending notifications
        createChannel();
    }

    /**
     * Creates a channel to send the notifications (Only for SDK versions up to "Android Oreo").
     */
    private void createChannel ()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Log.d("DEBUGGING", "Channel created");

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);

//            context.getSystemService (NotificationManager.class).createNotificationChannel(channel);
            NotificationManagerCompat.from (mContext).createNotificationChannel(channel);
        }
    }

    /**
     * Builds and sends a notification using the unique notification channel of the app.
     * Configures a pending intent so when the user taps on the notification the Reminders Activity will pop up.
     * Due to the simpleness of the app, the notification just configures the title, keeping empty the content text.
     * @param notification_id The id for the notification.
     * @param text The text that will act as the notification title.
     */
    void sendNotification (int notification_id, String text)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder (mContext, CHANNEL_ID);

        // Basic notification data
        builder.setSmallIcon (R.drawable.ic_notification);
        builder.setPriority (NOTIFICATION_IMPORTANCE);

        builder.setContentTitle (text);
//        builder.setContentText (context.getResources().getString(R.string.app_name));

        // Channel for API >= 26
        builder.setChannelId (CHANNEL_ID);

//        builder.setStyle(new NotificationCompat.BigTextStyle ().bigText(""));

        // Tap action
        Intent intent = new Intent (mContext, RemindersActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity (mContext, 0, intent, 0);
        builder.setContentIntent (pendingIntent);

//        builder.setAutoCancel(true);

        // Notification sending
        NotificationManagerCompat.from(mContext).notify (notification_id, builder.build());
    }
}
