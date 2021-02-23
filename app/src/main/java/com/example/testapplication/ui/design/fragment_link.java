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
import Logic.LinkCalcs;

import static java.lang.Math.log10;

public class fragment_link extends Fragment {
    TextView unit1,unit2;
    Button calculate;
    EditText distance, frequency, fadingMargin, transmitPower;
    EditText locationAGain, LocationATxPower, LocationAConnectorLoss, LocationACoaxCableLoss,cable1Length,cable2Length;
    EditText locationBGain, LocationBRxSensitivity, LocationBConnectorLoss, LocationBCoaxCableLoss;
    EditText TotalCoaxLoss, FreeSpacePathLoss,ERP,MaxPermissibleCoaxLoss, MaxPermissibleFreeSpacePathLoss, EstimatedReceivedSignalAtB;
    RadioButton Distance, TxPower, FadeMargin;
    RadioGroup outputCalc;
    Spinner distance_unit,coax1,coax2;
    String itemDistance,itemCoax1,itemCoax2;
    int calculator=0;

    CoaxCalcs coax= new CoaxCalcs();
    LinkCalcs linkB=new LinkCalcs();
    int row=0,column=0;

    double cableAttenuation=0;
    double cableLength1,cableLength2;
    double CoaxLossTx,CoaxLossRx;


    double powerReceived,powerReceived1;
    double FreeSpaceLoss;
    double distanceKm=0;
    double frequencyMHz=0;
    double gainTransmitter=0;
    double gainReceiver=0;
    double powerTransmitted=0;

    double TransmitPower;
    double fadeMargin;

    double transmitGainDouble;
    double recieverGainDouble;
    double RxSensitivity;
    double txTransmitDouble;
    double totalCoaxDouble;
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
        unit1=(TextView)getView().findViewById(R.id.textViewUnit1);
        unit2=(TextView)getView().findViewById(R.id.textViewUnit2);
        unit1.setText("(m)");
        unit2.setText("(m)");

        outputCalc=(RadioGroup) getView().findViewById(R.id.radioOutput);

        Distance=(RadioButton) getView().findViewById(R.id.radioButton5);
        FadeMargin=(RadioButton) getView().findViewById(R.id.radioButton6);
        TxPower=(RadioButton) getView().findViewById(R.id.radioButton7);

        transmitPower=(EditText) getView().findViewById(R.id.TxPower);
        calculate=(Button) getView().findViewById(R.id.button);
        distance=(EditText) getView().findViewById(R.id.Distance);
        fadingMargin=(EditText) getView().findViewById(R.id.FadingMargin);

        cable1Length=(EditText) getView().findViewById(R.id.Cable1Input);
        cable2Length=(EditText) getView().findViewById(R.id.Cable2Input);


        frequency=(EditText) getView().findViewById(R.id.Frequency);
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
        ERP=(EditText) getView().findViewById(R.id.ERPToDisplay);
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

        ERP.setEnabled(false);
        ERP.setTextColor(Color.RED);
        MaxPermissibleCoaxLoss.setEnabled(false);
        MaxPermissibleCoaxLoss.setTextColor(Color.RED);
        MaxPermissibleFreeSpacePathLoss.setEnabled(false);
        MaxPermissibleFreeSpacePathLoss.setTextColor(Color.RED);
        EstimatedReceivedSignalAtB.setEnabled(false);
        EstimatedReceivedSignalAtB.setTextColor(Color.RED);

        transmitPower.setEnabled(false);
        fadingMargin.setEnabled(false);

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

        outputCalc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radioButton5)
                {
                    calculator=0;
                    distance.setEnabled(true);
                    transmitPower.setEnabled(false);
                    fadingMargin.setEnabled(false);
                }
                else if(checkedId==R.id.radioButton6)
                {
                    calculator=1;
                    transmitPower.setEnabled(true);
                    distance.setEnabled(false);
                    fadingMargin.setEnabled(false);
                }
                else if(checkedId==R.id.radioButton7)
                {
                    calculator=2;
                    transmitPower.setEnabled(false);
                    distance.setEnabled(false);
                    fadingMargin.setEnabled(true);
                }
            }
        });

        calculate.setOnClickListener(v -> {
            coax.attenuationLoss_dB_m(frequencyMHz);
            switch (calculator)
            {
                case 0:
                    TransmitPower=linkB.TxPower(itemDistance,distanceKm,frequencyMHz,RxSensitivity,fadeMargin,CoaxLossTx,gainTransmitter,CoaxLossRx,gainReceiver);
                    transmitPower.setText(String.valueOf(TransmitPower));
                    fadingMargin.setText(String.valueOf(linkB.fadeMarginCalc(itemDistance,distanceKm,frequencyMHz,gainReceiver,CoaxLossRx,TransmitPower,gainTransmitter,CoaxLossTx,RxSensitivity)));
                    break;
                case 1:
                    distance.setText(String.valueOf(linkB.distanceCalc(frequencyMHz,fadeMargin,RxSensitivity,TransmitPower,CoaxLossTx,gainTransmitter,CoaxLossRx,gainReceiver)));
                    fadingMargin.setText(String.valueOf(linkB.fadeMarginCalc(itemDistance,distanceKm,frequencyMHz,gainReceiver,CoaxLossRx,TransmitPower,gainTransmitter,CoaxLossTx,RxSensitivity)));
                    break;
                case 2:
                    TransmitPower=linkB.TxPower(itemDistance,distanceKm,frequencyMHz,RxSensitivity,fadeMargin,CoaxLossTx,gainTransmitter,CoaxLossRx,gainReceiver);
                    transmitPower.setText(String.valueOf(TransmitPower));
                    distance.setText(String.valueOf(linkB.distanceCalc(frequencyMHz,fadeMargin,RxSensitivity,TransmitPower,CoaxLossTx,gainTransmitter,CoaxLossRx,gainReceiver)));
                    break;
            }
            ERP.setText(String.valueOf(linkB.erp(TransmitPower,gainTransmitter,CoaxLossTx)));
            /*checking1 = txRecieveDouble - rxTransmitDouble;

            TotalCoaxLoss.setText(String.valueOf(CoaxLossRx+CoaxLossTx));
            FreeSpacePathLoss.setText(String.valueOf(linkB.freeSpaceLoss(itemDistance,distanceKm,frequencyMHz)));

            MinAntennaAGain.setText(freeSpaceDouble - ((((((checking1 - totalCoaxDouble) - recieveConnectorDouble) - transmitConnectorDouble) + ancillaryDouble) - fadingDouble) + recieverGainDouble);

            MaxPermissibleCoaxLoss.setText(answerDoubleCoax = (((((((checking1 - recieveConnectorDouble) - recieverDouble) - transmitConnectorDouble) + ancillaryDouble) - fadingDouble) + recieverGainDouble) + transmitGainDouble) - freeSpaceDouble);
            MaxPermissibleFreeSpacePathLoss.setText((((((checking1 - totalCoaxDouble) - recieveConnectorDouble) - transmitConnectorDouble) + ancillaryDouble) - fadingDouble) + transmitGainDouble + recieverGainDouble;

            EstimatedReceivedSignalAtB.setText("");*/



        });

    }
}