package com.grzeluu.plantcareapp.core.register;

import com.grzeluu.plantcareapp.base.BaseView;

public interface RegisterContract {
    interface View extends BaseView {
        void setUsernameError(String error);

        void setEmailError(String error);

        void setPasswordError(String error);

        void setRepeatedPasswordError(String error);

        void onRegisterSuccess(String message);

        void onRegisterFailure(String message);
    }

    interface Presenter {
        void register(String username, String email, String password, String repeatedPassword);
    }

    interface Interactor {
        void performRegister(String username, String email, String password);
    }

    interface RegisterListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
