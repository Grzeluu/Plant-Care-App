package com.grzeluu.plantcareapp.core.myplants;

import static com.grzeluu.plantcareapp.utils.TimeUtils.getCurrentDate;

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

public class MyPlantsInteractor implements MyPlantsContract.Interactor {

    private MyPlantsContract.Listener myPlantsListener;

    public MyPlantsInteractor(MyPlantsContract.Listener myPlantsListener) {
        this.myPlantsListener = myPlantsListener;
    }

    @Override
    public void fetchMyPlantList() {
        myPlantsListener.onStart();
        List<UserPlant> plantList = new ArrayList();

        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getUid()).child("UserPlants")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        plantList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            UserPlant plant = ds.getValue(UserPlant.class);
                            plantList.add(plant);
                        }
                        myPlantsListener.onEnd();
                        myPlantsListener.onSuccess(plantList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        myPlantsListener.onEnd();
                        myPlantsListener.onFailure(error.getMessage());
                    }
                });
    }

    @Override
    public void performUpdatePlantNeeds(UserPlant plant, boolean isWatered, boolean isFertilized, boolean isSprayed) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(FirebaseAuth.getInstance().getUid())
                .child("UserPlants").child(plant.getId());
        String now = getCurrentDate();

        if(isWatered){
            ref.child("lastWatering").setValue(now);
            plant.setLastWatering(now);
        }
        if(isFertilized){
            ref.child("lastFertilizing").setValue(now);
            plant.setLastFertilizing(now);
        }
        if(isSprayed){
            ref.child("lastSpraying").setValue(now);
            plant.setLastSpraying(now);
        }
    }
}
