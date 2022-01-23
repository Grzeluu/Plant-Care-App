package com.grzeluu.plantcareapp.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.grzeluu.plantcareapp.databinding.ActivityLoginBinding;
import com.grzeluu.plantcareapp.ui.base.App;
import com.grzeluu.plantcareapp.ui.login.forgot.ForgotDialog;
import com.grzeluu.plantcareapp.ui.main.MainActivity;
import com.grzeluu.plantcareapp.ui.base.BaseActivity;
import com.grzeluu.plantcareapp.ui.register.RegisterActivity;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    ActivityLoginBinding binding;
    LoginMvpPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new LoginPresenter(this, ((App) getApplication()).getUserStorage());

        binding.btLogin.setOnClickListener(v -> presenter.onLoginClick(
                binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString()
        ));

        binding.btRegister.setOnClickListener(v-> presenter.onRegisterClick());

        binding.tvForgotPassword.setOnClickListener(v -> presenter.onForgotPasswordClick());
    }

    @Override
    public void setEmailError(String error) {
        binding.etEmail.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        binding.etPassword.setError(error);
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void openRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showForgotPasswordDialog() {
        ForgotDialog dialog = new ForgotDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}