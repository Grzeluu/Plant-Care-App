package com.grzeluu.plantcareapp.ui_temporary.add;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.databinding.ActivityAddPlantBinding;
import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.base.BaseActivity;

public class AddPlantActivity extends BaseActivity implements AddPlantMvpView {

    ActivityAddPlantBinding binding;
    AddPlantMvpPresenter presenter;
    private String plantId;
    Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new AddPlantPresenter(this);

        plantId = getIntent().getStringExtra("plantId");
        presenter.getPlant(plantId);

        binding.btAddPlant.setOnClickListener(v-> presenter.onSuggestNewPlantClick(
                binding.etName.getText().toString(),
                plant.getImage(),
                plant.getCommonName(),
                plant.getLatinName(),
                plant.getType(),
                plant.getWateringFrequency(),
                plant.getFertilizingFrequency(),
                plant.getWateringFrequency()
        ));
    }

    @Override
    public void setPlantCommonName(String commonName) {
        binding.tvCommonName.setText(commonName);
    }

    @Override
    public void setPlantLatinName(String latinName) {
        binding.tvLatinName.setText(latinName);
    }

    @Override
    public void setWateringFrequency(long wateringFrequency) {
        SeekBar.OnSeekBarChangeListener listener = createProgressBarChangeListener(binding.wateringSettings.tvFrequency);
        binding.wateringSettings.sbFrequency.setOnSeekBarChangeListener(listener);

        binding.wateringSettings.sbFrequency.setProgress(toProgress(wateringFrequency));
    }

    @Override
    public void setFertilizingFrequency(long fertilizingFrequency) {
        SeekBar.OnSeekBarChangeListener listener = createProgressBarChangeListener(binding.fertilizingSettings.tvFrequency);
        binding.fertilizingSettings.sbFrequency.setOnSeekBarChangeListener(listener);

        binding.fertilizingSettings.sbFrequency.setProgress(toProgress(fertilizingFrequency));
    }

    @Override
    public void setSprayingFrequency(long sprayingFrequency) {
        SeekBar.OnSeekBarChangeListener listener = createProgressBarChangeListener(binding.sprayingSettings.tvFrequency);
        binding.sprayingSettings.sbFrequency.setOnSeekBarChangeListener(listener);

        binding.sprayingSettings.sbFrequency.setProgress(toProgress(sprayingFrequency));
    }

    @Override
    public void setNameError(String error) {
        binding.etName.setError(error);
    }

    @Override
    public void setPlant(Plant newPlant) {
        plant = newPlant;
    }

    @Override
    public void setPlantPhoto(String uri) {
        Glide
                .with(this)
                .load(uri)
                .into(binding.ivPhoto);
    }

    @Override
    public void setPlantType(String type) {
        binding.tvCategory.setText(type);
    }

    private int toProgress(long frequency) {

        if (frequency >= 0 && frequency <= 30) {
            return (int) frequency;
        } else if (frequency > 30 && frequency <= 60) {
            return (int) (30 + frequency / 5 - 6);
        } else {
            return (int) ((frequency / 30) + 35);
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

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };
    }
}