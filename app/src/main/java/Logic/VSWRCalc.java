package Logic;

import java.text.DecimalFormat;

public class VSWRCalc {
    DecimalFormat df = new DecimalFormat("#.000");
    public VSWRCalc(){}

    public double pow_received_at_ant(double inPower, double totalCoaxLoss)
    {
        return Double.parseDouble(df.format(inPower / Math.pow(10.0d, totalCoaxLoss / 10.0d)));
    }
    public double ant_reflected_power(double vswr, double powerAntenna)
    {
        return Double.parseDouble(df.format((Math.pow(vswr - 1.0d, 2.0d) / Math.pow(vswr + 1.0d, 2.0d)) * powerAntenna));
    }

    public double coax_loss(double loss_per_meter, double length)
    {
        return Double.parseDouble(df.format(loss_per_meter*length));
    }

    public double vswr_at_transmitter(double transmitterReflected, double inPower)
    {
        return Double.parseDouble(df.format((Math.sqrt(transmitterReflected / inPower) + 1.0d) / (1.0d - Math.sqrt(transmitterReflected / inPower))));
    }

    public double return_loss(double VSWR)
    {
        if(VSWR==1d)
        {
            return 0.0d;
        }
        return Double.parseDouble(df.format(Math.log10((VSWR - 1.0d) / (1.0d + VSWR)) * -20.0d));
    }

    public double power_transmitted_by_antenna_watts(double vswr, double powRef_watts)
    {
        return Double.parseDouble(df.format((Math.pow(Math.sqrt(powRef_watts)*(1+vswr),2))/(Math.pow((vswr-1),2))));
    }

    /*public double power_loss_due_to_reflection_watts(double enteringPower_watts,double reflectedPower_watts)
    {
        return Double.parseDouble(df.format(0));
    }*/

    /*public double total_power_loss(double cableLoss_dB, double reflectionLoss_watts)
    {
        return Double.parseDouble(df.format(cableLoss+reflectionLoss));
    }*/


}
