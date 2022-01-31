package com.grzeluu.plantcareapp.view;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.grzeluu.plantcareapp.base.BaseActivity;
import com.grzeluu.plantcareapp.core.login.LoginContract;
import com.grzeluu.plantcareapp.core.login.LoginPresenter;
import com.grzeluu.plantcareapp.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    ActivityLoginBinding binding;
    LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        presenter = new LoginPresenter(this);

        binding.btLogin.setOnClickListener(v -> presenter.login(
                binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString()
        ));

        binding.btRegister.setOnClickListener(v -> openRegisterActivity());

        binding.tvForgotPassword.setOnClickListener(v -> showForgotPasswordDialog());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onLoginSuccess(int message) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void showForgotPasswordDialog() {
        ForgotDialog forgotDialog = new ForgotDialog(this);
        forgotDialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        forgotDialog.show();
    }

    @Override
    public void onLoginFailure(String message) {
        showMessage(message);
    }

    @Override
    public void setEmailError(int error) {
        binding.etEmail.setError(getString(error));
    }

    @Override
    public void setPasswordError(int error) {
        binding.etPassword.setError(getString(error));
    }
}