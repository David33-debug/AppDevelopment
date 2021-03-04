package com.example.testapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testapplication.ui.FresnelCalcs;
import com.example.testapplication.ui.UnitsDistance;

import java.text.DecimalFormat;

public class fragment_fresnel extends Fragment {
    DecimalFormat df = new DecimalFormat("#.000");
    Spinner spin1,spin2;
    Button calculate;
    String item="m",item2="m",text1,text2,value1,value2;

    TextView outputText,clarityText;

    EditText distance, distance1, frequency, heightAnt1, heightAnt2;
    EditText distanceObstruct, heightObstruct;
    EditText FresnelRadius, RelativeClearance,fresnelRadiusAtObstruction,radius60,earthToClear,clearObstacle,clearObstacleFZ;
    double constant = 0.0d;
    double clearEarth;
    double fresnel_Radius=0,loss_clearance=0,fresnel_Radius_max=0,relative_clearance=0;
    double clearanceO_FZ;
    double fresnel_zone=1;
    double totalDistanceDouble,distanceFirst,distanceSecond,frequencyDouble,speedOfLight,heightFirst,heightSecond,obstacleHeight;
    double heightToClear1,heightToClear2;

    UnitsDistance units=new UnitsDistance();
    FresnelCalcs fresnel=new FresnelCalcs();

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fresnel_2, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //groupChoice=(RadioGroup)getView().findViewById(R.id.obstacleRadio);
        distance= getView().findViewById(R.id.totalDistance);
        distance1= getView().findViewById(R.id.distance1);

        outputText=getView().findViewById(R.id.textView12);
        clarityText=getView().findViewById(R.id.textView13);

        value1=getColoredSpanned("RED","#f20000");
        value2=getColoredSpanned("BLUE","#0028f2");
        clarityText.setText(Html.fromHtml("Values in "+ value1 +" refer to distances encroaching the fresnel zone, while values in " +value2+" refer to distances between the object and the fresnel zone "));

        frequency= getView().findViewById(R.id.Frequency);
        heightAnt1= getView().findViewById(R.id.heightFirstAntenna);
        heightAnt2= getView().findViewById(R.id.heightSecondAntenna);

        heightObstruct= getView().findViewById(R.id.obstructionHeight);

        FresnelRadius= getView().findViewById(R.id.freznelRadius);
        fresnelRadiusAtObstruction= getView().findViewById(R.id.fresnelRadiusAtObstruction);
        //LossClearance= getView().findViewById(R.id.LossClearance);
        //RelativeClearance= getView().findViewById(R.id.relativeClearance60);
        radius60= getView().findViewById(R.id.relativeClearance60);

        earthToClear= getView().findViewById(R.id.Clearance_earth_Obstacle);
        clearObstacle= getView().findViewById(R.id.Clearance_Obstacle);
        clearObstacleFZ= getView().findViewById(R.id.Clearance_Obstacle_fz);

        spin1=getView().findViewById(R.id.spinner10);
        spin2=getView().findViewById(R.id.spinner11);

        calculate= getView().findViewById(R.id.buttonObstacle);

        earthToClear.setEnabled(false);
        earthToClear.setTextColor(Color.BLUE);
        fresnelRadiusAtObstruction.setEnabled(false);
        fresnelRadiusAtObstruction.setTextColor(Color.BLUE);
        clearObstacleFZ.setEnabled(false);
        clearObstacleFZ.setTextColor(Color.BLUE);
        clearObstacle.setEnabled(false);
        clearObstacle.setTextColor(Color.BLUE);
        FresnelRadius.setEnabled(false);
        FresnelRadius.setTextColor(Color.BLUE);
        //LossClearance.setEnabled(false);
        //LossClearance.setTextColor(Color.BLUE);
        radius60.setEnabled(false);
        radius60.setTextColor(Color.BLUE);

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
            distanceSecond=totalDistanceDouble-distanceFirst;

            heightFirst = Double.parseDouble(heightAnt1.getText().toString());
            heightSecond = Double.parseDouble(heightAnt2.getText().toString());
            obstacleHeight = Double.parseDouble(heightObstruct.getText().toString());

