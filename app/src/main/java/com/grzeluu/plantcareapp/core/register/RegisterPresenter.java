package com.grzeluu.plantcareapp.core.register;

import android.util.Patterns;

import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.base.BasePresenter;

public class RegisterPresenter extends BasePresenter
        implements RegisterContract.Presenter, RegisterContract.Listener {

    private RegisterContract.View registerView;
    private RegisterContract.Interactor registerInteractor;

    public RegisterPresenter(RegisterContract.View registerView) {
        super(registerView);
        this.registerView = registerView;
        registerInteractor = new RegisterInteractor(this);
    }

    @Override
    public void register(String username, String email, String password, String repeatedPassword) {
        if (checkRegisterRequirements(username, email, password, repeatedPassword))
            registerInteractor.performRegister(username, email, password);
    }

    public boolean checkRegisterRequirements(
            String username, String email,
            String password, String repeatedPassword) {
        boolean isCorrect = true;

        if (username.isEmpty()) {
            registerView.setUsernameError(R.string.this_field_cant_be_empty);
            isCorrect = false;
        }
        if (email.isEmpty()) {
            registerView.setEmailError(R.string.this_field_cant_be_empty);
            isCorrect = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerView.setEmailError(R.string.email_not_valid);
            isCorrect = false;
        }
        if (password.isEmpty()) {
            registerView.setPasswordError(R.string.this_field_cant_be_empty);
            isCorrect = false;
        } else if (password.length() < 6) {
            registerView.setPasswordError(R.string.password_too_short);
            isCorrect = false;

        } else if (!password.equals(repeatedPassword)) {
            registerView.setRepeatedPasswordError(R.string.passwords_didnt_match);
            isCorrect = false;
        }
        return isCorrect;
    }

    @Override
    public void onSuccess(int message) {
        registerView.onRegisterSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        registerView.onRegisterFailure(message);
    }
}
