package com.grzeluu.plantcareapp.ui.check;

public interface CheckPlantMvpPresenter {
    void getPlant(String id);

    void refreshAdvicesList(String id);

    void refreshDiscussionList(String id);

    void addPost(String id, String author, String text);
}
