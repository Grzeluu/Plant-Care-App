package com.grzeluu.plantcareapp.view.adapter;

import static com.grzeluu.plantcareapp.utils.Constants.PLANT_INTENT_EXTRAS_KEY;
import static com.grzeluu.plantcareapp.utils.FirebaseConstants.FIREBASE_IMAGE_REFERENCE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.grzeluu.plantcareapp.databinding.ItemDiscoverPlantBinding;
import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.view.CheckPlantFragment;

import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {

    private Context context;
    private List<Plant> plantList;

    public DiscoverAdapter(Context context, List<Plant> plantList) {
        this.context = context;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public DiscoverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemDiscoverPlantBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverAdapter.ViewHolder holder, int position) {
        Plant plant = plantList.get(position);

        Glide
                .with(context)
                .load(FIREBASE_IMAGE_REFERENCE.child(plant.getId()))
                .into(holder.binding.ivPlantPhoto);

        holder.binding.tvCommonName.setText(plant.getCommonName());
        holder.binding.tvLatinName.setText(plant.getLatinName());
        if (!plant.isVerified()) holder.binding.ivVerified.setVisibility(View.GONE);

        holder.binding.ivMore.setOnClickListener(v -> goToCheckPlant(plant));
    }

    private void goToCheckPlant(Plant plant) {
        Intent intent = new Intent(context, CheckPlantFragment.class);
        intent.putExtra(PLANT_INTENT_EXTRAS_KEY, plant);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (plantList == null) {
            return 0;
        } else {
            return plantList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemDiscoverPlantBinding binding;

        public ViewHolder(ItemDiscoverPlantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}