package com.example.testapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testapplication.ui.PowCalculations;

import java.text.DecimalFormat;


public class fragment_power extends Fragment {
    EditText watts, dbm,dbmV,uV,dbW;
    RadioGroup power_radio;
    Button clear,calculate;
    RadioButton powWatt,powdBm, powdBmV, powuV,powdBW;
    short n=0;
    double test;
    double dBmV,dBW,Volt,Wt,dBm;

    PowCalculations power= new PowCalculations();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_power, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        calculate= getView().findViewById(R.id.buttonPower);
        clear= getView().findViewById(R.id.buttonClear);

        watts= getView().findViewById(R.id.Watts);
        dbm= getView().findViewById(R.id.dBm);
        dbmV= getView().findViewById(R.id.dBmV);
        uV= getView().findViewById(R.id.uV);
        dbW= getView().findViewById(R.id.dBw);

        power_radio= getView().findViewById(R.id.powerRadio);

        powWatt= getView().findViewById(R.id.titleWatts);
        powdBm= getView().findViewById(R.id.titledBm);
        powdBmV= getView().findViewById(R.id.titledBmV);
        powuV= getView().findViewById(R.id.titleuV);
        powdBW= getView().findViewById(R.id.titledBW);

        watts.setEnabled(false);
        dbm.setEnabled(false);
        dbmV.setEnabled(false);
        uV.setEnabled(false);
        dbW.setEnabled(false);

