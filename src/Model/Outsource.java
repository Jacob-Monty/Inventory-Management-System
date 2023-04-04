/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Classes for Outsourced Parts
 * 
 * @author MRINFINITI187
 */
public class Outsource extends Part {
    
    
    private String companyName;
    
    /**
     * Constructor for a new instance of an Outsource object.
     *
     * @param id the ID for the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the inventory level of the part
     * @param min the minimum level for the part
     * @param max the maximum level for the part
     * @param companyName company name part came from
     */
    
    public Outsource(int id, String name, int stock, double price, int min, int max, String companyName){
        super(id, name, stock, price, min, max);
        this.companyName = companyName;
    }
    /**
     * getter for company name
     * 
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }
    
    /**
     * setter for company name
     * 
     * @param companyName is set here
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
     
}
