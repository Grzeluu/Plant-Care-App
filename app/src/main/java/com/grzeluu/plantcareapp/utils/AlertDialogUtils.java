package com.grzeluu.plantcareapp.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.model.UserPlant;

public class AlertDialogUtils {
    static public void showDeletePlantDialog(Context context, UserPlant plant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete plant");
        builder.setMessage("Are you sure you want to delete this plant?");
        builder.setPositiveButton("Confirm", (dialog, id) -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
            ref.child("UserPlants").child(plant.getId()).removeValue();
        });

        builder.setNeutralButton(R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