        power_radio.setOnCheckedChangeListener((group, checkedId) -> {
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
                if(isNum("100"))
                {
                    Toast.makeText(fragment_power.this.getContext(),"Please enter the antenna VSWR",Toast.LENGTH_SHORT).show();
                }
                dbW.setText(String.format("%.3f",dBm_watt(0)));

            }
        });

        calculate.setOnClickListener(v -> {

            switch(n)
            {
                case 1:
                        Wt=Double.parseDouble(watts.getText().toString());
                        //test = Math.log10(Wt);
                        dbm.setText(String.format("%.3f",watts_dBm(Wt)));
                        dbmV.setText(String.format("%.3f",watts_dBmV(Wt)));
                        uV.setText(String.format("%.3f",watts_microVolt(Wt)));
                        dbW.setText(String.format("%.3f",watts_dBW(Wt)));
                    break;
                case 2:
                        dBm=Double.parseDouble(dbm.getText().toString());
                        watts.setText(String.format("%.3f",dBm_watt(dBm)));
                        dbmV.setText(String.format("%.3f",dBm_dBmV(dBm)));
                        uV.setText(String.format("%.3f",dBm_microVolt(dBm)));
                        dbW.setText(String.format("%.3f",dBm_dBW(dBm)));
                    break;
                case 3:
                        dBmV=Double.parseDouble(dbmV.getText().toString());
                        watts.setText(String.format("%.3f",dBmV_watt(dBmV)));
                        dbm.setText(String.format("%.3f",dBmV_dBm(dBmV)));
                        uV.setText(String.format("%.3f",dBmV_microVolt(dBmV)));
                        dbW.setText(String.format("%.3f",dBmV_dBW(dBmV)));
                    break;
                case 4:
                        Volt=Double.parseDouble(uV.getText().toString());
                        //test=Math.log10(Volt);
                        watts.setText(String.format("%.3f",microVolt_watt(Volt)));
                        dbm.setText(String.format("%.3f",microVolt_dBm(Volt)));
                        dbmV.setText(String.format("%.3f",microVolt_dBmV(Volt)));
                        dbW.setText(String.format("%.3f",microVolt_dBW(Volt)));
                    break;
                case 5:
                        dBW=Double.parseDouble(dbW.getText().toString());
                        //test=Math.log10(dBW);
                        watts.setText(String.format("%.3f",dBW_watt(dBW)));
                        dbm.setText(String.format("%.3f",dBW_dBm(dBW)));
                        uV.setText(String.format("%.3f",dBW_microVolt(dBW)));
                        dbmV.setText(String.format("%.3f",dBW_dBmV(dBW)));
                    break;
                default:
                    watts.setText("");
                    dbm.setText("");
                    dbmV.setText("");
                    uV.setText("");
                    dbW.setText("");
                    break;
            }
        });

        clear.setOnClickListener(v -> {
            watts.setText(String.valueOf(0.0d));
            dbm.setText(String.valueOf(0.0d));
            dbmV.setText(String.valueOf(0.0d));
            uV.setText(String.valueOf(0.0d));
            dbW.setText(String.valueOf(0.0d));
        });
    }
    private String outputDecimal(double input)
    {
        return String.format("%.3f",input);
    }

    public double watts_dBmV(double Wt){
        return ((Math.log10(Wt) * 10.0d) + 30.0d)+ 46.9897d;
    }
    public double watts_dBW(double Wt){
        return ((Math.log10(Wt) * 10.0d));
    }
    public double watts_microVolt(double Wt){

        return ((Math.sqrt(Math.pow(10.0d, ((Math.log10(Wt) * 10.0d) + 30.0d) / 10.0d) * 0.05d))); }
    public double watts_dBm(double Wt){
        return (((Math.log10(Wt) * 10.0d) + 30.0d));
    }

    public double dBW_dBmV(double dBW){
        if(Math.pow(10.0d, dBW / 10.0d) == 0)
        {
            return 0.0d;
        }
        return ((46.9897d + ((Math.log10(Math.pow(10.0d, dBW / 10.0d)) * 10.0d) + 30.0d)));
    }
    public double dBW_microVolt(double dBW){
        return ((Math.sqrt(Math.pow(10.0d, ((Math.log10(Math.pow(10.0d, dBW / 10.0d)) * 10.0d) + 30.0d) / 10.0d) * 0.05d)));
    }
    public double dBW_watt(double dBW){
        return ((Math.pow(10.0d, dBW / 10.0d)));
    }
    public double dBW_dBm(double dBW){
        return (((Math.log10(Math.pow(10.0d, dBW / 10.0d)) * 10.0d) + 30.0d));
    }

    public double microVolt_dBmV(double uVolt){
        return ((46.9897d + (Math.log10((uVolt * uVolt) / 0.05d) * 10.0d)));
    }
    public double microVolt_dBW(double uVolt){
        return ((Math.log10(Math.pow(10.0d, (Math.log10((uVolt * uVolt) / 0.05d) * 10.0d) / 10.0d) / 1000.0d) * 10.0d));
    }
    public double microVolt_watt(double uVolt){
        return ((Math.pow(10.0d, (Math.log10((uVolt * uVolt) / 0.05d) * 10.0d) / 10.0d) / 1000.0d));
    }
    public double microVolt_dBm(double uVolt){
        return ((Math.log10((uVolt * uVolt) / 0.05d) * 10.0d));
    }

    public double dBmV_dBW(double dBmV){
        return (((Math.log10(Math.pow(10.0d, (dBmV - 46.9897d) / 10.0d) / 1000.0d) * 10.0d)));
    }
    public double dBmV_microVolt(double dBmV){
        return (((Math.sqrt(Math.pow(10.0d, (dBmV - 46.9897d) / 10.0d) * 0.05d))));
    }
    public double dBmV_watt(double dBmV){
        return ((Math.pow(10.0d, (dBmV - 46.9897d) / 10.0d) / 1000.0d));
    }
    public double dBmV_dBm(double dBmV){
        return ((dBmV - 46.9897d));
    }

    public double dBm_dBmV(double dBm){
        return ((46.9897d + dBm));
    }
    public double dBm_dBW(double dBm){
        return ((Math.log10(Math.pow(10.0d, dBm / 10.0d) / 1000.0d) * 10.0d));
    }
    public double dBm_microVolt(double dBm){
        return ((Math.sqrt(Math.pow(10.0d, dBm / 10.0d) * 0.05d)));
    }
    public double dBm_watt(double dBm){
        return (Math.pow(10.0d, dBm / 10.0d) / 1000.0d);
    }

    public boolean isNum(String text)
    {
        try {
            Double.parseDouble(text);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}