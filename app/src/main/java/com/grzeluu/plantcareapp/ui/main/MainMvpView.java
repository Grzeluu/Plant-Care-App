package com.grzeluu.plantcareapp.ui.main;

import com.grzeluu.plantcareapp.ui.base.BaseMvpView;

public interface MainMvpView extends BaseMvpView {

    void goToLogin();

    void openDiscover();

    void openMyPlants();

    void openSuggest();

    void updateUsername(String username);

    void updateEmail(String email);
}
