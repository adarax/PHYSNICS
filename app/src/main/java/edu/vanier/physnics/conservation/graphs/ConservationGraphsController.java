/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation.graphs;

import edu.vanier.physnics.conservation.Ball;
import edu.vanier.physnics.conservation.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 *
 * @author 2132170
 */
public class ConservationGraphsController {
    @FXML
    private Button buttonClose;
    
    @FXML
    private Pane paneAnimation;
    
    private Rectangle PEGraph;
    private Rectangle KEGraph;
    private Rectangle FrictionGraph;
    
    
    private Text textPotentialEnergy;
    private Text textKineticEnergy;
    private Text textVelocity;
    private Text textCurrentHeight;
    private Text textFrictionEnergy;
    
   
    
    private Ball ball;
    
    private Stage currentStage;
    
    public ConservationGraphsController(Ball ball){
        this.ball = ball;
        
    }
   
    @FXML
    private void initialize(){
        setup();
        
        buttonClose.setOnAction((eventHandler) -> {
            currentStage.hide();
        });
        
    }
    
    private void setup(){
        textVelocity = new Text("Current velocity: "  
                + " m/s");
        textVelocity.setFont(Settings.GRAPH_TEXT_FONT);
        textVelocity.setLayoutX(GraphSettings.VELOCITY_TEXT_POSITION_X);
        textVelocity.setLayoutY(GraphSettings.VELOCITY_TEXT_POSITION_Y);
        
        textCurrentHeight = new Text("Current height: "  
                + " m");
        textCurrentHeight.setFont(Settings.GRAPH_TEXT_FONT);
        textCurrentHeight.setLayoutX(GraphSettings.CURRENT_HEIGHT_TEXT_POSITION_X);
        textCurrentHeight.setLayoutY(GraphSettings.CURRENT_HEIGHT_TEXT_POSITION_Y);
        
        textKineticEnergy = new Text("Kinetic Energy: "  
                + " J");
        textKineticEnergy.setFont(Settings.GRAPH_TEXT_FONT);
        textKineticEnergy.setLayoutX(GraphSettings.KINETIC_ENERGY_TEXT_POSITION_X);
        textKineticEnergy.setLayoutY(GraphSettings.KINETIC_ENERGY_TEXT_POSITION_Y);
        
        textPotentialEnergy = new Text("Potential Energy: "  
                + " J");
        textPotentialEnergy.setFont(Settings.GRAPH_TEXT_FONT);
        textPotentialEnergy.setLayoutX(GraphSettings.POTENTIAL_ENERGY_TEXT_POSITION_X);
        textPotentialEnergy.setLayoutY(GraphSettings.POTENTIAL_ENERGY_TEXT_POSITION_Y);
        
        textFrictionEnergy = new Text("Friction energy: "  
                + " J");
        textFrictionEnergy.setFont(Settings.GRAPH_TEXT_FONT);
        textFrictionEnergy.setLayoutX(GraphSettings.FRICTION_ENERGY_TEXT_POSITION_X);
        textFrictionEnergy.setLayoutY(GraphSettings.FRICTION_ENERGY_TEXT_POSITION_Y);
        
        KEGraph = new Rectangle(GraphSettings.KINETIC_ENERGY_GRAPH_POSITION_X, 
                GraphSettings.GRAPHS_POSITION_Y, GraphSettings.GRAPH_WIDTH, 
                GraphSettings.MAX_GRAPH_HEIGHT);
        KEGraph.setFill(GraphSettings.KINETIC_ENERGY_GRAPH_COLOR);
        
        PEGraph = new Rectangle(GraphSettings.POTENTIAL_ENERGY_GRAPH_POSITION_X, 
            GraphSettings.GRAPHS_POSITION_Y, GraphSettings.GRAPH_WIDTH, 
                GraphSettings.MAX_GRAPH_HEIGHT);
        PEGraph.setFill(GraphSettings.POTENTIAL_ENERGY_GRAPH_COLOR);
        
        FrictionGraph = new Rectangle(GraphSettings.FRICTION_ENERGY_GRAPH_POSITION_x, 
                GraphSettings.GRAPHS_POSITION_Y, GraphSettings.GRAPH_WIDTH, 
                GraphSettings.MAX_GRAPH_HEIGHT);
        FrictionGraph.setFill(GraphSettings.FRICTION_ENERGY_GRAPH_COLOR);
        
        paneAnimation.getChildren().addAll(textVelocity, textKineticEnergy, 
                textPotentialEnergy, PEGraph, KEGraph, textCurrentHeight,textFrictionEnergy, FrictionGraph);
    }
    
    public void show(){
        currentStage = (Stage) paneAnimation.getScene().getWindow();
        currentStage.show();
    }

    public Rectangle getPEGraph() {
        return PEGraph;
    }

    public void setPEGraph(Rectangle PEGraph) {
        this.PEGraph = PEGraph;
    }

    public Rectangle getKEGraph() {
        return KEGraph;
    }

    public void setKEGraph(Rectangle KEGraph) {
        this.KEGraph = KEGraph;
    }
    
    public void setKeText(double ke){
        textKineticEnergy.setText("KE: "  
                + twoDecimalConverter(ke) + "J");
    }
    
    public void setPeText(double pe){
        textPotentialEnergy.setText("PE: "  
                + twoDecimalConverter(pe) + "J");
    }
    
    public void setVelocityText(double v){
        textVelocity.setText("Current velocity: "  + twoDecimalConverter(v) + " m/s");
    }
    
    public void setCurrentHeightText(double height){
        textCurrentHeight.setText("Current height: "  
                + twoDecimalConverter(height) + " m");
    }
    
    public String twoDecimalConverter(double value){
        String content = Double.toString(value);
        return content.substring(0, content.indexOf(".")+2);
    }
    
}
