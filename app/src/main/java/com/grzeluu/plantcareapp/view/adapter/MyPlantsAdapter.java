package com.grzeluu.plantcareapp.view.adapter;

import static com.grzeluu.plantcareapp.utils.Constants.iso_8601_format;
import static com.grzeluu.plantcareapp.utils.DaysUtils.daysFromLastAction;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.databinding.ItemMyPlantBinding;
import com.grzeluu.plantcareapp.model.UserPlant;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class MyPlantsAdapter extends RecyclerView.Adapter<MyPlantsAdapter.ViewHolder> {

    private Context context;
    private List<UserPlant> plantList;

    public MyPlantsAdapter(Context context, List<UserPlant> plantList) {
        this.context = context;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public MyPlantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemMyPlantBinding.inflate(LayoutInflater.from(context),
                        parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyPlantsAdapter.ViewHolder holder, int position) {

        UserPlant plant = plantList.get(position);

        if (plant.getImage() != null) {
            Glide
                    .with(context)
                    .load(plant.getImage())
                    .into(holder.binding.ivPhoto);
        }
        holder.binding.tvName.setText(plant.getName());

        if (plant.isCareNeeded()) holder.binding.warning.setVisibility(View.VISIBLE);
        else holder.binding.warning.setVisibility(View.INVISIBLE);


        if (plant.getWateringFrequency() > 0) {
            try {
                setItemLayout(holder.binding.tvWater,
                        holder.binding.pbWater,
                        plant.getWateringFrequency(),
                        iso_8601_format.parse(plant.getLastWatering()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            holder.binding.rlWater.setVisibility(View.GONE);
        }

        if (plant.getFertilizingFrequency() > 0) {
            try {
                setItemLayout(holder.binding.tvFertilization,
                        holder.binding.pbFertilizer,
                        plant.getFertilizingFrequency(),
                        iso_8601_format.parse(plant.getLastFertilizing()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            holder.binding.rlFertilization.setVisibility(View.GONE);
        }

        if (plant.getSprayingFrequency() > 0) {
            try {
                setItemLayout(holder.binding.tvSpraying,
                        holder.binding.pbSpraying,
                        plant.getSprayingFrequency(),
                        iso_8601_format.parse(plant.getLastSpraying()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            holder.binding.rlSpraying.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (plantList == null) {
            return 0;
        } else {
            return plantList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMyPlantBinding binding;

        public ViewHolder(ItemMyPlantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void setItemLayout(TextView text, ProgressBar progressBar, long frequency, Date lastAction) {
        long daysFromLastAction = daysFromLastAction(lastAction);
        long fill = 100 - daysFromLastAction * 100 / frequency;
        long daysLeft = frequency - daysFromLastAction;

        boolean anything = false;
        progressBar.setProgress((int) fill);
        if (daysLeft > 1) {
            text.setText("In " + daysLeft + " days");
        }
        if (daysLeft == 1) {
        }
        if (daysLeft < 1) {
            text.setText("Today");
            anything = true;
        }
    }

    private void deletePlant(UserPlant plant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete plant");
        builder.setMessage("Are you sure to delete this plant?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
                ref.child("UserPlants").child(plant.getId()).removeValue();
                notifyDataSetChanged();
            }
        });

        builder.setNeutralButton(R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
