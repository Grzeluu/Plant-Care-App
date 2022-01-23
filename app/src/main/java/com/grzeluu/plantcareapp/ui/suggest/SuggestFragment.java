package com.grzeluu.plantcareapp.ui.suggest;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.databinding.FragmentSuggestBinding;
import com.grzeluu.plantcareapp.ui.base.BaseFragment;
import com.grzeluu.plantcareapp.ui.main.MainActivity;

import static android.app.Activity.RESULT_OK;


public class SuggestFragment extends BaseFragment implements SuggestMvpView {

    static final int PICK_IMAGE_CAMERA = 4321;
    static final int PICK_IMAGE_GALLERY = 1234;

    private static final int PERMISSION_CAMERA = 1111;
    private static final int PERMISSION_STORAGE = 2222;
    private static final int WRITE_EXTERNAL_STORAGE = 3333;


    private Uri photoURI;
    private FragmentSuggestBinding binding;
    private SuggestMvpPresenter suggestPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUp(View view) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentSuggestBinding.inflate(getLayoutInflater());
        suggestPresenter = new SuggestPresenter(this);
        binding.ivPhoto.setOnClickListener(v -> showChoosePhotoDialog());
        binding.tvCategory.setOnClickListener(v -> showCategoryPickDialog());

        binding.wateringSettings.sbFrequency.setOnSeekBarChangeListener(
                createProgressBarChangeListener(binding.wateringSettings.tvFrequency));
        binding.fertilizingSettings.sbFrequency.setOnSeekBarChangeListener(
                createProgressBarChangeListener(binding.fertilizingSettings.tvFrequency));
        binding.sprayingSettings.sbFrequency.setOnSeekBarChangeListener(
                createProgressBarChangeListener(binding.sprayingSettings.tvFrequency));

        binding.btConfirm.setOnClickListener(v -> {
            String commonName = binding.etName.getText().toString();
            String latinName = binding.etLatinName.getText().toString();
            String type = binding.tvCategory.getText().toString();
            String description = binding.etDescription.getText().toString();
            int frequencyOfWatering = binding.wateringSettings.sbFrequency.getProgress();
            int frequencyOfFertilization = binding.fertilizingSettings.sbFrequency.getProgress();
            int frequencyOfSpraying = binding.sprayingSettings.sbFrequency.getProgress();

            suggestPresenter.onSuggestNewPlantClick(photoURI, commonName, latinName, type, description,
                    frequencyOfWatering, frequencyOfFertilization, frequencyOfSpraying);
        });

        return binding.getRoot();
    }

    @Override
    public void setNameError(String error) {
        binding.etName.setError(error);
    }

    @Override
    public void setCategoryError(String error) {
        binding.tvCategory.setError(error);
    }

    @Override
    public void showCategoryPickDialog() {
        String[] categories = getResources().getStringArray(R.array.categories);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Plant Type")
                .setItems(categories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.tvCategory.setError(null);
                        binding.tvCategory.setText(categories[which]);
                    }
                })
                .show();
    }

    @Override
    public void showChoosePhotoDialog() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA};
                getActivity().requestPermissions(permission, PERMISSION_CAMERA);
            } else {
                pickPhotoFromCamera();
            }
        } else {
            pickPhotoFromCamera();
        }
    }

    @Override
    public void tryPickPhotoFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                getActivity().requestPermissions(permission, PERMISSION_STORAGE);
            } else {
                pickPhotoFromGallery();
            }
        } else {
            pickPhotoFromGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhotoFromCamera();
                } else {
                    ((MainActivity) getActivity()).showMessage("Permissions denied");
                }
            }
            case PERMISSION_STORAGE: {
                if (grantResults.length > 00 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhotoFromGallery();
                } else {
                    ((MainActivity) getActivity()).showMessage("Permissions denied");
                }
            }
            case WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 00 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhotoFromCamera();
                } else {
                    ((MainActivity) getActivity()).showMessage("Permissions denied");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void pickPhotoFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "new picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "from camera");
        photoURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(cameraIntent, PICK_IMAGE_CAMERA);
    }

    @Override
    public void pickPhotoFromGallery() {
        Intent storageIntent = new Intent(Intent.ACTION_PICK);
        storageIntent.setType("image/*");
        startActivityForResult(storageIntent, PICK_IMAGE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE_GALLERY:
                    photoURI=data.getData();
                    binding.ivPhoto.setImageURI(data.getData());
                    break;
                case PICK_IMAGE_CAMERA:
                    binding.ivPhoto.setImageURI(photoURI);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public SeekBar.OnSeekBarChangeListener createProgressBarChangeListener(TextView tv) {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (progress == 0) {
                        tv.setText(getString(R.string.never));
                    } else if (progress == 1) {
                        tv.setText(getString(R.string.one_day));
                    } else if (progress > 1 && progress <= 30) {
                        String daysString = String.valueOf(progress) + " ";
                        tv.setText(daysString + getString(R.string.days));
                    } else if (progress > 30 && progress <= 35) {
                        String daysString = String.valueOf(30 + (progress - 30) * 5) + " ";
                        tv.setText(daysString + getString(R.string.days));
                    } else {
                        String monthsString = String.valueOf(progress - 34) + " ";
                        tv.setText(monthsString + getString(R.string.months));
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };
    }
}