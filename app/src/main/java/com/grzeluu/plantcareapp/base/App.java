package com.grzeluu.plantcareapp.base;

import static com.grzeluu.plantcareapp.utils.notification.NotificationUtils.createNotificationChannel;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

public class App extends Application {

    private static Context context;


    public static Context getAppContext() {
        return App.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        createNotificationChannel(context);
    }

}
