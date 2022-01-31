package com.grzeluu.plantcareapp.core.main;

import com.grzeluu.plantcareapp.base.BasePresenter;
import com.grzeluu.plantcareapp.model.User;

public class MainPresenter extends BasePresenter
        implements MainContract.Presenter, MainContract.Listener {

    private MainContract.View mainView;
    private MainInteractor mainInteractor;

    public MainPresenter(MainContract.View mainView) {
        super(mainView);
        this.mainView = mainView;
        mainInteractor = new MainInteractor(this);
    }

    @Override
    public void checkIfUserIsLoggedIn() {
        mainInteractor.performGetUserData();
    }

    @Override
    public void onDrawerOptionLogoutClick() {
        mainInteractor.performLogout();
    }

    @Override
    public void onSuccess(User user) {
        mainView.setUser(user);
    }

    @Override
    public void onFailure() {
        mainView.requireLogin();
    }
}
