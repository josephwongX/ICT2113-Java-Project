package com.mycompany.projecty1g7;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScheduledTransport extends Transport{
    private String startTime;
    private int frequencyInMin;
    private int numOfTrip;

    public ScheduledTransport(String transportName, double pricePerKm, double travelSpeed) {
        super(transportName, pricePerKm, travelSpeed);
    }
    
    public ScheduledTransport(String startTime, int frequencyInMin, int numOfTrip, String transportName, double pricePerKm, double travelSpeed) {
        super(transportName, pricePerKm, travelSpeed);
        this.startTime = startTime;
        this.frequencyInMin = frequencyInMin;
        this.numOfTrip = numOfTrip;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getFrequencyInMin() {
        return frequencyInMin;
    }

    public void setFrequencyInMin(int frequencyInMin) {
        this.frequencyInMin = frequencyInMin;
    }

    public int getNumOfTrip() {
        return numOfTrip;
    }

    public void setNumOfTrip(int numOfTrip) {
        this.numOfTrip = numOfTrip;
    }
    
    public String arrivalTime(String departureTime, double travelDistance) throws ParseException {
        DateFormat df = new SimpleDateFormat("H:mm");
        Date departure = df.parse(departureTime);
        Date arrival = null;
        boolean valid = false;
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < getSchedule().length; i++) {
            if(departure.equals(getSchedule()[i])){
                cal.setTime(departure);
                cal.add(Calendar.MINUTE, (int) (travellingTime(travelDistance)));
                arrival = cal.getTime();
                valid = true;
                return String.valueOf(df.format(arrival));
            } else {
                if (departure.before(getSchedule()[i])){
                    departure = getSchedule()[i];
                    cal.setTime(departure);
                    cal.add(Calendar.MINUTE, (int) (travellingTime(travelDistance)));
                    arrival = cal.getTime();
                    valid = true;
                    return String.valueOf(df.format(arrival));
                }
            }
        }
        if (departure.after(getSchedule()[getSchedule().length -1])){
                    departure = getSchedule()[0];
                    cal.setTime(departure);
                    cal.add(Calendar.MINUTE, (int) (travellingTime(travelDistance)));
                    arrival = cal.getTime();
                    valid = true;
                    return String.valueOf(df.format(arrival));
                }
        
        cal.setTime(departure);
        cal.add(Calendar.MINUTE, (int) (travellingTime(travelDistance)));
        arrival = cal.getTime();
        return String.valueOf(df.format(arrival));
    }
    
    public Date[] getSchedule() throws ParseException {
        DateFormat df = new SimpleDateFormat("H:mm");
        Date [] scheduleArr = new Date [numOfTrip];
        Date time = df.parse(startTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        scheduleArr[0] = cal.getTime();
        for (int i = 1; i < numOfTrip; i++){
            cal.add(Calendar.MINUTE, (int) frequencyInMin);
            scheduleArr[i] = cal.getTime();
        }
        return scheduleArr;
    }
    
    @Override
    public String toString(){
        DateFormat df = new SimpleDateFormat("H:mm");
        try {
            String schedule ="";
            for (int i = 0; i < getSchedule().length; i++) {
                schedule += " " + String.valueOf(df.format(getSchedule()[i]));
            }
            return "Information of the selected transport: \nName: " + transportName + "\nPrice per km (RM): "+String.format("%.2f",pricePerKm)+"\nKM per hour: "+travelSpeed+"\nSchedule: " + schedule;
        }catch (ParseException ex) {
            Logger.getLogger(ScheduledTransport.class.getName()). log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
