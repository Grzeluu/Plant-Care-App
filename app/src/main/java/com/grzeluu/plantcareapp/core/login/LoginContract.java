package com.grzeluu.plantcareapp.core.login;

import com.grzeluu.plantcareapp.base.BaseMvpView;

public interface LoginContract {
    interface View extends BaseMvpView {
        void setEmailError(String error);

        void setPasswordError(String error);

        void onLoginSuccess(String message);

        void onLoginFailure(String message);
    }

    interface Presenter {
        void login(String username, String password);
    }

    interface Interactor {
        void performLogin( String email, String password);
    }

    interface LoginListener {
        void onSuccess(String message);
        void onFailure(String message);
    }
}
