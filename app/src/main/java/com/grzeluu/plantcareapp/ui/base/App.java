package com.grzeluu.plantcareapp.ui.base;

import android.app.Application;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;

public class App extends Application {
    private UserStorage userStorage;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
}
