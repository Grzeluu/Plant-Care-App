package com.grzeluu.plantcareapp.ui_temporary.check;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grzeluu.plantcareapp.model.Advice;
import com.grzeluu.plantcareapp.model.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckPlantPresenter implements CheckPlantMvpPresenter {

    private final CheckPlantMvpView checkPlantView;
    DatabaseReference reference;
    List<Advice> adviceList;

    public CheckPlantPresenter(CheckPlantMvpView checkPlantView) {
        this.checkPlantView = checkPlantView;
        reference = FirebaseDatabase.getInstance().getReference("Plants");
        adviceList = new ArrayList<>();
    }

    @Override
    public void getPlant(String id) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Plant plant = ds.getValue(Plant.class);
                    if (plant.getId().equals(id)) {
                        checkPlantView.setPlantName(plant.getCommonName());
                        checkPlantView.setPlantLatinName(plant.getLatinName());
                        checkPlantView.setPlantDescription(plant.getDescription());
                        checkPlantView.setPlantType(plant.getType());
                        checkPlantView.updateAdvices(plant.getAdvicesList());
                        checkPlantView.setPlantPhoto(plant.getImage());

                        checkPlantView.setWateringFrequency(plant.getWateringFrequency());
                        checkPlantView.setFertilizingFrequency(plant.getFertilizingFrequency());
                        checkPlantView.setSprayingFrequency(plant.getSprayingFrequency());

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
    public void addPost(String id, String author, String text) {
        boolean hasErrors = false;
        String timestamp = "" + (System.currentTimeMillis());

        if (text.isEmpty()) {
            checkPlantView.setPostError("This field can\'t be empty.");
            hasErrors = true;
        }

        if (!hasErrors) {
            checkPlantView.showLoading();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", timestamp);
            hashMap.put("author", author);
            hashMap.put("text", text);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Plants");
            databaseReference.child(id).child("Posts").child(timestamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkPlantView.hideLoading();
                            checkPlantView.showMessage("Post added");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            checkPlantView.hideLoading();
                            checkPlantView.showMessage(e.getMessage());
                        }
                    });
        }
    }

    @Override
    public void refreshAdvicesList(String id) {
        checkPlantView.showLoading();
        reference.child(id).child("Advices").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adviceList.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Advice advice = ds.getValue(Advice.class);
                    adviceList.add(advice);
                }
                checkPlantView.updateAdvices(adviceList);
                checkPlantView.hideLoading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                checkPlantView.hideLoading();
            }
        });
    }
}
