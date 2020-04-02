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
import android.util.Log;
import android.util.SparseArray;

import androidx.core.app.NotificationCompat;

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

    private NotificationHelper() {
    }


    public static synchronized NotificationHelper get() {
        if (nm == null) {
            nm = new NotificationHelper();
        }
        return nm;
    }

    public void create(Context context, String title, String message) {

        new SendNotification(context, title).execute();

        /*createNotificationChannel(context);

        Intent intent = new Intent("ACTION");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();

        getManager(context).notify(count++, notification);

        notifications.put(count, notification);*/
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

    @SuppressLint("StaticFieldLeak")
    private class SendNotification extends AsyncTask<String, Void, Bitmap> {
        Context ctx;
        String message;
        String title;

        public SendNotification(Context context, String appName) {
            super();
            this.ctx = context;
            this.title = appName;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            InputStream in;
            for (String string : params)
                Log.w("Params", string);
            message = params[0] + params[1];
            try {
                URL url = new URL(params[2]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                return BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            try {

                createNotificationChannel(ctx);
                PendingIntent pendingIntent;
                Intent intent = new Intent("ACTION");

                pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
                        .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

//                if (result != null)
//                    builder.setLargeIcon(result);

                Notification notification = builder.build();

                getManager(ctx).notify(count++, notification);

                notifications.put(count, notification);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}