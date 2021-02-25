package com.example.testapplication.ui.design;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.R;

import Logic.PowCalculations;

public class fragment_power extends Fragment {
    EditText watts, dbm,dbmV,uV,dbW;
    RadioGroup power_radio;
    Button clear,calculate;
    RadioButton powWatt,powdBm, powdBmV, powuV,powdBW;
    short n=0;
    double dBmV,dBW,Volt,Wt,dBm;

    PowCalculations power=new PowCalculations();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_power, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calculate=(Button) getView().findViewById(R.id.buttonPower);
        clear=(Button) getView().findViewById(R.id.buttonClear);

        watts=(EditText) getView().findViewById(R.id.Watts);
        dbm=(EditText) getView().findViewById(R.id.dBm);
        dbmV=(EditText) getView().findViewById(R.id.dBmV);
        uV=(EditText) getView().findViewById(R.id.uV);
        dbW=(EditText) getView().findViewById(R.id.dBw);

        power_radio=(RadioGroup) getView().findViewById(R.id.powerRadio);

        powWatt=(RadioButton) getView().findViewById(R.id.titleWatts);
        powdBm=(RadioButton) getView().findViewById(R.id.titledBm);
        powdBmV=(RadioButton) getView().findViewById(R.id.titledBmV);
        powuV=(RadioButton) getView().findViewById(R.id.titleuV);
        powdBW=(RadioButton) getView().findViewById(R.id.titledBW);

        watts.setEnabled(false);
        dbm.setEnabled(false);
        dbmV.setEnabled(false);
        uV.setEnabled(false);
        dbW.setEnabled(false);

