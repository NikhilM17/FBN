package com.example.firebasenotification;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.notifications.BaseApp;
import com.google.firebase.messaging.RemoteMessage;

public class MyApp extends BaseApp {

    private static String TOKEN;
    SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
    }

    @Override
    public void tokenReceived(String token) {
        if (TOKEN == null || TOKEN.isEmpty())
            prefs.edit().putString("TOKEN", token).apply();
        else
            TOKEN = prefs.getString("TOKEN", "");
        Log.w("Token", TOKEN);
    }

    @Override
    public boolean notificationReceived(RemoteMessage message) {
        Log.w("Message", message.getNotification().getBody());
        return true;
    }
}
