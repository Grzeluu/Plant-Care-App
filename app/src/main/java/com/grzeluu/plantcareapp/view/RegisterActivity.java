package com.grzeluu.plantcareapp.view;

import android.content.Intent;
import android.os.Bundle;

import com.grzeluu.plantcareapp.core.register.RegisterContract;
import com.grzeluu.plantcareapp.core.register.RegisterPresenter;
import com.grzeluu.plantcareapp.databinding.ActivityRegisterBinding;
import com.grzeluu.plantcareapp.base.BaseActivity;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    ActivityRegisterBinding binding;
    RegisterContract.Presenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
    }

    private void initViews() {
        registerPresenter = new RegisterPresenter(this);

        binding.btRegister.setOnClickListener(v -> registerPresenter.register(
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
    public void setRepeatedPasswordError(String error) {
        binding.etRepeatPassword.setError(error);
    }

    @Override
    public void onRegisterSuccess(String message) {
        showMessage(message);
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRegisterFailure(String message) {
        showMessage(message);
    }
}