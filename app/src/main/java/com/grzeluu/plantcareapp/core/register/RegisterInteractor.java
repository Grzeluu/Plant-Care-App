package com.grzeluu.plantcareapp.core.register;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.grzeluu.plantcareapp.model.User;

public class RegisterInteractor implements RegisterContract.Interactor {

    private RegisterContract.Listener registerListener;

    public RegisterInteractor(RegisterContract.Listener registerListener) {
        this.registerListener = registerListener;
    }

    @Override
    public void performRegister(String username, String email, String password) {
        registerListener.onStart();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(registerUser -> {
                    if (registerUser.isSuccessful()) {
                        addUserToDatabase(username, email);
                    } else {
                        registerListener.onEnd();
                        registerListener.onFailure(registerUser.getException().getMessage());
                    }
                });
    }

    private void addUserToDatabase(String username, String email) {

        String uid = FirebaseAuth.getInstance().getUid();
        User user = new User(uid, username, email);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid())
                .setValue(user)
                .addOnCompleteListener(addUserTask -> {
                    if (addUserTask.isSuccessful()) {
                        registerListener.onEnd();
                        registerListener.onSuccess("User registered successfully");
                    } else {
                        registerListener.onEnd();
                        registerListener.onFailure(addUserTask.getException().getMessage());
                    }
                });
    }
}
