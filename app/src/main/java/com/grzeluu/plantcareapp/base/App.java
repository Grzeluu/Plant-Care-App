package com.grzeluu.plantcareapp.base;

import static com.grzeluu.plantcareapp.utils.notification.NotificationUtils.createNotificationChannel;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel(getApplicationContext());
    }
}
