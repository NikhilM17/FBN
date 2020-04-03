package com.example.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class NotificationActionService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public NotificationActionService() {
        super(NotificationActionService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.w("onHandleIntent", "Intent called");
    }
}
