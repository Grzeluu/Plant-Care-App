package com.grzeluu.plantcareapp.view;

import static com.grzeluu.plantcareapp.utils.Constants.PLANT_INTENT_EXTRAS_KEY;
import static com.grzeluu.plantcareapp.utils.DaysUtils.getProgressBarFill;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
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
    protected void onStart() {
        super.onStart();

    }

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
        setPlantPhoto(plant.getImage());

        binding.tvCommonName.setText(plant.getCommonName());
        binding.tvLatinName.setText(plant.getLatinName());
        binding.tvCategory.setText(plant.getType());

        binding.tvDescription.setText(plant.getDescription());

        initWateringFrequency(plant.getWateringFrequency());
        initFertilizingFrequency(plant.getFertilizingFrequency());
        initSprayingFrequency(plant.getSprayingFrequency());

        binding.btAddPlant.setOnClickListener(v -> openAddActivity());

        initAdapter();
    }

    private void initAdapter() {
        advicesLayoutManager = new LinearLayoutManager(this);
        binding.rvAdvices.setLayoutManager(advicesLayoutManager);
        checkPlantAdapter = new AdviceAdapter(this, plant.getAdvicesList());
        binding.rvAdvices.setAdapter(checkPlantAdapter);
    }

    private void openAddActivity() {
        Intent intent = new Intent(this, AddPlantFragment.class);
        intent.putExtra("Plant", plant);
        startActivity(intent);
    }


    public void initWateringFrequency(long wateringFrequency) {
        binding.tvWateringDays.setText(wateringFrequency + " Days");
        binding.pbWater.setProgress((int) getProgressBarFill(wateringFrequency));
    }

    public void initFertilizingFrequency(long fertilizingFrequency) {
        binding.tvFertilizingDays.setText(fertilizingFrequency + " Days");
        binding.pbFertilizer.setProgress((int) getProgressBarFill(fertilizingFrequency));
    }

    public void initSprayingFrequency(long sprayingFrequency) {
        binding.tvSprayingDays.setText(sprayingFrequency + " Days");
        binding.pbSpraying.setProgress((int) getProgressBarFill(sprayingFrequency));
    }

    public void setPlantPhoto(String uri) {
        Glide
                .with(getApplicationContext())
                .load(uri)
                .into(binding.ivPhoto);
    }
}