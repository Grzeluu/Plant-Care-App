package com.grzeluu.plantcareapp.model;

import java.io.Serializable;

public class Advice implements Serializable {
    String id;
    String answer;
    String question;
    boolean isVerified;

    public Advice() {
    }

    public Advice(String id, String answer, String question, boolean isVerified) {
        this.id = id;
        this.answer = answer;
        this.question = question;
        this.isVerified = isVerified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
