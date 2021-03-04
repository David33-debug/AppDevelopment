package com.example.testapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.example.testapplication.ui.CoaxCalcs;
import com.example.testapplication.ui.PowCalculations;
import com.example.testapplication.ui.UnitsDistance;
import com.example.testapplication.ui.VSWRCalc;

public class VSWRFragment extends Fragment {


    EditText vswrIn, cableLossPerMeter, inputPower,lengthCable,CoaxCableLoss,frequency,mismatch;
    EditText vswrOut, returnLoss, feederLoss, powerAtAntenna, reflectedPower, reflectedPowerAtTransmitter;
    Button calculate;
    Spinner distanceChoice,coax1,choicePower;
    String itemDistance,itemCoax1,itemPower;
    int choice=0,row=0,choosing=0;
    TextView displayUnits;
    RadioGroup coaxRadio;
    RadioButton chooseCoax1,enterCoax1;

    double totalCoaxLoss;

    double constant=1;

    double powerAntenna;
    double antennaREflected;
    double transmitterReflected;
    double finalVSWRDouble;
    double cableAttenuation;
    double freq;

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

    double vswr, cable, coaxLoss, inPower, powerTransmittedByAnt, totalLoss;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vswr, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CoaxCalcs cableCalc=new CoaxCalcs();
        UnitsDistance unitConv=new UnitsDistance();
        VSWRCalc vswrCalcs= new VSWRCalc();
        PowCalculations powCalcs=new PowCalculations();


        mismatch=getView().findViewById(R.id.MismatchLoss);

        vswrIn = getView().findViewById(R.id.AntennaVSWR);
        inputPower = getView().findViewById(R.id.inputPower);
        frequency= getView().findViewById(R.id.FrequencyInput);

        vswrOut = getView().findViewById(R.id.outputVSWR);
        returnLoss = getView().findViewById(R.id.returnLoss);
        feederLoss = getView().findViewById(R.id.totalCoax);
        powerAtAntenna = getView().findViewById(R.id.PowerAtAntenna);
        reflectedPower = getView().findViewById(R.id.reflectedPower);

        calculate = getView().findViewById(R.id.buttonVSWR);

        coaxRadio= getView().findViewById(R.id.radioCable);

        chooseCoax1= getView().findViewById(R.id.CoaxChoice);
        chooseCoax1.setChecked(true);
        enterCoax1= getView().findViewById(R.id.CoaxTransmit);

        choicePower=getView().findViewById(R.id.spinnerChoicePower);

        distanceChoice= getView().findViewById(R.id.spinnerChoiceDistance);
        coax1= getView().findViewById(R.id.spinnerChoice);

        lengthCable= getView().findViewById(R.id.CableInput);
        CoaxCableLoss= getView().findViewById(R.id.LossCoax);
        CoaxCableLoss.setEnabled(false);

        mismatch.setEnabled(false);
        mismatch.setTextColor(Color.BLUE);
        vswrOut.setEnabled(false);
        vswrOut.setTextColor(Color.BLUE);
        returnLoss.setEnabled(false);
        returnLoss.setTextColor(Color.BLUE);
        feederLoss.setEnabled(false);
        feederLoss.setTextColor(Color.BLUE);
        powerAtAntenna.setEnabled(false);
        powerAtAntenna.setTextColor(Color.BLUE);
        reflectedPower.setEnabled(false);
        reflectedPower.setTextColor(Color.BLUE);

