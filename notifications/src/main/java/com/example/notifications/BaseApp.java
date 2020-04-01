package com.example.notifications;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

public abstract class BaseApp extends Application {

    public abstract void tokenReceived(String token);

    public abstract boolean notificationReceived(RemoteMessage message);

}