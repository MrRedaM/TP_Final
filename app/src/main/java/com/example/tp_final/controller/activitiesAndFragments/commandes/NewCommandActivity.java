package com.example.tp_final.controller.activitiesAndFragments.commandes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp_final.R;
import com.example.tp_final.controller.activitiesAndFragments.menu.MenuFragment;
import com.example.tp_final.controller.adapters.PlatAdapter;
import com.example.tp_final.model.Commande;
import com.example.tp_final.model.Plat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
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
    private boolean next = false;

    SharedPreferences appSharedPrefs;

    //Command details
    private Commande newCommande;
    private HashMap<Plat, Integer> commandes;
    private Commande.ModePayment modePayment;
    private int nbTable;
    private Calendar calendar;
    private float montant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_command);

        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        title = findViewById(R.id.textTitle);
        buttonNext = findViewById(R.id.buttonNext);
        buttonCancel = findViewById(R.id.buttonCancelNewCommand);

        commandes = new HashMap<>();

        title.setText("Choix des plats");
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.frameNewCommand, new MenuFragment(this)).commit();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTreeStep++;
                switch (mTreeStep) {
                    case 2:
                        if (next) {
                            title.setText("Mode de payment");
                            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                    .replace(R.id.frameNewCommand, new PaymentFragment(NewCommandActivity.this)).commit();
                        } else {
                            Toast.makeText(NewCommandActivity.this, "Veuillez sélectioner au moins un plat",
                                    Toast.LENGTH_SHORT).show();
                            mTreeStep--;
                        }
                        break;
                    case 3:

                        int code = appSharedPrefs.getInt("nb_commandes", 1);
                        newCommande = new Commande(code, nbTable, Calendar.getInstance(),
                                modePayment, false, commandes, montant);
                        //Commande.setNbCommandes(newCommande.getCode());
                        buttonNext.setImageResource(R.drawable.ic_check_black_24dp);
                        title.setText("Resumé");
                        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                .replace(R.id.frameNewCommand, new ResumeFragment(newCommande)).commit();
                        break;
                    case 4:
                        //save command
                        GsonBuilder builder = new GsonBuilder();
                        builder.enableComplexMapKeySerialization();
                        builder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
                        Gson gson = builder.create();
                        String json = appSharedPrefs.getString("testFix", "");

                        Type type = new TypeToken<List<Commande>>() {
                        }.getType();
                        ArrayList<Commande> commands = gson.fromJson(json, type);
                        if (commands == null) commands = new ArrayList<>();
                        commands.add(newCommande);
                        json = gson.toJson(commands);
                        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                        prefsEditor.putString("testFix", json);
                        prefsEditor.putInt("nb_commandes", newCommande.getCode() + 1);
                        prefsEditor.apply();

                        Toast.makeText(NewCommandActivity.this, "Commande № "
                                        + newCommande.getCode() + " ajoutée" ,
                                Toast.LENGTH_SHORT).show();


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
            getSupportFragmentManager().popBackStack();
        }
        //super.onBackPressed();
    }

    @Override
    public void onClickAdd(Plat plat) {
        if (commandes.containsKey(plat)) {
            int quantity = commandes.get(plat);
            commandes.put(plat, quantity + 1);
        } else {
            next = true;
            commandes.put(plat, 1);
        }
    }

    @Override
    public void onClickReduce(Plat plat) {
        if (commandes.containsKey(plat)) {
            int quantity = commandes.get(plat);
            commandes.put(plat, quantity - 1);
            if (quantity == 1) {
                commandes.remove(plat);
            }
        }
        next = !commandes.isEmpty();
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
