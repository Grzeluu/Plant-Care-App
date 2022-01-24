package com.grzeluu.plantcareapp.core.add;

import com.grzeluu.plantcareapp.base.BaseListenerContract;
import com.grzeluu.plantcareapp.base.BasePresenterContract;
import com.grzeluu.plantcareapp.base.BaseViewContract;
import com.grzeluu.plantcareapp.model.UserPlant;

public interface AddContract {
    interface View extends BaseViewContract {
        void setNameError(String error);

        void plantAdded(String message);
    }

    interface Presenter extends BasePresenterContract {
        void addPlant(UserPlant plant);
    }

    interface Interactor {
        void performAddPlant(UserPlant plant);
    }

    interface Listener extends BaseListenerContract {
        void onSuccess(String message);
        void onFailure(String message);
    }
}