package com.example.tp_final.controller.activitiesAndFragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp_final.R;
import com.example.tp_final.controller.adapters.PagerAdapter;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommandsFragment extends Fragment {


    public CommandsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_commands, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewPager viewPager = getView().findViewById(R.id.viewPagerCommandes);
        TabLayout tabLayout = getView().findViewById(R.id.tabCommandes);

        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());

        adapter.addFragment(new ActifFragment(), "Actif");
        adapter.addFragment(new HistoriqueFragment(), "Historique");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);
    }
}
