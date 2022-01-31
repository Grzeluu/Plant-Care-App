package com.grzeluu.plantcareapp.core.login;

import com.google.firebase.auth.FirebaseAuth;
import com.grzeluu.plantcareapp.R;

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
                .addOnSuccessListener(task -> {
                        loginListener.onEnd();
                        loginListener.onSuccess(R.string.logged_successfully);
                    })
                .addOnFailureListener(error -> {
                    loginListener.onEnd();
                    loginListener.onFailure(error.getMessage());
                });
    }
}
