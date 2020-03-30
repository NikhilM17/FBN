package com.example.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.SparseArray
import androidx.core.app.NotificationCompat

class NotificationHelper private constructor() {
    private val notifications =
        SparseArray<Notification>()
    private var channelCreated = false
    private var count = 999
    fun create(
        context: Context,
        title: String,
        message: String
    ) {
        create(context, title, message, "")
    }

    private fun create(
        context: Context,
        title: String,
        message: String,
        auctionId: String
    ) {
        createNotificationChannel(context)
        val pendingIntent: PendingIntent
        pendingIntent = PendingIntent.getActivity(context, 0, Intent("ACTION"), 0)
        /*if (TextUtils.isEmpty(auctionId)) {
            Intent intent = new Intent(NotificationModule.ACTION_AUCTIONS_ACTIVITY);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        } else {
            Intent intent = new Intent(NotificationModule.ACTION_DETAILS_ACTIVITY);
            intent.putExtra(NotificationModule.EXTRA_EXTRA_AUCTION_ID, auctionId);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        }*/
        val builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        /*.addAction(R.drawable.ic_snooze, getString(R.string.snooze),
                snoozePendingIntent);*/
        val notification = builder.build()
        getManager(context).notify(count++, notification)
        notifications.put(count, notification)
    }

    /*  Notification notification = new Notification.Builder(this, CHANNEL_ID)
            .setStyle(new NotificationCompat.MessagingStyle("Me")
                    .setConversationTitle("Team lunch")
                    .addMessage("Hi", timestamp1, null) // Pass in null for user.
                    .addMessage("What's up?", timestamp2, "Coworker")
                    .addMessage("Not much", timestamp3, null)
                    .addMessage("How about lunch?", timestamp4, "Coworker"))
            .build();
*/
    private fun createNotificationChannel(context: Context) {
        if (channelCreated) {
            return
        }
        // Create the NotificationChannel, but only on API 26+ because
// the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "TITLE"
            val description = "DESCRIPTION"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager =
                context.getSystemService(
                    NotificationManager::class.java
                )
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel)
                channelCreated = true
            }
        } else {
            channelCreated = true
        }
    }

    private fun getManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    companion object {
        private const val CHANNEL_ID = "com.bidtowin.notifications.NotificationHelper.CHANNEL_ID"
        private var nm: NotificationHelper? = null

        @Synchronized
        fun get(): NotificationHelper? {
            if (nm == null) {
                nm = NotificationHelper()
            }
            return nm
        }
    }
}