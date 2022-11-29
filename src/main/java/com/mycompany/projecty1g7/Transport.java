package com.mycompany.projecty1g7;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat; 
import java.util.Calendar;
import java.util.Date;

public class Transport {
     protected String transportName;
     protected double pricePerKm;
     protected double travelSpeed;

    public Transport(String transportName, double pricePerKm, double travelSpeed) {
        this.transportName = transportName;
        this.pricePerKm = pricePerKm;
        this.travelSpeed = travelSpeed;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public double getTravelSpeed() {
        return travelSpeed;
    }

    public void setTravelSpeed(double travelSpeed) {
        this.travelSpeed = travelSpeed;
    }
    
    public String travellingFee(double travelDistance){
        // fee in 2dp
        //12.00 = 10 * 1.2
        return String.format("%.2f",travelDistance * pricePerKm);
    }
    
    public double travellingTime (double travelDistance){
        // travel time = (distance / speed) * 60 min
        // x h = (10km / 50 kmh) * 60 min
        return travelDistance / travelSpeed * 60;
    }
     
    public String arrivalTime (String departureTime, double travelDistance)throws ParseException{
        DateFormat df = new SimpleDateFormat("H:mm");
        Date departure = df.parse(departureTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(departure);
        cal.add(Calendar.MINUTE, (int) (travellingTime(travelDistance)));
        Date arrival = cal.getTime();
        return String.valueOf(df.format(arrival));
    }
    
    public String travellingData(String departureTime, double distance) throws ParseException {
        return "\nTravelling Data: \nTravelling fee (RM): "+travellingFee(distance)+" \nTravelling time: "
                +String.format("%.1f", travellingTime(distance))+"\nArrival Time: "
                +arrivalTime(departureTime, distance);
    }

    @Override
    public String toString() {
        
        return  "\nInformation of selected transport: \nName: " + transportName + "\nPrice per km (RM): " + String.format("%.2f",pricePerKm) + "\nKM per hour: " + travelSpeed;
    } 
}
