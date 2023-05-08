/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import edu.vanier.physnics.mainmenu.MainMenuController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *
 * @author vires
 */
public class GraphsController {
    
    @FXML
    private LineChart<?, ?> velocityX;
    
    @FXML
    private LineChart<?, ?> velocityY;
    
    @FXML 
    private LineChart<?, ?> accelerationY;
    
    @FXML
    private MFXButton exitGraphsButton;
    
    private Stage stage;
    
    
    private double velocityMetersPerSecond;
    private double launchAngleDegrees;
    private double gravityMetersPerSecondSquared;
    
    
    private double flightTimeSeconds;

    public GraphsController(double velocityMetersPerSecond, double launchAngleDegrees, double gravityMetersPerSecondSquared) {
        this.velocityMetersPerSecond = velocityMetersPerSecond;
        this.launchAngleDegrees = launchAngleDegrees;
        this.gravityMetersPerSecondSquared = gravityMetersPerSecondSquared;
           
        this.flightTimeSeconds = Equations.getFlightTime(launchAngleDegrees, velocityMetersPerSecond,gravityMetersPerSecondSquared);
    }

    /**
     * Used by the MainAppController to set the stage of the GraphsController. 
     * This is essential to access the properties of the stage from GraphsController.
     * @param stage The stage parameter is passed in from the MainAppController
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    /**
     * Initializes the action events for UI controls and populates the graphs that 
     * are already instantiated. 
     */
    @FXML
    public void initialize() {
        
        exitGraphsButton.setOnMouseClicked(leftClick -> {
            stage.close();
        });
        
        XYChart.Series seriesVelocityXTime = new XYChart.Series();
        seriesVelocityXTime.setName("Velocity X Vs Time");
        
        int numberOfPoints = 10;
        double timeBetweenPoints = flightTimeSeconds / numberOfPoints;
        
        for (int i = 0; i < numberOfPoints; i++) {
            double currentTime = i * timeBetweenPoints;
            double velocityXMetersPerSecond = Equations.getXVelocityMetersPerSecond(launchAngleDegrees, velocityMetersPerSecond);
            seriesVelocityXTime.getData().add(new XYChart.Data(String.valueOf(currentTime), velocityXMetersPerSecond));
        }
        
        velocityX.getData().add(seriesVelocityXTime);
    }
    

}
