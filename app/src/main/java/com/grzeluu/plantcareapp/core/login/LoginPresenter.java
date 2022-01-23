package com.grzeluu.plantcareapp.core.login;

import android.util.Patterns;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.LoginListener {

    private LoginContract.View loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
        loginInteractor = new LoginInteractor(this);
    }

    @Override
    public void login(String email, String password) {
        loginView.showLoading();
        if (checkLoginRequirements(email, password))
            loginInteractor.performLogin(email, password);
    }

    private boolean checkLoginRequirements(String email, String password) {
        boolean errorsFree = true;
        if (email.isEmpty()) {
            loginView.setEmailError("This field can\'t be empty.");
            errorsFree = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginView.setEmailError("This is not a valid email.");
            errorsFree = false;
        }
        if (password.isEmpty()) {
            loginView.setPasswordError("This field can\'t be empty.");
            errorsFree = false;
        }
        return errorsFree;
    }


    @Override
    public void onSuccess(String message) {
        loginView.hideLoading();
        loginView.onLoginSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        loginView.showLoading();
        loginView.onLoginFailure(message);
    }
}
