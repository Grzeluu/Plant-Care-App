package com.grzeluu.plantcareapp.core.login;

import com.google.firebase.auth.FirebaseAuth;

public class LoginInteractor implements LoginContract.Interactor {

    private LoginContract.Listener loginListener;

    public LoginInteractor(LoginContract.Listener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public void performLogin(String email, String password) {
        loginListener.onStart();
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginListener.onEnd();
                        loginListener.onSuccess("User logged in successfully");
                    } else {
                        loginListener.onEnd();
                        loginListener.onFailure(task.getException().getMessage());
                    }
                });
    }
}
