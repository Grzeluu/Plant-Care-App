package com.grzeluu.plantcareapp.core.login;

import android.util.Patterns;

import com.grzeluu.plantcareapp.base.BasePresenter;

public class LoginPresenter extends BasePresenter
        implements LoginContract.Presenter, LoginContract.Listener {

    private LoginContract.View loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenter(LoginContract.View loginView) {
        super(loginView);
        this.loginView = loginView;
        loginInteractor = new LoginInteractor(this);
    }

    @Override
    public void login(String email, String password) {
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
        loginView.onLoginSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        loginView.onLoginFailure(message);
    }
}
