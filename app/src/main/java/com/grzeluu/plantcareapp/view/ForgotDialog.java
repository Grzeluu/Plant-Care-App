package com.grzeluu.plantcareapp.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.grzeluu.plantcareapp.core.forgot.ForgotContract;
import com.grzeluu.plantcareapp.core.forgot.ForgotPresenter;
import com.grzeluu.plantcareapp.databinding.DialogForgotPasswordBinding;
import com.grzeluu.plantcareapp.utils.CommonUtils;

public class ForgotDialog extends Dialog implements ForgotContract.View {
    private static final String TAG = "ForgotPasswordDialog";

    ProgressDialog progressDialog;
    private ForgotPresenter presenter;
    private DialogForgotPasswordBinding binding;

    public ForgotDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DialogForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new ForgotPresenter(this);

        binding.btResetPassword.setOnClickListener(v ->
                presenter.resetPassword(binding.etEmail.getText().toString()));
    }

    @Override
    public void onSendEmailSuccess(String message) {
        showMessage(message);
        dismiss();
    }

    @Override
    public void setEmailError(String error) {
        binding.etEmail.setError(error);
    }

    @Override
    public void onSendEmailFailure(String message) {
        showMessage(message);
    }

    @Override
    public void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(getContext());
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {
        showMessage(getContext().getString(resId));
    }

}
