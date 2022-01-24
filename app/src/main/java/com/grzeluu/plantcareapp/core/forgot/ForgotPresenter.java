package com.grzeluu.plantcareapp.core.forgot;

import android.util.Patterns;

import com.grzeluu.plantcareapp.base.BasePresenter;

public class ForgotPresenter extends BasePresenter implements ForgotContract.Presenter, ForgotContract.ForgotListener {

    private ForgotContract.View forgotView;
    private ForgotContract.Interactor forgotInteractor;

    public ForgotPresenter(ForgotContract.View forgotView) {
        super(forgotView);
        this.forgotView = forgotView;
        forgotInteractor = new ForgotInteractor(this);
    }

    @Override
    public void resetPassword(String email) {
        forgotView.showLoading();
        if (checkResetPasswordRequirements(email)) {
            forgotInteractor.performResetPassword(email);
        }
    }

    private boolean checkResetPasswordRequirements(String email) {
        boolean isCorrect = true;
        if (email.isEmpty()) {
            forgotView.setEmailError("This field can\'t be empty.");
            isCorrect = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgotView.setEmailError("This is not a valid email.");
            isCorrect = false;
        }
        return isCorrect;
    }

    @Override
    public void onSuccess(String message) {
        forgotView.hideLoading();
        forgotView.onSendEmailSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        forgotView.hideLoading();
        forgotView.onSendEmailFailure(message);
    }
}
