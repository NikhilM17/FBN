package com.example.notifications;

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

        if (remoteMessage.getNotification() != null && showNotification) {
            NotificationHelper.get().create(getApplicationContext(),
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }
}