        power_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.titleWatts)
                {
                    n=1;
                    watts.setEnabled(true);
                    dbm.setEnabled(false);
                    dbmV.setEnabled(false);
                    uV.setEnabled(false);
                    dbW.setEnabled(false);
                }
                else if(checkedId==R.id.titledBm)
                {
                    n=2;
                    watts.setEnabled(false);
                    dbm.setEnabled(true);
                    dbmV.setEnabled(false);
                    uV.setEnabled(false);
                    dbW.setEnabled(false);
                }
                else if(checkedId==R.id.titledBmV)
                {
                    n=3;
                    watts.setEnabled(false);
                    dbm.setEnabled(false);
                    dbmV.setEnabled(true);
                    uV.setEnabled(false);
                    dbW.setEnabled(false);
                }
                else if(checkedId==R.id.titleuV)
                {
                    n=4;
                    watts.setEnabled(false);
                    dbm.setEnabled(false);
                    dbmV.setEnabled(false);
                    uV.setEnabled(true);
                    dbW.setEnabled(false);
                }
                else if(checkedId==R.id.titledBW)
                {
                    n=5;
                    watts.setEnabled(false);
                    dbm.setEnabled(false);
                    dbmV.setEnabled(false);
                    uV.setEnabled(false);
                    dbW.setEnabled(true);
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(n)
                {
                    case 1:
                        try{
                            Math.log10(Double.parseDouble(watts.getText().toString()));
                        }
                        catch (NumberFormatException e){
                            Toast.makeText(fragment_power.this.getContext(),"Invalid entry",Toast.LENGTH_SHORT).show();
                            watts.setText(String.valueOf(0.0d));
                            break;
                        }
                        if (BuildFile.FLAVOR.equals(watts.getText().toString())) {
                            Toast.makeText(fragment_power.this.getContext(),"Please enter a value for power",Toast.LENGTH_SHORT).show();
                            Wt = 0.0d;
                        } else {
                            Wt = Double.parseDouble(watts.getText().toString());
                        }
                        dbm.setText(String.valueOf(power.watts_dBm(Wt)));
                        dbmV.setText(String.valueOf(power.watts_dBmV(Wt)));
                        uV.setText(String.valueOf(power.watts_microVolt(Wt)));
                        dbW.setText(String.valueOf(power.watts_dBW(Wt)));
                        break;
                    case 2:
                        try{
                            Math.log10(Double.parseDouble(dbm.getText().toString()));
                            if (BuildFile.FLAVOR.equals(dbm.getText().toString())) {
                                dBm = 0.0d;
                            } else {
                                dBm = Double.parseDouble(dbm.getText().toString());
                            }
                            watts.setText(String.valueOf(power.dBm_watt(dBm)));
                            dbmV.setText(String.valueOf(power.dBm_dBmV(dBm)));
                            uV.setText(String.valueOf(power.dBm_microVolt(dBm)));
                            dbW.setText(String.valueOf(power.dBm_dBW(dBm)));
                        }
                        catch (NumberFormatException e){
                            Toast.makeText(fragment_power.this.getContext(),"Invalid entry",Toast.LENGTH_SHORT).show();
                            dbm.setText(String.valueOf(0.0d));
                            dBm=0.0d;
                        }
                        break;
                    case 3:
                        try {
                            Math.log10(Double.parseDouble(dbmV.getText().toString()));
                            if (BuildFile.FLAVOR.equals(dbmV.getText().toString())) {
                                dBmV = 0.0d;
                            } else {
                                dBmV = Double.parseDouble(dbmV.getText().toString());
                            }
                            watts.setText(String.valueOf(power.dBmV_watt(dBmV)));
                            dbm.setText(String.valueOf(power.dBmV_dBm(dBmV)));
                            uV.setText(String.valueOf(power.dBmV_microVolt(dBmV)));
                            dbW.setText(String.valueOf(power.dBmV_dBW(dBmV)));
                        }
                        catch(NumberFormatException e)
                        {
                            Toast.makeText(fragment_power.this.getContext(),"Invalid entry",Toast.LENGTH_SHORT).show();
                            dbmV.setText(String.valueOf(0.0d));
                        }
                        break;
                    case 4:
                        try {
                            Math.log10(Double.parseDouble(uV.getText().toString()));
                            if (BuildFile.FLAVOR.equals(uV.getText().toString())) {
                                Volt = 0.0d;
                            } else {
                                Volt = Double.parseDouble(uV.getText().toString());
                            }
                            watts.setText(String.valueOf(power.microVolt_watt(Volt)));
                            dbm.setText(String.valueOf(power.microVolt_dBm(Volt)));
                            dbmV.setText(String.valueOf(power.microVolt_dBmV(Volt)));
                            dbW.setText(String.valueOf(power.microVolt_dBW(Volt)));
                        }
                        catch(NumberFormatException e)
                        {
                            Toast.makeText(fragment_power.this.getContext(),"Invalid entry",Toast.LENGTH_SHORT).show();
                            uV.setText(String.valueOf(0.0d));
                        }
                        break;
                    case 5:
                        try{
                            Math.log10(Double.parseDouble(dbW.getText().toString()));
                            if (BuildFile.FLAVOR.equals(dbW.getText().toString())) {
                                dBW = 0.0d;
                            } else {
                                dBW = Double.parseDouble(dbW.getText().toString());
                            }
                            watts.setText(String.valueOf(power.dBW_watt(dBW)));
                            dbm.setText(String.valueOf(power.dBW_dBm(dBW)));
                            uV.setText(String.valueOf(power.dBW_microVolt(dBW)));
                            dbmV.setText(String.valueOf(power.dBW_dBmV(dBW)));
                        }
                        catch(NumberFormatException e){
                            Toast.makeText(fragment_power.this.getContext(),"Invalid entry", Toast.LENGTH_SHORT).show();
                            dbW.setText(String.valueOf(0.0d));}
                        break;
                    default:
                        watts.setText(BuildFile.FLAVOR);
                        dbm.setText(BuildFile.FLAVOR);
                        dbmV.setText(BuildFile.FLAVOR);
                        uV.setText(BuildFile.FLAVOR);
                        dbW.setText(BuildFile.FLAVOR);
                        break;
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watts.setText(String.valueOf(0.0d));
                dbm.setText(String.valueOf(0.0d));
                dbmV.setText(String.valueOf(0.0d));
                uV.setText(String.valueOf(0.0d));
                dbW.setText(String.valueOf(0.0d));
            }
        });
    }
}