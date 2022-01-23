package com.grzeluu.plantcareapp.ui.login.forgot;

import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPresenter implements ForgotMvpPresenter{

    ForgotMvpView forgotMvpView;

    ForgotPresenter(ForgotMvpView forgotMvpView){
        this.forgotMvpView = forgotMvpView;
    }

    @Override
    public void onResetPasswordClick(String email) {

        boolean hasErrors = false;
        if (email.isEmpty()) {
            forgotMvpView.setEmailError("This field can\'t be empty.");
            hasErrors = true;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgotMvpView.setEmailError("This is not a valid email.");
            hasErrors = true;
        }

        if(!hasErrors) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            forgotMvpView.showMessage("Email sent.");
                            forgotMvpView.dismissDialog();
                        } else {
                            forgotMvpView.showMessage("Something wrong happened");
                            forgotMvpView.dismissDialog();
                        }
                    });
        }
    }
}