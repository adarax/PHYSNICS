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
 * This class is used to display the help page when the user clicks on the help button.
 * @author Victor-Pen
 */
public class UniformCircularMotionHelpController extends Stage{
    /**
     * The button to close the help page.
     */
    @FXML
    private MenuItem helpMenuCloseButton;
    /**
     * The stage to set the help page.
     */
    Stage primaryStage;

    /**
     * Constructor for the class that instantiates it.
     * @param stage the stage that will pop up the help page
     * @throws IOException 
     */
    public UniformCircularMotionHelpController(Stage stage) throws IOException {
        this.primaryStage = stage;
    }
    
    /**
     * Initializes the methods in it.
     */
    public void initialize(){
        closeWindow();
    }
    
    /**
     * Closes the help page when the close button is clicked on.
     */
    public void closeWindow(){
        helpMenuCloseButton.setOnAction((event) -> {
            System.out.println("Closing window...");
            primaryStage.close();
        });        
    }
}
