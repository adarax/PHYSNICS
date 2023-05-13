/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class MainApp extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here      
        launch(args);
    }
    
    @Override
    public void start(Stage stage){
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uniform-circular-motion.fxml"));
        loader.setController(new UniformCircularMotionController());
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
        stage.setTitle("Uniform Circular Motion Controller");
        stage.setFullScreen(true);
        stage.show();
    }
}
