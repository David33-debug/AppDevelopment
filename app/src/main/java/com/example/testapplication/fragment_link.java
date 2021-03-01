package com.example.testapplication;

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
import com.example.testapplication.ui.LinkCalcs;
import com.example.testapplication.ui.UnitsDistance;

public class fragment_link extends Fragment {
    TextView unit1,unit2;
    Button calculate;
    EditText distance, frequency, fadingMargin, transmitPower;
    EditText locationAGain, LocationATxPower, LocationAConnectorLoss, LocationACoaxCableLoss,cable1Length,cable2Length;
    EditText locationBGain, LocationBRxSensitivity, LocationBConnectorLoss, LocationBCoaxCableLoss;
    EditText TotalCoaxLoss, FreeSpacePathLoss,ERP, EstimatedReceivedSignalAtB;
    RadioButton Distance, TxPower, FadeMargin;
    RadioButton chooseCoax1,enterCoax1;
    RadioButton chooseCoax2,enterCoax2;
    RadioGroup outputCalc,radioCoax1,radioCoax2;
    Spinner distance_unit,coax1,coax2;
    String itemDistance,itemCoax1,itemCoax2;
    int calculator=0,choosing1=0,choosing2=0;

    CoaxCalcs coaxObj1= new CoaxCalcs();
    CoaxCalcs coaxObj2=new CoaxCalcs();
    UnitsDistance unitConv=new UnitsDistance();
    LinkCalcs linkB=new LinkCalcs();

    int row=0;//,column=0;

    double cableAttenuation=0,cableAttenuation1=0;
    double cableLength1=0,cableLength2=0;
    double CoaxLossTx=0,CoaxLossRx=0;

    double distanceKm=0;
    double frequencyMHz=0;
    double gainTransmitter=0;
    double gainReceiver=0;

    double TransmitPower;
    double fadeMargin;

    double RxSensitivity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_link_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unit1= getView().findViewById(R.id.textViewUnit1);
        unit2= getView().findViewById(R.id.textViewUnit2);
        unit1.setText("(m)");
        unit2.setText("(m)");

        radioCoax1= getView().findViewById(R.id.radioCoax1);
        radioCoax2= getView().findViewById(R.id.radioCoax2);
        outputCalc= getView().findViewById(R.id.radioOutput);

        chooseCoax1= getView().findViewById(R.id.CoaxChoice1);
        chooseCoax1.setChecked(true);
        enterCoax1= getView().findViewById(R.id.Title_CoaxTransmit);


        chooseCoax2= getView().findViewById(R.id.radioButton10);
        chooseCoax2.setChecked(true);
        enterCoax2= getView().findViewById(R.id.radioButton11);

        Distance= getView().findViewById(R.id.radioButton5);
        FadeMargin= getView().findViewById(R.id.radioButton6);
        TxPower= getView().findViewById(R.id.radioButton7);

        transmitPower= getView().findViewById(R.id.TxPower);
        calculate= getView().findViewById(R.id.button);
        distance= getView().findViewById(R.id.Distance);
        fadingMargin= getView().findViewById(R.id.FadingMargin);

        cable1Length= getView().findViewById(R.id.Cable1Input);
        cable2Length= getView().findViewById(R.id.Cable2Input);

        frequency= getView().findViewById(R.id.Frequency);
        locationAGain= getView().findViewById(R.id.GainTransmitter);
        LocationATxPower= getView().findViewById(R.id.TxTransmit);
        LocationAConnectorLoss= getView().findViewById(R.id.ConnectorTransmit);
        LocationACoaxCableLoss= getView().findViewById(R.id.TransmitCoax);

        locationBGain= getView().findViewById(R.id.GainReciever);
        LocationBRxSensitivity= getView().findViewById(R.id.RxReciever);
        LocationBConnectorLoss= getView().findViewById(R.id.ConnectorReciever);
        LocationBCoaxCableLoss= getView().findViewById(R.id.RecieverCoax);

        TotalCoaxLoss= getView().findViewById(R.id.TotalCoax);
        FreeSpacePathLoss= getView().findViewById(R.id.FreeSpaceLoss);
        ERP= getView().findViewById(R.id.ERPToDisplay);

        EstimatedReceivedSignalAtB= getView().findViewById(R.id.Bsignal);

        distance_unit= getView().findViewById(R.id.spinnerChoice_distance);
        coax1= getView().findViewById(R.id.spinnerChoice);
        coax2= getView().findViewById(R.id.spinnerChoice1);

        TotalCoaxLoss.setEnabled(false);
        TotalCoaxLoss.setTextColor(Color.RED);
        FreeSpacePathLoss.setEnabled(false);
        FreeSpacePathLoss.setTextColor(Color.RED);

        ERP.setEnabled(false);
        ERP.setTextColor(Color.RED);

