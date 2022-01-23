package com.grzeluu.plantcareapp.ui.check.advice;

public interface AdviceMvpView {

    void setQuestionError(String error);

    void showMessage(String s);

    void dismissDialog();

    void setAnswerError(String s);
}
