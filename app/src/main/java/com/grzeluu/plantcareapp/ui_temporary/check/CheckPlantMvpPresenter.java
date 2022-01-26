package com.grzeluu.plantcareapp.ui_temporary.check;

public interface CheckPlantMvpPresenter {
    void getPlant(String id);

    void refreshAdvicesList(String id);

    void addPost(String id, String author, String text);
}
