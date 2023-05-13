/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation.graphs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Controller for the graph window.
 * Instantiates all FXML elements and provides their functionalities.
 * @author Benjamin Pratt
 */
public class ConservationGraphsController {
    @FXML
    private Button buttonClose;
    
    @FXML
    private Pane paneAnimation;
    
    private Rectangle potentialEnergyGraph;
    private Rectangle kineticEnergyGraph;
    
    private Text textPotentialEnergy;
    private Text textKineticEnergy;
    private Text textVelocity;
    private Text textCurrentHeight;
    private Text textTotalEnergy;
    
    private Line energyAxis;
 
    private Stage currentStage;
    
    /**
    * Sets up the UI elements in the graph window and gives functionality to the close button
    */
    @FXML
    private void initialize(){
        setup();
        
        buttonClose.setOnAction((clicked) -> {
            currentStage.hide();
        });
        
    }
    
    /**
     * Initializes all UI elements and places them in the pane
     */
    private void setup(){
        textVelocity = new Text("Current velocity: "  
                + " m/s");
        textVelocity.setFont(GraphSettings.GRAPH_TEXT_FONT);
        textVelocity.setLayoutX(GraphSettings.VELOCITY_TEXT_POSITION_X);
        textVelocity.setLayoutY(GraphSettings.VELOCITY_TEXT_POSITION_Y);
        
        textCurrentHeight = new Text("Current height: "  
                + " m");
        textCurrentHeight.setFont(GraphSettings.GRAPH_TEXT_FONT);
        textCurrentHeight.setLayoutX(GraphSettings.CURRENT_HEIGHT_TEXT_POSITION_X);
        textCurrentHeight.setLayoutY(GraphSettings.CURRENT_HEIGHT_TEXT_POSITION_Y);
        
        textKineticEnergy = new Text("Kinetic Energy: "  
                + " J");
        textKineticEnergy.setFont(GraphSettings.GRAPH_TEXT_FONT);
        textKineticEnergy.setLayoutX(GraphSettings.KINETIC_ENERGY_TEXT_POSITION_X);
        textKineticEnergy.setLayoutY(GraphSettings.KINETIC_ENERGY_TEXT_POSITION_Y);
        
        textPotentialEnergy = new Text("Potential Energy: "  
                + " J");
        textPotentialEnergy.setFont(GraphSettings.GRAPH_TEXT_FONT);
        textPotentialEnergy.setLayoutX(GraphSettings.POTENTIAL_ENERGY_TEXT_POSITION_X);
        textPotentialEnergy.setLayoutY(GraphSettings.POTENTIAL_ENERGY_TEXT_POSITION_Y);
        
        textTotalEnergy = new Text("Total energy: "  
                + " J");
        textTotalEnergy.setFont(GraphSettings.GRAPH_TEXT_FONT);
        textTotalEnergy.setLayoutX(GraphSettings.TOTAL_ENERGY_TEXT_POSITION_X);
        textTotalEnergy.setLayoutY(GraphSettings.TOTAL_ENERGY_TEXT_POSITION_Y);
        
        kineticEnergyGraph = new Rectangle(GraphSettings.KINETIC_ENERGY_GRAPH_POSITION_X, 
                GraphSettings.GRAPHS_POSITION_Y, GraphSettings.GRAPH_WIDTH, 
                GraphSettings.MAX_GRAPH_HEIGHT);
        kineticEnergyGraph.setFill(GraphSettings.KINETIC_ENERGY_GRAPH_COLOR);
        
        potentialEnergyGraph = new Rectangle(GraphSettings.POTENTIAL_ENERGY_GRAPH_POSITION_X, 
            GraphSettings.GRAPHS_POSITION_Y, GraphSettings.GRAPH_WIDTH, 
                GraphSettings.MAX_GRAPH_HEIGHT);
        potentialEnergyGraph.setFill(GraphSettings.POTENTIAL_ENERGY_GRAPH_COLOR);
        
        energyAxis = new Line(GraphSettings.ENERGY_AXIS_POSITION_X, GraphSettings.ENERGY_AXIS_START_POSITION_Y, 
                GraphSettings.ENERGY_AXIS_POSITION_X, GraphSettings.ENERGY_AXIS_END_POSITION_Y);
        energyAxis.setStrokeWidth(GraphSettings.ENERGY_AXIS_STROKE_WIDTH);
        
        Line leftPoint = new Line(GraphSettings.ENERGY_AXIS_POSITION_X, 
                GraphSettings.ENERGY_AXIS_START_POSITION_Y, 
                GraphSettings.ENERGY_AXIS_POSITION_X-GraphSettings.ENERGY_AXIS_WIDTH,
                GraphSettings.ENERGY_AXIS_START_POSITION_Y+GraphSettings.ENERGY_AXIS_WIDTH);
        leftPoint.setStrokeWidth(GraphSettings.ENERGY_AXIS_STROKE_WIDTH);
        
        Line rightPoint = new Line(GraphSettings.ENERGY_AXIS_POSITION_X, 
                GraphSettings.ENERGY_AXIS_START_POSITION_Y, 
                GraphSettings.ENERGY_AXIS_POSITION_X+GraphSettings.ENERGY_AXIS_WIDTH,
                GraphSettings.ENERGY_AXIS_START_POSITION_Y+GraphSettings.ENERGY_AXIS_WIDTH);
        rightPoint.setStrokeWidth(GraphSettings.ENERGY_AXIS_STROKE_WIDTH);
        
        paneAnimation.getChildren().addAll(textCurrentHeight,textKineticEnergy,textPotentialEnergy,textTotalEnergy,textVelocity,
                kineticEnergyGraph, potentialEnergyGraph, leftPoint, rightPoint, energyAxis);
        
    }
    
