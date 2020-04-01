package com.example.notifications;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

public abstract class BaseApp extends Application {

    abstract void token(String token);

    abstract void message(RemoteMessage remoteMessage);
}
