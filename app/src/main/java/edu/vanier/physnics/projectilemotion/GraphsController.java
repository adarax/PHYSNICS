/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.text.DecimalFormat;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


/**
 * Populates the data in the LineCharts present in the graphs scene. Gets
 * values from sliders to graph accurately. 
 * 
 * @author vireshpatel43
 */
public class GraphsController {
    
    // List of elements present in the scene
    @FXML
    private LineChart<?, ?> velocityXvsTime;
    
    @FXML
    private LineChart<?, ?> velocityYvsTime;
    
    @FXML 
    private LineChart<?, ?> accelerationYvsTime;
    
    @FXML
    private MFXButton exitGraphsButton;
    
    private Stage stage;
    
    // Holds values obtained from sliders
    private double velocityMetersPerSecond;
    private double launchAngleDegrees;
    private double gravityMetersPerSecondSquared;
    // Holds the total time of simulation for x-axis of graphs
    private double flightTimeSeconds;

    /**
     * Constructor to obtain slider values from MainAppController()
     * @param velocityMetersPerSecond gets initial velocity
     * @param launchAngleDegrees gets launch angle
     * @param gravityMetersPerSecondSquared get gravitational acceleration
     */
    public GraphsController(double velocityMetersPerSecond, double launchAngleDegrees, double gravityMetersPerSecondSquared) {
        this.velocityMetersPerSecond = velocityMetersPerSecond;
        this.launchAngleDegrees = launchAngleDegrees;
        this.gravityMetersPerSecondSquared = gravityMetersPerSecondSquared;
        this.flightTimeSeconds = Equations.getFlightTimeSeconds(launchAngleDegrees, velocityMetersPerSecond,gravityMetersPerSecondSquared);
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
     * Initializes the action events for UI controls and populates the Charts in the
     * scene.
     */
    @FXML
    public void initialize() {
        
        // Closes the stage on button pressed
        exitGraphsButton.setOnMouseClicked(leftClick -> {
            stage.close();
        });
        
        // Creates series of data points for each chart
        XYChart.Series seriesVelocityXTime = new XYChart.Series();
        XYChart.Series seriesVelocityYTime = new XYChart.Series();
        XYChart.Series seriesAccelerationYTime = new XYChart.Series();
        
        // Sets name for line charts
        seriesVelocityXTime.setName("Velocity X Vs Time");
        seriesVelocityYTime.setName("Velocity Y Vs Time");
        seriesAccelerationYTime.setName("Acceleration Y Vs Time");
        
        // Number of points to be populated in graph
        final int NUMBER_OF_POINTS = 10;
        // gets the time between each point for the iterator. 
        double timeBetweenPoints = flightTimeSeconds / NUMBER_OF_POINTS;
        
        // Iterates through each data point and gets the value for that respective point
        for (int i = 0; i < NUMBER_OF_POINTS + 1; i++) {
            double currentTimeSeconds = i * timeBetweenPoints;
            // Rounds data to 2 decimal places 
            DecimalFormat round = new DecimalFormat("#.00");

            double velocityXMetersPerSecond = Equations.getXVelocityMetersPerSecond(launchAngleDegrees, velocityMetersPerSecond);
            // Adds the rounded data point to the series.
            seriesVelocityXTime.getData().add(new XYChart.Data(String.valueOf(round.format(currentTimeSeconds)), velocityXMetersPerSecond));
            double velocityYMetersPerSecond = Equations.getYVelocityMetersPerSecond(launchAngleDegrees, velocityMetersPerSecond, gravityMetersPerSecondSquared, currentTimeSeconds);
            // Adds the rounded data point to the series.
            seriesVelocityYTime.getData().add(new XYChart.Data(String.valueOf(round.format(currentTimeSeconds)), velocityYMetersPerSecond));
            double accelerationYMetersPerSecondSquared = gravityMetersPerSecondSquared;
            // Adds the rounded data point to the series.
            seriesAccelerationYTime.getData().add(new XYChart.Data(String.valueOf(round.format(currentTimeSeconds)), accelerationYMetersPerSecondSquared));
           
        }
        // Adds data to the FXML line charts
        velocityXvsTime.getData().add(seriesVelocityXTime);
        velocityYvsTime.getData().add(seriesVelocityYTime);
        accelerationYvsTime.getData().add(seriesAccelerationYTime);
    }
    

}
