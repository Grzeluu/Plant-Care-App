package com.grzeluu.plantcareapp.ui.login;

public interface LoginMvpPresenter {

    void onLoginClick(String username, String password);

    void onRegisterClick();

    void onForgotPasswordClick();

    void onDestroy();
}
