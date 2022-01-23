package com.grzeluu.plantcareapp.ui_temporary.check;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grzeluu.plantcareapp.databinding.ItemAdviceBinding;
import com.grzeluu.plantcareapp.model.Advice;
import com.grzeluu.plantcareapp.ui_temporary.check.advice.AdviceDialog;

import java.util.List;

public class CheckPlantAdviceAdapter extends RecyclerView.Adapter<CheckPlantAdviceAdapter.ViewHolder> {

    private Context context;
    private List<Advice> adviceList;
    private String plantId;

    public CheckPlantAdviceAdapter(Context context, List<Advice> adviceList, String plantId) {
        this.context = context;
        this.adviceList = adviceList;
        this.plantId = plantId;
    }

    @NonNull
    @Override
    public CheckPlantAdviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckPlantAdviceAdapter.ViewHolder(ItemAdviceBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckPlantAdviceAdapter.ViewHolder holder, int position) {
        Advice advice = adviceList.get(position);

        holder.binding.tvAnswer.setText(advice.getAnswer());
        holder.binding.tvQuestion.setText(advice.getQuestion());

        if (!advice.isIsVerified()) holder.binding.ivVerified.setVisibility(View.GONE);

        if(advice.getAnswer().equals("Answer this question")) {
            holder.binding.tvAnswer.setOnClickListener(v -> openAdviceDialog(position));
        }
    }

    private void openAdviceDialog(int position) {
        AdviceDialog dialog = new AdviceDialog(context, plantId, true, adviceList.get(position).getId(), adviceList.get(position).getQuestion());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getItemCount() {
        if (adviceList == null) {
            return 0;
        } else {
            return adviceList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemAdviceBinding binding;

        public ViewHolder(ItemAdviceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}