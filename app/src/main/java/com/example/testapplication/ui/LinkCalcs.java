package com.example.testapplication.ui;

import com.example.testapplication.ui.UnitsDistance;

import java.text.DecimalFormat;

public class LinkCalcs {

    DecimalFormat df = new DecimalFormat("#.000");
    UnitsDistance unitConvert=new UnitsDistance();
    PowCalculations powCalc= new PowCalculations();
    double distanceConstKm=32.45d;
    double distanceConstMile=36.6d;
    double num1,num2,num3,exp;

    public double freeSpaceLoss(String input, double distance, double frequency)
    {
        if(frequency==0 || distance==0)
        {
            return 0.0d;
        }
        distance=unitConvert.normalise(input,distance);
        return (32.5d+(20*Math.log10(distance/1000)+(20*Math.log10(frequency))));
    }
    public double erp(double transmitPower, double TxGain, double TxcableLoss)
    {
        return powCalc.dBm_dBW(transmitPower+TxGain-TxcableLoss);
    }
    public double RxReceiveStrength(String input, double distance, double frequency,double RxGain,double RxCableLoss,double transmitPower, double TxGain, double TxcableLoss )
    {
        //return (TxPower+TxGain+RxGain-distanceConst-(20*Math.log10(distance))-(20*Math.log10(frequency))));
        return (transmitPower+TxGain-TxcableLoss)-freeSpaceLoss(input, distance,frequency)+RxGain-RxCableLoss;
    }

    public double TxPower(String input,double distance, double frequency, double RxSensitivity, double fadeMargin, double TxCableLoss, double TxGain, double RxCableLoss, double RxGain)
    {
        num1=fadeMargin+RxSensitivity;
        num2=num1+freeSpaceLoss(input,distance,frequency)-RxGain+RxCableLoss;
        return (num2+TxCableLoss-TxGain);
    }
    public double distanceCalc(double frequency, double fadeMargin, double RxSensitivity, double TxPower, double TxCableLoss, double TxGain, double RxCableLoss, double RxGain)
    {
        if(frequency==0)
        {
            return 0.0d;
        }
        num1=fadeMargin+RxSensitivity;
        num2=TxPower-TxCableLoss+TxGain;
        num3=(num2-num1+RxGain-RxCableLoss);
        exp=(num3-distanceConstKm-(20*Math.log10(frequency)))/20;
        return (Math.pow(10,exp));
    }
    public double fadeMarginCalc(String input, double distance, double frequency,double RxGain,double RxCableLoss,double transmitPower, double TxGain, double TxcableLoss,double RxSensitivity)
    {
        return (RxReceiveStrength(input,distance,frequency,RxGain, RxCableLoss,transmitPower, TxGain, TxcableLoss)-RxSensitivity);
    }

    private double distanceConvert(double value, String output)
    {
        return unitConvert.distanceConvert(value,output);
    }

    public double ERPdB_EIRPdB(double ERPdB)
    {
        return ERPdB+2.15d;
    }
    public double ERPW_EIRPW(double ERPW)
    {
        return ERPW*1.64d;
    }
    public double EIRPdB_ERPdB(double EIRPdB)
    {
        return EIRPdB-2.15d;
    }
    public double EIRPW_ERPW(double EIRPW)
    {
        return EIRPW/1.64d;
    }
    public double dBi_dBd(double dBi)
    {
        return dBi-2.16d;
    }
    public double dBd_dBi(double dBd)
    {
        return dBd+2.16;
    }

}
