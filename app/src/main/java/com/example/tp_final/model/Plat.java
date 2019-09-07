package com.example.tp_final.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Plat implements Serializable {

    @SerializedName("ref")
    private int ref;

    @SerializedName("nbPlats")
    private static int nbPlats; //pour donner un code au plat lors de la création

    @SerializedName("nom")
    private String nom;

    @SerializedName("prix")
    private float prix;

    @SerializedName("description")
    private String description;

    public enum Categorie {
        ENTREE,
        PRINCIPAL,
        DESSERT,
    }

    @SerializedName("categorie")
    private Categorie categorie;

    //Constructeurs

    public Plat(String nom, float prix, Categorie type, String description) {
        nbPlats++;
        this.ref = nbPlats;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.categorie = type;
    }

    //Getters et setters

    public int getRef() {
        return ref;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public int hashCode() {
        return this.ref;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Plat) {
            return ((Plat) obj).ref == this.ref;
        }
        return false;
    }
}
