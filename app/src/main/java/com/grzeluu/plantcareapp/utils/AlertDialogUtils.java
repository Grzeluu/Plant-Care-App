package com.grzeluu.plantcareapp.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.core.myplants.MyPlantsContract;
import com.grzeluu.plantcareapp.model.UserPlant;

public class AlertDialogUtils {
    static public void showDeletePlantDialog(Context context, MyPlantsContract.Presenter presenter, UserPlant plant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete plant");
        builder.setMessage("Are you sure you want to delete this plant?");
        builder.setPositiveButton("Confirm", (dialog, id) -> {
            presenter.deletePlant(plant);
        });

        builder.setNeutralButton(R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
