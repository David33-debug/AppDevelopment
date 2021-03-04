package com.example.testapplication.ui;

import java.text.DecimalFormat;

public class UnitsDistance {
    double Km,m,mile,ft;
    public UnitsDistance()
    {
        Km=0;
        m=0;
        mile=0;
        ft=0;
    }

    public double distanceConvert(double value, String output)
    {
                switch (output)
                {
                    case "m":
                        return value;
                    case "Km":
                        return m_km(value);
                    case "ft":
                        return m_ft(value);
                    case "mile":
                        return m_mile(value);
                    default:
                        return 0.0d;
                }
    }
    public double normalise(String input,double value)
    {
        switch (input)
        {
            case "m":
                return value;
            case "Km":
                return Km_m(value);
            case "ft":
                return ft_meter(value);
            case "mile":
                return mile_m(value);
            default:
                return 0.0d;
        }
    }

    public double Km_mile(double Kilo)
    {
        return (Kilo*(0.621371d));
    }
    public double mile_Km(double mile)
    {
        return (mile*1.60934d);
    }
    public double mile_m(double mile) {return (mile*1609.34); }
    public double Km_m(double km)
    {
        return (km*1000d);
    }
    public double m_km(double m)
    {
        return (m/1000d);
    }
    public double m_mile(double m){return (m*0.0006213712d);}
    public double ft_mile (double ft) {return (ft*0.0001893939d);}
    public double ft_meter (double ft) {
        return (ft*0.3048d);
    }
    public double ft_km (double ft) {
        return (ft*0.0003047999366d);}
    public double km_ft (double km) {
        return (km*3280.84d);}
    public double m_ft (double m) {
        return (m*3.28084d);}
    public double mile_ft (double mile) {
        return (mile*5280d);}
        public double dBi_dBd(double dBi) {
        return dBi-2.16d;
    }
    public double dBd_dBi(double dBd)
    {
        return dBd+2.16;
    }
    public double mm_inch(double mm)
    {
        return Double.parseDouble(String.format("%.3f",mm*0.03937));
    }
    public double inch_mm(double inch)
    {
        return Double.parseDouble(String.format("%.3f",inch*25.4));
    }

 }
