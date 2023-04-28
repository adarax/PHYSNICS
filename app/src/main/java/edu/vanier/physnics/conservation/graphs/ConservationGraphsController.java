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
    
    private double paneHeight = 400;
    private double paneWidth = 800;
    
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
        textVelocity.setLayoutX(paneWidth/2-75);
        textVelocity.setLayoutY(50);
        
        textCurrentHeight = new Text("Current height: "  
                + " m");
        textCurrentHeight.setFont(Settings.GRAPH_TEXT_FONT);
        textCurrentHeight.setLayoutX(50);
        textCurrentHeight.setLayoutY(50);
        
        textFrictionEnergy = new Text("Friction energy: "  
                + " J");
        textFrictionEnergy.setFont(Settings.GRAPH_TEXT_FONT);
        textFrictionEnergy.setLayoutX(50);
        textFrictionEnergy.setLayoutY(paneHeight-50);
        
        textKineticEnergy = new Text("Kinetic Energy: "  
                + " J");
        textKineticEnergy.setFont(Settings.GRAPH_TEXT_FONT);
        textKineticEnergy.setLayoutX(paneWidth/2-150);
        textKineticEnergy.setLayoutY(paneHeight-50);
        
        textPotentialEnergy = new Text("Potential Energy: "  
                + " J");
        textPotentialEnergy.setFont(Settings.GRAPH_TEXT_FONT);
        textPotentialEnergy.setLayoutX(paneWidth/2+50);
        textPotentialEnergy.setLayoutY(paneHeight-50);
        
        
        PEGraph = new Rectangle(paneWidth/2+100, paneHeight-300, 50,200);
        PEGraph.setFill(Color.BLACK);
        
        KEGraph = new Rectangle(paneWidth/2-100, paneHeight-300, 50, 200);
        KEGraph.setFill(Color.RED);
        
        FrictionGraph = new Rectangle(50, paneHeight-300, 50, 200);
        FrictionGraph.setFill(Color.TEAL);
        
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
                + (int) ke + "J");
    }
    
    public void setPeText(double pe){
        textPotentialEnergy.setText("PE: "  
                + (int) pe + "J");
    }
    
    public void setVelocityText(double v){
        textVelocity.setText("Current velocity: "  + (int)v + " m/s");
    }
    
    
}
