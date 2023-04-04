package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * 
 *
 * @author MRINFINITI187
 */


/**
 * 
 * Creates an AddProductController class for Add Product FXML Scene.
*/
public class AddProductController implements Initializable {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    /**
     * Table for Parts
     */
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    
    /**
     * Parts that are associated with Products will be on this table
     */
    @FXML private TableView <Part> associatedPartTableView;
    @FXML private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> associatedPartPriceColumn;
   
    /**
     * Textfield for products
     */ 
    @FXML private TextField productIdTextField;
    @FXML private TextField productNameTextField;
    @FXML private TextField productInventoryTextField;
    @FXML private TextField productPriceTextField;
    @FXML private TextField productMaxTextField;
    @FXML private TextField productMinTextField;
    
    @FXML private TextField partSearchTextField;
    
    
    /** This returns user to main scene.
    * 
     * @param event return to Main Form Scene
     * @throws IOException From FXMLLoader if there is not a correct file in the getResoure
    */ 
    public void returnToMainFormScene(ActionEvent event) throws IOException {
        
        Parent parent =FXMLLoader.load(getClass().getResource("/View/FXML.fxml"));
        Scene scene = new Scene (parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    
   /** This Button Returns to the Main Form
    * 
     @param event return to Main Form Scene
     * @throws IOException From FXMLLoader 
    */ 
    public void addProductFormCancelButtonPushed(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Important");
        alert.setContentText("Do you want to cancel changes and return to Main Form?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainFormScene(event);
        }
    }
    
    /** *This is the add part method. When the user clicks the add button, the part that is selected in the part table is added to the associated part list and displayed in the bottom table.
    * 
     * @param event  add part
     * 
    */ 
    @FXML void addButtonPushed(ActionEvent event) {

        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {
            associatedParts.add(selectedPart);
            associatedPartTableView.setItems(associatedParts);
        }
    }
    
    /**
     * Removes part from associated parts table.
     * @param event Remove Part from table button action.
    */ 
     @FXML void removeButtonPushed(ActionEvent event) {

        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(4);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                associatedPartTableView.setItems(associatedParts);
            }
        }
    }
     
     /* Saves part to main scene and returns user back to main form. Fixed all exceptions by making sure that the information entered matches the correct values for the part properties. Also added an alert that will pop up if there are no values at all in the text boxes.
     * @param actionEvent saves a new part to the part table
     * @throws IOException Due to the format or value not being entered properly by user.
    */ 
    @FXML void saveButtonPushed(ActionEvent event) throws IOException {

        try {
            int id = 0;
            String name = productNameTextField.getText();
            Double price = Double.parseDouble(productPriceTextField.getText());
            int stock = Integer.parseInt(productInventoryTextField.getText());
            int min = Integer.parseInt(productMinTextField.getText());
            int max = Integer.parseInt(productMaxTextField.getText());

            if (name.isEmpty()) {
                displayAlert(6);
            } else {
                if (minValid(min, max) && inventoryValid(min, max, stock)) {

                    Product newProduct = new Product(id, name, stock, price, min, max);

                    for (Part part : associatedParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    newProduct.setId(Inventory.getNewProductId());
                    Inventory.addProduct(newProduct);
                    returnToMainFormScene(event);
                }
            }
        } catch (Exception e){
            displayAlert(1);
        }
    }
    /***
     * This is the part search method. This method searches the part table by part name or ID number for a specified part when the search button is clicked.
     * @param actionEvent the click on the search button.
     * 
    */ 
    
    @FXML void partSearchBtnAction(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchTextField.getText();

        for (Part part : allParts) {
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
    
    /** If there is nothing in this field it refreshes table to default setting. 
     * 
     * @param event
     * 
    */ 
    @FXML void partSearchKeyPressed(KeyEvent event) {

        if (partSearchTextField.getText().isEmpty()) {
            partsTableView.setItems(Inventory.getAllParts());
        }
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

        if (min <= 0 || min >= max) {
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
   
    private boolean inventoryValid(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(5);
        }

        return isValid;
    }
    
    /**
     * Displays alert messages
     * @param alertType Alert message selector.
     */
    
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1:
                alert.setTitle("Imporant");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alertInfo.setTitle("Important");
                alertInfo.setHeaderText("Part not found");
                alertInfo.showAndWait();
                break;
            case 3:
                alert.setTitle("Important");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Important");
                alert.setHeaderText("Part not selected");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Imporant");
                alert.setHeaderText("Invalid value for Inventory");
                alert.setContentText("Inventory must be a number equal to or between Min and Max");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Important");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
        }
    }
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    /** 
     * set up the columns in the table for parts Tabelview
     */
    partsTableView.setItems(Inventory.getAllParts());
    partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    partsTableView.setItems(Inventory.getAllParts());
    
    /**
     * set up the columns in the table for parts Tabelview
     */
    associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    associatedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        
        
    }  
    
    
    
}
