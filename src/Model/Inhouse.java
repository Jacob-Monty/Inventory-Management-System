package Model;

/**
 *Classes an in-house part
 * 
 * 
 * /

/**
 *
 * @author MRINFINITI187
 */
public class Inhouse extends Part {
    
    /**Machine ID for part
     * 
     */
    
    
    private int machineId;
    
    /**
     * Constructor for a new instance of an InHouse object.
     *
     * @param id the ID for the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the inventory level of the part
     * @param min the minimum level for the part
     * @param max the maximum level for the part
     * @param machineId the machine ID for the part
     */
    
    public Inhouse (int id, String name, int stock, double price, int min, int max, int machineId) {
        super(id, name, stock, price, min, max);
        this.machineId = machineId;
    }
    
    /**
     * getter for machine id
     * 
     * @return the machineId
     */
    public int getMachineId(){
        return machineId;
    }
    
    /**
     * setter for machine id
     * 
     * @param machineId the machineId created for part
     */
   public void setMachineId(int machineId) {
       this.machineId =  machineId;
   }
}
