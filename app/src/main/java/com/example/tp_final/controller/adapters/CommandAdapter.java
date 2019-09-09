package com.example.tp_final.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_final.R;
import com.example.tp_final.model.Commande;

import java.util.ArrayList;
import java.util.Calendar;

public class CommandAdapter extends RecyclerView.Adapter<CommandAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Commande> commandes;
    private CallBackCommande callBack;

    public CommandAdapter(Context context, ArrayList<Commande> commandes, CallBackCommande callBack) {
        mContext = context;
        this.commandes = commandes;
        this.callBack = callBack;
    }

    public void setCommandes(ArrayList<Commande> commandes) {
        this.commandes = commandes;
        notifyDataSetChanged();
    }

    public ArrayList<Commande> getCommandes() {
        return commandes;
    }

    public void remove(Commande commande) {
        int position = commandes.indexOf(commande);
        notifyItemRemoved(position);
        commandes.get(position).setCloture(true);
        commandes.remove(commande);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comand_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.code.setText("№ " + commandes.get(position).getCode());
        holder.table.setText(String.valueOf(commandes.get(position).getNbTable()));
        holder.montant.setText(String.valueOf(commandes.get(position).getMontant()) + "DZD");
        String mode = null;
        switch (commandes.get(position).getModePayment()) {
            case CHEQUE:
                mode = "Payment par chèque";
                break;
            case CARTE:
                mode = "Payment par carte";
                break;
            case ESPECE:
                mode = "Payment en espèce";
        }
        holder.mode.setText(mode);
        String mounth = String.valueOf(commandes.get(position).getDate().get(Calendar.MONTH));
        if (Integer.parseInt(mounth) < 10) mounth = "0" + mounth;
        String day = String.valueOf(commandes.get(position).getDate().get(Calendar.DAY_OF_MONTH));
        if (Integer.parseInt(day) < 10) day = "0" + day;
        String hour = String.valueOf(commandes.get(position).getDate().get(Calendar.HOUR_OF_DAY));
        if (Integer.parseInt(hour) < 10) hour = "0" + hour;
        String minutes = String.valueOf(commandes.get(position).getDate().get(Calendar.MINUTE));
        if (Integer.parseInt(minutes) < 10) minutes = "0" + minutes;
        String date = day + "/" + mounth + "  " + hour + ":" + minutes;
        holder.date.setText(date);
        holder.recycler.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recycler.setAdapter(new CommandesAdapter(mContext, commandes.get(position).getCommandes()));
        if (!commandes.get(position).isCloture()) {
            holder.cloturer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.cloturer(commandes.get(position));
                }
            });
        } else {
            holder.cloturer.setEnabled(false);
            holder.cloturer.setVisibility(View.INVISIBLE);
        }
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
        TextView montant;
        TextView mode;
        Button cloturer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.textCode);
            table = itemView.findViewById(R.id.textTable);
            date = itemView.findViewById(R.id.textDate);
            recycler = itemView.findViewById(R.id.recyclerCommands);
            montant = itemView.findViewById(R.id.textMontantItem);
            mode = itemView.findViewById(R.id.textPaymentItem);
            cloturer = itemView.findViewById(R.id.buttonCloturer);
        }
    }

    public interface CallBackCommande {
        void cloturer(Commande commande);
    }
}
