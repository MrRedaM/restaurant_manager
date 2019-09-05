package com.example.tp_final.controller.activitiesAndFragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp_final.R;
import com.example.tp_final.controller.adapters.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MenuFragment extends Fragment {

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewPager viewPager = getView().findViewById(R.id.viewPager);
        TabLayout tabLayout = getView().findViewById(R.id.tabLayout);

        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(new EntreeFragment(), "Entrées");
        adapter.addFragment(new PrincipalFragment(), "Principal");
        adapter.addFragment(new DessertFragment(), "Desserts");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}