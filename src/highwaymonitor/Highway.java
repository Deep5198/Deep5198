package highwaymonitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Highway {

    private static Highway HIGHWAY_INSTANCE = null;
    private static final String HIGHWAY_TEXT_FILE = "highway.txt";
    private static final String VEHICAL_TEXT_FILE = "vehicals.txt";
    private static final String TERMINALS_TEXT_FILE = "terminals.txt";
    private static HashMap<String, Integer> Enterances = new HashMap<String, Integer>();
    private static ArrayList<Vehical> Vehicals = new ArrayList<Vehical>();
    private static ArrayList<Terminal> Terminals = new ArrayList<Terminal>();
    private static int currentAvailableTerminalIndex=0;

   
/*  This constructor calls three functions which takes data from text file populate to arraylist  */
    private Highway() throws InvalidEnteranceException {
        try {
            readAndProcessTextFile();
            processVehicals();
            processTerminals();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Highway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // static method to create instance of Singleton class
    public static Highway getInstance() throws InvalidEnteranceException {
        if (HIGHWAY_INSTANCE == null) {
           
            HIGHWAY_INSTANCE = new Highway();
        }

        return HIGHWAY_INSTANCE;
    }
    
    // this function read and process Highway text file.
    public static void readAndProcessTextFile() throws FileNotFoundException {

        File file = new File(HIGHWAY_TEXT_FILE);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {

            String line = sc.nextLine();
//          takes data from text file and split it to get enterance name and its distance
            String[] values = line.split(" ");
            
//          puts enterances data into hashmap 
            Enterances.put(values[0], Integer.parseInt(values[1]));

        }

    }

    public static void processVehicals() throws FileNotFoundException, InvalidEnteranceException {

        File file = new File(VEHICAL_TEXT_FILE);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {

            String line = sc.nextLine();
            String[] values = line.split(" ");
            if(!Enterances.containsKey(values[1])){
              throw new InvalidEnteranceException("No Such enterance found");
            }
            //fetches data from vehicle.txt file and simulate vehicle by adding vehicle objects to array list
            Vehicals.add(new Vehical(values[0], Integer.parseInt(values[2]), values[1], getExitDistance(values[1])));

        }

    }
    
//  fetch data from terminals.txt file
    public static void processTerminals() throws FileNotFoundException{
      
        File file = new File(TERMINALS_TEXT_FILE);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {

            String line = sc.nextLine();
            String[] values = line.split(" ");
           
//          simulate terminals by adding object of terminal class to Terminals array list
            Terminals.add(new Terminal(values[0],Integer.parseInt(values[1])));

        }
    
    }
    
    public static ArrayList<Terminal> getTerminals(){
      return Terminals;
    }
    
//  returns the next available Terminal where vehicle can queue to process payment
//  synchronised is used to protect it from access of two threads at same time
    public static synchronized int nextAvailableTerminalIndex(){
     
   
        currentAvailableTerminalIndex=currentAvailableTerminalIndex+1;
  
        return currentAvailableTerminalIndex%(Terminals.size());
    
       
    }
    
   
//  keeps an eye on the changes in vehicle flow
    public static ObservableList<String> getVehicalFlowListItems(){
        ObservableList<String> VehicalFlowList =FXCollections.observableArrayList();
      

        Vehicals.forEach((vehicle) -> {
              VehicalFlowList.add(vehicle.getIdNumber()+" "+vehicle.getEnterance()+" "+vehicle.getSpeed()+" KM/HOUR");
        });
        
        return VehicalFlowList;
    
    }

    public static ArrayList<Vehical> getVehicals() {
        return Vehicals;
    }

    public static Integer getExitDistance(String EnteranceName) {
        return Enterances.get(EnteranceName);
    }
    
    
//  function simulates the running of vehicle
    public void startVehicalSimulation() {
     
//      creates a seprate thread for each running vehicle
        Vehicals.forEach((vehical) -> {
           
            Thread v=new Thread(vehical);
            vehical.start();


        });
        
    }
   
    
    
    

}
