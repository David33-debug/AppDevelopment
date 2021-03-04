package com.example.testapplication.ui;

import java.text.DecimalFormat;

public class VSWRCalc {
    DecimalFormat df = new DecimalFormat("#.000");
    public VSWRCalc(){}
    double ReturnLoss;

    public double pow_received_at_ant(double inPower, double totalCoaxLoss)
    {
        return (inPower / Math.pow(10.0d, totalCoaxLoss / 10.0d));
    }
    public double ant_reflected_power(double vswr, double powerAntenna)
    {
        return ((Math.pow(vswr - 1.0d, 2.0d) / Math.pow(vswr + 1.0d, 2.0d)) * powerAntenna);
    }

    public double coax_loss(double loss_per_meter, double length)
    {
        return (loss_per_meter*length);
    }

    public double vswr_at_transmitter(double transmitterReflected, double inPower)
    {
        return ((Math.sqrt(transmitterReflected / inPower) + 1.0d) / (1.0d - Math.sqrt(transmitterReflected / inPower)));
    }

    public double return_loss(double VSWR)
    {
        if(VSWR==1d)
        {
            return 0.0d;
        }
        ReturnLoss=20*Math.log10((VSWR+1)/(VSWR-1));
        return ReturnLoss;//(Math.log10((VSWR - 1.0d) / (1.0d + VSWR)) * -20.0d);
    }

    public double power_transmitted_by_antenna_watts(double vswr, double powRef_watts)
    {
        return ((Math.pow(Math.sqrt(powRef_watts)*(1+vswr),2))/(Math.pow((vswr-1),2)));
    }

    public double power_lost_in_transfer_dB(double vswr)
    {
        return 10*Math.log10(1-Math.pow((vswr-1)/(vswr+1),2));
    }

    public double reflectedPower_watts(double powerIncident)
    {
        return powerIncident/(Math.pow(10,(ReturnLoss/10)));
    }

    public double mismatchLoss_dB(double vswr)
    {
        return (-1)*10*Math.log10(1-Math.pow((vswr-1)/(vswr+1),2));
    }



    /*public double power_loss_due_to_reflection_watts(double enteringPower_watts,double reflectedPower_watts)
    {
        return (0));
    }*/

    /*public double total_power_loss(double cableLoss_dB, double reflectionLoss_watts)
    {
        return (cableLoss+reflectionLoss));
    }*/


}
