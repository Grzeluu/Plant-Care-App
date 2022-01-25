package com.grzeluu.plantcareapp.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class DialogUtils {
    static public void showChoosePhotoDialog(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose plant photo");

        builder.setItems(options, (dialog, item) -> {

            if (options[item].equals("Take Photo")) {
                //tryPickPhotoFromCamera();

            } else if (options[item].equals("Choose from Gallery")) {
                //tryPickPhotoFromGallery();

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
