/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class UniformCircularMotionHelpController extends Stage{
    Stage primaryStage;

    public UniformCircularMotionHelpController(Stage stage) throws IOException {
        this.primaryStage = stage;
        
    }
    
    @FXML
    private MenuItem helpMenuCloseButton;
    
    public void initialize(){
        closeWindow();
    }
    
    public void closeWindow(){
        helpMenuCloseButton.setOnAction((event) -> {
            System.out.println("Closing window...");
            primaryStage.close();
        });        
    }
}
