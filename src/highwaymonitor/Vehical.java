
package highwaymonitor;

import java.util.logging.Level;
import java.util.logging.Logger;

// this class is blueprint of a vehicle
public class Vehical extends Thread {
    private  String idNumber;
    private  int speed;
    private String enterance;
    private int journey;

//  common getters and setters 
    public int getJourney() {
        return journey;
    }

    public void setJourney(int journey) {
        this.journey = journey;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getEnterance() {
        return enterance;
    }

    public void setEnterance(String enterance) {
        this.enterance = enterance;
    }

    public int getDistanceLeft() {
        return distanceLeft;
    }

    public void setDistanceLeft(int distanceLeft) {
        this.distanceLeft = distanceLeft;
    }
    private int distanceLeft;

//  constructor
    public Vehical(String id, int speed, String enterance,int distanceLeft) {
        this.idNumber = id;
        this.speed = speed;
        this.enterance = enterance;
        this.distanceLeft = distanceLeft;

    }
    
    
//  this method simulates the vehicle flow
    @Override
    public void run(){

        journey=0;
        while(distanceLeft>0){
//          distance from exit is decremented by speed after a thread sleep of 1 second to simulate vehicle run  
            distanceLeft=distanceLeft-speed;
            journey=journey+speed;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }

        }
//      finds the available terminal where vehicle can process payment
        Terminal availableTerminal=Highway.getTerminals().get(Highway.nextAvailableTerminalIndex());
//      puts the vehicle in terminal queue        
        availableTerminal.addVehicleToQueue(this);
        try {
//          this code simulates waiting in terminal queue
            while(this.getIdNumber() == null ? availableTerminal.currentlyProcessedVehical().getIdNumber() != null : !this.getIdNumber().equals(availableTerminal.currentlyProcessedVehical().getIdNumber())){
            }
//          simulates payment process time of 30 seconds
            Thread.sleep(30000);
            availableTerminal.addToProcessedVehicle();
            availableTerminal.exitVehicle();
        } catch (InterruptedException ex) {
            Logger.getLogger(Vehical.class.getName()).log(Level.SEVERE, null, ex);
        }
//      stops the vehicle stops the vehicle thread
        Thread.currentThread().interrupt();
      
    }
    
    
}
