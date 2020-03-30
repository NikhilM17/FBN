package com.example.notifications

import android.app.Application
import com.google.firebase.messaging.RemoteMessage

abstract class MyBaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        MyFirebaseMessaging().listener = object : FirebaseMessageListener {
            override fun onNewToken(newToken: String) {
                token(newToken)
            }

            override fun onMessageReceived(remoteMessage: RemoteMessage) {
                message(remoteMessage)
            }

        }
    }

    abstract fun token(token: String)

    abstract fun message(remoteMessage: RemoteMessage)

}