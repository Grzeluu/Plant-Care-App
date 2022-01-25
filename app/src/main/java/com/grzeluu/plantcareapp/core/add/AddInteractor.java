package com.grzeluu.plantcareapp.core.add;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.grzeluu.plantcareapp.model.UserPlant;

public class AddInteractor implements AddContract.Interactor {

    AddContract.Listener addListener;

    public AddInteractor(AddContract.Listener addListener) {
        this.addListener = addListener;
    }

    @Override
    public void performAddPlant(UserPlant plant) {

        addListener.onStart();
        if (plant.getImage() != null) {
            addPlantWithImage(plant);
        } else {
            addPlant(plant);
        }
    }

    private void addPlantWithImage(UserPlant plant) {
        String filePathAndName = "plant_images/" + plant.getId();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(Uri.parse(plant.getImage()))
                .addOnSuccessListener(taskSnapshot -> {
                    addPlant(plant);
                })
                .addOnFailureListener(e -> {
                    addListener.onEnd();
                    addListener.onFailure(e.getMessage());
                });
    }

    private void addPlant(UserPlant plant) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("UserPlants");

        databaseReference.child(plant.getId()).setValue(plant)
                .addOnSuccessListener(aVoid -> {
                    addListener.onEnd();
                    addListener.onSuccess("Plant added to our database");
                })
                .addOnFailureListener(e -> {
                    addListener.onEnd();
                    addListener.onFailure(e.getMessage());
                });
    }
}