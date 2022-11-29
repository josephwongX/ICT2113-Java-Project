package com.mycompany.projecty1g7;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TransportMain {

    public static void main(String[] args) 
    {
        Transport [] transport = new Transport[20];
        transport [0] = new ScheduledTransport("6:00",7,150,"LRT",0.3,75);
        transport [1] = new Transport("Taxi", 1.00, 80);        
        transport [2] = new ScheduledTransport("4:30",6,170,"MRT",0.8,70);
        int input_data;
        Scanner input = new Scanner(System.in);
        while(true)
        {
            try
            {
            System.out.println("\nMain Menu:");
            System.out.println("1. Add Transport");
            System.out.println("2. Select Transport");
            System.out.println("3. Exit");
            System.out.print("Enter Selection -> ");
            input_data=input.nextInt();
            
            if (input_data > 3 || input_data < 1)
                    System.out.print("\nInvalid input, please enter a number from 1 to 3.");
            switch(input_data)
            {
                case 1:    addTransport(transport);
                    break;
                case 2:    selectTransport(transport);
                    break;
                case 3:
                    System.out.println("\nThank You For Using The System.");
                    return;
                default:
                    System.out.println("\nInvalid Input");
                    break;
                }
            }catch (NumberFormatException e) {
                System.out.println("Only numbers are allowed");
            }catch (InputMismatchException e) {
                System.out.println("\nInvalid input.");input.nextLine();
            }catch (Exception e){
            System.out.println("Please enter a valid number");
            }
        
        }
    
    }
        
    public static void addTransport(Transport[] transport) {
        Scanner input = new Scanner(System.in);
        int transportType = 0;
        String transportName = null;
        double travelPrice = 0;
        double travelSpeed =0;
        
        boolean validType = false;
        
        while(!validType){
            try {
                while(transportType <1 || transportType > 2){
                System.out.println("\nTransport Type:");
                System.out.println("1. without schedule");     
                System.out.println("2. with schedule");
                System.out.print("Enter selection -> ");
                transportType = input.nextInt();
                if (transportType == 1 || transportType == 2){
                    validType = true;
                } else if (transportType < 1 || transportType > 2){
                    System.out.print("Invalid input, please enter the number 1 or 2: ");
                }
                }
            } catch (NumberFormatException e){
                System.out.println("Only numbers 1 or 2 could be accepted\n");
            }  catch (InputMismatchException e){
                System.out.println("Please input number 1 or 2");
            }catch (Exception e){
                System.out.println("Invalid input");
            }
        }
        input.nextLine();
        
        System.out.print("\nProvide the following data \n");
        
        System.out.print("\nTransport name -> ");
        transportName = input.nextLine();
        
        boolean validPrice = false;
        while (!validPrice) {
            try {
                System.out.print("Price per km (RM) -> ");
                travelPrice = input.nextDouble();
                validPrice = true;
            }catch (NumberFormatException e){
                System.out.println("Please enter decimal with correct format (eg. 10.00)");
            }catch (InputMismatchException e){
                System.out.println("Only Numbers (RM) are allowed");
            }catch (Exception e){
                System.out.println("Invalid input");
            }
        }
        input.nextLine();
        boolean validSpeed = false;
        while (!validSpeed){
            try {
                System.out.print("Speed (KM/Hour) -> ");
                travelSpeed = input.nextDouble();
                validSpeed = true;
            }catch (NumberFormatException e){
                System.out.println("Please enter decimal with correct format (eg. 10.00)");
            }catch (InputMismatchException e){
                System.out.println("Only Numbers (RM) are allowed");
            }catch (Exception e){
                System.out.println("Invalid input");
            }
        }
        if (transportType == 2){
            ScheduledTransport st = new ScheduledTransport(transportName, travelPrice, travelSpeed);
            readSchedule(st);
            int i = 0;
            while (i<transport.length){
                if (transport[i] == null){
                    transport[i] =st;
                    break;
                }
                i++;
            }
            
        }else {
            Transport t = new Transport(transportName, travelPrice, travelSpeed);
            int i = 0;
            while (i<transport.length){
                if (transport[i] == null){
                    transport[i] =t;
                    break;
                }
                i++;
            }
        }
        System.out.println("\nNew transport " + transportName + " added.");
    }
    
    public static void readSchedule(ScheduledTransport st) {
        Scanner input = new Scanner(System.in);
        String startTime = null;
        int frequency = 0;
        int numOfTrip = 0;
        boolean validTime = false;
        while(!validTime){
            System.out.print("Start time in 00:00 format -> ");
            startTime = input.nextLine();
            validTime = isValidTime(startTime);
            if (validTime == false){
                System.out.println("Invalid input");
            }
        }
        boolean validFreq = false;
        while(!validFreq){
            try {
                System.out.print("Frequency in minutes -> ");
                frequency = input.nextInt();
                if (frequency <=0){
                    System.out.println("Frequency cannot be negative or zero");
                }else {
                    validFreq = true;
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid entry for Frequency");
            }catch (InputMismatchException e){
                System.out.println("Only allow number value for frequency");
            }catch (Exception e) {
                System.out.println("Please input number value");
            }
        }
        
        boolean validTrip = false;
        while (!validTrip){
            try {
                System.out.print("Number of trips -> ");
                numOfTrip = input.nextInt();
                if (numOfTrip <=0){
                    System.out.println("Number of trips in a day cannot be negative or zero");
                }else {
                    validTrip = true;
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid entry for trips");
            }catch (InputMismatchException e){
                System.out.println("Only allow numbers (of trips)");
            }catch (Exception e) {
                System.out.println("Please input number value");
            }
        }
        st.setStartTime(startTime);
        st.setFrequencyInMin(frequency);
        st.setNumOfTrip(numOfTrip);
        
    }
    
    public static void selectTransport (Transport[] transport){
        Scanner input = new Scanner(System.in);
        int input_data = -1;
        boolean validInput = false;
        double distance = 0;
        
        String departure = null;
        
        while(!validInput){
        System.out.println("\n Transport Selection: ");

        int i =0;
        while (i<transport.length){
            if (transport[i] != null){
                System.out.println((i+1)+". "+transport[i].getTransportName());
            }
            i++;
        }
        
        i = -1;
        if (transport != null){
            for (i = transport.length -1; i>=0; i--){
                if (transport[i] != null){
                    break;
                }
            }
        }
        
        
            try{
                System.out.print("Enter Selection (0 to cancel) -> ");
                input_data = input.nextInt();
                
                
                if (input_data == 0){
                    return;
                }
                if (input_data <0 || input_data > i+1){
                    System.out.print("\nInvalid Input.");
                }else if (input_data >= 0 && input_data <= i+1){
                    validInput = true;
                }
                
                
            }catch(NumberFormatException e){
                System.out.println("Invalid input\n");
            }catch(InputMismatchException e){
                System.out.println("Only accept number between 1 to " +transport.length);
            }catch (Exception e){
                System.out.println("Please input number value");
            }
        }
        input_data -= 1;
        System.out.println(transport[input_data]);
        
        boolean validDistance = false;
        while (!validDistance){
            try {
                System.out.print("\nEnter travelling distance in km -> ");
                distance = input.nextInt();
                validDistance = true;
            }catch (NumberFormatException e) {
                System.out.println("Please enter a numeric value");
            }catch (InputMismatchException e){
                System.out.println("Please input number(km) value");
            }catch (Exception e){
                System.out.println("Please input number value");
            }
        }
        input.nextLine();
        
        boolean validTime = false;
        while (!validTime) {
            System.out.print("Enter departure time in 00:00 format -> ");
            departure = input.nextLine();
            validTime = isValidTime(departure);
            if(validTime == false){
                System.out.println("Invalid input");
            }
        }
        try {
            System.out.println(transport[input_data].travellingData(departure, distance));
        }catch(ParseException ex){
            Logger.getLogger(TransportMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Function to validate the time in 24-hour format
    public static boolean isValidTime(String time)
    {
 
        // Regex to check valid time in 24-hour format.
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
 
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
 
        // If the time is empty
        // return false
        if (time == null) {
            return false;
        }
 
        // Pattern class contains matcher() method
        // to find matching between given time
        // and regular expression.
        Matcher m = p.matcher(time);
 
        // Return if the time
        // matched the ReGex
        return m.matches();
    }
}