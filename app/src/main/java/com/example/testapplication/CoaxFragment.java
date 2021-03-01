package com.example.testapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testapplication.ui.CoaxCalcs;

import java.text.DecimalFormat;

public class CoaxFragment extends Fragment {
    Spinner spin;
    SeekBar seek;
    EditText frequency, cableLength, attenuation, totalLoss;
    String item;

    CoaxCalcs cable=new CoaxCalcs();

    double attenuationConst=1,length=0,number;
    int row=0, column=0;
    EditText manufacturer,innerConductor,Dielectric,outerShield1,outerShield2,jacket,jacketTolerance,oneTimeBendRadius,repeatedBendRadius,installation,staticBendRadius,dynamicBendRadius,maxBend;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coax, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spin= getView().findViewById(R.id.CoaxSpinner);
        seek= getView().findViewById(R.id.seekBar2);

        frequency= getView().findViewById(R.id.FrequencyShow);
        cableLength= getView().findViewById(R.id.CableLength);
        attenuation= getView().findViewById(R.id.AttenuationatFrequency);
        totalLoss= getView().findViewById(R.id.TotalLoss);
        totalLoss.setEnabled(false);

        manufacturer= getView().findViewById(R.id.Manufacturer);
        innerConductor= getView().findViewById(R.id.InnerConductor);
        Dielectric= getView().findViewById(R.id.Dielectric);
        outerShield1= getView().findViewById(R.id.Shield1);
        outerShield2= getView().findViewById(R.id.Shield2);
        jacket= getView().findViewById(R.id.Jacket);
        jacketTolerance= getView().findViewById((R.id.JacketTolerance));
        oneTimeBendRadius= getView().findViewById(R.id.OneTime);
        repeatedBendRadius= getView().findViewById(R.id.Repeated);
        installation= getView().findViewById(R.id.Installation);
        staticBendRadius= getView().findViewById(R.id.Static);
        dynamicBendRadius= getView().findViewById(R.id.Dynamic);
        maxBend= getView().findViewById(R.id.dynamic);

        attenuation.setText("N/A");

        seek.setMax(8000);
        seek.setProgress(50);

