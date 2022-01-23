package com.grzeluu.plantcareapp.ui_temporary.register;

import android.content.Intent;
import android.os.Bundle;

import com.grzeluu.plantcareapp.databinding.ActivityRegisterBinding;
import com.grzeluu.plantcareapp.ui_temporary.base.BaseActivity;
import com.grzeluu.plantcareapp.view.LoginActivity;

public class RegisterActivity extends BaseActivity implements RegisterMvpView {

    ActivityRegisterBinding binding;
    RegisterMvpPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerPresenter = new RegisterPresenter(this);

        binding.btRegister.setOnClickListener(v -> registerPresenter.onRegisterClick(
                binding.etUsername.getText().toString(),
                binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString(),
                binding.etRepeatPassword.getText().toString()
        ));
    }

    @Override
    public void setUsernameError(String error) {
        binding.etUsername.setError(error);
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
    public void setRepeatPasswordError(String error) {
        binding.etRepeatPassword.setError(error);
    }

    @Override
    public void openLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}