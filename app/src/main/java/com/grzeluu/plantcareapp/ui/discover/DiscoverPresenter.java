package com.grzeluu.plantcareapp.ui.discover;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grzeluu.plantcareapp.model.Plant;

import java.util.ArrayList;
import java.util.List;

public class DiscoverPresenter implements DiscoverMvpPresenter{

    DiscoverMvpView discoverView;
    DatabaseReference reference;
    List<Plant> plantList;

    public DiscoverPresenter(DiscoverMvpView discoverView) {
        this.discoverView = discoverView;
        this.reference = FirebaseDatabase.getInstance().getReference("Plants");;
        this.plantList = new ArrayList<>();
    }

    @Override
    public void refreshPlantList() {
        discoverView.showLoading();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plantList.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Plant plant = ds.getValue(Plant.class);
                    plantList.add(plant);
                }
                discoverView.hideLoading();
                discoverView.updatePlants(plantList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void refreshPlantList(String search) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plantList.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Plant plant = ds.getValue(Plant.class);
                    if((plant.getCommonName()).toUpperCase().contains(search.toUpperCase())) plantList.add(plant);
                }
                discoverView.updatePlants(plantList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