        frequency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isNum(s.toString())) {
                    number = Double.parseDouble(s.toString());
                    attenuationConst=cable.attenuationLoss_dB_m(number);

                    length = Double.parseDouble(cableLength.getText().toString());
                    totalLoss.setText(String.format("%.3f",cable.totalLoss(length,attenuationConst)));
                    if(attenuationConst!=0)
                        attenuation.setText(String.format("%.3f",attenuationConst));
                    else
                        attenuation.setText("N/A");
                }
                else
                    Toast.makeText(CoaxFragment.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cableLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isNum(s.toString())) {
                    length = Double.parseDouble(s.toString());
                    totalLoss.setText(String.format("%.3f",cable.totalLoss(length,attenuationConst)));//(Double.toString(length * attenuationConst));
                }
                else
                    Toast.makeText(CoaxFragment.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(CoaxFragment.this.getContext(),R.array.coax_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item =parent.getItemAtPosition(position).toString();
                if(item.equals("RG 174")){
                    manufacturer.setText("Pasternack");
                    innerConductor.setText("0.41");
                    Dielectric.setText("1.52");
                    outerShield1.setText("2.06");
                    outerShield2.setText("NA");
                    jacket.setText("2.79");
                    jacketTolerance.setText("NA");
                    oneTimeBendRadius.setText(" 6.35");
                    repeatedBendRadius.setText("25.4");
                    installation.setText("NA");
                    staticBendRadius.setText("NA");
                    dynamicBendRadius.setText("NA");
                    maxBend.setText("1000");
                    row=0;}
                else if(item.equals("RG 174_U")){
                    manufacturer.setText("Pasternack");
                    innerConductor.setText("0.51");
                    Dielectric.setText("1.52");
                    outerShield1.setText("2.06");
                    outerShield2.setText("NA");
                    jacket.setText("2.49");
                    jacketTolerance.setText("NA");
                    oneTimeBendRadius.setText("6.35");
                    repeatedBendRadius.setText("25.4");
                    installation.setText("NA");
                    staticBendRadius.setText("NA");
                    dynamicBendRadius.setText("NA");
                    maxBend.setText("1000");
                    row=1;}
                else if (item.equals("LMR 195_FR")){
                    manufacturer.setText("Times Microwave Systems");
                    innerConductor.setText("0.94");
                    Dielectric.setText("2.79");
                    outerShield1.setText("2.95");
                    outerShield2.setText("3.53");
                    jacket.setText("4.95");
                    jacketTolerance.setText("NA");
                    oneTimeBendRadius.setText("NA");
                    repeatedBendRadius.setText("50.8");
                    installation.setText("12.7");
                    staticBendRadius.setText("NA");
                    dynamicBendRadius.setText("NA");
                    maxBend.setText("5800");
                    row=2;}
                else if (item.equals("HDF 195")){
                    manufacturer.setText("NA");
                    innerConductor.setText("0.94");
                    Dielectric.setText("2.79");
                    outerShield1.setText("2.95");
                    outerShield2.setText("3.53");
                    jacket.setText("4.95");
                    jacketTolerance.setText("NA");
                    oneTimeBendRadius.setText("NA");
                    repeatedBendRadius.setText("50.8");
                    installation.setText("12.7");
                    staticBendRadius.setText("NA");
                    dynamicBendRadius.setText("NA");
                    maxBend.setText("5800");
                    row=3;}
                else if(item.equals("LMR 200")){
                    manufacturer.setText("Times Microwave Systems");
                    innerConductor.setText("1.12");
                    Dielectric.setText("2.95");
                    outerShield1.setText("3.07");
                    outerShield2.setText("3.66");
                    jacket.setText("4.95");
                    jacketTolerance.setText("NA");
                    oneTimeBendRadius.setText("NA");
                    repeatedBendRadius.setText("50.8");
                    installation.setText("12.7");
                    staticBendRadius.setText("NA");
                    dynamicBendRadius.setText("NA");
                    maxBend.setText("8000");
                    row=4;}
                else if(item.equals("LMR 400")){
                    manufacturer.setText("Times Microwave Systems");
                    innerConductor.setText("2.74");
                    Dielectric.setText("7.24");
                    outerShield1.setText("7.39");
                    outerShield2.setText("8.13");
                    jacket.setText("10.29");
                    jacketTolerance.setText("NA");
                    oneTimeBendRadius.setText("NA");
                    repeatedBendRadius.setText("101.6");
                    installation.setText("25.4");
                    staticBendRadius.setText("NA");
                    dynamicBendRadius.setText("NA");
                    maxBend.setText("8000");
                    row=5;}
                else if(item.equals("RG 316")){
                    manufacturer.setText("Pasternack");
                    innerConductor.setText("0.51");
                    Dielectric.setText("1.52");
                    outerShield1.setText("2.06");
                    outerShield2.setText("NA");
                    jacket.setText("2.49");
                    jacketTolerance.setText("NA");
                    oneTimeBendRadius.setText("14.99");
                    repeatedBendRadius.setText("NA");
                    installation.setText("NA");
                    staticBendRadius.setText("NA");
                    dynamicBendRadius.setText("NA");
                    maxBend.setText("1000");
                    row=6;}
                else if(item.equals("RG 58")){
                    manufacturer.setText("Pasternack");
                    innerConductor.setText("0.91");
                    Dielectric.setText("2.95");
                    outerShield1.setText("NA");
                    outerShield2.setText("NA");
                    jacket.setText("4.95");
                    jacketTolerance.setText("NA");
                    oneTimeBendRadius.setText("NA");
                    repeatedBendRadius.setText("49.78");
                    installation.setText("24.78");
                    staticBendRadius.setText("NA");
                    dynamicBendRadius.setText("NA");
                    maxBend.setText("5000");
                    row=7;}
                else if(item.equals("H 155A00")){
                    manufacturer.setText("Belden");
                    innerConductor.setText("1.41");
                    Dielectric.setText("3.9");
                    outerShield1.setText("4.5");
                    outerShield2.setText("NA");
                    jacket.setText("5.4");
                    jacketTolerance.setText("Â±0,2");
                    oneTimeBendRadius.setText("NA");
                    repeatedBendRadius.setText("NA");
                    installation.setText("54");
                    staticBendRadius.setText("NA");
                    dynamicBendRadius.setText("NA");
                    maxBend.setText("800");
                    row=8;}
                else if(item.equals("Enviroflex 316_D")){
                    manufacturer.setText("Huber & Sunher");
                    innerConductor.setText("0.54");
                    Dielectric.setText("1.53");
                    outerShield1.setText("1.99");
                    outerShield2.setText("2.44");
                    jacket.setText("3.16");
                    jacketTolerance.setText("Â±0,08");
                    oneTimeBendRadius.setText("NA");
                    repeatedBendRadius.setText("30");
                    installation.setText("NA");
                    staticBendRadius.setText("5");
                    dynamicBendRadius.setText("30");
                    maxBend.setText("6000");
                    row=9;}
                else if(item.equals("LEONI Dacar 302")){
                    manufacturer.setText("LEONI Dacar");
                    innerConductor.setText("0.81");
                    Dielectric.setText("555");
                    outerShield1.setText("555");
                    outerShield2.setText("2.5");
                    jacket.setText("3.2");
                    jacketTolerance.setText("NA");
                    oneTimeBendRadius.setText("555");
                    repeatedBendRadius.setText("555");
                    installation.setText("555");
                    staticBendRadius.setText("555");
                    dynamicBendRadius.setText("55");
                    maxBend.setText("6000");
                    row=10;}
                else{
                    manufacturer.setText("NA");
                    innerConductor.setText("NA");
                    Dielectric.setText("NA");
                    outerShield1.setText("NA");
                    outerShield2.setText("NA");
                    jacket.setText("NA");
                    jacketTolerance.setText("NA");
                    oneTimeBendRadius.setText("NA");
                    repeatedBendRadius.setText("NA");
                    installation.setText("NA");
                    staticBendRadius.setText("NA");
                    dynamicBendRadius.setText("NA");
                    maxBend.setText("NA");}
                attenuationConst = cable.fetchAttenuation(row);//,column);//Double.parseDouble(strArr[row][column]);
                totalLoss.setText(String.format("%.3f",cable.totalLoss(length ,attenuationConst)));
                attenuation.setText(String.valueOf(attenuationConst));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                frequency.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
