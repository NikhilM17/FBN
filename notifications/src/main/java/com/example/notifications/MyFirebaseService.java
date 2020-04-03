package com.example.notifications;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        ((BaseApp) getApplication()).tokenReceived(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        boolean showNotification = ((BaseApp) getApplication()).notificationReceived(remoteMessage);

        RemoteMessage.Notification notification = remoteMessage.getNotification();

        if (notification != null && !showNotification) {
            if (notification.getImageUrl() == null || TextUtils.isEmpty(notification.getImageUrl().toString())) {
                NotificationHelper.get().send(getApplicationContext(),
                        notification.getTitle(), notification.getBody());
            } else {
                NotificationHelper.get().send(getApplicationContext(),
                        notification.getTitle(), notification.getBody(), notification.getImageUrl().toString());
            }
        }
    }
}