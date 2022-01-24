package com.grzeluu.plantcareapp.ui_temporary.main;

import com.grzeluu.plantcareapp.base.BaseViewContract;

public interface MainMvpView extends BaseViewContract {

    void goToLogin();

    void openDiscover();

    void openMyPlants();

    void openSuggest();

    void updateUsername(String username);

    void updateEmail(String email);
}
