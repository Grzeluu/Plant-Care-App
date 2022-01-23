package com.grzeluu.plantcareapp.ui.myPlants;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import com.grzeluu.plantcareapp.ui.check.CheckPlantActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyPlantsAdapter extends RecyclerView.Adapter<com.grzeluu.plantcareapp.ui.myPlants.MyPlantsAdapter.ViewHolder> {

    private Context context;
    private List<UserPlant> plantList;
    private SimpleDateFormat iso_8601_format;

    public MyPlantsAdapter(Context context, List<UserPlant> plantList) {
        this.context = context;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public com.grzeluu.plantcareapp.ui.myPlants.MyPlantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new com.grzeluu.plantcareapp.ui.myPlants.MyPlantsAdapter.ViewHolder (ItemMyPlantBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull com.grzeluu.plantcareapp.ui.myPlants.MyPlantsAdapter.ViewHolder holder, int position) {
        UserPlant plant = plantList.get(position);
        Glide
                .with(context)
                .load(plant.getImage())
                .into(holder.binding.ivPhoto);

        holder.binding.tvName.setText(plant.getName());
        holder.binding.ivCare.setOnClickListener(v-> goToCheckPlant(plant.getId()));

        iso_8601_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");

        holder.binding.ivCare.setOnClickListener(showPopup(plant, position));
        holder.binding.ivDelete.setOnClickListener(v -> deletePlant(plant));

        if (plant.getWateringFrequency() > 0) {
            try {
                setItemLayout(holder.binding.tvWater,
                        holder.binding.pbWater,
                        holder.binding.warning,
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
                        holder.binding.warning,
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
                        holder.binding.warning,
                        plant.getSprayingFrequency(),
                        iso_8601_format.parse(plant.getLastSpraying()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            holder.binding.rlSpraying.setVisibility(View.GONE);
        }

    }

    private void goToCheckPlant(String plantId) {
        Intent intent = new Intent(context, CheckPlantActivity.class);
        intent.putExtra("plantId", plantId);
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
        private ItemMyPlantBinding binding;

        public ViewHolder(ItemMyPlantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void setItemLayout(TextView text, ProgressBar progressBar, ImageView warning, long frequency, Date lastAction) {
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
        if (anything) {
            warning.setVisibility(View.VISIBLE);
        } else {
            warning.setVisibility(View.GONE);
        }
    }

    private int daysFromLastAction(Date lastDate) {
        Date currentDate = new Date();
        long diff = currentDate.getTime() - lastDate.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public View.OnClickListener showPopup(UserPlant plant, int position) {
        return v -> {
            Dialog careDialog = new Dialog(context);
            careDialog.setContentView(R.layout.popup_take_care);

            ImageView image = careDialog.findViewById(R.id.imageView);
            TextView name = careDialog.findViewById(R.id.nameTv);

            Button confirmButton = careDialog.findViewById(R.id.confirmButton);

            CheckBox watering = careDialog.findViewById(R.id.wateringCheckBox);
            CheckBox fertilizing = careDialog.findViewById(R.id.fertilizingCheckBox);
            CheckBox spraying = careDialog.findViewById(R.id.sprayingCheckBox);

            TextView wateringT = careDialog.findViewById(R.id.wateringTv);
            TextView fertilizingT = careDialog.findViewById(R.id.fertilizationTv);
            TextView sprayingT = careDialog.findViewById(R.id.sprayingTv);

            ImageView water = careDialog.findViewById(R.id.waterIcon);
            ImageView ferti = careDialog.findViewById(R.id.fertilizationIcon);
            ImageView spray = careDialog.findViewById(R.id.sprayingIcon);
            Glide
                    .with(context)
                    .load(plant.getImage())
                    .into(image);

            name.setText(plant.getName());

            try {
                if(daysFromLastAction(iso_8601_format.parse(plant.getLastWatering())) >= plant.getWateringFrequency())
                    water.setColorFilter(context.getResources().getColor(R.color.red));
                if(daysFromLastAction(iso_8601_format.parse(plant.getLastFertilizing())) >= plant.getFertilizingFrequency())
                    ferti.setColorFilter(context.getResources().getColor(R.color.red));
                if(daysFromLastAction(iso_8601_format.parse(plant.getLastSpraying())) >= plant.getSprayingFrequency())
                    spray.setColorFilter(context.getResources().getColor(R.color.red));
            } catch (ParseException e) {
                e.printStackTrace();
            }



            if (plant.getWateringFrequency() == 0) {
                watering.setVisibility(View.INVISIBLE);
                wateringT.setVisibility(View.INVISIBLE);
                water.setVisibility(View.INVISIBLE);
            }

            if (plant.getFertilizingFrequency() == 0) {
                ferti.setVisibility(View.INVISIBLE);
                fertilizing.setVisibility(View.INVISIBLE);
                fertilizingT.setVisibility(View.INVISIBLE);
            }

            if (plant.getSprayingFrequency() == 0) {
                spray.setVisibility(View.INVISIBLE);
                sprayingT.setVisibility(View.INVISIBLE);
                spraying.setVisibility(View.INVISIBLE);
            }


            confirmButton.setOnClickListener(v1 -> {
                Date currentDate = new Date();
                if (watering.isChecked()) {
                    plant.setLastWatering( iso_8601_format.format(new Date()));
                }

                if (fertilizing.isChecked()) {
                    plant.setLastFertilizing(iso_8601_format.format(new Date()));
                }

                if (spraying.isChecked()) {
                    plant.setLastSpraying(iso_8601_format.format(new Date()));
                }

                notifyDataSetChanged();
                careDialog.dismiss();

                String now = iso_8601_format.format(new Date());

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
                ref.child("UserPlants").child(plant.getId()).child("lastFertilizing").setValue(now);
                ref.child("UserPlants").child(plant.getId()).child("lastWatering").setValue(now);
                ref.child("UserPlants").child(plant.getId()).child("lastSpraying").setValue(now);
            });
            careDialog.show();
        };
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
