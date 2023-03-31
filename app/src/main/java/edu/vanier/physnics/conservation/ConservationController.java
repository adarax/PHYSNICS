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
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

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
    
    //width and height of the animation pane
    private double width = 1600;
    private double height = 880;
    
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
        
        
        btnPlay.setOnAction((e) -> {
            animBackend.play();
        });
        
        btnPause.setOnAction((e) -> {
            animBackend.pause();
        });
        
        btnReset.setOnAction((e) -> {
            animBackend.stop();
            ball.setCenterX(270);
            ball.setCenterY(270);
        });
        
    }
    
    public void setup(){
        //initialize the ball and ramp
        ball = new Ball(20, ballColor);
        
        //draw the ramp
        Ramp ramp = new Ramp(500, 20, width/2, height/2+400, rampColor);
        //set the path of the ball
        ramp.createBallPath(ball);
        
        paneAnimation.getChildren().addAll(ball, ramp);
        
        //initializes the variables
        mass = 10;
        initialHeight = 10;
        g = 9.8;
        
        ball.setMass(mass);
        
        animBackend.createAnimation(ball);
        
    }
    
}
