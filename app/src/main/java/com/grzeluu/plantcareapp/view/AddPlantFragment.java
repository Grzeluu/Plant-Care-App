package com.grzeluu.plantcareapp.view;

import static com.grzeluu.plantcareapp.utils.Constants.PERMISSION_CAMERA;
import static com.grzeluu.plantcareapp.utils.Constants.PERMISSION_STORAGE;
import static com.grzeluu.plantcareapp.utils.Constants.PICK_IMAGE_CAMERA;
import static com.grzeluu.plantcareapp.utils.Constants.PICK_IMAGE_GALLERY;
import static com.grzeluu.plantcareapp.utils.Constants.PLANT_INTENT_EXTRAS_KEY;
import static com.grzeluu.plantcareapp.utils.Constants.WRITE_EXTERNAL_STORAGE;
import static com.grzeluu.plantcareapp.utils.ProgressUtils.daysToProgress;
import static com.grzeluu.plantcareapp.utils.ProgressUtils.progressToDays;
import static com.grzeluu.plantcareapp.utils.SeekBarUtils.initSeekBarGroupWithText;
import static com.grzeluu.plantcareapp.utils.TimeUtils.getCurrentDate;
import static com.grzeluu.plantcareapp.utils.TimeUtils.getTimestamp;
import static com.grzeluu.plantcareapp.utils.notification.NotificationUtils.scheduleNotificationForPlant;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.base.BaseFragment;
import com.grzeluu.plantcareapp.core.add.AddContract;
import com.grzeluu.plantcareapp.core.add.AddPresenter;
import com.grzeluu.plantcareapp.databinding.FragmentAddPlantBinding;
import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.model.UserPlant;

public class AddPlantFragment extends BaseFragment implements AddContract.View {

    FragmentAddPlantBinding binding;
    AddContract.Presenter presenter;

    private Uri photoURI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddPlantBinding.inflate(getLayoutInflater());
        presenter = new AddPresenter(this);

        init();

        return binding.getRoot();
    }

    private void init() {
        initSeekBars();
        initButtons();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Plant plant = (Plant) bundle.getSerializable(PLANT_INTENT_EXTRAS_KEY);
            initPlantData(plant);
        }
    }

    private void initPlantData(Plant plant) {
        binding.etName.setText(plant.getCommonName());

        binding.fertilizingSettings.sbFrequency.setProgress(daysToProgress(plant.getFertilizingFrequency()));
        binding.wateringSettings.sbFrequency.setProgress(daysToProgress(plant.getWateringFrequency()));
        binding.sprayingSettings.sbFrequency.setProgress(daysToProgress(plant.getSprayingFrequency()));
    }

    private void initSeekBars() {
        initSeekBarGroupWithText(
                getContext(),
                binding.wateringSettings.sbFrequency,
                binding.wateringSettings.tvFrequency,
                binding.wateringSettings.ivPlus,
                binding.wateringSettings.ivMinus,
                10
        );
        initSeekBarGroupWithText(
                getContext(),
                binding.fertilizingSettings.sbFrequency,
                binding.fertilizingSettings.tvFrequency,
                binding.fertilizingSettings.ivPlus,
                binding.fertilizingSettings.ivMinus,
                30
        );
        initSeekBarGroupWithText(
                getContext(),
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
                            "" + getTimestamp(),
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

    public void setNameError(int error) {
        binding.etName.setError(getString(error));
    }

    public void plantAdded(int message, UserPlant plant) {
        scheduleNotificationForPlant(plant);
        showMessage(message);
    }

    public void setPlantPhoto(String uri) {
        Glide
                .with(this)
                .load(uri)
                .into(binding.ivPhoto);
    }

    private void showChoosePhotoDialog() {
        final CharSequence[] options = getResources().getStringArray(R.array.photo_options);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.choose_photo);

        builder.setItems(options, (dialog, item) -> {

            if (options[item].equals(getString(R.string.take_photo))) {
                tryPickPhotoFromCamera();

            } else if (options[item].equals(getString(R.string.gallery))) {
                tryPickPhotoFromGallery();

            } else if (options[item].equals(getString(R.string.cancel))) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void tryPickPhotoFromCamera() {
        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED) {
            String[] permission = {Manifest.permission.CAMERA};
            requestPermissions(permission, PERMISSION_CAMERA);
        } else {
            pickPhotoFromCamera();
        }
    }

    private void tryPickPhotoFromGallery() {
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
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
        photoURI = getActivity().getApplication().getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

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
                    showMessage(R.string.permissions_denied);
                }
            }
            case PERMISSION_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhotoFromGallery();
                } else {
                    showMessage(R.string.permissions_denied);
                }
            }
            case WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhotoFromCamera();
                } else {
                    showMessage(R.string.permissions_denied);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
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