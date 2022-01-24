package com.grzeluu.plantcareapp.core.forgot;

import com.grzeluu.plantcareapp.base.BaseView;

public interface ForgotContract {
    interface View extends BaseView {
        void onSendEmailSuccess(String message);

        void setEmailError(String message);

        void onSendEmailFailure(String message);
    }

    interface Presenter {
        void resetPassword(String email);
    }

    interface Interactor {
        void performResetPassword(String email);
    }

    interface ForgotListener {
        void onSuccess(String message);
        void onFailure(String message);
    }
}