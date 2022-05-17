package com.grzeluu.plantcareapp.view;

import static com.grzeluu.plantcareapp.utils.TextViewUtils.setErrorBehavior;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.grzeluu.plantcareapp.R;
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
        super.onViewCreated(binding.getRoot(), savedInstanceState);

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

        setErrorBehavior(binding.etEmail, binding.tilEmail);
        setErrorBehavior(binding.etPassword, binding.tilPassword);
    }

    @Override
    public void onLoginSuccess(int message) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_fragment_login_to_fragment_host);
    }

    public void openRegisterActivity() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_fragment_login_to_fragment_register);
    }

    public void showForgotPasswordDialog() {
        ForgotDialog forgotDialog = new ForgotDialog(requireContext());
        forgotDialog.show();
        Window window = forgotDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setEmailError(int error) {
        binding.tilEmail.setError(getString(error));
    }

    @Override
    public void setPasswordError(int error) {
        binding.tilPassword.setError(getString(error));
    }
}