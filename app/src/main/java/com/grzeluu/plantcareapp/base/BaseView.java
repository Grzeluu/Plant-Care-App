package com.grzeluu.plantcareapp.base;

import androidx.annotation.StringRes;

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showMessage(String message);

    void showMessage(@StringRes int resId);
}