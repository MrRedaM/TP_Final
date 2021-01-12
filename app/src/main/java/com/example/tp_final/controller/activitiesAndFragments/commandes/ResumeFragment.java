package com.example.tp_final.controller.activitiesAndFragments.commandes;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tp_final.R;
import com.example.tp_final.model.Commande;
import com.example.tp_final.controller.adapters.CommandesAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeFragment extends Fragment {

    private Commande newCommande;
    private TextView montant, modePayment, nbTable;
    private RecyclerView recylerCommandes;

    public ResumeFragment(Commande commande ) {
        this.newCommande = commande;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resume, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        montant = getView().findViewById(R.id.textMontant);
        modePayment = getView().findViewById(R.id.textPaymentResume);
        nbTable = getView().findViewById(R.id.textVTableResume);
        recylerCommandes = getView().findViewById(R.id.recyclerResume);

        montant.setText(newCommande.getMontant() + "DZD");
        String mode = null;
        switch (newCommande.getModePayment()) {
            case ESPECE:
                mode = "Espèce";
                break;
            case CARTE:
                mode = "Carte de crédit";
                break;
            case CHEQUE:
                mode = "Chèque";
                break;
        }
        modePayment.setText(mode);
        nbTable.setText(String.valueOf(newCommande.getNbTable()));

        recylerCommandes.setLayoutManager(new LinearLayoutManager(getContext()));
        recylerCommandes.setAdapter(new CommandesAdapter(getContext(), newCommande.getCommandes()));

    }
}
