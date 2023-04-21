/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation.graphs;

import edu.vanier.physnics.conservation.Ball;
import edu.vanier.physnics.conservation.Settings;
import edu.vanier.physnics.mainmenu.MainMenuController;
import edu.vanier.physnics.projectilemotion.ProjectileGraphs;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    
    
    private Text textPotentialEnergy;
    private Text textKineticEnergy;
    private Text textVelocity;
    
    private double paneHeight = 400;
    private double paneWidth = 600;
    
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
                + "m/s");
        textVelocity.setFont(Settings.GRAPH_TEXT_FONT);
        textVelocity.setLayoutX(paneWidth/2-75);
        textVelocity.setLayoutY(50);
        
        textKineticEnergy = new Text("KE: "  
                + "J");
        textKineticEnergy.setFont(Settings.GRAPH_TEXT_FONT);
        textKineticEnergy.setLayoutX(paneWidth/2-100);
        textKineticEnergy.setLayoutY(paneHeight-50);
        
        textPotentialEnergy = new Text("PE: "  
                + "J");
        textPotentialEnergy.setFont(Settings.GRAPH_TEXT_FONT);
        textPotentialEnergy.setLayoutX(paneWidth/2+100);
        textPotentialEnergy.setLayoutY(paneHeight-50);
        
        
        PEGraph = new Rectangle(paneWidth/2+100, paneHeight-300, 50,200);
        PEGraph.setFill(Color.BLACK);
        
        KEGraph = new Rectangle(paneWidth/2-100, paneHeight-300, 50, 200);
        KEGraph.setFill(Color.RED);
        
        paneAnimation.getChildren().addAll(textVelocity, textKineticEnergy, 
                textPotentialEnergy, PEGraph, KEGraph);
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
        textKineticEnergy.setText("PE: "  
                + "J");
    }
    
    public void setVelocityText(double v){
        textVelocity.setText("Current velocity: "  + v + " m/s");
    }
    
    
}