        EstimatedReceivedSignalAtB.setEnabled(false);
        EstimatedReceivedSignalAtB.setTextColor(Color.RED);

        distance.setEnabled(false);
        transmitPower.setEnabled(true);
        fadingMargin.setEnabled(true);

        Distance.setChecked(true);
        LocationACoaxCableLoss.setEnabled(false);
        LocationBCoaxCableLoss.setEnabled(false);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(fragment_link.this.getContext() , R.array.distances_few, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distance_unit.setAdapter(adapter);

        distance_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemDistance=parent.getItemAtPosition(position).toString();
                if(itemDistance.equals("Km"))
                {
                    unit1.setText("(m)");
                    unit2.setText("(m)");
                }
                else if(itemDistance.equals("mile"))
                {
                    unit1.setText("(ft)");
                    unit2.setText("(ft)");
                }
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
                switch (itemCoax1) {
                    case "RG 174":
                        row = 0;
                        break;
                    case "RG 174_U":
                        row = 1;
                        break;
                    case "LMR 195_FR":
                        row = 2;
                        break;
                    case "HDF 195":
                        row = 3;
                        break;
                    case "LMR 200":
                        row = 4;
                        break;
                    case "LMR 400":
                        row = 5;
                        break;
                    case "RG 316":
                        row = 6;
                        break;
                    case "RG 58":
                        row = 7;
                        break;
                    case "H 155A00":
                        row = 8;
                        break;
                    case "Enviroflex 316_D":
                        row = 9;
                        break;
                    case "LEONI Dacar 302":
                        row = 10;
                        break;
                }
                cableAttenuation=coaxObj1.fetchAttenuation(row);
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
                switch (itemCoax2) {
                    case "RG 174":
                        row = 0;
                        break;
                    case "RG 174_U":
                        row = 1;
                        break;
                    case "LMR 195_FR":
                        row = 2;
                        break;
                    case "HDF 195":
                        row = 3;
                        break;
                    case "LMR 200":
                        row = 4;
                        break;
                    case "LMR 400":
                        row = 5;
                        break;
                    case "RG 316":
                        row = 6;
                        break;
                    case "RG 58":
                        row = 7;
                        break;
                    case "H 155A00":
                        row = 8;
                        break;
                    case "Enviroflex 316_D":
                        row = 9;
                        break;
                    case "LEONI Dacar 302":
                        row = 10;
                        break;
                }
                cableAttenuation1=coaxObj2.fetchAttenuation(row);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        LocationACoaxCableLoss.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newString = s.toString();
                try {
                    CoaxLossTx=Double.parseDouble(newString);
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
                    CoaxLossRx=Double.parseDouble(newString);
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
                cableAttenuation=coaxObj1.attenuationLoss_dB_m(frequencyMHz);
                cableAttenuation1=coaxObj2.attenuationLoss_dB_m(frequencyMHz);


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

            }

            public void afterTextChanged(Editable s) {
            }
        });
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

            }

            public void afterTextChanged(Editable s) {
            }
        });

        LocationATxPower.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                try {
                    TransmitPower=Double.parseDouble(testing);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }

            }

            public void afterTextChanged(Editable s) {
            }
        });

        LocationBRxSensitivity.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                try {
                    RxSensitivity=Double.parseDouble(testing);
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(fragment_link.this.getContext(),"Need a number",Toast.LENGTH_SHORT).show();
                }
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
            }

            public void afterTextChanged(Editable s) {
            }
        });

        radioCoax1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.CoaxChoice1)
                {
                    choosing1=0;
                    cable1Length.setEnabled(true);
                    LocationACoaxCableLoss.setEnabled(false);
                }
                else if(checkedId==R.id.Title_CoaxTransmit)
                {
                    choosing1=1;
                    LocationACoaxCableLoss.setEnabled(true);
                    cable1Length.setEnabled(false);
                }
            }
        });
        radioCoax2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radioButton10)
                {
                    choosing2=0;
                    cable2Length.setEnabled(true);
                    LocationBCoaxCableLoss.setEnabled(false);
                }
                else if(checkedId==R.id.radioButton11)
                {
                    choosing2=1;
                    LocationBCoaxCableLoss.setEnabled(true);
                    cable2Length.setEnabled(false);
                }
            }
        });

        outputCalc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radioButton5)
                {
                    calculator=0;
                    distance.setEnabled(false);
                    transmitPower.setEnabled(true);
                    fadingMargin.setEnabled(true);
                }
                else if(checkedId==R.id.radioButton6)
                {
                    calculator=1;
                    transmitPower.setEnabled(false);
                    distance.setEnabled(true);
                    fadingMargin.setEnabled(true);
                    LocationATxPower.setEnabled(false);
                }
                else if(checkedId==R.id.radioButton7)
                {
                    calculator=2;
                    transmitPower.setEnabled(true);
                    distance.setEnabled(true);
                    fadingMargin.setEnabled(false);
                }
            }
        });

        calculate.setOnClickListener(v -> {
            if(choosing1==0)
            {
                cableLength1=Double.parseDouble(cable1Length.getText().toString());
                if(itemDistance.equals("mile"))
                    cableLength1=unitConv.ft_meter(cableLength1);
                CoaxLossTx=cableAttenuation*cableLength1;//coaxObj1.attenuationLoss_dB_m(frequencyMHz)*cableLength1;
                LocationACoaxCableLoss.setText(String.format("%.3f",CoaxLossTx));
            }
            if(choosing2==0)
            {
                cableLength2=Double.parseDouble(cable2Length.getText().toString());
                if(itemDistance.equals("mile"))
                    cableLength2=unitConv.ft_meter(cableLength2);
                CoaxLossRx=cableAttenuation1*cableLength2;//coaxObj2.attenuationLoss_dB_m(frequencyMHz)*cableLength2;
                LocationBCoaxCableLoss.setText(String.format("%.3f",CoaxLossRx));
            }
            switch (calculator)
            {
                case 0:
                    TransmitPower=Double.parseDouble(transmitPower.getText().toString());//linkB.TxPower(itemDistance,distanceKm,frequencyMHz,RxSensitivity,fadeMargin,CoaxLossTx,gainTransmitter,CoaxLossRx,gainReceiver);
                    fadeMargin=Double.parseDouble(fadingMargin.getText().toString());
                    distanceKm=linkB.distanceCalc(frequencyMHz,fadeMargin,RxSensitivity,TransmitPower,CoaxLossTx,gainTransmitter,CoaxLossRx,gainReceiver);
                    distance.setText(String.format("%.3f",distanceKm));
                    //transmitPower.setText(String.valueOf(TransmitPower));
                    //fadingMargin.setText(String.valueOf(linkB.fadeMarginCalc(itemDistance,distanceKm,frequencyMHz,gainReceiver,CoaxLossRx,TransmitPower,gainTransmitter,CoaxLossTx,RxSensitivity)));
                    break;
                case 1:
                    distanceKm=Double.parseDouble(distance.getText().toString());
                    fadeMargin=Double.parseDouble(fadingMargin.getText().toString());
                    TransmitPower=linkB.TxPower(itemDistance,distanceKm,frequencyMHz,RxSensitivity,fadeMargin,CoaxLossTx,gainTransmitter,CoaxLossRx,gainReceiver);
                    transmitPower.setText(String.format("%.3f",TransmitPower));
                    //distance.setText(String.valueOf(linkB.distanceCalc(frequencyMHz,fadeMargin,RxSensitivity,TransmitPower,CoaxLossTx,gainTransmitter,CoaxLossRx,gainReceiver)));
                    //fadingMargin.setText(String.valueOf(linkB.fadeMarginCalc(itemDistance,distanceKm,frequencyMHz,gainReceiver,CoaxLossRx,TransmitPower,gainTransmitter,CoaxLossTx,RxSensitivity)));
                    break;
                case 2:
                    distanceKm=Double.parseDouble(distance.getText().toString());
                    TransmitPower=Double.parseDouble(transmitPower.getText().toString());
                    fadeMargin=linkB.fadeMarginCalc(itemDistance,distanceKm,frequencyMHz,gainReceiver,CoaxLossRx,TransmitPower,gainTransmitter,CoaxLossTx,RxSensitivity);
                    fadingMargin.setText(String.format("%.3f",fadeMargin));
                    //TransmitPower=linkB.TxPower(itemDistance,distanceKm,frequencyMHz,RxSensitivity,fadeMargin,CoaxLossTx,gainTransmitter,CoaxLossRx,gainReceiver);
                    //transmitPower.setText(String.valueOf(TransmitPower));
                    //distance.setText(String.valueOf(linkB.distanceCalc(frequencyMHz,fadeMargin,RxSensitivity,TransmitPower,CoaxLossTx,gainTransmitter,CoaxLossRx,gainReceiver)));
                    break;
            }
            ERP.setText(String.valueOf(linkB.erp(TransmitPower,gainTransmitter,CoaxLossTx)));

            TotalCoaxLoss.setText(String.format("%.3f",CoaxLossRx+CoaxLossTx));
            FreeSpacePathLoss.setText(String.format("%.3f",linkB.freeSpaceLoss(itemDistance,distanceKm,frequencyMHz)));
            EstimatedReceivedSignalAtB.setText(String.format("%.3f",linkB.RxReceiveStrength(itemDistance,distanceKm,frequencyMHz,gainReceiver,CoaxLossRx,TransmitPower,gainTransmitter,CoaxLossTx)));

        });

    }
}