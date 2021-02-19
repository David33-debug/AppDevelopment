package com.example.testapplication.ui.design;

import android.graphics.Color;
import android.os.Bundle;
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

import java.text.DecimalFormat;

import Logic.FresnelCalcs;
import Logic.UnitsDistance;

public class fragment_fresnel extends Fragment {
    DecimalFormat df = new DecimalFormat("#.000");
    Spinner spin1,spin2;
    Button calculate;
    String item="m",item2="m";

    EditText distance, distance1,distance2, frequency, heightAnt1, heightAnt2;
    EditText distanceObstruct, heightObstruct;
    EditText FresnelRadius, LossClearance, RelativeClearance,fresnelRadiusAtObstruction,radius60,earthToClear,clearObstacle,clearObstacleFZ;
    double constant = 0.0d;
    double radius;
    double fresnel_Radius=0,loss_clearance=0,fresnel_Radius_max=0,relative_clearance=0;
    double fresnel_zone=1;
    double totalDistanceDouble,distanceFirst,distanceSecond,frequencyDouble,speedOfLight,heightFirst,heightSecond,obstacleHeight;

    UnitsDistance units=new UnitsDistance();
    FresnelCalcs fresnel=new FresnelCalcs();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fresnel_2, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //groupChoice=(RadioGroup)getView().findViewById(R.id.obstacleRadio);
        distance=(EditText) getView().findViewById(R.id.totalDistance);
        distance1=(EditText) getView().findViewById(R.id.distance1);
        distance2=(EditText) getView().findViewById(R.id.distance2);

        frequency=(EditText)getView().findViewById(R.id.Frequency);
        heightAnt1=(EditText)getView().findViewById(R.id.heightFirstAntenna);
        heightAnt2=(EditText)getView().findViewById(R.id.heightSecondAntenna);

        heightObstruct=(EditText)getView().findViewById(R.id.obstructionHeight);

        FresnelRadius=(EditText)getView().findViewById(R.id.freznelRadius);
        fresnelRadiusAtObstruction=(EditText) getView().findViewById(R.id.fresnelRadiusAtObstruction);
        LossClearance=(EditText)getView().findViewById(R.id.LossClearance);
        RelativeClearance=(EditText)getView().findViewById(R.id.relativeClearance60);
        radius60=(EditText)getView().findViewById(R.id.relativeClearance60);

        earthToClear=(EditText)getView().findViewById(R.id.Clearance_earth_Obstacle);
        clearObstacle=(EditText)getView().findViewById(R.id.Clearance_Obstacle);
        clearObstacleFZ=(EditText)getView().findViewById(R.id.Clearance_Obstacle_fz);

        spin1=getView().findViewById(R.id.spinner10);
        spin2=getView().findViewById(R.id.spinner11);

        calculate=(Button) getView().findViewById(R.id.buttonObstacle);

        earthToClear.setEnabled(false);
        earthToClear.setTextColor(Color.RED);
        fresnelRadiusAtObstruction.setEnabled(false);
        fresnelRadiusAtObstruction.setTextColor(Color.RED);
        clearObstacleFZ.setEnabled(false);
        clearObstacleFZ.setTextColor(Color.RED);
        clearObstacle.setEnabled(false);
        clearObstacle.setTextColor(Color.RED);
        FresnelRadius.setEnabled(false);
        FresnelRadius.setTextColor(Color.RED);
        LossClearance.setEnabled(false);
        LossClearance.setTextColor(Color.RED);
        RelativeClearance.setEnabled(false);
        RelativeClearance.setTextColor(Color.RED);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(fragment_fresnel.this.getContext() , R.array.distances, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(fragment_fresnel.this.getContext() , R.array.distances, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapter2);

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item2=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calculate.setOnClickListener(v -> {

            totalDistanceDouble = Double.parseDouble(distance.getText().toString());
            frequencyDouble = Double.parseDouble(frequency.getText().toString());
            distanceFirst=Double.parseDouble(distance1.getText().toString());
            distanceSecond=Double.parseDouble(distance2.getText().toString());

            //fresnel_zone = Double.parseDouble(item);
            fresnel_Radius_max=fresnel.max_clearance(totalDistanceDouble,frequencyDouble,item,item2);

            FresnelRadius.setText(String.valueOf(fresnel_Radius_max));
            /////////////////////////////////////////////////////////////
            fresnel_Radius = fresnel.fresnel_radius(1,totalDistanceDouble,distanceFirst,distanceSecond,frequencyDouble,item,item2);//Math.sqrt(((((fresnel_zone * totalDistanceDouble) * totalDistanceDouble) * 0.25d) * (speedOfLight / frequencyDouble)) / totalDistanceDouble);
            if(fresnel_Radius==0.0d)
            {
                Toast.makeText(fragment_fresnel.this.getContext(),"1 and d2 must be greater than the wavelength of the signal",Toast.LENGTH_SHORT).show();
            }
            fresnelRadiusAtObstruction.setText(String.valueOf(fresnel_Radius));
            ///////////////////////////////////////////////////////////////
            radius60.setText(String.valueOf(Double.parseDouble(df.format(fresnel_Radius*(0.6)))));

            heightFirst = Double.parseDouble(heightAnt1.getText().toString());
            heightSecond = Double.parseDouble(heightAnt2.getText().toString());
            obstacleHeight = Double.parseDouble(heightObstruct.getText().toString());

            loss_clearance =fresnel.loss_clearance(heightSecond,heightFirst,distanceFirst,totalDistanceDouble,distanceSecond,1.33d,obstacleHeight,item,item2);//((((heightSecond - heightFirst) * distanceFirst) / totalDistanceDouble) + heightFirst) - (((distanceFirst / 1000.0d) * (distanceSecond / 1000.0d)) / (12.74d * constant))) - obstacleHeight;
            LossClearance.setText(String.valueOf(loss_clearance));

            earthToClear.setText(String.valueOf(fresnel.heightOfAntToClearEarth_forSameHeightAnt(totalDistanceDouble,fresnel_Radius_max,item,item2)));
            clearObstacle.setText(String.valueOf(fresnel.ClearanceBetweenOandFZ(heightFirst,distanceFirst,totalDistanceDouble,heightSecond,item,item2,fresnel_Radius)));
            clearObstacleFZ.setText(String.valueOf(Double.parseDouble(df.format(fresnel.ClearanceBetweenOandFZ(heightFirst,distanceFirst,totalDistanceDouble,heightSecond,item,item2,fresnel_Radius)-obstacleHeight))));

            //relative_clearance=fresnel.relative_clearance(fresnel_Radius,loss_clearance);
           // RelativeClearance.setText(String.valueOf(relative_clearance));//loss_clearance - (0.6d * fresnel_Radius)));
        });

    }
}