package com.example.tp_final.controller.activitiesAndFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tp_final.R;
import com.example.tp_final.controller.adapters.PlatAdapter;
import com.example.tp_final.model.Commande;
import com.example.tp_final.model.Plat;

import java.util.Calendar;
import java.util.HashMap;

public class NewCommandActivity extends AppCompatActivity implements PlatAdapter.QuantityCallBack,
        PaymentFragment.PaymentCallBack {

    private TextView title;
    private ImageButton buttonNext;
    private ImageButton buttonCancel;
    private int mTreeStep = 1;

    //Command details
    private HashMap<Plat, Integer> commandes;
    private Commande.ModePayment modePayment;
    private int nbTable;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_command);

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
                        title.setText("Resumé");
                        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                .replace(R.id.frameNewCommand, new ResumeFragment()).commit();
                        break;
                    case 4:
                        //save command

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
    public void onClickAdd(Plat plat) {
        if (commandes.containsKey(plat)) {
            int quantity = commandes.get(plat);
            commandes.put(plat, quantity + 1);
        } else {
            commandes.put(plat, 1);
        }
    }

    @Override
    public void onClickReduce(Plat plat) {
        if (commandes.containsKey(plat)) {
            int quantity = commandes.get(plat);
            commandes.put(plat, quantity - 1);
        }
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
