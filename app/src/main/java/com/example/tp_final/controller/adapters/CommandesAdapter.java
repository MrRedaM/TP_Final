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

import java.util.HashMap;
import java.util.Map;

public class CommandesAdapter extends RecyclerView.Adapter<CommandesAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<Plat, Integer> commandes;
    private CommandesAdapter mAdapter;

    public CommandesAdapter(Context context, HashMap<Plat, Integer> commandes) {
        mContext = context;
        this.commandes = commandes;
    }

    public void setCommandes(HashMap<Plat, Integer> commandes) {
        this.commandes = commandes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_commandes_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i = 0;
        for (Map.Entry<Plat, Integer> command : commandes.entrySet()) {
            if (i == position) {
                holder.nom.setText(command.getKey().getNom());
                holder.quantite.setText(String.valueOf(command.getValue()));
                break;
            } else {
                i++;
            }
        }
    }

    @Override
    public int getItemCount() {
        return (commandes != null)? commandes.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView quantite;
        TextView nom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            quantite = itemView.findViewById(R.id.textQantite);
            nom = itemView.findViewById(R.id.textNomCommand);
        }
    }
}
