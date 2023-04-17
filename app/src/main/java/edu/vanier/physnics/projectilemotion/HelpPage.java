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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author vires
 */
public class HelpPage {
    public void openHelpWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/projectileHelpPage.fxml"));
        ScrollPane root = null;
        
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(HelpPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Scene helpWindow = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(helpWindow);
        stage.sizeToScene();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
    }
}
