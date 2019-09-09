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

import com.example.tp_final.R;
import com.example.tp_final.controller.activitiesAndFragments.dialogs.ClotureDialog;
import com.example.tp_final.controller.adapters.CommandAdapter;
import com.example.tp_final.model.Commande;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActifFragment extends Fragment implements CommandAdapter.CallBackCommande,
        ClotureDialog.DialogCallBack {

    private RecyclerView mRecyclerView;
    private CommandAdapter mAdapter;

    public ActifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actif, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = getView().findViewById(R.id.recyclerViewCommandes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CommandAdapter(getContext(), loadCommandes(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        mAdapter.setCommandes(loadCommandes());
        super.onResume();
    }

    public ArrayList<Commande> loadCommandes() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getContext().getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("testFix", "");
        Type type = new TypeToken<List<Commande>>() {
        }.getType();
        ArrayList<Commande> commandes = gson.fromJson(json, type);
        if (commandes == null) commandes = new ArrayList<>();
        return commandes;
    }

    public void removeFromActifs(Commande commande) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getContext().getApplicationContext());

        GsonBuilder builder = new GsonBuilder();
        builder.enableComplexMapKeySerialization();
        builder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
        Gson gson = builder.create();
        String json = appSharedPrefs.getString("testFix", "");

        Type type = new TypeToken<List<Commande>>() {
        }.getType();
        ArrayList<Commande> commands = gson.fromJson(json, type);
        if (commands == null) {
            return;
        }
        commands.remove(commande);
        json = gson.toJson(commands);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("testFix", json);
        prefsEditor.apply();

        //save into historique file
        json = appSharedPrefs.getString("cloturees", "");
        commands = gson.fromJson(json, type);
        if (commands == null) commands = new ArrayList<>();
        commands.add(commande);
        json = gson.toJson(commands);
        prefsEditor.putString("cloturees", json);
        prefsEditor.apply();
    }

    @Override
    public void cloturer(Commande commande) {


        ClotureDialog.newInstance(this, commande).show(getFragmentManager(), "confirm");
    }

    @Override
    public void confirmCloture(Commande commande) {
        mAdapter.remove(commande);
        removeFromActifs(commande);
    }
}
