package com.grzeluu.plantcareapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.base.BaseFragment;
import com.grzeluu.plantcareapp.core.myplants.MyPlantsContract;
import com.grzeluu.plantcareapp.core.myplants.MyPlantsPresenter;
import com.grzeluu.plantcareapp.databinding.FragmentMyPlantsBinding;
import com.grzeluu.plantcareapp.model.UserPlant;
import com.grzeluu.plantcareapp.view.adapter.MyPlantsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPlantsFragment extends BaseFragment implements MyPlantsContract.View {

    FragmentMyPlantsBinding binding;
    com.grzeluu.plantcareapp.core.myplants.MyPlantsPresenter presenter;
    private LinearLayoutManager plantsLayoutManager;
    private MyPlantsAdapter myPlantsAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_plants, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rv_my_plants);

        binding = FragmentMyPlantsBinding.inflate(getLayoutInflater());
        presenter =  new MyPlantsPresenter(this);

        init();

        presenter.getMyPlantsList();
        super.onViewCreated(binding.getRoot(), savedInstanceState);
    }

    private void init() {
        plantsLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(plantsLayoutManager);
        myPlantsAdapter = new MyPlantsAdapter(getContext(), new ArrayList<UserPlant>());

        if (myPlantsAdapter.getItemCount() == 0)
            binding.tvHint.setVisibility(View.VISIBLE);

        recyclerView.setAdapter(myPlantsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlantList();
    }

    private void checkPlantList() {
        if (myPlantsAdapter.getItemCount() > 0)
            binding.tvHint.setVisibility(View.INVISIBLE);
        else
            binding.tvHint.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateMyPlants(List<UserPlant> plantList) {
        if (plantList != null){
            myPlantsAdapter = new MyPlantsAdapter(getContext(), plantList);
            recyclerView.setAdapter(myPlantsAdapter);
            myPlantsAdapter.notifyDataSetChanged();
        }
    }
}