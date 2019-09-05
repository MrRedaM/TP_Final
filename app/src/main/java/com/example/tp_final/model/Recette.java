package com.example.tp_final.model;

import java.util.ArrayList;
import java.util.Date;

public class Recette {

    private Date date;

    private ArrayList<Commande> commandes;

    public Recette(Date date, ArrayList<Commande> commandes) {
        this.date = date;
        this.commandes = commandes;
    }
}
