package com.example.notifications;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

public abstract class BaseApp extends Application implements FirebaseMessageListener {

    public boolean shouldShowNotification = false;

    public void setShouldShowNotification(boolean shouldShowNotification) {
        this.shouldShowNotification = shouldShowNotification;
    }
}