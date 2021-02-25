package com.example.testapplication.ui.design;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.R;

import Logic.CoaxCalcs;
import Logic.UnitsDistance;
import Logic.VSWRCalc;

public class VSWRFragment extends Fragment {


    EditText vswrIn, cableLossPerMeter, inputPower,lengthCable,CoaxCableLoss,frequency;
    EditText vswrOut, returnLoss, feederLoss, powerAtAntenna, reflectedPower, reflectedPowerAtTransmitter;
    Button calculate;
    Spinner distanceChoice,coax1;
    String itemDistance,itemCoax1;
    int choice=0,row=0,choosing=0;
    TextView displayUnits;
    RadioGroup coaxRadio;
    RadioButton chooseCoax1,enterCoax1;

    double totalCoaxLoss;
    double powerAntenna;
    double antennaREflected;
    double transmitterReflected;
    double finalVSWRDouble;
    double cableAttenuation;
    double freq;

    CoaxCalcs cableCalc=new CoaxCalcs();
    UnitsDistance unitConv=new UnitsDistance();
    VSWRCalc vswrCalcs= new VSWRCalc();

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
        View root = inflater.inflate(R.layout.fragment_vswr, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vswrIn = (EditText) getView().findViewById(R.id.AntennaVSWR);
        inputPower = (EditText) getView().findViewById(R.id.inputPower);
        frequency=(EditText)getView().findViewById(R.id.FrequencyInput);

        vswrOut = (EditText) getView().findViewById(R.id.outputVSWR);
        returnLoss = (EditText) getView().findViewById(R.id.returnLoss);
        feederLoss = (EditText) getView().findViewById(R.id.totalCoax);
        powerAtAntenna = (EditText) getView().findViewById(R.id.PowerAtAntenna);
        reflectedPower = (EditText) getView().findViewById(R.id.reflectedPower);

        vswrIn.setText(BuildFile.FLAVOR);
        //cableLossPerMeter.setText(BuildFile.FLAVOR);
        //cableLength.setText(BuildFile.FLAVOR);
        inputPower.setText(BuildFile.FLAVOR);

        calculate = (Button) getView().findViewById(R.id.buttonVSWR);

        coaxRadio=(RadioGroup) getView().findViewById(R.id.radioCable);

        chooseCoax1=(RadioButton)getView().findViewById(R.id.CoaxChoice);
        chooseCoax1.setChecked(true);
        enterCoax1=(RadioButton)getView().findViewById(R.id.CoaxTransmit);

        distanceChoice=(Spinner)getView().findViewById(R.id.spinnerChoiceDistance);
        coax1=(Spinner)getView().findViewById(R.id.spinnerChoice);

        lengthCable=(EditText)getView().findViewById(R.id.CableInput);
        CoaxCableLoss=(EditText)getView().findViewById(R.id.LossCoax);
        CoaxCableLoss.setEnabled(false);

        vswrOut.setEnabled(false);
        vswrOut.setTextColor(Color.RED);
        returnLoss.setEnabled(false);
        returnLoss.setTextColor(Color.RED);
        feederLoss.setEnabled(false);
        feederLoss.setTextColor(Color.RED);
        powerAtAntenna.setEnabled(false);
        powerAtAntenna.setTextColor(Color.RED);
        reflectedPower.setEnabled(false);
        reflectedPower.setTextColor(Color.RED);

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
                    cableAttenuation=cableCalc.attenuationLoss_dB_m(freq);
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
                }
                else if(itemDistance.equals("ft"))
                {
                    choice=1;
                }
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
                cableAttenuation=cableCalc.fetchAttenuation(row);
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

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    vswr=Double.parseDouble(vswrIn.getText().toString());

                    //using OOP


                    //Using OOP Class
                    powerAntenna=vswrCalcs.pow_received_at_ant(inPower,coaxLoss);
                    powerAtAntenna.setText(String.valueOf(powerAntenna));

                    feederLoss.setText(String.valueOf(coaxLoss));

                    //using OOP
                    antennaREflected = vswrCalcs.ant_reflected_power(vswr,powerAntenna);

                    reflectedPower.setText(String.valueOf(antennaREflected));

                    //using OOP
                    powerTransmittedByAnt=vswrCalcs.power_transmitted_by_antenna_watts(vswr,antennaREflected);
                    //to be recalculated;
                    // totalLoss=vswrCalcs.total_power_loss(coaxLoss,antennaREflected);

                    transmitterReflected = antennaREflected / Math.pow(10.0d, coaxLoss / 10.0d);

                    finalVSWRDouble = vswrCalcs.vswr_at_transmitter(transmitterReflected,inPower);

                    vswrOut.setText(String.valueOf(finalVSWRDouble));

                    returnLoss.setText(String.valueOf(vswrCalcs.return_loss(vswr)));

            }
        });

    }
}