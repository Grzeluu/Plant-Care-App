package com.grzeluu.plantcareapp.ui.base;

import androidx.annotation.StringRes;

public interface BaseMvpView {

    void showLoading();

    void hideLoading();

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void showProgress();

    void hideProgress();

}