    /**
     * Shows the window when the graph button is clicked
     */
    public void show(){
        currentStage = (Stage) paneAnimation.getScene().getWindow();
        currentStage.show();
    }
    
    /**
     * Sets a new values of kinetic energy in the graph window
     * @param kineticEnergy
     */
    public void setKineticEnergyText(double kineticEnergy){
        textKineticEnergy.setText("Kinetic energy: "  
                + oneDecimalConverter(kineticEnergy) + "J");
    }

    /**
     * Sets a new values of total mechanical energy in the graph window
     * @param totalMechanicalEnergy
     */
    public void setTotalEnergyText(double totalMechanicalEnergy){
        textTotalEnergy.setText("Total energy: "  
                + oneDecimalConverter(totalMechanicalEnergy) + "J");
    }
    
    /**
     * Sets a new values of potential energy in the graph window
     * @param potentialEnergy
     */
    public void setPotentialEnergy(double potentialEnergy){
        textPotentialEnergy.setText("Potential energy: "  
                + oneDecimalConverter(potentialEnergy) + "J");
    }
    
    /**
     * Sets a new values of current velocity in the graph window
     * @param velocity
     */
    public void setVelocityText(double velocity){
        textVelocity.setText("Current velocity: "  + oneDecimalConverter(velocity) + " m/s");
    }
    
    /**
     * Sets a new values of current height in the graph window
     * @param height
     */
    public void setCurrentHeightText(double height){
        textCurrentHeight.setText("Current height: "  
                + oneDecimalConverter(height) + " m");
    }
    
    /**
     * Converts a double with many decimal points to only a single decimal point
     * @param value
     * @return
     */
    public String oneDecimalConverter(double value){
        String content = Double.toString(value);
        return content.substring(0, content.indexOf(".")+2);
    }
    

    /**
     * getter for potentialEnergyGraph
     * @return
     */
    public Rectangle getPotentialEnergyGraph() {
        return potentialEnergyGraph;
    }
    
    /**
     * setter for potentialEnergyGraph
     * @param potentialEnergyGraph
     */
    public void setPotentialEnergyGraph(Rectangle potentialEnergyGraph) {
        this.potentialEnergyGraph = potentialEnergyGraph;
    }

    /**
     * getter for kineticEnergyGraph
     * @return
     */
    public Rectangle getKineticEnergyGraph() {
        return kineticEnergyGraph;
    }

    /**
     * setter for kineticEnergyGraph
     * @param kineticEnergyGraph
     */
    public void setKineticEnergyGraph(Rectangle kineticEnergyGraph) {
        this.kineticEnergyGraph = kineticEnergyGraph;
    }
    
    
}
