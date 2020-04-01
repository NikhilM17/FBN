package com.example.notifications;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseService extends FirebaseMessagingService {

    BaseApp app = (BaseApp) getApplication();

    @Override
    public void onNewToken(@NonNull String s) {
        app.token(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        app.message(remoteMessage);
    }
}
