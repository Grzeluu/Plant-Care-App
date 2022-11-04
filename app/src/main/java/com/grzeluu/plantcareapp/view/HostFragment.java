package com.grzeluu.plantcareapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.base.BaseFragment;
import com.grzeluu.plantcareapp.core.main.MainContract;
import com.grzeluu.plantcareapp.core.main.MainPresenter;
import com.grzeluu.plantcareapp.databinding.FragmentHostBinding;
import com.grzeluu.plantcareapp.model.User;
import com.grzeluu.plantcareapp.view.adapter.HostViewPagerAdapter;

import java.util.ArrayList;

public class HostFragment extends BaseFragment implements MainContract.View {

    private MainPresenter presenter;
    private FragmentHostBinding binding;

    private HostViewPagerAdapter viewPagerAdapter;

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

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyPlantsFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new AddPlantFragment());

        viewPagerAdapter = new HostViewPagerAdapter(fragments ,getChildFragmentManager(), getLifecycle() );

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: R.id.menu
                }
            }
        });

        initViewPager();
    }

    private void initViewPager() {

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