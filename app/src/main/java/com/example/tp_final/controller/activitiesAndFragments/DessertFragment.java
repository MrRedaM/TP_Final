package com.example.tp_final.controller.activitiesAndFragments;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tp_final.R;
import com.example.tp_final.controller.adapters.PlatAdapter;
import com.example.tp_final.model.Plat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DessertFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PlatAdapter mAdapter;

    private PlatAdapter.QuantityCallBack mCallBack;

    public DessertFragment() {
        // Required empty public constructor
    }

    public DessertFragment(PlatAdapter.QuantityCallBack callBack) {
        mCallBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dessert, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = getView().findViewById(R.id.dessertRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new PlatAdapter(getContext(), new ArrayList<Plat>(), mCallBack);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        mAdapter.setPlats(loadDessert());
        super.onResume();
    }

    public ArrayList<Plat> loadDessert(){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("dessert", "");
        Type type = new TypeToken<ArrayList<Plat>>(){}.getType();
        ArrayList<Plat> plats = gson.fromJson(json, type);
        if (plats == null) {
            return new ArrayList<>();
        }
        return plats;
    }
}
