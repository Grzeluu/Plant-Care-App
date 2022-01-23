package com.grzeluu.plantcareapp.ui_temporary.main;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grzeluu.plantcareapp.ui_temporary.base.UserStorage;

public class MainPresenter implements MainMvpPresenter {
    MainMvpView mainView;
    private final FirebaseAuth firebaseAuth;
    private UserStorage userStorage;

    public MainPresenter(MainMvpView mainView, UserStorage userStorage) {
        this.mainView = mainView;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userStorage = userStorage;
    }


    @Override
    public void onNavMenuCreated() {
        if (firebaseAuth.getCurrentUser() != null) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot userSnapShot : snapshot.getChildren()) {
                                String username = "" + userSnapShot.child("username").getValue();
                                mainView.updateUsername(username);
                                mainView.updateEmail(firebaseAuth.getCurrentUser().getEmail());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }
    }

    @Override
    public void checkUser() {
        if (!userStorage.getSessionToken().isEmpty()) {
            firebaseAuth.signInWithCustomToken(userStorage.getSessionToken());
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                mainView.goToLogin();
            }
        } else {
            mainView.goToLogin();
        }
    }

    @Override
    public void onDrawerOptionLogoutClick() {
        firebaseAuth.signOut();
        userStorage.logout();
        mainView.goToLogin();
    }
}
