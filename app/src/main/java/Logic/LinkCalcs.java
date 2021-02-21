package Logic;

import java.text.DecimalFormat;

public class LinkCalcs {

    DecimalFormat df = new DecimalFormat("#.000");
    UnitsDistance unitConvert=new UnitsDistance();
    double distanceConst=32.5d;
    double num1,num2,num3,exp;

    public double freeSpaceLoss(double distance, double frequency)
    {
        return Double.parseDouble(df.format(32.5d+(20*Math.log10(distance)+(20*Math.log10(frequency)))));
    }
    private double erp(double transmitPower, double TxGain, double TxcableLoss)
    {
        return Double.parseDouble(df.format(transmitPower+TxGain-TxcableLoss));
    }
    public double RxReceiveStrength(double distance, double frequency,double RxGain,double RxCableLoss,double transmitPower, double TxGain, double TxcableLoss )
    {
        return Double.parseDouble(df.format(erp(transmitPower,TxGain,TxcableLoss)-freeSpaceLoss(distance,frequency)+RxGain-RxCableLoss));
    }

    public double TxPower(double distance, double frequency, double RxSensitivity, double fadeMargin, double TxCableLoss, double TxGain, double RxCableLoss, double RxGain)
    {
        num1=fadeMargin+RxSensitivity;
        num2=num1+freeSpaceLoss(distance,frequency)-RxGain+RxCableLoss;
        return Double.parseDouble(df.format(num2+TxCableLoss-TxGain));
    }
    public double distanceCalc(double frequency, double fadeMargin, double RxSensitivity, double TxPower, double TxCableLoss, double TxGain, double RxCableLoss, double RxGain)
    {
        num1=fadeMargin+RxSensitivity;
        num2=TxPower-TxCableLoss+TxGain;
        num3=(num2-num1+RxGain-RxCableLoss);
        exp=(num3-distanceConst-(20*Math.log10(frequency)))/20;
        return Double.parseDouble(df.format(Math.pow(10,exp)));
    }
    public double fadeMarginCalc(double distance, double frequency,double RxGain,double RxCableLoss,double transmitPower, double TxGain, double TxcableLoss,double RxSensitivity)
    {
        return Double.parseDouble(df.format(RxReceiveStrength(distance,frequency,RxGain, RxCableLoss,transmitPower, TxGain, TxcableLoss)-RxSensitivity));
    }

    private double distanceConvert(double distance1)
    {
        return 0;
    }

}
