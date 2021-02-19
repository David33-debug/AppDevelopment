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

import static java.lang.Math.log10;

public class fragment_link extends Fragment {
    Button calculate;
    EditText distance, frequency, fadingMargin, ancillaryGain;
    EditText locationAGain, LocationATxPower, LocationAConnectorLoss, LocationACoaxCableLoss;
    EditText locationBGain, LocationBRxSensitivity, LocationBConnectorLoss, LocationBCoaxCableLoss;
    EditText TotalCoaxLoss, FreeSpacePathLoss, MinAntennaAGain, MaxPermissibleCoaxLoss, MaxPermissibleFreeSpacePathLoss, EstimatedReceivedSignalAtB;
    //RadioButton
    //RadioGroup
    Spinner distance_unit,coax1,coax2;
    String itemDistance,itemCoax1,itemCoax2;

    CoaxCalcs coax= new CoaxCalcs();
    int row=0,column=0;

    double cableAttenuation=0;
    double cableLength1,cableLength2;
    double totalCoaxLoss1,totalCoaxLoss2;


    double powerReceived,powerReceived1;
    double FreeSpaceLoss;
    double distanceKm=0;
    double frequencyMHz=0;
    double gainTransmitter=0;
    double gainReceiver=0;
    double powerTransmitted=0;

    double transmitGainDouble;
    double recieverGainDouble;
    double rxRecieverDouble;
    double txTransmitDouble;
    double totalCoaxDouble;
    double fadingDouble;
    double recieveConnectorDouble;
    double transmitConnectorDouble;
    double freeSpaceDouble;
    double checking1;
    double checking2;
    double answerDoubleGain;
    double answerDoublePathLoss;
    double recieverDouble;
    double answerDoubleCoax;
    double answerB;
    double answerA;

