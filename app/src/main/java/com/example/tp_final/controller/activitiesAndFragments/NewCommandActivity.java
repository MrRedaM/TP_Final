package com.example.tp_final.controller.activitiesAndFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tp_final.R;
import com.example.tp_final.controller.adapters.PlatAdapter;
import com.example.tp_final.model.Commande;
import com.example.tp_final.model.Plat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class NewCommandActivity extends AppCompatActivity implements PlatAdapter.QuantityCallBack,
        PaymentFragment.PaymentCallBack {

    private TextView title;
    private ImageButton buttonNext;
    private ImageButton buttonCancel;
    private int mTreeStep = 1;

    //Command details
    private Commande newCommande;
    private HashMap<String, Integer> commandes;
    private Commande.ModePayment modePayment;
    private int nbTable;
    private Calendar calendar;
    private float montant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_command);

        title = findViewById(R.id.textTitle);
        buttonNext = findViewById(R.id.buttonNext);
        buttonCancel = findViewById(R.id.buttonCancelNewCommand);

        commandes = new HashMap<String, Integer>();

        title.setText("Choix des plats");
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.frameNewCommand, new MenuFragment(this)).commit();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTreeStep++;
                switch (mTreeStep) {
                    case 1:
                        title.setText("Choix des plats");
                        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                .replace(R.id.frameNewCommand, new MenuFragment()).commit();
                        break;
                    case 2:
                        title.setText("Mode de payment");
                        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                .replace(R.id.frameNewCommand, new PaymentFragment(NewCommandActivity.this)).commit();
                        break;
                    case 3:
                        newCommande = new Commande(nbTable, Calendar.getInstance(),
                                modePayment, false, commandes, montant);
                        buttonNext.setImageResource(R.drawable.ic_check_black_24dp);
                        title.setText("Resumé");
                        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                .replace(R.id.frameNewCommand, new ResumeFragment(newCommande)).commit();
                        break;
                    case 4:
                        //save command
                        SharedPreferences appSharedPrefs = PreferenceManager
                                .getDefaultSharedPreferences(getApplicationContext());

                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        builder.enableComplexMapKeySerialization();
                        String json = appSharedPrefs.getString("com", "");

                        Type type = new TypeToken<List<Commande>>() {
                        }.getType();
                        ArrayList<Commande> commands = gson.fromJson(json, type);
                        if (commands == null) commands = new ArrayList<>();
                        commands.add(newCommande);
                        String jsonNew = gson.toJson(commands);
                        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                        prefsEditor.putString("com", jsonNew);
                        prefsEditor.apply();

                        Intent returnIntent = new Intent();
                        setResult(RESULT_OK, returnIntent);
                        finish();

                        break;
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        mTreeStep--;
        if (mTreeStep == 0) {
            finish();
        } else {
            //getSupportFragmentManager().popBackStack();
        }
        //super.onBackPressed();
    }

    @Override
    public void onClickAdd(String plat, float prix) {
        if (commandes.containsKey(plat)) {
            int quantity = commandes.get(plat);
            commandes.put(plat, quantity + 1);
        } else {
            commandes.put(plat, 1);
        }
        montant += prix;
    }

    @Override
    public void onClickReduce(String plat, float prix) {
        if (commandes.containsKey(plat)) {
            int quantity = commandes.get(plat);
            commandes.put(plat, quantity - 1);
            if (quantity == 1) {
                commandes.remove(plat);
            }
        }
        montant -= prix;
    }

    @Override
    public void selectPayment(Commande.ModePayment modePayment) {
        this.modePayment = modePayment;
    }

    @Override
    public void selectTable(int nbTable) {
        this.nbTable = nbTable;
    }
}
