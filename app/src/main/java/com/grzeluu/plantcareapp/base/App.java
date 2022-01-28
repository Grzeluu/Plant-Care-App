package com.grzeluu.plantcareapp.base;

import static com.grzeluu.plantcareapp.utils.NotificationUtils.createNotificationChannel;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        createNotificationChannel(getApplicationContext());
    }

}
