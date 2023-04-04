package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.FXMLController;
import Model.Inhouse;
import Model.Outsource;
import Model.Part;
import Model.Inventory;
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
 * 
 * @author MRINFINITI187
 */

/**
 * Creates an ModifyPartController Class for the Modify Part FXML Scene.
 */
public class ModifyPartController implements Initializable {
    
    private Part selectedPart;

    
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
    
    /**
      * 
      * This Button Returns you to Main Form Screen
      * @param event return to Main Form Scene
      * @throws IOException From FXMLLoader if the file from getResource is not selected properly.
    */
    @FXML  void cancelButton(ActionEvent event)throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Important");
        alert.setContentText("Do you want to cancel changes and return to Main Form?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainFormScene(event);
        }
 
     }
    

    /**This is the save method for parts. This method takes the part and adds it to the overall data. Fixed by not allowing the user to continue to the next screen without fixing the issues.
     * @param actionEvent this is the event that occurs when the user clicks the save button.
     * @throws IOException Due to the format or value not being entered properly by user.
     */
    @FXML void savePartButton(ActionEvent event) throws IOException{
        
        try {
            int id = selectedPart.getId();
            String name = partNameTextField.getText();
            Double price = Double.parseDouble(partPriceTextField.getText());
            int stock = Integer.parseInt(partInventoryTextField.getText());
            int max = Integer.parseInt(partMaxTextField.getText());
            int min = Integer.parseInt(partMinTextField.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;
            
        if (minValid(min,max) && inventoryValid(min,max,stock)) {
            
            if (inhouseButton.isSelected()) {
                try {
                    machineId = Integer.parseInt(partMachineIdTextField.getText());
                    Inhouse newInhousePart = new Inhouse(id, name, stock, price, min, max, machineId);
                    Inventory.addPart(newInhousePart);
                    partAddSuccessful = true;
                    
                } catch(Exception e) {
                    displayAlert(2);
                }
            }
            
            if (outsourcedButton.isSelected()) {
                companyName = partMachineIdTextField.getText();
                Outsource newOutsourcedPart = new Outsource(id, name, stock, price, min, max, companyName);
                Inventory.addPart (newOutsourcedPart);
                partAddSuccessful = true;
            }
            
            if (partAddSuccessful) {
                Inventory.deletePart(selectedPart);
                returnToMainFormScene(event);
            } 
        }
        }catch (Exception e){
            displayAlert(1);
            }  
    }
    
   /**
    * These are the radio button that control if part is inhouse or outsourced
    */
    @FXML void inhouseButton(ActionEvent event) {
        radioButtonLabel.setText("Machine ID");
    }
    
    
    @FXML void outsourcedButton(ActionEvent event) {
        radioButtonLabel.setText("Company Name");
    }
    
    
    

    
    
    /** These are the alerts that are displayed if there are any errors
     * 
     * @param alertType 
     */
    private void displayAlert(int alertType){
        
      Alert alert = new Alert(Alert.AlertType.ERROR);
        
    switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText("Form is not complete or has invalide values.");
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
        }          
              
              
    }
      
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     selectedPart =FXMLController.getPartToModify();
     
     
     if (selectedPart instanceof Inhouse) {
         inhouseButton.setSelected(true);
         radioButtonLabel.setText("Machine ID");
         partMachineIdTextField.setText(String.valueOf(((Inhouse)selectedPart).getMachineId()));
         
     }
     
     if (selectedPart instanceof Outsource) {
         outsourcedButton.setSelected(true);
         radioButtonLabel.setText("Company Name");
         partMachineIdTextField.setText(((Outsource)selectedPart).getCompanyName());
     }
     
     
     
     
     partIdTextField.setText(String.valueOf(selectedPart.getId()));
     partNameTextField.setText(String.valueOf(selectedPart.getName()));
     partInventoryTextField.setText(String.valueOf(selectedPart.getStock()));
     partPriceTextField.setText(String.valueOf(selectedPart.getPrice()));
     partMaxTextField.setText(String.valueOf(selectedPart.getMax()));
     partMinTextField.setText(String.valueOf(selectedPart.getMin()));
     
     
       
    sourceToggleGroup = new ToggleGroup();
    this.inhouseButton.setToggleGroup(sourceToggleGroup);
    this.outsourcedButton.setToggleGroup(sourceToggleGroup);
    
    
    
    }    
    
}
