/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * Classes an inventory for Products and Parts
 *
 * @author MRINFINITI187
 */
public class Inventory {
    
    /**
     * An ID for a part. Number used for unique part IDs.
     */
  
   private static int partId = 0;
   
   /**
     * An ID for a product. Number used for unique part IDs.
     */
   
   private static int productId =0;
   
   /**
     * List of all parts
     */
   
   private static ObservableList<Part> allParts = FXCollections.observableArrayList();
   
   /**
     * List of all products
     */
   
   private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
   
   /**
     *gets a list of all parts
     *@return a list of all parts
     */
   
   public static ObservableList<Part> getAllParts() {
       return allParts;
   }
   /**
     * gets al list of all products
     *@return a list of all products
     */
   public static ObservableList<Product> getAllProducts() {
       return allProducts;
   }
   
   /**
     * Adds a part 
     *
     * @param newPart The part  to add to objects.
     */
   public static void addPart(Part newPart) {
       allParts.add(newPart);
   }
    
    /**
     * Adds a part 
     *
     * @param newProduct The product to add to objects.
     */
   
   public static void addProduct(Product newProduct) {
       allProducts.add(newProduct);
   }
   
   /**
     * Generates a new part ID.
     *
     * @return creates a unique part ID.
     */
   
   public static int getNewPartId() {
       return ++partId;
   }
   
    /**
     * Generates a new product ID.
     *
     * @return creates a unique product ID.
     */
   public static int getNewProductId(){
       return ++productId;
   }
   
   /**
     * Searches the list of products.
     *
     * @param productId The products ID.
     * @return The product object if found, null if not found.
     */
   public static Product lookupProduct(int productId) {
       Product productFound = null;
       
       for (Product product : allProducts){
           if (product.getId() == productId) {
           productFound = product;
           }
       }
       return productFound;
       
   }
   
   /**
     * Searches the list of products.
     *
     * @param productName The products by name.
     * @return The product object if found, null if not found.
     */
   
   public static ObservableList<Product> lookupProduct(String productName){
       ObservableList<Product> productsFound = FXCollections.observableArrayList();
       
       for (Product product : allProducts) {
           if (product.getName().equals(productName)){
               productsFound.add(product);
           }
       }
       
       return productsFound;
   }
   
   /**
     * Searches the list of parts.
     *
     * @param partId The parts ID.
     * @return The parts object if found, null if not found.
     */
    public static Part lookupPart(int partId) {
       Part partFound = null;
       
       for (Part part : allParts){
           if (part.getId() == partId) {
           partFound = part;
           }
       }
       return partFound;
       
   }
    
    /**
     * Searches the list of parts.
     *
     * @param partName The part ID.
     * @return The part object if found, null if not found.
     */
    public static ObservableList<Part> lookupPart(String partName){
       ObservableList<Part> partsFound = FXCollections.observableArrayList();
       
       for (Part part : allParts) {
           if (part.getName().equals(partName)){
               partsFound.add(part);
           }
       }
       
       return partsFound;
    
    }
   
    /**
     * Replaces a part in list.
     *
     * @param index Index of the part to be replaced.
     * @param selectedPart The part used for replacement.
     */
    
   public static void updatePart (int index, Part selectedPart) {
       
       allParts.set(index, selectedPart);
   } 
   
   /**
     * Replaces a product in the list.
     *
     * @param index Index of the part to be replaced.
     * @param selectedProduct The part used for replacement.
     */
   public static void updateProduct (int index, Product selectedProduct) {
       
       allProducts.set(index, selectedProduct);
   }
   
    /**
     * Removes a part from list
     *
     * @param selectedPart The part to be removed.
     * @return A boolean indicating status of part removal.
     */
   public static boolean deletePart(Part selectedPart) {
       if(allParts.contains(selectedPart)){
           allParts.remove(selectedPart);
           return true;
       }else {
           return false;
       }
   }
    
  /**
     * Removes a product from list
     *
     * @param selectedProduct The part to be removed.
     * @return A boolean indicating status of part removal.
     */
    public static boolean deleteProduct(Product selectedProduct) {
       if(allProducts.contains(selectedProduct)){
           allProducts.remove(selectedProduct);
           return true;
       }else {
           return false;
       }
    }
    
    
    
}
