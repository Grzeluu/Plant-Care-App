package com.grzeluu.plantcareapp.core.add;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grzeluu.plantcareapp.model.UserPlant;

public class AddInteractor implements AddContract.Interactor {

    AddContract.Listener addListener;

    public AddInteractor(AddContract.Listener addListener) {
        this.addListener = addListener;
    }

    @Override
    public void performAddPlant(UserPlant plant) {
        String timestamp = "" + (System.currentTimeMillis());

        addListener.onStart();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(FirebaseAuth.getInstance().getUid())
                .child("UserPlants").child(timestamp).setValue(plant)
                .addOnSuccessListener(lister -> {
                    addListener.onEnd();
                    addListener.onSuccess("Your plant has been added");
                })
                .addOnFailureListener(listener -> {
                    addListener.onEnd();
                    addListener.onFailure(listener.getMessage());
                });
    }

   /* public void getPlant(String id) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addPlantView.showLoading();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Plant plant = ds.getValue(Plant.class);
                    if (plant.getId().equals(id)) {
                        addPlantView.setPlantCommonName(plant.getCommonName());
                        addPlantView.setPlantLatinName(plant.getLatinName());
                        addPlantView.setPlantType(plant.getType());

                        addPlantView.setPlantPhoto(plant.getImage());

                        addPlantView.setWateringFrequency(plant.getWateringFrequency());
                        addPlantView.setFertilizingFrequency(plant.getFertilizingFrequency());
                        addPlantView.setSprayingFrequency(plant.getSprayingFrequency());

                        addPlantView.setPlant(plant);
                        addPlantView.hideLoading();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }*/
}