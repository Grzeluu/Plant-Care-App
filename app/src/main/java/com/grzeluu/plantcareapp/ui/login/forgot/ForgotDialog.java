package com.grzeluu.plantcareapp.ui.login.forgot;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.grzeluu.plantcareapp.databinding.DialogForgotPasswordBinding;

public class ForgotDialog extends Dialog implements ForgotMvpView {
    private static final String TAG = "ForgotPasswordDialog";

    private ForgotMvpPresenter presenter;
    private DialogForgotPasswordBinding binding;

    public ForgotDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ForgotPresenter(this);
        binding = DialogForgotPasswordBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btResetPassword.setOnClickListener(v -> presenter.onResetPasswordClick(binding.etEmail.getText().toString()));
    }

    @Override
    public void setEmailError(String error) {
        binding.etEmail.setError(error);
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
}
