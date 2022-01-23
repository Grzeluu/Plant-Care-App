package com.grzeluu.plantcareapp.ui.login.forgot;

public interface ForgotMvpView {

    void setEmailError(String error);

    void showMessage(String s);

    void dismissDialog();
}
