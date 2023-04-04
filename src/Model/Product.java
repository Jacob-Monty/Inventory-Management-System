/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classes for Products
 *
 * @author MRINFINITI187
 */
public class Product {
    
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;  
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     *
     * @param id gets id for product  
     * @param name name for product  
     * @param stock stock level for product 
     * @param price price for product 
     * @param min min inventory level for product 
     * @param max max inventory level for product 
     */
    public Product(int id, String name, int stock, double price, int min, int max) {
        this.id = id;
        this.name = name; 
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * Adds associated parts to list
     * @param part adds associated parts
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
     /**
     * Deletes a part
     *
     * @param selectedAssociatedPart The part that will be deleted
     * @return a boolean indicating status of deletion
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }else
            return false;
    }
    
    /**
     * Gets list of associated parts 
     *
     * @return a list of  associated parts
     */
   public ObservableList<Part> getAllAssociatedParts() {return associatedParts;} 
}
