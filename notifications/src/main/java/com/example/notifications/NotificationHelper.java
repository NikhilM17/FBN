package com.example.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.SparseArray;

import androidx.core.app.NotificationCompat;

public class NotificationHelper {

    private SparseArray<Notification> notifications = new SparseArray<>();
    private static final String CHANNEL_ID = "com.bidtowin.notifications.NotificationHelper.CHANNEL_ID";
    private static NotificationHelper nm;
    private boolean channelCreated;
    private int count = 999;

    private NotificationHelper() {
    }


    public static synchronized NotificationHelper get() {
        if (nm == null) {
            nm = new NotificationHelper();
        }
        return nm;
    }

    public void create(Context context, String title, String message) {
        create(context, title, message, "");
    }


    private void create(Context context, String title, String message, String auctionId) {

        createNotificationChannel(context);

        PendingIntent pendingIntent;

        /*if(TextUtils.isEmpty(auctionId)) {
            Intent intent = new Intent(NotificationModule.ACTION_AUCTIONS_ACTIVITY);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        }else{
            Intent intent = new Intent(NotificationModule.ACTION_DETAILS_ACTIVITY);
            intent.putExtra(NotificationModule.EXTRA_EXTRA_AUCTION_ID, auctionId);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        }*/

        Intent intent = new Intent("ACTION");
        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                /*.addAction(R.drawable.ic_snooze, getString(R.string.snooze),
                snoozePendingIntent);*/


        Notification notification = builder.build();

        getManager(context).notify(count++, notification);

        notifications.put(count, notification);
    }

    /*Notification notification = new Notification.Builder(this, CHANNEL_ID)
            .setStyle(new NotificationCompat.MessagingStyle("Me")
                    .setConversationTitle("Team lunch")
                    .addMessage("Hi", timestamp1, null) // Pass in null for user.
                    .addMessage("What's up?", timestamp2, "Coworker")
                    .addMessage("Not much", timestamp3, null)
                    .addMessage("How about lunch?", timestamp4, "Coworker"))
            .build();*/

    private void createNotificationChannel(Context context) {
        if (channelCreated) {
            return;
        }
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "TITLE";
            String description = "DESCRIPTION";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
                channelCreated = true;
            }
        } else {
            channelCreated = true;
        }
    }

    private NotificationManager getManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
