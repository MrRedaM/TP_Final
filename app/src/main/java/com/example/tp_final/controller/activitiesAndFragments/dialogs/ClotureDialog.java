package com.example.tp_final.controller.activitiesAndFragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tp_final.model.Commande;

public class ClotureDialog extends DialogFragment {

    private DialogCallBack mCallBack;
    private Commande mCommande;

    public ClotureDialog(DialogCallBack callBack, Commande commande) {
        mCallBack = callBack;
        mCommande = commande;
    }

    public static ClotureDialog newInstance(DialogCallBack callBack, Commande commande) {

        Bundle args = new Bundle();

        ClotureDialog fragment = new ClotureDialog(callBack, commande);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Confirmer la clôture de la commande № " + mCommande.getCode());
        builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCallBack.confirmCloture(mCommande);
            }
        });
        builder.setNegativeButton("Annuler", null);

        return builder.create();
    }

    public interface DialogCallBack{
        void confirmCloture(Commande commande);
    }
}
