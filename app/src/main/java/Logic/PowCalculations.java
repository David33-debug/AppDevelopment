package Logic;

import java.text.DecimalFormat;

public class PowCalculations {
    private double dBmV,dBW,microVolt,Wt,dBm;
    DecimalFormat df = new DecimalFormat("#.000");
    public PowCalculations(){
        dBm=0.0;
        dBW=0.0;
        microVolt=0.0;
        Wt=0.0;
        dBmV=0.0;
    }

    public double watts_dBmV(double Wt){
        if(Wt==0)
            return 0d;
        return Double.parseDouble(df.format(((Math.log10(Wt) * 10.0d) + 30.0d)+ 46.9897d));
    }
    public double watts_dBW(double Wt){
        if(Wt==0)
            return 0d;
        return Double.parseDouble(df.format(Math.log10(Wt) * 10.0d));
    }
    public double watts_microVolt(double Wt){
        if(Wt==0)
            return 0d;
        return Double.parseDouble(df.format(Math.sqrt(Math.pow(10.0d, ((Math.log10(Wt) * 10.0d) + 30.0d) / 10.0d) * 0.05d))); }
    public double watts_dBm(double Wt){
        if(Wt==0)
            return 0d;
        return Double.parseDouble(df.format((Math.log10(Wt) * 10.0d) + 30.0d));
    }

    public double dBW_dBmV(double dBW){
        if(Math.pow(10.0d, dBW / 10.0d) == 0)
        {
            return 0.0d;
        }
        return Double.parseDouble(df.format(46.9897d + ((Math.log10(Math.pow(10.0d, dBW / 10.0d)) * 10.0d) + 30.0d)));
    }
    public double dBW_microVolt(double dBW){
        return Double.parseDouble(df.format(Math.sqrt(Math.pow(10.0d, ((Math.log10(Math.pow(10.0d, dBW / 10.0d)) * 10.0d) + 30.0d) / 10.0d) * 0.05d)));
    }
    public double dBW_watt(double dBW){
        return Double.parseDouble(df.format(Math.pow(10.0d, dBW / 10.0d)));
    }
    public double dBW_dBm(double dBW){
        return Double.parseDouble(df.format((Math.log10(Math.pow(10.0d, dBW / 10.0d)) * 10.0d) + 30.0d));
    }

    public double microVolt_dBmV(double uVolt){
        return Double.parseDouble(df.format(46.9897d + (Math.log10((uVolt * uVolt) / 0.05d) * 10.0d)));
    }
    public double microVolt_dBW(double uVolt){
        return Double.parseDouble(df.format(Math.log10(Math.pow(10.0d, (Math.log10((uVolt * uVolt) / 0.05d) * 10.0d) / 10.0d) / 1000.0d) * 10.0d));
    }
    public double microVolt_watt(double uVolt){
        return Double.parseDouble(df.format(Math.pow(10.0d, (Math.log10((uVolt * uVolt) / 0.05d) * 10.0d) / 10.0d) / 1000.0d));
    }
    public double microVolt_dBm(double uVolt){
        return Double.parseDouble(df.format(Math.log10((uVolt * uVolt) / 0.05d) * 10.0d));
    }

    public double dBmV_dBW(double dBmV){
        return Double.parseDouble(df.format((Math.log10(Math.pow(10.0d, (dBmV - 46.9897d) / 10.0d) / 1000.0d) * 10.0d)));
    }
    public double dBmV_microVolt(double dBmV){
        return Double.parseDouble(df.format((Math.sqrt(Math.pow(10.0d, (dBmV - 46.9897d) / 10.0d) * 0.05d))));
    }
    public double dBmV_watt(double dBmV){
        return Double.parseDouble(df.format((Math.pow(10.0d, (dBmV - 46.9897d) / 10.0d) / 1000.0d)));
    }
    public double dBmV_dBm(double dBmV){
        return Double.parseDouble(df.format((dBmV - 46.9897d)));
    }

    public double dBm_dBmV(double dBm){
        return Double.parseDouble(df.format((46.9897d + dBm)));
    }
    public double dBm_dBW(double dBm){
        return Double.parseDouble(df.format(Math.log10(Math.pow(10.0d, dBm / 10.0d) / 1000.0d) * 10.0d));
    }
    public double dBm_microVolt(double dBm){
        return Double.parseDouble(df.format(Math.sqrt(Math.pow(10.0d, dBm / 10.0d) * 0.05d)));
    }
    public double dBm_watt(double dBm){
        return Double.parseDouble(df.format(Math.pow(10.0d, dBm / 10.0d) / 1000.0d));
    }

}


