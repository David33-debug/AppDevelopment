package Logic;

import java.text.DecimalFormat;

public class FresnelCalcs {
    DecimalFormat df = new DecimalFormat("#.000");
    UnitsDistance units=new UnitsDistance();
    private final double speedOfLight = Math.pow(10.0d, 6.0d) * 299.792d;
    double result;
    double d1,d2,d3;

    public double fresnel_radius(int fresnel_zone, double totalDistance, double distance1, double distance2, double frequency,String length,String length2)
    {
        distance1=units.normalise(length,distance1);
        totalDistance=units.normalise(length,totalDistance);
        distance2=units.normalise(length,distance2);
       // if((distance1>(fresnel_zone*(speedOfLight/frequency))) && distance2>(fresnel_zone*(speedOfLight/frequency))) {
        result =Double.parseDouble(df.format(Math.sqrt((fresnel_zone*distance1*distance2)/(totalDistance)*(300/(frequency)))));//Math.sqrt(((((fresnel_zone * totalDistance) * totalDistance) * 0.25d) * (speedOfLight / frequency)) / totalDistance);
        return units.distanceConvert(result,length2);
    }

    public double relative_clearance(double fresnel_Radius, double loss_clearance)
    {
        return Double.parseDouble(df.format(loss_clearance - (0.6d * fresnel_Radius)));
    }

    public double loss_clearance(double heightSecond, double heightFirst, double distanceFirst, double totalDistanceDouble, double distanceSecond, double constant, double obstacleHeight,String length,String length2)
    {
        heightFirst=units.normalise(length,heightFirst);
        heightSecond=units.normalise(length,heightSecond);
        distanceFirst=units.normalise(length,distanceFirst);
        distanceSecond=units.normalise(length,distanceSecond);
        totalDistanceDouble=units.normalise(length,totalDistanceDouble);
        obstacleHeight=units.normalise(length,obstacleHeight);

        result= Double.parseDouble(df.format((((((heightSecond - heightFirst) * distanceFirst) / totalDistanceDouble) + heightFirst) - (((distanceFirst / 1000.0d) * (distanceSecond / 1000.0d)) / (12.74d * constant))) - obstacleHeight));
        return units.distanceConvert(result,length2);//return convertUnits(result,length1,length2);
    }
    public double max_clearance(double distance, double frequency, String length,String length2)
    {
        distance=units.normalise(length,distance);
        result=Double.parseDouble(df.format(0.5d*Math.sqrt((300*distance)/frequency)));
        //result=Double.parseDouble(df.format(8.656d*Math.sqrt((distance/1000d)/(frequency/(Math.pow(1,3))))));
        return units.distanceConvert(result,length2);// convertUnits(result,length);
    }

    public double heightOfAntToClearEarth_forSameHeightAnt(double totalDistanceKm,double fresnelRadiusMax,String length, String length2)
    {
        totalDistanceKm=units.normalise(length,totalDistanceKm);
        fresnelRadiusMax=units.normalise(length,fresnelRadiusMax);

        result=Double.parseDouble(df.format(Math.sqrt(Math.pow((totalDistanceKm/1000d)/0.002,2)+Math.pow((8472/0.001+fresnelRadiusMax),2))-(8472/0.001)));
        return units.distanceConvert(result,length2);
    }

    public double ClearanceBetweenOandFZ(double height1,double distance1,double totalDistance,double height2,String length,String length2,double fresnelRadius)
    {
        height1=units.normalise(length,height1);
        distance1=units.normalise(length,distance1);
        totalDistance=units.normalise(length,totalDistance);
        height2=units.normalise(length,height2);

        d1=Math.pow(8472d*1000d+height1,2)+Math.pow((distance1/1000)*1000,2);
        d2=((distance1/1000d)/(totalDistance/1000d))*(Math.pow(8472d*1000d+height1,2)+Math.pow((totalDistance/1000d)*1000d,2)-Math.pow(8472d*1000d+height2,2));
        d3=Double.parseDouble(df.format((Math.sqrt(d1-d2)-(8472d*1000d))-fresnelRadius));
        return units.distanceConvert(d3,length2);
    }


}
