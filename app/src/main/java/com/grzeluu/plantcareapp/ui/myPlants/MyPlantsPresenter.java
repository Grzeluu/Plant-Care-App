package com.grzeluu.plantcareapp.ui.myPlants;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grzeluu.plantcareapp.model.UserPlant;

import java.util.ArrayList;
import java.util.List;

public class MyPlantsPresenter implements MyPlantsMvpPresenter {
    MyPlantsMvpView view;
    DatabaseReference reference;
    List<UserPlant> plantList;

    public MyPlantsPresenter(MyPlantsMvpView view) {
        this.view = view;
        this.reference = FirebaseDatabase.getInstance().getReference("Users");;
        this.plantList = new ArrayList<>();
    }

    @Override
    public void refreshPlantList() {
        view.showLoading();
        reference.child(FirebaseAuth.getInstance().getUid()).child("UserPlants")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plantList.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    UserPlant plant = ds.getValue(UserPlant.class);
                    plantList.add(plant);
                }
                view.hideLoading();
                view.updatePlants(plantList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
