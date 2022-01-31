package com.grzeluu.plantcareapp.core.forgot;

import android.util.Patterns;

import com.grzeluu.plantcareapp.R;
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
            forgotView.setEmailError(R.string.this_field_cant_be_empty);
            isCorrect = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgotView.setEmailError(R.string.email_not_valid);
            isCorrect = false;
        }
        return isCorrect;
    }

    @Override
    public void onSuccess(int message) {
        forgotView.hideLoading();
        forgotView.onSendEmailSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        forgotView.hideLoading();
        forgotView.onSendEmailFailure(message);
    }
}
