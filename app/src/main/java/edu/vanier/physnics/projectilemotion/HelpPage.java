/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 * Class used to open the Help Page
 * 
 * @author vireshpatel43
 */
public class HelpPage {
    /**
     * Loads the scene graph into a ScrollPane. Sets the graph to a scene. 
     * Adds scene to stage.
     */
    public void openHelpWindow() {
        // Loads the fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/projectileHelpPage.fxml"));
        ScrollPane root = null;
        // Sets controller for scene 
        HelpPageController helpPageController = new HelpPageController();
        loader.setController(helpPageController);
        
        // Loads scene graph to ScrollPane
        try {
            root = loader.load();
        } catch (IOException ex) { /// abbreviated variable name
            Logger.getLogger(HelpPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Creates scene based on scene graph
        Scene helpWindow = new Scene(root);
        
        // Sets scene to Stage
        Stage stage = new Stage();
        stage.setScene(helpWindow);
        stage.sizeToScene();
        stage.setTitle("Help Page");
        stage.show();
        
    }
}
