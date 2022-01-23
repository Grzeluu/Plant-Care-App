package com.grzeluu.plantcareapp.ui_temporary.discover;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grzeluu.plantcareapp.databinding.FragmentDiscoverBinding;
import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends BaseFragment implements DiscoverMvpView {

    private FragmentDiscoverBinding binding;
    private DiscoverMvpPresenter presenter;
    private RecyclerView.LayoutManager plantsLayoutManager;
    private RecyclerView.Adapter discoverAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentDiscoverBinding.inflate(getLayoutInflater());
        presenter =  new DiscoverPresenter(this);
        plantsLayoutManager = new LinearLayoutManager(getContext());
        binding.rvPlants.setLayoutManager(plantsLayoutManager);

        discoverAdapter = new DiscoverAdapter(getContext(), new ArrayList<Plant>());
        binding.rvPlants.setAdapter(discoverAdapter);

        presenter.refreshPlantList();
        binding.btSearch.setOnClickListener( v-> presenter.refreshPlantList(binding.etSearch.getText().toString()));
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.refreshPlantList(binding.etSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return binding.getRoot();
    }


    @Override
    public void updatePlants(List<Plant> plantList) {
        if (plantList != null){
            discoverAdapter = new DiscoverAdapter(getContext(), plantList);
            binding.rvPlants.setAdapter(discoverAdapter);
            discoverAdapter.notifyDataSetChanged();
        }
    }
}