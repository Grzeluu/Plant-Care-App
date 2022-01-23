package com.grzeluu.plantcareapp.core.register;

import android.util.Patterns;

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.RegisterListener {

    private RegisterContract.View registerView;
    private RegisterContract.Interactor registerInteractor;

    public RegisterPresenter(RegisterContract.View registerView) {
        this.registerView = registerView;
        registerInteractor = new RegisterInteractor(this);
    }

    @Override
    public void register(String username, String email, String password, String repeatedPassword) {
        registerView.showLoading();
        if (checkRegisterRequirements(username, email, password, repeatedPassword))
            registerInteractor.performRegister(username, email, password);
    }

    public boolean checkRegisterRequirements(
            String username, String email,
            String password, String repeatedPassword) {
        boolean isCorrect = true;

        if (username.isEmpty()) {
            registerView.setUsernameError("This field can't be empty");
            isCorrect = false;
        }
        if (email.isEmpty()) {
            registerView.setEmailError("This field can't be empty");
            isCorrect = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerView.setEmailError("This is not a valid email.");
            isCorrect = false;
        }
        if (password.isEmpty()) {
            registerView.setPasswordError("This field can't be empty");
            isCorrect = false;
        } else if (password.length() < 6) {
            registerView.setPasswordError("Password too short! At least 6 char.");
            isCorrect = false;

        } else if (!password.equals(repeatedPassword)) {
            registerView.setRepeatedPasswordError("Passwords didn't match");
            isCorrect = false;
        }
        return isCorrect;
    }

    @Override
    public void onSuccess(String message) {
        registerView.hideLoading();
        registerView.onRegisterSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        registerView.hideLoading();
        registerView.onRegisterFailure(message);
    }
}
