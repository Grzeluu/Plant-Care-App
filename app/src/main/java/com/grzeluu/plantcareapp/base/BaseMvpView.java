package com.grzeluu.plantcareapp.base;

import androidx.annotation.StringRes;

public interface BaseMvpView {

    void showLoading();

    void hideLoading();

    void showMessage(String message);

    void showMessage(@StringRes int resId);
}