package com.grzeluu.plantcareapp.ui_temporary.register;

import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterPresenter implements RegisterMvpPresenter {
    RegisterMvpView registerView;
    private FirebaseAuth mAuth;

    public RegisterPresenter(RegisterMvpView registerView) {
        this.registerView = registerView;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onRegisterClick(String username, String email, String password, String repeatedPassword) {
        boolean hasErrors = false;

        if (username.isEmpty()) {
            registerView.setUsernameError("This field can't be empty");
            hasErrors = true;
        }
        if (email.isEmpty()) {
            registerView.setEmailError("This field can't be empty");
            hasErrors = true;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerView.setEmailError("This is not a valid email.");
            hasErrors = true;
        }
        if (password.isEmpty()) {
            registerView.setPasswordError("This field can't be empty");
            hasErrors = true;
        } else if (password.length() < 6) {
            registerView.setPasswordError("Password too short! At least 6 char.");
            hasErrors = true;

        } else if (!password.equals(repeatedPassword) ) {
            registerView.setRepeatPasswordError("Passwords didn't match");
            hasErrors = true;
        }
        if (!hasErrors) {
            registerUser(username, email, password);
        }
    }

    private void registerUser(String username, String email, String password) {
        registerView.showProgress();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            HashMap<String, Object> userData = new HashMap<>();
                            userData.put("uid", mAuth.getUid());
                            userData.put("username", username);
                            userData.put("email", email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        registerView.showMessage("User has been registered successfully!");
                                        registerView.hideProgress();

                                        registerView.openLoginActivity();
                                    } else {
                                        registerView.showMessage("Failed to register! Try again!");
                                        registerView.hideProgress();
                                    }
                                }
                            });
                        } else {
                            registerView.showMessage("Failed to register!");
                            registerView.hideProgress();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        registerView = null;
    }
}