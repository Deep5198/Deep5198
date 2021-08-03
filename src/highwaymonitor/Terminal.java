
package highwaymonitor;

import java.util.LinkedList;
import java.util.Queue;


public class Terminal {
    private Queue<Vehical> vehicleQueue=new LinkedList<>();
    private int chargePerVehicle;
    private int totalIncome;
    private int totalVehicleProcessed;
    private String name;
    
     public Terminal(String name,int chargePerVehicle) {
        this.chargePerVehicle = chargePerVehicle;
        this.name = name;
    }
    
    public void addVehicleToQueue(Vehical vehicle){
       vehicleQueue.add(vehicle);
    
    }   
    
    public Vehical currentlyProcessedVehical(){
      Vehical v=vehicleQueue.peek();
      return v;
    }

    public Queue<Vehical> getVehicleQueue() {
        return vehicleQueue;
    }

    public void setVehicleQueue(Queue<Vehical> vehicleQueue) {
        this.vehicleQueue = vehicleQueue;
    }

    public int getChargePerVehicle() {
        return chargePerVehicle;
    }

    public void setChargePerVehicle(int chargePerVehicle) {
        this.chargePerVehicle = chargePerVehicle;
    }

    public int getTotalIncome() {
        return chargePerVehicle*totalVehicleProcessed;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getTotalVehicleProcessed() {
        return totalVehicleProcessed;
    }

    public void addToProcessedVehicle() {

        ++this.totalVehicleProcessed;
        
       
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void exitVehicle(){
     
     
     vehicleQueue.remove();
    
    }
}
