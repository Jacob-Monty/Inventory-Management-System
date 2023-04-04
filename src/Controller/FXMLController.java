package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.Part;
import Model.Inhouse;
import Model.Inventory;
import Model.Product;
import Model.Outsource;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;



/**
 * FXML Controller class that provide control for the main scene of the application. <p>
 * 
 * Runtime Error Comments can be found under modifyPartFormButtonPushed. 
 *
 * @author MRINFINITI187
 */
public class FXMLController implements Initializable {
    
    
    private static Part partToModify;
    
    private static Product productToModify;
    
    @FXML private Label InventoryManagementSystemlabel;
    @FXML private TextField  partsSearchTextField;
    @FXML private TextField  productsSearchTextField;
   
    
    //Table for Parts
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    
    //Table for Products
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Part, Integer> productIdColumn;
    @FXML private TableColumn<Part, String> productNameColumn;
    @FXML private TableColumn<Part, Integer> productInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> productPriceColumn;
    
    
    
    /** parts to modify
     * 
     * @return parts to modify 
     */
    public static Part getPartToModify() {
        return partToModify;
    }
    /** parts to modify
     * 
     * @return products to modify 
     */
    public static Product getProductToModify()  {
        return productToModify;
    }
    
    
    /** When button is pushed is will change the scene to Add/Modify Part Screen. The proper fxml file needs to be selected in order for the next screen to be chosen.
     *@param event changes scene to add part
     * @throws IOException From Make sure that the fxml that is chosen is correct
     */
    public void addPartFormButtonPushed(ActionEvent event) throws IOException
    {
       Parent addFormParent = FXMLLoader.load(getClass().getResource("/View/AddForm.fxml"));
        Scene addFormScene = new Scene(addFormParent);
       
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addFormScene);
        window.show();
    }
    
    
    /**
     * This will send you to modify part screen when an object is selected in the parts tableview. Will throw an exception if not part is selected by the user. Created an alert that will not let user proceed to next screen without choosing a part. <p>
     * 
     *  RUNTIME ERROR: Occurs when pushing the modify or delete buttons without selecting a part within the tables.
     *  It occurs due to a null value being passed to the initialize method of that buttons controller. <p>
     *  
     * @param event send user to modify part screen
     * @throws IOException user needs to select a part.
     */
    public void modifyPartFormButtonPushed(ActionEvent event) throws IOException
    {
        
        partToModify = partsTableView.getSelectionModel().getSelectedItem();
        
        //Example of correcting a runtime error by preventing null from being passed
        // to the ModifyPartController.
        
        if(partToModify == null){
            displayAlert(2);
            
        }else{
       
       Parent parent = FXMLLoader.load(getClass().getResource("/View/ModifyPart.fxml"));
        Scene scene = new Scene(parent);
       
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
        }
        
    }
    
     /*This will delete selected object that is in the parts table view. If user does not select a part to delete it will create and exception. Used an alert that will not let the user continue unless part is selected.
     @param event delete object from parts table
     * @throws IOException if no part is selected user can not advance.
     */
    
    @FXML void partDeleteButtonPushed(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        
       
        if(selectedPart == null) {
            displayAlert(2);
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Important");
            alert.setContentText("Are you sure you want to delete Part?");
            Optional<ButtonType> result = alert.showAndWait();
        
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
       } 
    }
    
    /**
     *This is the product search method. This method searches for a product match to the user entered information in the search field and compares it to the existing product names or ID's.
     * @param actionEvent Search button clicked.
     */
    @FXML void partSearchTextKeyPressed(KeyEvent event) {

        if (partsSearchTextField.getText().isEmpty()) {
            partsTableView.setItems(Inventory.getAllParts());
        }

    }
    
    
    @FXML  void partSearchButtonPushed(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partsSearchTextField.getText();

        for (Part part : allParts){
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        partsTableView.setItems(partsFound);

        if (partsFound.size() == 0) {
            displayAlert(1);
        }
    }
    
    
    /**
     * This will send user to add product screen. If the correct fxml file is not selected it will throw an exception.
     @param event send to add product screen
     * @throws IOException From FXMLLoader
     */
    
    @FXML public void addProductFormButtonPushed(ActionEvent event) throws IOException
    {
       Parent addFormParent = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));
        Scene addFormScene = new Scene(addFormParent);
       
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addFormScene);
        window.show();
    }
    
    /**
     * This will send user to modify products screen. Throws exception if the user does not select a product to modify. Created an alert system that will notify the user that no product was selected.
     @param event when pushed user goes to modify products screen
     * @throws IOException if no product was selected.
    */
    public void modifyProductFormButtonPushed(ActionEvent event) throws IOException
    {
        
        productToModify = productsTableView.getSelectionModel().getSelectedItem();
        
        if(productToModify == null){
            displayAlert(4);
            
        }else{
       
       Parent parent = FXMLLoader.load(getClass().getResource("/View/ModifyProduct.fxml"));
        Scene scene = new Scene(parent);
       
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
        }
        
    }
    
    /**
     * This will delete selected object that is in the products table view.
     *@param event deletes product from table.
     * 
    */
    @FXML
    void productDeleteButtonPushed(ActionEvent event) {

        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            displayAlert(4);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();

                if (associatedParts.size() >= 1) {
                    displayAlert(5);
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }
    
     /**
      * This will search for products in product table view.
      * @param event seraches for products when pushed
      * 
      */
    @FXML void productSearchButtonPushed(ActionEvent event) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = productsSearchTextField.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                productsFound.add(product);
            }
        }

        productsTableView.setItems(productsFound);

        if (productsFound.size() == 0) {
            displayAlert(3);
        }
    }
    
    @FXML void productSearchTextKeyPressed(KeyEvent event) {

        if (productsSearchTextField.getText().isEmpty()) {
            productsTableView.setItems(Inventory.getAllProducts());
        }
    }
     
     
   
     
     /**
     * This displays alerts that when something is not done correctly.
     *
     * @param alertType Alert message selector.
     */
    private void displayAlert(int alertType)
    {     
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
     Alert alertError = new Alert(Alert.AlertType.ERROR);
     
        switch (alertType) {
            case 1:
                alert.setTitle("Information");
                alert.setHeaderText("Part not found");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Part Not Selected");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Information");
                alert.setHeaderText("Product not found");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Product Not Selected");
                alert.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Parts Associated");
                alertError.setContentText("All parts must be removed from product before deletion.");
                alertError.showAndWait();
                break;
        }
    }
    
   
    @FXML void exitButtonAction(ActionEvent event) {

        System.exit(0);
    }
     
  

    /**
     * Initializes the controller class and tables.
     * @param url the location used to resolve relative paths for the root object
     * @param rb the resources used to localize the root object, or null if the root object is not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
   //set up the columns in the table for parts Tabelview
    partsTableView.setItems(Inventory.getAllParts());
    partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    
    //set up the columns in the table for the productTableview
    productsTableView.setItems(Inventory.getAllProducts());
    productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    
    
    
   
        
    }
    
   
    
}
