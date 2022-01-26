package com.grzeluu.plantcareapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grzeluu.plantcareapp.databinding.ItemAdviceBinding;
import com.grzeluu.plantcareapp.model.Advice;

import java.util.List;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.ViewHolder> {

    private Context context;
    private List<Advice> adviceList;

    public AdviceAdapter(Context context, List<Advice> adviceList) {
        this.context = context;
        this.adviceList = adviceList;
    }

    @NonNull
    @Override
    public AdviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdviceAdapter.ViewHolder(ItemAdviceBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdviceAdapter.ViewHolder holder, int position) {
        Advice advice = adviceList.get(position);

        holder.binding.tvAnswer.setText(advice.getAnswer());
        holder.binding.tvQuestion.setText(advice.getQuestion());

        if (!advice.isIsVerified()) holder.binding.ivVerified.setVisibility(View.GONE);
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