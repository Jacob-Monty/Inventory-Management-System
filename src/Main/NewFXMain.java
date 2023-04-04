package Main;

/*
 * JavaDoc is located after opening main zip File. Clicking JavaProject. There it should be located. 
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Parent;


/**
 *
 * @author MRINFINITI187
 */

/** 
 * This Class creates the Main Form of the Inventory Management.<p>
 * 
 * FUTURE ENHANCEMENT: One addition would be to update features to extend its usability. Add a section for tracking pricing. <p>
 *
 * JavaDoc is located after opening main zip File. Clicking JavaProject. There it should be located.
 */
public class NewFXMain extends Application {
    
/** 
 * This is the start method. Method loads the main form of the application.
 * @param Stage main stage of the program.
 * @throws Exception if the incorrect file is selected from the FXML loader
 */    
    
    @Override
    public void start(Stage Stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/FXML.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage.setScene(scene);
        Stage.show();
      
    }

    /**
     * This is the main method. Method launches the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
