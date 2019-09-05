package com.example.tp_final.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_final.R;
import com.example.tp_final.model.Plat;

import java.util.ArrayList;

public class PlatAdapter extends RecyclerView.Adapter<PlatAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Plat> plats;

    public PlatAdapter(Context context, ArrayList<Plat> plats) {
        mContext = context;
        this.plats = plats;
        //temp
        Plat p1 = new Plat("Poulet frites", 250, Plat.Categorie.PRINCIPAL, "poulet + frites + sauce blanche + champinion");
        Plat p2 = new Plat("Couscous", 1000, Plat.Categorie.PRINCIPAL, "couscous + sauce rouge + legumes + viande de beuf");
        plats.add(p1);
        plats.add(p2);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plat_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nom.setText(plats.get(position).getNom());
        String prix = plats.get(position).getPrix() + "DZD";
        holder.prix.setText(prix);
        holder.description.setText(plats.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return (plats != null)? plats.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nom;
        TextView prix;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nomTexteView);
            prix = itemView.findViewById(R.id.prixTexteView);
            description = itemView.findViewById(R.id.descriptionTexteView);
        }
    }

}
