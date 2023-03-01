/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

/**
 *
 * @author benja
 */
public class ConservationController {
    @FXML
    private Button btnFile;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnPause;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnReset;

    @FXML
    private CheckBox checkBoxFriction;

    @FXML
    private ChoiceBox<?> choiceBoxg;

    @FXML
    private ChoiceBox<?> choiceBoxu;

    @FXML
    private Slider sliderHeight;

    @FXML
    private Slider sliderMass;
    
    @FXML
    private Pane paneAnimation;
    
    @FXML
    public void initialize(){
        //setup the ramp and the ball
        setup();
        
        
        btnPlay.setOnAction((e) -> {
            
        });
        
        btnPause.setOnAction((e) -> {
            
        });
        
        btnReset.setOnAction((e) -> {
            
        });
        
        
        choiceBoxg.setOnAction((e) -> {
            
        });
        
    }
    
    public void setup(){
        Arc ramp = new Arc();
        System.out.println(paneAnimation.getLayoutX());
        System.out.println(paneAnimation.getHeight());
        //Setting the properties of the arc 
        ramp.setCenterX(500); 
        ramp.setCenterY(500); 
        ramp.setRadiusX(90f); 
        ramp.setRadiusY(90.0f); 
        ramp.setStartAngle(180); 
        ramp.setLength(100f); 
        ramp.setType(ArcType.OPEN);
        
        paneAnimation.getChildren().add(ramp);
    }
}
