package Logic;

import java.text.DecimalFormat;

public class LinkCalcs {

    DecimalFormat df = new DecimalFormat("#.000");
    UnitsDistance unitConvert=new UnitsDistance();
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
        return Double.parseDouble(df.format(32.5d+(20*Math.log10(distance)+(20*Math.log10(frequency)))));
    }
    public double erp(double transmitPower, double TxGain, double TxcableLoss)
    {
        return Double.parseDouble(df.format(transmitPower+TxGain-TxcableLoss));
    }
    public double RxReceiveStrength(String input, double distance, double frequency,double RxGain,double RxCableLoss,double transmitPower, double TxGain, double TxcableLoss )
    {
        //return Double.parseDouble(df.format(TxPower+TxGain+RxGain-distanceConst-(20*Math.log10(distance))-(20*Math.log10(frequency))));
        return Double.parseDouble(df.format(erp(transmitPower,TxGain,TxcableLoss)-freeSpaceLoss(input, distance,frequency)+RxGain-RxCableLoss));
    }

    public double TxPower(String input,double distance, double frequency, double RxSensitivity, double fadeMargin, double TxCableLoss, double TxGain, double RxCableLoss, double RxGain)
    {
        num1=fadeMargin+RxSensitivity;
        num2=num1+freeSpaceLoss(input,distance,frequency)-RxGain+RxCableLoss;
        return Double.parseDouble(df.format(num2+TxCableLoss-TxGain));
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
        return Double.parseDouble(df.format(Math.pow(10,exp)));
    }
    public double fadeMarginCalc(String input, double distance, double frequency,double RxGain,double RxCableLoss,double transmitPower, double TxGain, double TxcableLoss,double RxSensitivity)
    {
        return Double.parseDouble(df.format(RxReceiveStrength(input,distance,frequency,RxGain, RxCableLoss,transmitPower, TxGain, TxcableLoss)-RxSensitivity));
    }

    private double distanceConvert(double value, String output)
    {
        return unitConvert.distanceConvert(value,output);
    }

}
