package com.grzeluu.plantcareapp.ui.login;

import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.grzeluu.plantcareapp.ui.base.UserStorage;

public class LoginPresenter implements LoginMvpPresenter {

    LoginMvpView loginView;
    private final FirebaseAuth mAuth;
    private  UserStorage userStorage;

    public LoginPresenter(LoginMvpView loginView, UserStorage userStorage) {
        this.loginView = loginView;
        this.mAuth = FirebaseAuth.getInstance();
        this.userStorage = userStorage;
    }

    @Override
    public void onLoginClick(String email, String password) {
        if (loginView != null) {
            boolean hasErrors = false;
            if (email.isEmpty()) {
                loginView.setEmailError("This field can\'t be empty.");
                hasErrors = true;
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                loginView.setEmailError("This is not a valid email.");
                hasErrors = true;
            }
            if (password.isEmpty()) {
                loginView.setPasswordError("This field can\'t be empty.");
                hasErrors = true;
            }

            if (!hasErrors) {
                loginUser(email, password);
            }
        }
    }

    private void loginUser(String email, String password) {
        loginView.showProgress();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loginView.hideProgress();
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    userStorage.save(mAuth);
                    if (user != null) {
                        if (user.isEmailVerified()) {
                            loginView.openMainActivity();
                        } else {
                            user.sendEmailVerification();
                            loginView.showMessage("Check your email to verify account!");
                        }
                    }
                } else {
                    loginView.showMessage("Failed to login! Please check your credentials");
                }
            }
        });
    }

    @Override
    public void onRegisterClick() {
        if (loginView != null) loginView.openRegisterActivity();
    }

    @Override
    public void onForgotPasswordClick() {
        loginView.showForgotPasswordDialog();
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }


}
