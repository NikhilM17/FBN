package com.example.notifications

import com.google.firebase.messaging.RemoteMessage

interface FirebaseMessageListener {

    fun onNewToken(newToken: String)

    fun onMessageReceived(remoteMessage: RemoteMessage)

}