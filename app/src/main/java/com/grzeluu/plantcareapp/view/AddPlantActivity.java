package com.grzeluu.plantcareapp.view;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.base.BaseActivity;
import com.grzeluu.plantcareapp.core.add.AddContract;
import com.grzeluu.plantcareapp.core.add.AddPresenter;
import com.grzeluu.plantcareapp.databinding.ActivityAddPlantBinding;
import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.model.UserPlant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPlantActivity extends BaseActivity implements AddContract.View {

    ActivityAddPlantBinding binding;
    AddContract.Presenter presenter;
    Plant plant;

    static final int PICK_IMAGE_CAMERA = 4321;
    static final int PICK_IMAGE_GALLERY = 1234;

    private static final int PERMISSION_CAMERA = 1111;
    private static final int PERMISSION_STORAGE = 2222;
    private static final int WRITE_EXTERNAL_STORAGE = 3333;


    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new AddPresenter(this);

        SimpleDateFormat ISO_8601_FORMAT =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        String now = ISO_8601_FORMAT.format(new Date());
        String timestamp = "" + (System.currentTimeMillis());

        binding.ivPhoto.setOnClickListener(v -> showChoosePhotoDialog());

        setWateringFrequency(10);
        setFertilizingFrequency(60);
        setSprayingFrequency(0);

        binding.sprayingSettings.ivPlus.setOnClickListener(v -> {
            int current = binding.sprayingSettings.sbFrequency.getProgress();
            binding.sprayingSettings.sbFrequency.setProgress(++current);
        });
        binding.fertilizingSettings.ivPlus.setOnClickListener(v -> {
            int current = binding.fertilizingSettings.sbFrequency.getProgress();
            binding.fertilizingSettings.sbFrequency.setProgress(++current);
        });
        binding.wateringSettings.ivPlus.setOnClickListener(v -> {
            int current = binding.wateringSettings.sbFrequency.getProgress();
            binding.wateringSettings.sbFrequency.setProgress(++current);
        });

        binding.sprayingSettings.ivMinus.setOnClickListener(v -> {
            int current = binding.sprayingSettings.sbFrequency.getProgress();
            binding.sprayingSettings.sbFrequency.setProgress(--current);
        });
        binding.fertilizingSettings.ivMinus.setOnClickListener(v -> {
            int current = binding.fertilizingSettings.sbFrequency.getProgress();
            binding.fertilizingSettings.sbFrequency.setProgress(--current);
        });
        binding.wateringSettings.ivMinus.setOnClickListener(v -> {
            int current = binding.wateringSettings.sbFrequency.getProgress();
            binding.wateringSettings.sbFrequency.setProgress(--current);
        });

        binding.toolbar.btAddPlant.setOnClickListener(v -> presenter.addPlant(
                new UserPlant(
                        timestamp,
                        binding.etName.getText().toString(),
                        toDays(binding.wateringSettings.sbFrequency.getProgress()),
                        toDays(binding.fertilizingSettings.sbFrequency.getProgress()),
                        toDays(binding.sprayingSettings.sbFrequency.getProgress()),
                        now,
                        now,
                        now,
                        photoURI.toString()
                )
        ));
    }

    public void setWateringFrequency(int wateringFrequency) {
        SeekBar.OnSeekBarChangeListener listener = createProgressBarChangeListener(binding.wateringSettings.tvFrequency);
        binding.wateringSettings.sbFrequency.setOnSeekBarChangeListener(listener);

        binding.wateringSettings.sbFrequency.setProgress(toProgress(wateringFrequency));
    }

    public void setFertilizingFrequency(int fertilizingFrequency) {
        SeekBar.OnSeekBarChangeListener listener = createProgressBarChangeListener(binding.fertilizingSettings.tvFrequency);
        binding.fertilizingSettings.sbFrequency.setOnSeekBarChangeListener(listener);

        binding.fertilizingSettings.sbFrequency.setProgress(toProgress(fertilizingFrequency));
    }

    public void setSprayingFrequency(int sprayingFrequency) {
        SeekBar.OnSeekBarChangeListener listener = createProgressBarChangeListener(binding.sprayingSettings.tvFrequency);
        binding.sprayingSettings.sbFrequency.setOnSeekBarChangeListener(listener);

        binding.sprayingSettings.sbFrequency.setProgress(toProgress(sprayingFrequency));
    }

    public void setNameError(String error) {
        binding.etName.setError(error);
    }

    public void plantAdded(String message) {
        showMessage(message);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setPlant(Plant newPlant) {
        plant = newPlant;
    }

    public void setPlantPhoto(String uri) {
        Glide
                .with(this)
                .load(uri)
                .into(binding.ivPhoto);
    }

    public void setPlantPhoto(Uri uri) {
        Glide
                .with(this)
                .load(uri)
                .into(binding.ivPhoto);
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

    private int toProgress(int frequency) {

        if (frequency >= 0 && frequency <= 30) {
            return frequency;
        } else if (frequency > 30 && frequency <= 60) {
            return (30 + frequency / 5 - 6);
        } else {
            return ((frequency / 30) + 35);
        }
    }

    public SeekBar.OnSeekBarChangeListener createProgressBarChangeListener(TextView tv) {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    tv.setText(getString(R.string.never));
                } else if (progress == 1) {
                    tv.setText(getString(R.string.one_day));
                } else if (progress > 1 && progress <= 30) {
                    String daysString = progress + " ";
                    tv.setText(daysString + getString(R.string.days));
                } else if (progress > 30 && progress <= 35) {
                    String daysString = (30 + (progress - 30) * 5) + " ";
                    tv.setText(daysString + getString(R.string.days));
                } else {
                    String monthsString = (progress - 34) + " ";
                    tv.setText(monthsString + getString(R.string.months));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };
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

    public void tryPickPhotoFromGallery() {
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
                if (grantResults.length > 00 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhotoFromGallery();
                } else {
                    showMessage("Permissions denied");
                }
            }
            case WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 00 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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