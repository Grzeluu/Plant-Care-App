package com.grzeluu.plantcareapp.view;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.grzeluu.plantcareapp.base.BaseActivity;
import com.grzeluu.plantcareapp.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
