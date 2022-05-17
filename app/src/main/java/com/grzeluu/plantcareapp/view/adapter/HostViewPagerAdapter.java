package com.grzeluu.plantcareapp.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class HostViewPagerAdapter extends FragmentStateAdapter {
    ArrayList<Fragment> fragments;

    public HostViewPagerAdapter(ArrayList<Fragment> fragments, FragmentManager manager, Lifecycle lifecycle) {
        super(manager, lifecycle);
        this.fragments = fragments;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
