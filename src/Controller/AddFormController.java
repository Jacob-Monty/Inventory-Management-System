package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.Inhouse;
import Model.Inventory;
import Model.Outsource;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *  @author
 *
 *  MRINFINITI187
 */

/** 
 * 
 * Creates an AddPartsController class for the AddForm FXML Scene 
 *
 */
public class AddFormController implements Initializable {
    

    
    @FXML private RadioButton inhouseButton;
    @FXML private RadioButton outsourcedButton;
    @FXML private Label radioButtonLabel;
    @FXML private ToggleGroup sourceToggleGroup;
    
    
    @FXML private TextField partIdTextField;
    @FXML private TextField partNameTextField;
    @FXML private TextField partInventoryTextField;
    @FXML private TextField partPriceTextField;
    @FXML private TextField partMaxTextField;
    @FXML private TextField partMinTextField;
    @FXML private TextField partMachineIdTextField;
    
    /**
     * This Button Returns you to Main Form Screen, this method confirms the action then closes the screen without saving information and returns user to main screen.
     * 
     * @param event user clicking on the Cancel button.
     * @throws IOException From FXMLLoader
    */
    public void addPartFormCancelButtonPushed(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Important");
        alert.setContentText("Do you want to cancel changes and return to Main Form?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainFormScene(event);
        }
    }
     /**
      * 
      * This Button Returns you to Main Form Screen
      * @param event return to Main Form Scene
      * @throws IOException From FXMLLoader if the file from getResource is not selected properly.
    */
    public void returnToMainFormScene(ActionEvent event) throws IOException {
        
        Parent parent =FXMLLoader.load(getClass().getResource("/View/FXML.fxml"));
        Scene scene = new Scene (parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /** Validates that inventory level is equal to or between the min and max. Fixed by making the sure that the user enters the correct value if not a pop will tell user that it is incorrect.
     * 
     * @param min minimum value for the part.
     * @param max maximum value for the part.
     * @param stock inventory level.
     * @return Boolean indicating if the inventory is valid.
     * @throws ValidationException thrown if the value entered does not fit within the values of the min and max.
     */
     private boolean minValid(int min, int max) {
        boolean isValid = true;
        
    if (min <=0  || min >= max) {
        isValid = false;
        displayAlert(3);
    }
    
    return isValid;
    }
     
    /** Validates that inventory level is equal to or between the min and max. Fixed by making the sure that the user enters the correct value if not a pop will tell user that it is incorrect.
     * 
     * @param min minimum value for the part.
     * @param max maximum value for the part.
     * @param stock inventory level.
     * @return Boolean indicating if the inventory is valid.
     * @throws ValidationException thrown if the value entered does not fit within the values of the min and max.
     */
     
    private boolean inventoryValid(int min, int max, int stock){
        boolean isValid = true;
        
    if (stock < min || stock > max) {
        isValid = false;
        displayAlert(4);
    }
    
    return isValid;
    }
    
    /* Saves part to main scene and returns user back to main form. Fixed all exceptions by making sure that the information entered matches the correct values for the part properties. Also added an alert that will pop up if there are no values at all in the text boxes.
     * @param actionEvent saves a new part to the part table
     * @throws IOException Due to the format or value not being entered properly by user.
    */ 
    @FXML void savePartButton(ActionEvent event) throws IOException{
        
        try {
            int id = 0;
            String name = partNameTextField.getText();
            Double price = Double.parseDouble(partPriceTextField.getText());
            int stock = Integer.parseInt(partInventoryTextField.getText());
            int max = Integer.parseInt(partMaxTextField.getText());
            int min = Integer.parseInt(partMinTextField.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;
            
        if (name.isEmpty()) {
            displayAlert(5);
        } else{
            if (minValid(min,max) && inventoryValid(min, max, stock)){
                if (inhouseButton.isSelected())
                {
                 try{
                  machineId = Integer.parseInt(partMachineIdTextField.getText());
                  Inhouse newInhousePart = new Inhouse (id,name, stock, price, min, max, machineId);
                  newInhousePart.setId(Inventory.getNewPartId());
                  Inventory.addPart(newInhousePart);
                  partAddSuccessful = true;
                 } catch (Exception e) {
                    displayAlert(2); 
                 }   
                }
             if (outsourcedButton.isSelected())  {
                 companyName = partMachineIdTextField.getText();
                 Outsource newOutsourcedPart = new Outsource(id, name, stock, price, min, max, companyName);
                 newOutsourcedPart.setId(Inventory.getNewPartId());
                Inventory.addPart(newOutsourcedPart);
                partAddSuccessful = true;
                
             } 
             
             if (partAddSuccessful) {
                 returnToMainFormScene(event);
             }
                
            }
        }
        
    } catch(Exception e) {
        displayAlert(1);
        }
    }
    
    /**
     * Displays various alert messages
     * 
     * @param alertType Alert message Selector
     */
    private void displayAlert(int alertType){
        
      Alert alert = new Alert(Alert.AlertType.ERROR);
        
    switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");
                alert.setContentText("Form is not complete or has invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("invalid value for Machine ID");
                alert.setContentText("Machine ID can only have Numbers");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value in Minimum");
                alert.setContentText("Min must be great than 0 or less than Max");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Value for Inventory");
                alert.setContentText("Inventory must be a number inbetween Min and Max");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name must have a value");
                alert.showAndWait();
                break;
             
        }
    }
    
   /** This button is selected to add Inhouse Part */
    @FXML void inhouseButton(ActionEvent event) {
        radioButtonLabel.setText("Machine ID");
    }
    
    /** This button is selected to add Outsourced part */
    @FXML void outsourcedButton(ActionEvent event) {
        radioButtonLabel.setText("Company Name");
    }
    
    
            

    /**
     * Initializes the controller class. Also sets inhouse radio button to true.
     * @param url location used to resolve the relative paths for the root object
     * @param rb the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        inhouseButton.setSelected(true);
       
        radioButtonLabel.setText("Machine ID");
        sourceToggleGroup = new ToggleGroup();
        this.inhouseButton.setToggleGroup(sourceToggleGroup);
        this.outsourcedButton.setToggleGroup(sourceToggleGroup);
        
        
        
        
    }    
    
}
