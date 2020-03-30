package com.example.notifications

import com.google.firebase.messaging.RemoteMessage

interface FirebaseMessageListener {

    fun onNewToken(token: String)

    fun onMessageReceived(remoteMessage: RemoteMessage)

}