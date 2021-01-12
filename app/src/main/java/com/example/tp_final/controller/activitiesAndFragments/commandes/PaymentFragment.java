package com.example.tp_final.controller.activitiesAndFragments.commandes;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tp_final.R;
import com.example.tp_final.model.Commande;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    private RadioGroup mRadioGroup;
    private NumberPicker mNumberPicker;
    private PaymentCallBack mCallBack;

    public PaymentFragment(PaymentCallBack callBack) {
        mCallBack = callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mRadioGroup = getView().findViewById(R.id.radioGroupPayment);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioEspece:
                        mCallBack.selectPayment(Commande.ModePayment.ESPECE);
                        break;
                    case R.id.radioCarte:
                        mCallBack.selectPayment(Commande.ModePayment.CARTE);
                        break;
                    case R.id.radioCheque:
                        mCallBack.selectPayment(Commande.ModePayment.CHEQUE);
                        break;
                }
            }
        });
        mRadioGroup.check(R.id.radioEspece);

        mNumberPicker = getView().findViewById(R.id.numberPickerTable);
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(50);
        mNumberPicker.setWrapSelectorWheel(true);
        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCallBack.selectTable(newVal);

            }
        });


    }

    public interface PaymentCallBack {
        void selectPayment(Commande.ModePayment modePayment);

        void selectTable(int nbTable);
    }
}
