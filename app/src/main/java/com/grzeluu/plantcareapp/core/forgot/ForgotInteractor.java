package com.grzeluu.plantcareapp.core.forgot;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotInteractor implements ForgotContract.Interactor {

    private ForgotContract.ForgotListener forgotListener;

    public ForgotInteractor(ForgotContract.ForgotListener forgotListener) {
        this.forgotListener = forgotListener;
    }

    @Override
    public void performResetPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        forgotListener.onSuccess("Email sent.");
                    } else {
                        forgotListener.onFailure("Something wrong happened");
                    }
                });
    }
}
