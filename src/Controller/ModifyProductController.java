package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.FXMLController;
import Model.Part;
import Model.Product;
import Model.Inventory;
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
 * FXML Controller class
 *
 * @author 
 * MRINFINITI187
 */

/** Creates a ModifyProductController class for the Modify Product FXML Scene*/

public class ModifyProductController implements Initializable {
    
    private Product selectedProduct;
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    //Table for Parts
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    
    // Parts that are associated with Products will be on this table
    @FXML private TableView <Part> associatedPartTableView;
    @FXML private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartInventoryLevelColumn;
    @FXML private TableColumn<Part, Double> associatedPartPriceColumn;
   
    // Textfield for products
    @FXML private TextField productIdTextField;
    @FXML private TextField productNameTextField;
    @FXML private TextField productInventoryTextField;
    @FXML private TextField productPriceTextField;
    @FXML private TextField productMaxTextField;
    @FXML private TextField productMinTextField;
    
    @FXML private TextField partSearchTextField;
    
    
    /**
     * This will send user to main screen
     @param event send user to main screen
     * @throws IOException From FXMLLoader if there is not a correct file for the getResource.
    */
    
    public void returnToMainFormScene(ActionEvent event) throws IOException {
        
        Parent parent =FXMLLoader.load(getClass().getResource("/View/FXML.fxml"));
        Scene scene = new Scene (parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    
   /**This Button Returns to the Main Form
    * 
     * @param event when pushed cancels changes
     * @throws IOException From FXMLLoader if there is not a correct file for the getResource.
    */ 
    public void modifyProductFormCancelButtonPushed(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Important");
        alert.setContentText("Do you want to cancel changes and return to Main Form?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainFormScene(event);
        }
    }
    
     /**This will add part to associated parts table view. Will throw an exception if there is no part selected. It will give the user an alert to select a part.
    * 
     * @param event Add button action pushed.
     * @throws IOException if no part is selected.
    */ 
    @FXML  public void addButtonPushed(ActionEvent event) throws IOException {

        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {
            associatedParts.add(selectedPart);
            associatedPartTableView.setItems(associatedParts);
        }
    }
    
    
     /**This Button removes associated parts from the associated parts table view. will throw an exception if there is no part selected. Created an alert to stop user from continuing unless selecting a part.
    * 
     * @param event Removed button is pushed.
     * @throws IOException if there is not associated part selected.
    */ 
     @FXML 
        public void removeButtonPushed(ActionEvent event) throws IOException {

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
      /**This Button will save any product info and also send the user to the main scene. Takes information from the text field and save it. Will stop user from continuing if information is not in correct format.
    * 
     * @param event
     * @throws IOException if the entered information does not match the format for the products. 
    */ 
    @FXML void saveButtonPushed(ActionEvent event) throws IOException {

        try {
            int id = selectedProduct.getId();
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

                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(selectedProduct);
                    returnToMainFormScene(event);
                }
            }
        } catch (Exception e){
            displayAlert(1);
        }
    }
    
     /**This allows user to search for products in product table view.
    * 
     * @param event
     * @throws java.io.IOException if there
    */ 
    
    @FXML
    void partSearchBtnAction(ActionEvent event) {

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
     /**This refreshes the table views when there is no string in this box.
     * @param event
     * 
    */ 
    
    @FXML
    void partSearchKeyPressed(KeyEvent event) {

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
    
    /* These are the alerts for any issues that may arise.
     *
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
        
    selectedProduct = FXMLController.getProductToModify();
    associatedParts = selectedProduct.getAllAssociatedParts();
        
    //set up the columns in the table for parts Tabelview
    
    partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    partsTableView.setItems(Inventory.getAllParts());
    
    //set up the columns in the table for parts Tabelview
    associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    associatedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    associatedPartTableView.setItems(associatedParts);
    
     productIdTextField.setText(String.valueOf(selectedProduct.getId()));
     productNameTextField.setText(String.valueOf(selectedProduct.getName()));
     productInventoryTextField.setText(String.valueOf(selectedProduct.getStock()));
     productPriceTextField.setText(String.valueOf(selectedProduct.getPrice()));
     productMaxTextField.setText(String.valueOf(selectedProduct.getMax()));
     productMinTextField.setText(String.valueOf(selectedProduct.getMin()));
    
    
    
        
    }    
    
}
