package com.example.tp_final.controller.activitiesAndFragments.menu;


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
import com.example.tp_final.controller.adapters.PlatAdapter;
import com.example.tp_final.model.Plat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PlatAdapter mAdapter;

    private PlatAdapter.QuantityCallBack mCallBack;

    public PrincipalFragment(PlatAdapter.QuantityCallBack callBack) {
        mCallBack = callBack;
    }

    public PrincipalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_principal, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = getView().findViewById(R.id.principalRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new PlatAdapter(getContext(), new ArrayList<Plat>(), mCallBack);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        mAdapter.setPlats(loadPrincipal());
        super.onResume();
    }

    //public void saveData() {
    //    ObjectOutputStream out = null;
    //    try {
    //        out = new ObjectOutputStream(new FileOutputStream("platsP.ser", true));
    //        for(Plat plat : plats) {
    //            out.writeObject(plat);
    //        }
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    } finally {
    //        try {
    //            out.close();
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //}

     //public void loadData() {
     //    ObjectInputStream in = null;
     //    try {
     //        in = new ObjectInputStream(new FileInputStream("principal.ser"));
     //        boolean b = false;
     //        while (!b) {
     //            try {
     //                Plat plat = (Plat)in.readObject();
     //                if (!plats.contains(plat) && plat.getCategorie() == Plat.Categorie.PRINCIPAL) plats.add(plat);
     //            } catch (ClassNotFoundException e) {
     //                e.printStackTrace();
     //            } catch (EOFException e) {
     //                b = true;
     //            }
     //        }
     //    } catch (IOException e) {
     //        e.printStackTrace();
     //    } finally {
     //        if (in != null) {
     //            try {
     //                in.close();
     //            } catch (IOException e) {
     //                e.printStackTrace();
     //            }
     //        }
     //    }
     //}

     public ArrayList<Plat> loadPrincipal(){
         SharedPreferences appSharedPrefs = PreferenceManager
                 .getDefaultSharedPreferences(getContext());
         Gson gson = new Gson();
         String json = appSharedPrefs.getString("principal", "");
         Type type = new TypeToken<ArrayList<Plat>>(){}.getType();
         ArrayList<Plat> plats = gson.fromJson(json, type);
         if (plats == null) {
             return new ArrayList<>();
         }
         return plats;
     }
}
