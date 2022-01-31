package com.grzeluu.plantcareapp.core.discover;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grzeluu.plantcareapp.model.Plant;

import java.util.ArrayList;
import java.util.List;

public class DiscoverInteractor implements DiscoverContract.Interactor {

    DiscoverContract.Listener discoverListener;

    public DiscoverInteractor(DiscoverContract.Listener discoverListener) {
        this.discoverListener = discoverListener;
    }

    @Override
    public void performGetAllPlants() {
        discoverListener.onStart();
        List<Plant> plantList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Plants")
                .limitToFirst(50)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Plant plant = ds.getValue(Plant.class);
                            plantList.add(plant);
                        }
                        discoverListener.onEnd();
                        discoverListener.onSuccess(plantList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        discoverListener.onEnd();
                        discoverListener.onFailure(error.getMessage());
                    }
                });
    }

    @Override
    public void performGetMatchingPlants(String regex) {
        discoverListener.onStart();
        List<Plant> plantList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Plants")
                .orderByChild("commonName")
                .startAt(regex)
                .endAt(regex + "\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Plant plant = ds.getValue(Plant.class);
                            plantList.add(plant);
                        }
                        discoverListener.onEnd();
                        discoverListener.onSuccess(plantList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        discoverListener.onEnd();
                        discoverListener.onFailure(error.getMessage());
                    }
                });
    }
}
