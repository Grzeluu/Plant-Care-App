package com.grzeluu.plantcareapp.core.login;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginInteractor implements LoginContract.Interactor {

    private LoginContract.LoginListener loginListener;

    public LoginInteractor(LoginContract.LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public void performLogin(String email, String password) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginListener.onSuccess(task.getResult().toString());
                    } else {
                        loginListener.onFailure(task.getException().toString());
                    }
                });
    }
}
