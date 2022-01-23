package com.grzeluu.plantcareapp.ui_temporary.suggest;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class SuggestPresenter implements SuggestMvpPresenter {

    private final SuggestMvpView suggestView;

    public SuggestPresenter(SuggestMvpView suggestView) {
        this.suggestView = suggestView;
    }

    @Override
    public void onSuggestNewPlantClick(Uri imageUri, String commonName, String latinName, String type, String description, int frequencyOfWatering, int frequencyOfFertilization, int frequencyOfSpraying) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String timestamp = "" + (System.currentTimeMillis());

        boolean hasErrors = false;
        if (imageUri == null) {
            suggestView.showMessage("Please add photo of your plant");
            hasErrors = true;
        }
        if (commonName.isEmpty()) {
            suggestView.setNameError("Field can't be empty");
            hasErrors = true;
        }
        if (type.isEmpty()) {
            suggestView.setCategoryError("Please choose category");
            hasErrors = true;
        }

        if (!hasErrors) {
            suggestView.showProgress();

            String filePathAndName = "plant_images/" + timestamp;

            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadedImageUri = uriTask.getResult();
                            if (uriTask.isSuccessful()) {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("id", timestamp);
                                hashMap.put("image", "" + downloadedImageUri);
                                hashMap.put("commonName", commonName);
                                hashMap.put("latinName", latinName);
                                hashMap.put("type", type);
                                hashMap.put("description", description);
                                hashMap.put("wateringFrequency", toDays(frequencyOfWatering));
                                hashMap.put("fertilizingFrequency", toDays(frequencyOfFertilization));
                                hashMap.put("sprayingFrequency", toDays(frequencyOfSpraying));
                                hashMap.put("isVerified", false);

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Plants");
                                databaseReference.child(timestamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                suggestView.hideProgress();
                                                suggestView.showMessage("Plant added to our database");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                suggestView.hideProgress();
                                                suggestView.showMessage(e.getMessage());
                                            }
                                        });
                            }
                        }
                    });
        }
    }

    private int toDays(int frequency) {

        if (frequency >= 0 && frequency <= 30) {
            return frequency;
        } else if (frequency > 30 && frequency <= 35) {
            return (30 + (frequency - 30) * 5);
        } else {
            return (frequency - 34) * 30;
        }
    }
}

