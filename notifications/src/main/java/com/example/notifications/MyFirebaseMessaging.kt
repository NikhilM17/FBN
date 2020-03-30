package com.example.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessaging : FirebaseMessagingService() {

    lateinit var listener: FirebaseMessageListener

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        listener.onNewToken(newToken)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        listener.onMessageReceived(remoteMessage)
    }
}