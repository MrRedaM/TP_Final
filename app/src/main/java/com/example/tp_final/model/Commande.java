package com.example.tp_final.model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Commande {

    private int code;

    private static int nbCommandes;

    private int nbTable;

    private Calendar date;

    private float montant;

    public enum ModePayment{
        ESPECE,
        CHEQUE,
        CARTE,
    }

    private ModePayment modePayment;

    private boolean cloture;

    private HashMap<Plat, Integer> commandes;

    //Constructeurs

    public Commande(int nbTable, Calendar date, ModePayment modePayment, boolean cloture, HashMap<Plat, Integer> commandes) {
        nbCommandes++;
        this.code = nbCommandes;
        this.nbTable = nbTable;
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
}
