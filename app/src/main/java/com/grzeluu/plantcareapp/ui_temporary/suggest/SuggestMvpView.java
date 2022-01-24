package com.grzeluu.plantcareapp.ui_temporary.suggest;

import android.view.View;

import com.grzeluu.plantcareapp.base.BaseViewContract;

public interface SuggestMvpView extends BaseViewContract {

    void setUp(View view);

    void setNameError(String error);

    void setCategoryError(String error);

    void showCategoryPickDialog();

    void showChoosePhotoDialog();

    void pickPhotoFromGallery();

    void tryPickPhotoFromGallery();

    void pickPhotoFromCamera();
}
