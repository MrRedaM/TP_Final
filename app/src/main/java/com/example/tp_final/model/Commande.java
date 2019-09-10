package com.example.tp_final.model;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Commande {

    @SerializedName("code")
    private int code;

    @SerializedName("nbCom")
    private static int nbCommandes;

    @SerializedName("nbTab")
    private int nbTable;

    @SerializedName("date")
    private Calendar date;

    @SerializedName("montant")
    private float montant;

    public enum ModePayment{
        ESPECE,
        CHEQUE,
        CARTE,
    }

    @SerializedName("mode")
    private ModePayment modePayment;

    @SerializedName("cloture")
    private boolean cloture;

    @SerializedName("commandes")
    private HashMap<Plat, Integer> commandes;

    //Constructeurs

    public Commande(int code, int nbTable, Calendar date, ModePayment modePayment, boolean cloture, HashMap<Plat, Integer> commandes, float montant) {
        this.code = code;
        this.nbTable = (nbTable == 0)? 1 : nbTable;
        this.date = date;
        this.modePayment = modePayment;
        this.cloture = cloture;
        this.commandes = commandes;
        for (Map.Entry<Plat, Integer> entry : commandes.entrySet()) {
            this.montant += entry.getKey().getPrix() * entry.getValue();
        }
    }

    //Getters et Setters

    public int getCode() {
        return code;
    }

    public static int getNbCommandes() {
        return nbCommandes;
    }

    public static void setNbCommandes(int nbCommandes) {
        Commande.nbCommandes = nbCommandes;
    }

    public int getNbTable() {
        return nbTable;
    }

    public void setNbTable(int nbTable) {
        this.nbTable = nbTable;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public float getMontant() {
        this.montant = 0;
        for (Map.Entry<Plat, Integer> entry : commandes.entrySet()) {
            this.montant += entry.getKey().getPrix() * entry.getValue();
        }
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public ModePayment getModePayment() {
        return modePayment;
    }

    public void setModePayment(ModePayment modePayment) {
        this.modePayment = modePayment;
    }

    public boolean isCloture() {
        return cloture;
    }

    public void setCloture(boolean cloture) {
        this.cloture = cloture;
    }

    public HashMap<Plat, Integer> getCommandes() {
        return commandes;
    }

    public void setCommandes(HashMap<Plat, Integer> commandes) {
        this.commandes = commandes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commande commande = (Commande) o;
        return code == commande.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
