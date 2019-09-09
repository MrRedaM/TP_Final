package com.example.tp_final.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_final.R;
import com.example.tp_final.controller.activitiesAndFragments.NewCommandActivity;
import com.example.tp_final.model.Plat;

import java.util.ArrayList;

public class PlatAdapter extends RecyclerView.Adapter<PlatAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Plat> plats;
    private QuantityCallBack mCallBack;

    public PlatAdapter(Context context, ArrayList<Plat> plats, QuantityCallBack callBack) {
        mContext = context;
        this.plats = plats;
        this.mCallBack = callBack;
    }

    public void setPlats(ArrayList<Plat> plats) {
        this.plats = plats;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plat_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.nom.setText(plats.get(position).getNom());
        String prix = plats.get(position).getPrix() + "DZD";
        holder.prix.setText(prix);
        holder.description.setText(plats.get(position).getDescription());

        if (mContext instanceof NewCommandActivity) {
            final Animation show = AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.text_open);
            final Animation hide = AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.text_close);
            holder.add.setVisibility(View.VISIBLE);
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.onClickAdd(plats.get(position));
                    int previousQant = Integer.parseInt(holder.quantity.getText().toString());
                    holder.quantity.setText(String.valueOf(previousQant + 1));
                    if (previousQant == 0) {
                        holder.reduce.startAnimation(show);
                        holder.quantity.startAnimation(show);
                        holder.reduce.setVisibility(View.VISIBLE);
                        holder.quantity.setVisibility(View.VISIBLE);
                    }
                }
            });
            holder.reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.onClickReduce(plats.get(position));
                    int previousQant = Integer.parseInt(holder.quantity.getText().toString());
                    if (previousQant != 0) {
                        holder.quantity.setText(String.valueOf(previousQant - 1));
                        if (previousQant == 1) {
                            holder.reduce.startAnimation(hide);
                            holder.quantity.startAnimation(hide);
                            holder.quantity.setVisibility(View.INVISIBLE);
                            holder.reduce.setVisibility(View.INVISIBLE);
                        }
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (plats != null)? plats.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nom;
        TextView prix;
        TextView description;
        ImageButton add;
        ImageButton reduce;
        TextView quantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nomTexteView);
            prix = itemView.findViewById(R.id.prixTexteView);
            description = itemView.findViewById(R.id.descriptionTexteView);
            add = itemView.findViewById(R.id.buttonAddQuantity);
            reduce = itemView.findViewById(R.id.buttonReduceQuantity);
            quantity = itemView.findViewById(R.id.textQuantityNewCommand);
        }
    }

    public interface QuantityCallBack {
        void onClickAdd(Plat plat);

        void onClickReduce(Plat plat);
    }

}
