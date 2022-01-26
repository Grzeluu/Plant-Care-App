package com.grzeluu.plantcareapp.view;

import static com.grzeluu.plantcareapp.utils.Constants.PERMISSION_CAMERA;
import static com.grzeluu.plantcareapp.utils.Constants.PERMISSION_STORAGE;
import static com.grzeluu.plantcareapp.utils.Constants.PICK_IMAGE_CAMERA;
import static com.grzeluu.plantcareapp.utils.Constants.PICK_IMAGE_GALLERY;
import static com.grzeluu.plantcareapp.utils.Constants.WRITE_EXTERNAL_STORAGE;
import static com.grzeluu.plantcareapp.utils.DaysUtils.progressToDays;
import static com.grzeluu.plantcareapp.utils.SeekBarUtils.initSeekBarGroupWithText;
import static com.grzeluu.plantcareapp.utils.TimeUtils.getCurrentDate;
import static com.grzeluu.plantcareapp.utils.TimeUtils.getTimestamp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.grzeluu.plantcareapp.base.BaseActivity;
import com.grzeluu.plantcareapp.core.add.AddContract;
import com.grzeluu.plantcareapp.core.add.AddPresenter;
import com.grzeluu.plantcareapp.databinding.ActivityAddPlantBinding;
import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.model.UserPlant;

public class AddPlantActivity extends BaseActivity implements AddContract.View {

    ActivityAddPlantBinding binding;
    AddContract.Presenter presenter;

    Plant plant;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new AddPresenter(this);

        init();
    }

    private void init() {
        initSeekBars();
        initButtons();
    }

    private void initSeekBars() {
        initSeekBarGroupWithText(
                this,
                binding.wateringSettings.sbFrequency,
                binding.wateringSettings.tvFrequency,
                binding.wateringSettings.ivPlus,
                binding.wateringSettings.ivMinus,
                0
        );
        initSeekBarGroupWithText(
                this,
                binding.fertilizingSettings.sbFrequency,
                binding.fertilizingSettings.tvFrequency,
                binding.fertilizingSettings.ivPlus,
                binding.fertilizingSettings.ivMinus,
                0
        );
        initSeekBarGroupWithText(
                this,
                binding.sprayingSettings.sbFrequency,
                binding.sprayingSettings.tvFrequency,
                binding.sprayingSettings.ivPlus,
                binding.sprayingSettings.ivMinus,
                0
        );
    }

    private void initButtons() {
        binding.ivPhoto.setOnClickListener(v -> showChoosePhotoDialog());


        binding.toolbar.btAddPlant.setOnClickListener(v -> {
            String photoURIString = null;
            if(photoURI != null)
                photoURIString = photoURI.toString();

            presenter.addPlant(
                    new UserPlant(
                            getTimestamp(),
                            binding.etName.getText().toString(),
                            progressToDays(binding.wateringSettings.sbFrequency.getProgress()),
                            progressToDays(binding.fertilizingSettings.sbFrequency.getProgress()),
                            progressToDays(binding.sprayingSettings.sbFrequency.getProgress()),
                            getCurrentDate(),
                            getCurrentDate(),
                            getCurrentDate(),
                            photoURIString
                    )
            );
        });
    }

    public void setPlant(Plant newPlant) {
        plant = newPlant;
    }

    public void setNameError(String error) {
        binding.etName.setError(error);
    }

    public void plantAdded(String message) {
        showMessage(message);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setPlantPhoto(String uri) {
        Glide
                .with(this)
                .load(uri)
                .into(binding.ivPhoto);
    }

    private void showChoosePhotoDialog() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose plant photo");

        builder.setItems(options, (dialog, item) -> {

            if (options[item].equals("Take Photo")) {
                tryPickPhotoFromCamera();

            } else if (options[item].equals("Choose from Gallery")) {
                tryPickPhotoFromGallery();

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void tryPickPhotoFromCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED) {
            String[] permission = {Manifest.permission.CAMERA};
            requestPermissions(permission, PERMISSION_CAMERA);
        } else {
            pickPhotoFromCamera();
        }
    }

    private void tryPickPhotoFromGallery() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, PERMISSION_STORAGE);
        } else {
            pickPhotoFromGallery();
        }
    }

    private void pickPhotoFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "new picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "from camera");
        photoURI = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(cameraIntent, PICK_IMAGE_CAMERA);
    }

    private void pickPhotoFromGallery() {
        Intent storageIntent = new Intent(Intent.ACTION_PICK);
        storageIntent.setType("image/*");
        startActivityForResult(storageIntent, PICK_IMAGE_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhotoFromCamera();
                } else {
                    showMessage("Permissions denied");
                }
            }
            case PERMISSION_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhotoFromGallery();
                } else {
                    showMessage("Permissions denied");
                }
            }
            case WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhotoFromCamera();
                } else {
                    showMessage("Permissions denied");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE_GALLERY:
                    assert data != null;
                    photoURI = data.getData();
                    binding.ivPhoto.setImageURI(data.getData());
                    break;
                case PICK_IMAGE_CAMERA:
                    binding.ivPhoto.setImageURI(photoURI);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}