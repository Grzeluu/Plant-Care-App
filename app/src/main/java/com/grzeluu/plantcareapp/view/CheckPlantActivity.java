package com.grzeluu.plantcareapp.view;

import static com.grzeluu.plantcareapp.utils.Constants.PLANT_INTENT_EXTRAS_KEY;
import static com.grzeluu.plantcareapp.utils.FirebaseConstants.FIREBASE_IMAGE_REFERENCE;
import static com.grzeluu.plantcareapp.utils.ProgressUtils.getProgressBarFill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.base.BaseActivity;
import com.grzeluu.plantcareapp.core.check.CheckContract;
import com.grzeluu.plantcareapp.core.check.CheckPresenter;
import com.grzeluu.plantcareapp.databinding.ActivityCheckPlantBinding;
import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.view.adapter.AdviceAdapter;

public class CheckPlantActivity extends BaseActivity implements CheckContract.View {

    private ActivityCheckPlantBinding binding;
    private CheckContract.Presenter presenter;
    private Plant plant;
    private LinearLayoutManager advicesLayoutManager;
    private AdviceAdapter checkPlantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCheckPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        plant = (Plant) getIntent().getSerializableExtra(PLANT_INTENT_EXTRAS_KEY);
        presenter = new CheckPresenter(this);

        init();

        binding.btAddPlant.setOnClickListener(v -> openAddActivity());
    }

    private void init() {
        setPlantPhoto();

        if (plant != null) {
            binding.tvCommonName.setText(plant.getCommonName());
            binding.tvLatinName.setText(plant.getLatinName());
            binding.tvCategory.setText(plant.getType());

            binding.tvDescription.setText(plant.getDescription());

            initWateringFrequency(plant.getWateringFrequency());
            initFertilizingFrequency(plant.getFertilizingFrequency());
            initSprayingFrequency(plant.getSprayingFrequency());

            binding.btAddPlant.setOnClickListener(v -> openAddActivity());

            if (plant.getAdvicesList() == null || plant.getAdvicesList().size() == 0) {
                binding.tvAdvices.setVisibility(View.GONE);
                binding.llAdvices.setVisibility(View.GONE);
            }

            initAdapter();
        }
    }

    private void initAdapter() {
        advicesLayoutManager = new LinearLayoutManager(this);
        binding.rvAdvices.setLayoutManager(advicesLayoutManager);
        checkPlantAdapter = new AdviceAdapter(this, plant.getAdvicesList());
        binding.rvAdvices.setAdapter(checkPlantAdapter);
    }

    private void openAddActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(PLANT_INTENT_EXTRAS_KEY, plant);
        startActivity(intent);
    }


    public void initWateringFrequency(long wateringFrequency) {
        if (wateringFrequency != 0) {
            binding.tvWateringDays.setText(getString(R.string.days, wateringFrequency));
            binding.pbWater.setProgress((int) getProgressBarFill(wateringFrequency));
        } else {
            binding.tvWateringDays.setText(getString(R.string.never));
            binding.pbWater.setProgress(0);
        }
    }

    public void initFertilizingFrequency(long fertilizingFrequency) {
        if (fertilizingFrequency != 0) {
            binding.tvFertilizingDays.setText(getString(R.string.days, fertilizingFrequency));
            binding.pbFertilizer.setProgress((int) getProgressBarFill(fertilizingFrequency));
        } else {
            binding.tvFertilizingDays.setText(getString(R.string.never));
            binding.pbFertilizer.setProgress(0);
        }
    }

    public void initSprayingFrequency(long sprayingFrequency) {
        if (sprayingFrequency != 0) {
            binding.tvSprayingDays.setText(getString(R.string.days, sprayingFrequency));
            binding.pbSpraying.setProgress((int) getProgressBarFill(sprayingFrequency));
        } else {
            binding.tvSprayingDays.setText(getString(R.string.never));
            binding.pbSpraying.setProgress(0);
        }
    }

    public void setPlantPhoto() {
        if (plant.getImage() != null) {
            Glide
                    .with(this)
                    .load(FIREBASE_IMAGE_REFERENCE.child(plant.getId()))
                    .into(binding.ivPhoto);
        }
    }
}