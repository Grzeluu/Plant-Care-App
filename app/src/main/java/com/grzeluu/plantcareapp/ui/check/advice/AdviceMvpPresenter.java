package com.grzeluu.plantcareapp.ui.check.advice;

public interface AdviceMvpPresenter {

    void onAskQuestionClick(String question, String answer, String plantId);
    void onAskQuestionClick(String question, String answer, String plantId, String questionId);
}
