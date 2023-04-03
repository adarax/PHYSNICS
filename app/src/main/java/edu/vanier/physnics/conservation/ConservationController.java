/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXSlider;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author benja
 */
public class ConservationController {
     @FXML
    private Button btnFile;

    @FXML
    private ImageView btnGraph;

    @FXML
    private Button btnMenu;

    @FXML
    private ImageView btnPause;

    @FXML
    private ImageView btnPlay;

    @FXML
    private ImageView btnReset;

    @FXML
    private MFXCheckbox checkBoxFriction;

    @FXML
    private ChoiceBox<String> choiceBoxg;

    @FXML
    private ChoiceBox<String> choiceBoxu;

    @FXML
    private Pane paneAnimation;

    @FXML
    private MFXSlider sliderHeight;

    @FXML
    private MFXSlider sliderMass;
    
    //values obtained from https://space.nss.org/settlement/nasa/teacher/lessons/bryan/microgravity/gravback.html
    
    private final String[] gravitationalConstants = {
        "Earth: 9.8", "Moon: 1.6", "Mars: 3.7", "Venus: 8.87", "Jupiter: 24.5", "Sun: 275"};
    
    //width and height of the animation pane
    private double width = 1480;
    private double height = 790;
    
    //color of the ramp and the ball
    private Color rampColor;
    private Color ballColor;
    
    //ball object
    private Ball ball;
    
    //physics variables (TODO: add units to variables and rename)
    private double g; //m/s^2
    private double u; //no units
    private double initialHeight; //m
    private double mass; // kg
    
    //object to generate the animation of the ball
    private AnimationBackend animBackend;
    
    @FXML
    public void initialize(){
        //setup color of the ramp and the ball
        rampColor = Color.BLACK;
        ballColor = Color.RED;
        
        //initialize the animation backend
        animBackend = new AnimationBackend();
        
        //setup the ramp and the ball
        setup();
        
        
        btnPlay.setOnMouseClicked((e) -> {
            animBackend.play(ball, initialHeight, g);
        });
        
        btnPause.setOnMouseClicked((e) -> {
            animBackend.pause();
        });
        
        btnReset.setOnMouseClicked((e) -> {
            animBackend.reset();
            ball.reset();
        });
        
        sliderMass.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                mass = sliderMass.getValue();
                
            } 
        });
        
        sliderHeight.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                height = sliderHeight.getValue();
            } 
        });
        
        choiceBoxg.setOnAction((e) -> {
            g = getChoiceBoxValue(choiceBoxg.getValue());
        });
        
    }
    
    public void setup(){
        //initialize the ball and ramp
        ball = new Ball(20, ballColor);
        
        //draw the ramp
        Ramp ramp = new Ramp(500, 20, width/2, height/2+300, rampColor);
        
        //set the path of the ball
        ramp.createBallPath(ball);
        
        paneAnimation.getChildren().addAll(ball, ramp);
        
        //add the options to the choiceboxes
        for(int i = 0; i<gravitationalConstants.length; i++){
            choiceBoxg.getItems().add(gravitationalConstants[i]);
        }
        
        
        //initializes the variables
        mass = 10;
        initialHeight = 10;
        g = 9.8;
        
        sliderMass.setValue(10);
        sliderHeight.setValue(10);
        
        ball.setMass(mass);
        
        
        
    }
    
    public double getChoiceBoxValue(String option){
        String value = "";
        for(int i = 0; i<option.length(); i++){
            if(Character.isDigit(option.charAt(i)) || option.charAt(i) == '.' ){
                value += option.charAt(i);
            }
        }
            
        return Double.parseDouble(value);
    }
}
