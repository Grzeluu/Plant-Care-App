package com.grzeluu.plantcareapp.view;

import static com.grzeluu.plantcareapp.utils.TextViewUtils.setErrorBehavior;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grzeluu.plantcareapp.base.BaseFragment;
import com.grzeluu.plantcareapp.core.register.RegisterContract;
import com.grzeluu.plantcareapp.core.register.RegisterPresenter;
import com.grzeluu.plantcareapp.databinding.FragmentRegisterBinding;

public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    FragmentRegisterBinding binding;
    RegisterContract.Presenter registerPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        registerPresenter = new RegisterPresenter(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    private void initViews() {
        binding.btRegister.setOnClickListener(v -> registerPresenter.register(
                binding.etUsername.getText().toString(),
                binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString(),
                binding.etRepeatPassword.getText().toString()
        ));

        setErrorBehavior(binding.etUsername, binding.tilUsername);
        setErrorBehavior(binding.etEmail, binding.tilEmail);
        setErrorBehavior(binding.etPassword, binding.tilPassword);
        setErrorBehavior(binding.etRepeatPassword, binding.tilRepeatPassword);
    }

    @Override
    public void setUsernameError(int error) {
        binding.tilUsername.setError(getString(error));
    }

    @Override
    public void setEmailError(int error) {
        binding.tilEmail.setError(getString(error));
    }

    @Override
    public void setPasswordError(int error) {
        binding.tilPassword.setError(getString(error));
    }

    @Override
    public void setRepeatedPasswordError(int error) {
        binding.tilRepeatPassword.setError(getString(error));
    }

    @Override
    public void onRegisterSuccess(int message) {
        showMessage(message);

    }

    @Override
    public void onRegisterFailure(String message) {
        showMessage(message);
    }
}