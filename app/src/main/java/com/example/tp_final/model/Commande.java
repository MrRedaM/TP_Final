package com.example.tp_final.model;

import java.util.Date;
import java.util.HashMap;

public class Commande {

    private int code;

    private static int nbCommandes;

    private int nbTable;

    private Date date;

    private float montant;

    public enum ModePayment{
        ESPECE,
        CHEQUE,
        CARTE,
    }

    private ModePayment modePayment;

    private boolean cloture;

    private HashMap<Integer, Integer> commandes;

    //Constructeurs

    public Commande(int nbTable, Date date, float montant, ModePayment modePayment, boolean cloture, HashMap<Integer, Integer> commandes) {
        nbCommandes++;
        this.code = nbCommandes;
        this.nbTable = nbTable;
        this.date = date;
        this.montant = montant;
        this.modePayment = modePayment;
        this.cloture = cloture;
        this.commandes = commandes;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public HashMap<Integer, Integer> getCommandes() {
        return commandes;
    }

    public void setCommandes(HashMap<Integer, Integer> commandes) {
        this.commandes = commandes;
    }
}
