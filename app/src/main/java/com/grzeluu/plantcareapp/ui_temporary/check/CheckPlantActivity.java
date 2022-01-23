package com.grzeluu.plantcareapp.ui_temporary.check;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.grzeluu.plantcareapp.databinding.ActivityCheckPlantBinding;
import com.grzeluu.plantcareapp.model.Advice;
import com.grzeluu.plantcareapp.model.Post;
import com.grzeluu.plantcareapp.ui_temporary.add.AddPlantActivity;
import com.grzeluu.plantcareapp.base.BaseActivity;
import com.grzeluu.plantcareapp.ui_temporary.check.advice.AdviceDialog;

import java.util.ArrayList;
import java.util.List;

public class CheckPlantActivity extends BaseActivity implements CheckPlantMvpView {

    private ActivityCheckPlantBinding binding;

    private CheckPlantMvpPresenter presenter;

    private String plantId;

    private LinearLayoutManager discussionLayoutManager;
    private LinearLayoutManager advicesLayoutManager;

    private CheckPlantAdviceAdapter checkPlantAdapter;
    private CheckPlantDiscussionAdapter discussionAdapter;

    @Override
    protected void onStart() {
        super.onStart();

        presenter.refreshAdvicesList(getIntent().getStringExtra("plantId"));
        presenter.refreshDiscussionList(getIntent().getStringExtra("plantId"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        plantId = getIntent().getStringExtra("plantId");
        presenter = new CheckPlantPresenter(this);
        presenter.getPlant(plantId);

        advicesLayoutManager = new LinearLayoutManager(this);
        binding.rvAdvices.setLayoutManager(advicesLayoutManager);

        discussionLayoutManager = new LinearLayoutManager(this);
        binding.rvDiscussion.setLayoutManager(discussionLayoutManager);

        discussionAdapter = new CheckPlantDiscussionAdapter(this, new ArrayList<Post>());
        binding.rvAdvices.setAdapter(discussionAdapter);

        checkPlantAdapter = new CheckPlantAdviceAdapter(this, new ArrayList<Advice>(), plantId);
        binding.rvAdvices.setAdapter(checkPlantAdapter);

        presenter.refreshAdvicesList(plantId);
        presenter.refreshDiscussionList(plantId);

        binding.btAskQuestion.setOnClickListener(v -> openAskQuestionDialog());
        binding.btAddPlant.setOnClickListener(v -> openAddActivity());
        binding.btAddPost.setOnClickListener(v -> presenter.addPost(
                plantId,
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                binding.etPost.getText().toString()));
    }

    private void openAddActivity() {
        Intent intent = new Intent(this, AddPlantActivity.class);
        intent.putExtra("plantId", plantId);
        startActivity(intent);
    }

    @Override
    public void setPlantName(String commonName) {
        binding.tvCommonName.setText(commonName);
    }

    @Override
    public void setPlantLatinName(String latinName) {
        binding.tvLatinName.setText(latinName);
    }

    @Override
    public void setPlantDescription(String description) {
        binding.tvDescription.setText(description);
    }

    @Override
    public void setWateringFrequency(long wateringFrequency) {
        binding.tvWateringDays.setText(wateringFrequency + " Days");
        binding.pbWater.setProgress((int) getProgressBarFill(wateringFrequency));
    }

    @Override
    public void setFertilizingFrequency(long fertilizingFrequency) {
        binding.tvFertilizngDays.setText(fertilizingFrequency + " Days");
        binding.pbFertilizer.setProgress((int) getProgressBarFill(fertilizingFrequency));
    }

    @Override
    public void setSprayingFrequency(long sprayingFrequency) {
        binding.tvSprayingDays.setText(sprayingFrequency + " Days");
        binding.pbSpraying.setProgress((int) getProgressBarFill(sprayingFrequency));
    }

    @Override
    public void setPlantPhoto(String uri) {
        Glide
                .with(getApplicationContext())
                .load(uri)
                .into(binding.ivPhoto);
    }

    @Override
    public void setPlantType(String type) {
        binding.tvCategory.setText(type);
    }

    @Override
    public void openAskQuestionDialog() {
        AdviceDialog dialog = new AdviceDialog(this, getIntent().getStringExtra("plantId"));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void updateAdvices(List adviceList) {
        if (adviceList != null) {
            checkPlantAdapter = new CheckPlantAdviceAdapter(this, adviceList, plantId);
            binding.rvAdvices.setAdapter(checkPlantAdapter);
            checkPlantAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setPostError(String message) {
        binding.etPost.setError(message);
    }

    @Override
    public void updateDiscussion(List discussionList) {
        if (discussionList != null) {
            discussionAdapter = new CheckPlantDiscussionAdapter(this, discussionList);
            binding.rvDiscussion.setAdapter(discussionAdapter);
            discussionAdapter.notifyDataSetChanged();
            binding.etPost.setText("");
        }
    }

    private long getProgressBarFill(long days) {
        if (days <= 60) {
            return 100 - (days);
        } else
            return (long) (40 -  (days - 60) / 7.5);
    }
}