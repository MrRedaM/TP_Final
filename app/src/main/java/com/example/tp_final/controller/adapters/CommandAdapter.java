package com.example.tp_final.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_final.R;
import com.example.tp_final.model.Commande;
import com.example.tp_final.model.CommandesAdapter;
import com.example.tp_final.model.Plat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CommandAdapter extends RecyclerView.Adapter<CommandAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Commande> commandes;

    public CommandAdapter(Context context, ArrayList<Commande> commandes) {
        mContext = context;
        this.commandes = commandes;
        //test
        Plat p1 = new Plat("Poulet", 250, Plat.Categorie.PRINCIPAL, "juste poulet");
        Plat p2 = new Plat("Frites", 150, Plat.Categorie.PRINCIPAL, "juste des frites");
        HashMap<Plat, Integer> testCommande = new HashMap<>();
        testCommande.put(p1, 1);
        testCommande.put(p2, 2);
        Commande commande = new Commande(2, Calendar.getInstance(), Commande.ModePayment.CARTE, false, testCommande);
        commandes.add(commande);
    }

    public void setCommandes(ArrayList<Commande> commandes) {
        this.commandes = commandes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comand_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.code.setText("№" + commandes.get(position).getCode());
        holder.table.setText(String.valueOf(commandes.get(position).getNbTable()));
        String mounth = String.valueOf(commandes.get(position).getDate().get(Calendar.MONTH));
        String day = String.valueOf(commandes.get(position).getDate().get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(commandes.get(position).getDate().get(Calendar.HOUR_OF_DAY));
        String minutes = String.valueOf(commandes.get(position).getDate().get(Calendar.MINUTE));
        String date = day + "/" + mounth + "  " + hour + ":" + minutes;
        holder.date.setText(date);
        holder.recycler.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recycler.setAdapter(new CommandesAdapter(mContext, commandes.get(position).getCommandes()));
    }

    @Override
    public int getItemCount() {
        return (commandes != null)? commandes.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView code;
        TextView table;
        TextView date;
        RecyclerView recycler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.textCode);
            table = itemView.findViewById(R.id.textTable);
            date = itemView.findViewById(R.id.textDate);
            recycler = itemView.findViewById(R.id.recyclerCommands);
        }
    }
}
