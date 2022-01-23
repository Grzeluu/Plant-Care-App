package com.grzeluu.plantcareapp.ui.suggest;

import android.view.View;

import com.grzeluu.plantcareapp.ui.base.BaseMvpView;

public interface SuggestMvpView extends BaseMvpView {

    void setUp(View view);

    void setNameError(String error);

    void setCategoryError(String error);

    void showCategoryPickDialog();

    void showChoosePhotoDialog();

    void pickPhotoFromGallery();

    void tryPickPhotoFromGallery();

    void pickPhotoFromCamera();
}
