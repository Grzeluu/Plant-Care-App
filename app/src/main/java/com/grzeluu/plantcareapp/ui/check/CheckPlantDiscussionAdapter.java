package com.grzeluu.plantcareapp.ui.check;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grzeluu.plantcareapp.databinding.ItemDiscussionBinding;
import com.grzeluu.plantcareapp.model.Post;

import java.util.List;

public class CheckPlantDiscussionAdapter extends RecyclerView.Adapter<CheckPlantDiscussionAdapter.ViewHolder> {

    private Context context;
    private List<Post> adviceList;

    public CheckPlantDiscussionAdapter(Context context, List<Post> adviceList) {
        this.context = context;
        this.adviceList = adviceList;
    }

    @NonNull
    @Override
    public CheckPlantDiscussionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckPlantDiscussionAdapter.ViewHolder(ItemDiscussionBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckPlantDiscussionAdapter.ViewHolder holder, int position) {
        Post post = adviceList.get(position);

        holder.binding.tvAuthor.setText(post.getAuthor());
        holder.binding.tvPost.setText(post.getText());
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
        private ItemDiscussionBinding binding;

        public ViewHolder(ItemDiscussionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}