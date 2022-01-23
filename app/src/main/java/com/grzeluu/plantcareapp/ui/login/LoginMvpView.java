package com.grzeluu.plantcareapp.ui.login;

import com.grzeluu.plantcareapp.ui.base.BaseMvpView;

public interface LoginMvpView extends BaseMvpView {

    void setEmailError(String error);

    void setPasswordError(String error);

    void openMainActivity();

    void openRegisterActivity();

    void showForgotPasswordDialog();
}
