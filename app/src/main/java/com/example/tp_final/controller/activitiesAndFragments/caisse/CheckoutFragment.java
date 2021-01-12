package com.example.tp_final.controller.activitiesAndFragments.caisse;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.tp_final.R;
import com.example.tp_final.model.Commande;
import com.example.tp_final.model.Plat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckoutFragment extends Fragment {

    private CalendarView mCalendarView;
    private TextView mRecetteText, mPlatText;
    private List<Commande> mCommandes;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCalendarView = getView().findViewById(R.id.caisseCalendarView);
        mRecetteText = getView().findViewById(R.id.recetteTextView);
        mPlatText = getView().findViewById(R.id.bestPlatTextView);

        loadAllCommands();

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                float recette = getRecetteByDay(year, month, dayOfMonth);
                Plat plat = getBestPlatByDay(year, month, dayOfMonth);
                String recetteStr = recette + " DZD";
                String platStr = (plat == null) ? "Aucune commande" : plat.getNom();
                mRecetteText.setText(recetteStr);
                mPlatText.setText(platStr);
            }
        });

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        float recette = getRecetteByDay(year, month, dayOfMonth);
        Plat plat = getBestPlatByDay(year, month, dayOfMonth);
        String recetteStr = recette + " DZD";
        String platStr = (plat == null) ? "Aucune commande" : plat.getNom();

        mCalendarView.setDate(calendar.getTime().getTime());

        mRecetteText.setText(recetteStr);
        mPlatText.setText(platStr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    public List<Commande> getCommandsByDay(int year, int month, int day) {
        List<Commande> result = new ArrayList<>();
        for (Commande commande : mCommandes) {
            if (commande.getDate().get(Calendar.YEAR) == year
                    && commande.getDate().get(Calendar.MONTH) == month
                    && commande.getDate().get(Calendar.DAY_OF_MONTH) == day) {
                result.add(commande);
            }
        }
        return result;
    }

    public float getRecetteByDay(int year, int month, int day) {
        float result = 0;
        for (Commande commande : getCommandsByDay(year, month, day)) {
            result += commande.getMontant();
        }
        return result;
    }

    public Plat getBestPlatByDay(int year, int month, int day) {
        HashMap<Plat, Integer> result = new HashMap<>();
        for (Commande commande : getCommandsByDay(year, month, day)) {
            Iterator it = commande.getCommandes().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                if (result.containsKey(pair.getKey())) {
                    int val = result.get(pair.getKey());
                    val += (int) pair.getValue();
                    result.put((Plat) pair.getKey(), val);
                } else {
                    result.put((Plat) pair.getKey(), (int) pair.getValue());
                }
                it.remove();
            }
        }
        Iterator it = result.entrySet().iterator();
        Plat bestPlat = null;
        int maxQnt = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (bestPlat == null) {
                bestPlat = (Plat) pair.getKey();
                maxQnt = (int) pair.getValue();
            } else if (((int) pair.getValue()) > maxQnt) {
                bestPlat = (Plat) pair.getKey();
                maxQnt = (int) pair.getValue();
            }
            it.remove();
        }
        return bestPlat;
    }

    public void loadAllCommands() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getContext().getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("cloturees", "");
        Type type = new TypeToken<List<Commande>>() {
        }.getType();
        mCommandes = gson.fromJson(json, type);
        if (mCommandes == null) mCommandes = new ArrayList<>();
    }

}