        frequency.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                if (!BuildFile.FLAVOR.equals(testing)) {
                    try {
                        freq=Double.parseDouble(testing);
                    } catch (NumberFormatException e) {
                        frequency.setText(BuildFile.FLAVOR);
                    }
                    cableAttenuation=cableCalc.attenuationLoss_dB_m(freq,constant);
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });

        coaxRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.CoaxChoice)
                {
                    choosing=0;
                    lengthCable.setEnabled(true);
                    CoaxCableLoss.setEnabled(false);
                    CoaxCableLoss.setText("0");
                }
                else if(checkedId==R.id.CoaxTransmit)
                {
                    choosing=1;
                    CoaxCableLoss.setEnabled(true);
                    lengthCable.setEnabled(false);
                    lengthCable.setText("0");
                }
            }
        });

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(VSWRFragment.this.getContext() , R.array.distances_small, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceChoice.setAdapter(adapter);

        distanceChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemDistance=parent.getItemAtPosition(position).toString();
                if(itemDistance.equals("m"))
                {
                    choice=0;
                    constant=1;

                }
                else if(itemDistance.equals("ft"))
                {
                    choice=1;
                    constant=3.28;
                }
                frequency.setText(frequency.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(VSWRFragment.this.getContext() , R.array.coax_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coax1.setAdapter(adapter2);

        coax1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemCoax1=parent.getItemAtPosition(position).toString();
                if(itemCoax1.equals("RG 174")){
                    row=0;}
                else if(itemCoax1.equals("RG 174_U")){
                    row=1;}
                else if (itemCoax1.equals("LMR 195_FR")){
                    row=2;}
                else if (itemCoax1.equals("HDF 195")){
                    row=3;}
                else if(itemCoax1.equals("LMR 200")){
                    row=4;}
                else if(itemCoax1.equals("LMR 400")){
                    row=5;}
                else if(itemCoax1.equals("RG 316")){
                    row=6;}
                else if(itemCoax1.equals("RG 58")){
                    row=7;}
                else if(itemCoax1.equals("H 155A00")){
                    row=8;}
                else if(itemCoax1.equals("Enviroflex 316_D")){
                    row=9;}
                else if(itemCoax1.equals("LEONI Dacar 302")){
                    row=10;}
                cableAttenuation=cableCalc.fetchAttenuation(row,constant);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter3= ArrayAdapter.createFromResource(VSWRFragment.this.getContext() , R.array.power_units_small, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choicePower.setAdapter(adapter3);

        choicePower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemPower=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vswrIn.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                if (!BuildFile.FLAVOR.equals(testing)) {
                    try {
                        Double.parseDouble(testing);
                    } catch (NumberFormatException e) {
                        vswrIn.setText(BuildFile.FLAVOR);
                    }
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });
        inputPower.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                if (!BuildFile.FLAVOR.equals(testing)) {
                    try {
                        Double.parseDouble(testing);
                    } catch (NumberFormatException e) {
                        inputPower.setText(BuildFile.FLAVOR);
                    }
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });

        calculate.setOnClickListener(v -> {

            if(!isNum(vswrIn.getText().toString()))
            {
                Toast.makeText(VSWRFragment.this.getContext(),"Please enter the antenna VSWR",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!isNum(inputPower.getText().toString()))
            {
                Toast.makeText(VSWRFragment.this.getContext(),"Please enter the antenna VSWR",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!isNum(frequency.getText().toString()))
            {
                Toast.makeText(VSWRFragment.this.getContext(),"Please enter the antenna VSWR",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!isNum(lengthCable.getText().toString()) && (choosing==0))
            {
                Toast.makeText(VSWRFragment.this.getContext(),"Please enter the antenna VSWR",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!isNum(CoaxCableLoss.getText().toString()) && (choosing==1))
            {
                Toast.makeText(VSWRFragment.this.getContext(),"Please enter the antenna VSWR",Toast.LENGTH_SHORT).show();
                return;
            }
                cable=Double.parseDouble(lengthCable.getText().toString());
                if(choice==1)
                {
                    cable=unitConv.normalise(itemDistance,cable);
                }

                if(choosing==0){
                    coaxLoss=cable*cableAttenuation;
                    //totalCoaxLoss = vswrCalcs.coax_loss(coaxLoss,cable);
                }
                else if(choosing==1)
                {
                    coaxLoss=Double.parseDouble(CoaxCableLoss.getText().toString());
                    //totalCoaxLoss = vswrCalcs.coax_loss(coaxLoss,cable);
                }

                inPower=Double.parseDouble(inputPower.getText().toString());

                if(itemPower.equals("dBm"))
                {
                    inPower=powCalcs.dBm_watt(inPower);
                }

                vswr=Double.parseDouble(vswrIn.getText().toString());

                //using OOP


                //Using OOP Class
                powerAntenna=vswrCalcs.pow_received_at_ant(inPower,coaxLoss);
                powerAtAntenna.setText(String.format("%.3f",powerAntenna));

                feederLoss.setText(String.format("%.3f",coaxLoss));

                //using OOP
                //antennaREflected = vswrCalcs.ant_reflected_power(vswr,powerAntenna);
                antennaREflected = vswrCalcs.reflectedPower_watts(powerAntenna);
                reflectedPower.setText(String.format("%.3f",antennaREflected));

                //using OOP
                powerTransmittedByAnt=vswrCalcs.power_transmitted_by_antenna_watts(vswr,antennaREflected);
                //to be recalculated;
                // totalLoss=vswrCalcs.total_power_loss(coaxLoss,antennaREflected);

                transmitterReflected = antennaREflected / Math.pow(10.0d, coaxLoss / 10.0d);

                finalVSWRDouble = vswrCalcs.vswr_at_transmitter(transmitterReflected,inPower);

                vswrOut.setText(String.format("%.3f",finalVSWRDouble));

                returnLoss.setText(String.format("%.3f",vswrCalcs.return_loss(vswr)));

                mismatch.setText(String.format("%.3f",vswrCalcs.mismatchLoss_dB(vswr)));

        });

    }
}