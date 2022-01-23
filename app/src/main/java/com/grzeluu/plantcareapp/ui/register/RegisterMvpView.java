package com.grzeluu.plantcareapp.ui.register;

import com.grzeluu.plantcareapp.ui.base.BaseMvpView;

public interface RegisterMvpView extends BaseMvpView {
    void setUsernameError(String error);

    void setEmailError(String error);

    void setPasswordError(String error);

    void openLoginActivity();

    void setRepeatPasswordError(String s);

}