            //fresnel_zone = Double.parseDouble(item);
            fresnel_Radius_max=fresnel.max_clearance(totalDistanceDouble,frequencyDouble,item,item2);

            FresnelRadius.setText(String.format("%.3f",fresnel_Radius_max));
            /////////////////////////////////////////////////////////////
            fresnel_Radius = fresnel.fresnel_radius(1,totalDistanceDouble,distanceFirst,distanceSecond,frequencyDouble,item,item2);//Math.sqrt(((((fresnel_zone * totalDistanceDouble) * totalDistanceDouble) * 0.25d) * (speedOfLight / frequencyDouble)) / totalDistanceDouble);
            if(fresnel_Radius==0.0d)
            {
                Toast.makeText(fragment_fresnel.this.getContext(),"1 and d2 must be greater than the wavelength of the signal",Toast.LENGTH_SHORT).show();
            }
            fresnelRadiusAtObstruction.setText(String.format("%.3f",fresnel_Radius));
            ///////////////////////////////////////////////////////////////
            radius60.setText(String.format("%.3f",fresnel_Radius*(0.6)));


            clearanceO_FZ=fresnel.ClearanceBetweenOandFZ(heightFirst,distanceFirst,totalDistanceDouble,heightSecond,item,item2,fresnel_Radius);
            clearanceO_FZ=clearanceO_FZ-(units.distanceConvert(units.normalise(item,obstacleHeight),item2));
            if(clearanceO_FZ<0)
            {
                clearObstacleFZ.setTextColor(Color.RED);
            }
            else
            {
                clearObstacleFZ.setTextColor(Color.BLUE);
            }
            clearObstacleFZ.setText(String.format("%.3f",clearanceO_FZ));

            //loss_clearance =fresnel.loss_clearance(heightSecond,heightFirst,distanceFirst,totalDistanceDouble,distanceSecond,1.33d,obstacleHeight,item,item2);//((((heightSecond - heightFirst) * distanceFirst) / totalDistanceDouble) + heightFirst) - (((distanceFirst / 1000.0d) * (distanceSecond / 1000.0d)) / (12.74d * constant))) - obstacleHeight;
            //LossClearance.setText(String.format("%.3f",loss_clearance));

            earthToClear.setText(String.format("%.3f",fresnel.heightOfAntToClearEarth_forSameHeightAnt(totalDistanceDouble,fresnel_Radius_max,item,item2)));

            clearEarth=fresnel.ClearanceBetweenOandFZ(heightFirst,distanceFirst,totalDistanceDouble,heightSecond,item,item2,fresnel_Radius);
            if(clearEarth<0)
            {
                clearObstacle.setTextColor(Color.RED);
            }
            else
            {
                clearObstacle.setTextColor(Color.BLUE);
            }
            clearObstacle.setText(String.format("%.3f",clearEarth));


            heightToClear1=fresnel.height1_toClear(distanceFirst,totalDistanceDouble,heightSecond,fresnel_Radius,obstacleHeight,item,item2);
            heightToClear2=fresnel.height2_toClear(distanceFirst,totalDistanceDouble,heightFirst,fresnel_Radius,obstacleHeight,item,item2);

            if(heightToClear1<0)
            {
                heightToClear1=0;
            }
            if(heightToClear2<0)
            {
                heightToClear2=0;
            }

            text1="The height of antenna1 needs to be "+ getColoredSpanned(String.format("%.3f",heightToClear1),"#0028f2") +item2+" for the fresnel zone to clear the obstacle, if the height of antenna2 is " + getColoredSpanned(String.format("%.3f",heightSecond),"#0028f2")+item+".";
            text2="The height of antenna2 needs to be "+ getColoredSpanned(String.format("%.3f",heightToClear2),"#0028f2") +item2+" for the fresnel zone to clear the obstacle, if the height of antenna1 is " + getColoredSpanned(String.format("%.3f",heightFirst),"#0028f2")+item+".";
            outputText.setText(Html.fromHtml(text1+" "+text2));


            //relative_clearance=fresnel.relative_clearance(fresnel_Radius,loss_clearance);
           // RelativeClearance.setText(String.valueOf(relative_clearance));//loss_clearance - (0.6d * fresnel_Radius)));
        });

    }
}