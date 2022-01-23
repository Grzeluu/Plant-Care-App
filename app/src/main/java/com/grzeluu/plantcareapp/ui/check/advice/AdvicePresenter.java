package com.grzeluu.plantcareapp.ui.check.advice;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdvicePresenter implements AdviceMvpPresenter {

    AdviceMvpView questionMvpView;

    AdvicePresenter(AdviceMvpView forgotMvpView) {
        this.questionMvpView = forgotMvpView;
    }

    @Override
    public void onAskQuestionClick(String question, String answer, String id) {

        boolean hasErrors = false;
        String timestamp = "" + (System.currentTimeMillis());

        if (question.isEmpty()) {
            questionMvpView.setQuestionError("This field can\'t be empty.");
            hasErrors = true;
        }

        if (!hasErrors) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", timestamp);
            hashMap.put("question", question);

            if (answer.isEmpty()) {
                hashMap.put("answer", "Answer this question");
            } else {
                hashMap.put("answer", answer);
            }
            hashMap.put("isVerified", true);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Plants");
            databaseReference.child(id).child("Advices").child(timestamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            questionMvpView.showMessage("Advice added to our database");
                            questionMvpView.dismissDialog();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            questionMvpView.showMessage(e.getMessage());
                            questionMvpView.dismissDialog();
                        }
                    });
        }
    }

    @Override
    public void onAskQuestionClick(String question, String answer, String plantId, String questionId) {
        boolean hasErrors = false;
        if (answer.isEmpty()) {
            questionMvpView.setAnswerError("This field can\'t be empty.");
            hasErrors = true;
        }
        if (!hasErrors) {
            HashMap<String, Object> hashMap = new HashMap<>();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Plants");
            databaseReference.child(plantId).child("Advices").child(questionId).child(answer).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            questionMvpView.showMessage("Advice updated");
                            questionMvpView.dismissDialog();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            questionMvpView.showMessage(e.getMessage());
                            questionMvpView.dismissDialog();
                        }
                    });
        }
    }
}