package com.grzeluu.plantcareapp.core.register;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.grzeluu.plantcareapp.model.User;

public class RegisterInteractor implements RegisterContract.Interactor {

    private RegisterContract.RegisterListener registerListener;

    public RegisterInteractor(RegisterContract.RegisterListener registerListener) {
        this.registerListener = registerListener;
    }

    @Override
    public void performRegister(String username, String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(registerUser -> {
                    if (registerUser.isSuccessful()) {
                        addUserToDatabase(username, email);
                    } else {
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
                        registerListener.onSuccess("User registered successfully");
                    } else {
                        registerListener.onFailure(addUserTask.getException().getMessage());
                    }
                });
    }
}