    public boolean isNum(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_link_2, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //groupChoice=(RadioGroup) getView().findViewById(R.id.linkRadioGroup);

        calculate=(Button) getView().findViewById(R.id.button);
        distance=(EditText) getView().findViewById(R.id.Distance);
        frequency=(EditText) getView().findViewById(R.id.Frequency);
        fadingMargin=(EditText) getView().findViewById(R.id.FadingMargin);
        ancillaryGain=(EditText) getView().findViewById(R.id.AncillaryGain);

        locationAGain=(EditText) getView().findViewById(R.id.GainTransmitter);
        LocationATxPower=(EditText) getView().findViewById(R.id.TxTransmit);
        LocationAConnectorLoss=(EditText) getView().findViewById(R.id.ConnectorTransmit);
        LocationACoaxCableLoss=(EditText) getView().findViewById(R.id.TransmitCoax);

        locationBGain=(EditText) getView().findViewById(R.id.GainReciever);
        LocationBRxSensitivity=(EditText) getView().findViewById(R.id.RxReciever);
        LocationBConnectorLoss=(EditText) getView().findViewById(R.id.ConnectorReciever);
        LocationBCoaxCableLoss=(EditText) getView().findViewById(R.id.RecieverCoax);

        TotalCoaxLoss=(EditText) getView().findViewById(R.id.TotalCoax);
        FreeSpacePathLoss=(EditText) getView().findViewById(R.id.FreeSpaceLoss);
        MinAntennaAGain=(EditText) getView().findViewById(R.id.maxToDisplay);
        MaxPermissibleCoaxLoss=(EditText) getView().findViewById(R.id.maxACoax);
        MaxPermissibleFreeSpacePathLoss=(EditText) getView().findViewById(R.id.maxFreeSpaceLoss);
        EstimatedReceivedSignalAtB=(EditText)getView().findViewById(R.id.Bsignal);

        distance_unit=(Spinner)getView().findViewById(R.id.spinnerChoice_distance);
        coax1=(Spinner)getView().findViewById(R.id.spinnerChoice);
        coax2=(Spinner)getView().findViewById(R.id.spinnerChoice1);

        TotalCoaxLoss.setEnabled(false);
        TotalCoaxLoss.setTextColor(Color.RED);
        FreeSpacePathLoss.setEnabled(false);
        FreeSpacePathLoss.setTextColor(Color.RED);
        MinAntennaAGain.setEnabled(false);
        MinAntennaAGain.setTextColor(Color.RED);
        MaxPermissibleCoaxLoss.setEnabled(false);
        MaxPermissibleCoaxLoss.setTextColor(Color.RED);
        MaxPermissibleFreeSpacePathLoss.setEnabled(false);
        MaxPermissibleFreeSpacePathLoss.setTextColor(Color.RED);
        EstimatedReceivedSignalAtB.setEnabled(false);
        EstimatedReceivedSignalAtB.setTextColor(Color.RED);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(fragment_link.this.getContext() , R.array.distances_few, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distance_unit.setAdapter(adapter);

        distance_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemDistance=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(fragment_link.this.getContext() , R.array.coax_array, android.R.layout.simple_spinner_item);
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
                cableAttenuation=coax.fetchAttenuation(row);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter3= ArrayAdapter.createFromResource(fragment_link.this.getContext() , R.array.coax_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coax2.setAdapter(adapter3);

        coax2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemCoax2=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/*
        groupChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            }
        });*/

        LocationACoaxCableLoss.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newString = s.toString();
                try {
                    Double.parseDouble(newString);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }

            }

            public void afterTextChanged(Editable s) {
            }
        });
        LocationBCoaxCableLoss.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newString = s.toString();
                try {
                    Double.parseDouble(newString);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });

        frequency.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String frequencyString = s.toString();
                try{
                    frequencyMHz=Double.parseDouble(frequencyString);}
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();}
                cableAttenuation=coax.attenuationLoss_dB_m(frequencyMHz);

            }
            public void afterTextChanged(Editable s) {
            }
        });
        distance.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String distanceString = s.toString();
                try{
                    distanceKm=Double.parseDouble(distanceString);}
                catch(NumberFormatException e){
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });

        fadingMargin.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                try {
                    Double.parseDouble(testing);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }
            }
            public void afterTextChanged(Editable s) {
            }
        });

        LocationBConnectorLoss.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                try {
                    Double.parseDouble(testing);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }
                        /*if (!BuildFile.FLAVOR.equals(testing)) {
                            try {
                                Double.parseDouble(testing);
                            } catch (NumberFormatException e) {
                                LocationBConnectorLoss.setText(BuildFile.FLAVOR);
                            }
                        }*/
            }

            public void afterTextChanged(Editable s) {
            }
        });
        //final EditText transmitConnector4 = transmitConnector2;
        LocationAConnectorLoss.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                try {
                    Double.parseDouble(testing);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }
                        /*if (!BuildFile.FLAVOR.equals(testing)) {
                            try {
                                Double.parseDouble(testing);
                            } catch (NumberFormatException e) {
                                LocationAConnectorLoss.setText(BuildFile.FLAVOR);
                            }
                        }*/
            }

            public void afterTextChanged(Editable s) {
            }
        });
        //final EditText txTransmit5 = txTransmit3;
        LocationATxPower.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                try {
                    Double.parseDouble(testing);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }
                        /*if (!BuildFile.FLAVOR.equals(testing)) {
                            try {
                                Double.parseDouble(testing);
                            } catch (NumberFormatException e) {
                                LocationATxPower.setText(BuildFile.FLAVOR);
                            }
                        }*/
            }

            public void afterTextChanged(Editable s) {
            }
        });
        //final EditText txRecieve5 = txRecieve3;

        //final EditText rxRecieverEdit4 = rxRecieverEdit2;
        LocationBRxSensitivity.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                try {
                    Double.parseDouble(testing);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }
                        /*if (!BuildFile.FLAVOR.equals(testing)) {
                            try {
                                Double.parseDouble(testing);
                            } catch (NumberFormatException e) {
                                if (!"-".equals(Character.toString(testing.charAt(start)))) {
                                    LocationBRxSensitivity.setText(BuildFile.FLAVOR);
                                }
                            }
                        }*/
            }

            public void afterTextChanged(Editable s) {
            }
        });

        locationAGain.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                try{
                    gainTransmitter=Double.parseDouble(testing);}
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }
                        /*if (!BuildFile.FLAVOR.equals(testing)) {
                            try {
                                Double.parseDouble(testing);
                            } catch (NumberFormatException e) {
                                if (!"-".equals(Character.toString(testing.charAt(start)))) {
                                    locationBGain.setText(BuildFile.FLAVOR);
                                }
                            }
                        }*/
            }

            public void afterTextChanged(Editable s) {
            }
        });
        locationBGain.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                try{
                    gainReceiver=Double.parseDouble(testing);}
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }
                        /*if (!BuildFile.FLAVOR.equals(testing)) {
                            try {
                                Double.parseDouble(testing);
                            } catch (NumberFormatException e) {
                                if (!"-".equals(Character.toString(testing.charAt(start)))) {
                                    locationBGain.setText(BuildFile.FLAVOR);
                                }
                            }
                        }*/
            }

            public void afterTextChanged(Editable s) {
            }
        });

        calculate.setOnClickListener(v -> {
            coax.attenuationLoss_dB_m(frequencyMHz);


        });

    }
}