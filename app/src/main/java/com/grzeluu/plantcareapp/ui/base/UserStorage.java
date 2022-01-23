package com.grzeluu.plantcareapp.ui.base;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserStorage {
    private SharedPreferences sharedPreferences;

    public static final String SESSION_TOKEN = "session_token";
    public static final String UID = "uid";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

    public UserStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getSessionToken() {
        return sharedPreferences.getString(SESSION_TOKEN, "");
    }

    public String getUid() {
        return sharedPreferences.getString(UID, "");
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, "");
    }

    public void save(FirebaseAuth firebaseAuth) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth.getAccessToken(true);

        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String username;
                            username = "" + ds.child("username").getValue();
                            editor.putString(USERNAME, username);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

        editor.putString(SESSION_TOKEN, String.valueOf(firebaseAuth.getAccessToken(true)));
        editor.putString(UID, firebaseAuth.getUid());
        editor.putString(EMAIL, Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail());
        editor.apply();
    }

    public boolean hasToLogin() {
        return sharedPreferences.getString(UID, "").isEmpty();
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
