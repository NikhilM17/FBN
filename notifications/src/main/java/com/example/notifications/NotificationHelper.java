package com.example.notifications;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class NotificationHelper {

    private SparseArray<Notification> notifications = new SparseArray<>();
    private static final String CHANNEL_ID = "com.bidtowin.notifications.NotificationHelper.CHANNEL_ID";
    private static NotificationHelper nm;
    private boolean channelCreated;
    private int count = 999;
    private Handler handler;

    private NotificationHelper() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    public static synchronized NotificationHelper get() {
        if (nm == null) {
            nm = new NotificationHelper();
        }
        return nm;
    }


    private void sendNotification(Context context, Notification notification, int id) {

        getManager(context).notify(id, notification);
        notifications.put(count, notification);
    }

    private void sendImageNotification(final Context context, final NotificationCompat.Builder builder, final String imageURl, final int id) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = fetchImageAsync(imageURl);
                if (bitmap != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            builder.setLargeIcon(bitmap);
                            Notification notification = builder.build();
                            sendNotification(context, notification, id);
                        }
                    });
                }
            }
        });
    }

    public void send(Context context, String title, String message, String imageUrl) {
        NotificationCompat.Builder builder = create(context, title, message);
        Notification notification = builder.build();
        int id = ++count;
        sendNotification(context, notification, id);
        if (!TextUtils.isEmpty(imageUrl))
            sendImageNotification(context, builder, imageUrl, id);
    }

    public void send(Context context, String title, String message) {
        NotificationCompat.Builder builder = create(context, title, message);
        Notification notification = builder.build();
        sendNotification(context, notification, ++count);
    }

    private NotificationCompat.Builder create(Context context, String title, String message) {

        createNotificationChannel(context);


        Intent intent = new Intent(context, NotificationActionService.class);
        intent.setAction("ClickAction");
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder;

    }

    private void createNotificationChannel(Context context) {
        if (channelCreated) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.app_name);
            String description = context.getResources().getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
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


    private Bitmap fetchImageAsync(String c) {
        BufferedInputStream in = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(c);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            in = new BufferedInputStream(connection.getInputStream());
            return BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();

                if (connection != null)
                    connection.disconnect();
            } catch (Exception oe) {
                oe.printStackTrace();
            }
        }
        return null;
    }
}