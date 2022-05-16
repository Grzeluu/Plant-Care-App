package com.grzeluu.plantcareapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grzeluu.plantcareapp.base.BaseFragment;
import com.grzeluu.plantcareapp.core.main.MainContract;
import com.grzeluu.plantcareapp.core.main.MainPresenter;
import com.grzeluu.plantcareapp.databinding.FragmentHostBinding;
import com.grzeluu.plantcareapp.model.User;

public class HostFragment extends BaseFragment implements MainContract.View {

    private MainPresenter presenter;
    private FragmentHostBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHostBinding.inflate(getLayoutInflater());
        presenter = new MainPresenter(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init() {
        presenter.checkIfUserIsLoggedIn();
    }

    @Override
    public void requireLogin() {

    }

    @Override
    public void setUser(User username) {

    }

    @Override
    public void setEmail(String email) {

    }
}