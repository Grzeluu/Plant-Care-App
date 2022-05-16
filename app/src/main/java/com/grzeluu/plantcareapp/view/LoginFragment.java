package com.grzeluu.plantcareapp.view;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grzeluu.plantcareapp.base.BaseFragment;
import com.grzeluu.plantcareapp.core.login.LoginContract;
import com.grzeluu.plantcareapp.core.login.LoginPresenter;
import com.grzeluu.plantcareapp.databinding.FragmentLoginBinding;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    FragmentLoginBinding binding;
    LoginContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        presenter = new LoginPresenter(this);

        initViews();
        super.onViewCreated(binding.getRoot(), savedInstanceState);
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
    public void onLoginSuccess(int message) {

    }

    public void openRegisterActivity() {

    }

    public void showForgotPasswordDialog() {
        ForgotDialog forgotDialog = new ForgotDialog(getContext());
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