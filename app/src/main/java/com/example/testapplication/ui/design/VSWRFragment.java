package com.example.testapplication.ui.design;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.R;

import Logic.VSWRCalc;

public class VSWRFragment extends Fragment {


    EditText vswrIn, cableLossPerMeter, cableLength, inputPower;
    EditText vswrOut, returnLoss, feederLoss, powerAtAntenna, reflectedPower, reflectedPowerAtTransmitter;
    Button calculate;

    double totalCoaxLoss;
    double powerAntenna;
    double antennaREflected;
    double transmitterReflected;
    double finalVSWRDouble;
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
        getActivity().setTitle("VSWR");
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vswrIn = (EditText) getView().findViewById(R.id.AntennaVSWR);
        cableLossPerMeter = (EditText) getView().findViewById(R.id.meterLoss);
        cableLength = (EditText) getView().findViewById(R.id.lengthCoax);
        inputPower = (EditText) getView().findViewById(R.id.inputPower);

        vswrOut = (EditText) getView().findViewById(R.id.outputVSWR);
        returnLoss = (EditText) getView().findViewById(R.id.returnLoss);
        feederLoss = (EditText) getView().findViewById(R.id.totalCoax);
        powerAtAntenna = (EditText) getView().findViewById(R.id.PowerAtAntenna);
        reflectedPower = (EditText) getView().findViewById(R.id.reflectedPower);

        vswrIn.setText(BuildFile.FLAVOR);
        cableLossPerMeter.setText(BuildFile.FLAVOR);
        cableLength.setText(BuildFile.FLAVOR);
        inputPower.setText(BuildFile.FLAVOR);

        calculate = (Button) getView().findViewById(R.id.buttonVSWR);

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

        cableLength.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                if (!BuildFile.FLAVOR.equals(testing)) {
                    try {
                        Double.parseDouble(testing);
                    } catch (NumberFormatException e) {
                        cableLength.setText(BuildFile.FLAVOR);
                    }
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });

        cableLossPerMeter.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String testing = s.toString();
                if (!BuildFile.FLAVOR.equals(testing)) {
                    try {
                        Double.parseDouble(testing);
                    } catch (NumberFormatException e) {
                        cableLossPerMeter.setText(BuildFile.FLAVOR);
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

                if (!isNum(vswrIn.getText().toString())) {
                    Toast.makeText(VSWRFragment.this.getContext(),"Please enter the antenna VSWR",Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isNum(cableLength.getText().toString())) {
                    Toast.makeText(VSWRFragment.this.getContext(), "Please enter the length of the coax cable", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!isNum(cableLossPerMeter.getText().toString())) {
                    Toast.makeText(VSWRFragment.this.getContext(), "Please enter the loss per meter for the coax cable", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isNum(inputPower.getText().toString())) {
                    Toast.makeText(VSWRFragment.this.getContext(), "Please enter the forward power of the system", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    cable=Double.parseDouble(cableLength.getText().toString());
                    coaxLoss=Double.parseDouble(cableLossPerMeter.getText().toString());
                    inPower=Double.parseDouble(inputPower.getText().toString());
                    vswr=Double.parseDouble(vswrIn.getText().toString());

                    //using OOP
                    totalCoaxLoss = vswrCalcs.coax_loss(coaxLoss,cable);

                    //Using OOP Class
                    powerAntenna=vswrCalcs.pow_received_at_ant(inPower,totalCoaxLoss);
                    powerAtAntenna.setText(String.valueOf(powerAntenna));

                    feederLoss.setText(String.valueOf(totalCoaxLoss));

                    //using OOP
                    antennaREflected = vswrCalcs.ant_reflected_power(vswr,powerAntenna);

                    reflectedPower.setText(String.valueOf(antennaREflected));

                    //using OOP
                    powerTransmittedByAnt=vswrCalcs.power_transmitted_by_antenna_watts(vswr,antennaREflected);
                    //to be recalculated;
                    // totalLoss=vswrCalcs.total_power_loss(coaxLoss,antennaREflected);

                    transmitterReflected = antennaREflected / Math.pow(10.0d, totalCoaxLoss / 10.0d);

                    finalVSWRDouble = vswrCalcs.vswr_at_transmitter(transmitterReflected,inPower);

                    vswrOut.setText(String.valueOf(finalVSWRDouble));

                    returnLoss.setText(String.valueOf(vswrCalcs.return_loss(finalVSWRDouble)));
                }
            }
        });

    }
}