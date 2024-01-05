package com.grzeluu.plantcareapp.core.main;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grzeluu.plantcareapp.model.User;

public class MainInteractor implements MainContract.Interactor {

    private MainContract.Listener mainListener;

    public MainInteractor(MainContract.Listener mainListener) {
        this.mainListener = mainListener;
    }

    @Override
    public void performGetUserData() {

        mainListener.onStart();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseAuth.getCurrentUser().getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                User user = snapshot.getValue(User.class);
                                mainListener.onEnd();
                                mainListener.onSuccess(user);
                            } else {
                                mainListener.onEnd();
                                mainListener.onFailure();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            mainListener.onEnd();
                            mainListener.onFailure();
                        }
                    });
        } else {
            mainListener.onEnd();
            mainListener.onFailure();
        }
    }

    @Override
    public void performLogout() {
        mainListener.onEnd();
        FirebaseAuth.getInstance().signOut();
        mainListener.onFailure();
    }
}
