package com.grzeluu.plantcareapp.ui_temporary.check.advice;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.grzeluu.plantcareapp.databinding.DialogAskBinding;

public class AdviceDialog extends Dialog implements AdviceMvpView {

    private AdviceMvpPresenter presenter;
    private DialogAskBinding binding;
    private String plantId;
    private boolean isQuestioned;
    private String questionId;
    private String question;

    public AdviceDialog(@NonNull Context context, String id) {
        super(context);
        plantId = id;
        this.isQuestioned = false;
    }

    public AdviceDialog(@NonNull Context context, String id, boolean isQuestioned, String questionId, String question) {
        super(context);
        this.plantId = id;
        this.isQuestioned = isQuestioned;
        this.questionId = questionId;
        this.question = question;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AdvicePresenter(this);
        binding = DialogAskBinding.inflate(getLayoutInflater());

        if (isQuestioned) {
            binding.etQuestion.setFocusable(false);
            binding.etQuestion.setText(question);
            binding.btAskAQuestion.setOnClickListener(v -> presenter.onAskQuestionClick(
                    binding.etQuestion.getText().toString(),
                    binding.etAnswer.getText().toString(),
                    plantId, questionId));
        }

        binding.btAskAQuestion.setOnClickListener(v -> presenter.onAskQuestionClick(binding.etQuestion.getText().toString(), binding.etAnswer.getText().toString(), plantId));
        setContentView(binding.getRoot());
    }

    @Override
    public void setQuestionError(String error) {
        binding.etQuestion.setError(error);
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }

    @Override
    public void setAnswerError(String error) {
        binding.etAnswer.setError(error);

    }

}
