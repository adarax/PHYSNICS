package edu.vanier.physnics;

import edu.vanier.physnics.conservation.ConservationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public Stage mainMenu;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConservationController controller = new ConservationController();
        mainMenu = primaryStage;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/conservation.fxml"));

        
        loader.setController(controller);
        
        Parent root = loader.load();               
        Scene scene = new Scene(root, 1980, 1080);
        
        mainMenu.setScene(scene);        
        mainMenu.setMaximized(true);
        mainMenu.sizeToScene();
       
        mainMenu.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
