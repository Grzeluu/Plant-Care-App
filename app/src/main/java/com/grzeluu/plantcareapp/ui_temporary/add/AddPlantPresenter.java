package com.grzeluu.plantcareapp.ui_temporary.add;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grzeluu.plantcareapp.model.Advice;
import com.grzeluu.plantcareapp.model.Plant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddPlantPresenter implements AddPlantMvpPresenter {

    private final AddPlantMvpView addPlantView;
    DatabaseReference reference;
    List<Advice> adviceList;

    public AddPlantPresenter(AddPlantMvpView addPlantView) {
        this.addPlantView = addPlantView;
        reference = FirebaseDatabase.getInstance().getReference("Plants");
    }

    @Override
    public void getPlant(String id) {
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
    }

    @Override
    public void onSuggestNewPlantClick(String name, String imageUri, String commonName, String latinName, String type, long frequencyOfWatering, long frequencyOfFertilization, long frequencyOfSpraying) {
        String timestamp = "" + (System.currentTimeMillis());

        boolean hasErrors = false;

        if (name.isEmpty()) {
            addPlantView.setNameError("Field can't be empty");
            hasErrors = true;
        }

        if (!hasErrors) {
            addPlantView.showLoading();

            String filePathAndName = "user_plant_images/" + timestamp;
            SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");

            String now = ISO_8601_FORMAT.format(new Date());

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", timestamp);
            hashMap.put("name", name);
            hashMap.put("image", "" + imageUri);
            hashMap.put("commonName", commonName);
            hashMap.put("latinName", latinName);
            hashMap.put("type", type);
            hashMap.put("wateringFrequency", toDays(frequencyOfWatering));
            hashMap.put("fertilizingFrequency", toDays(frequencyOfFertilization));
            hashMap.put("sprayingFrequency", toDays(frequencyOfSpraying));
            hashMap.put("isVerified", false);
            hashMap.put("lastWatering", now);
            hashMap.put("lastFertilizing", now);
            hashMap.put("lastSpraying", now);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            databaseReference.child(FirebaseAuth.getInstance().getUid()).child("UserPlants").child(timestamp).setValue(hashMap)
                    .addOnSuccessListener(aVoid -> {
                        addPlantView.hideLoading();
                        addPlantView.showMessage("Plant added to our database");
                    })
                    .addOnFailureListener(e -> {
                        addPlantView.hideLoading();
                        addPlantView.showMessage(e.getMessage());
                    });
        }
    }

    private long toDays(long frequency) {

        if (frequency >= 0 && frequency <= 30) {
            return frequency;
        } else if (frequency > 30 && frequency <= 35) {
            return (30 + (frequency - 30) * 5);
        } else {
            return (frequency - 34) * 30;
        }
    }
}
