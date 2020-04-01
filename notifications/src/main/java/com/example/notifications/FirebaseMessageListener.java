package com.example.notifications;

import com.google.firebase.messaging.RemoteMessage;

public interface FirebaseMessageListener {

    void tokenReceived(String token);

    void messageReceived(RemoteMessage message);
}
