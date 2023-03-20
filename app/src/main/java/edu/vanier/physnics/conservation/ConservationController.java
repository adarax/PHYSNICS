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
            
        });
        
        btnPause.setOnAction((e) -> {
            
        });
        
        btnReset.setOnAction((e) -> {
            
        });
        
    }
    
    public void setup(){
        //draw the ramp
        Path ramp = new Path();
        ramp.setFill(rampColor);
        ramp.setStroke(rampColor);
        ramp.setFillRule(FillRule.EVEN_ODD);

        MoveTo moveTo = new MoveTo();
        moveTo.setX(width/2.0 - 550);
        moveTo.setY(250);

        ArcTo arcToInner = new ArcTo();
        arcToInner.setX(width/2.0 + 550);
        arcToInner.setY(250);
        arcToInner.setRadiusX(550);
        arcToInner.setRadiusY(550);

        MoveTo moveTo2 = new MoveTo();
        moveTo2.setX(width/2.0 - 550);
        moveTo2.setY(250);

        HLineTo hLineToRightLeg = new HLineTo();
        hLineToRightLeg.setX(width/2.0 - 570);

        ArcTo arcTo = new ArcTo();
        arcTo.setX(width/2.0 + 570);
        arcTo.setY(250);
        arcTo.setRadiusX(570);
        arcTo.setRadiusY(570);

        HLineTo hLineToLeftLeg = new HLineTo();
        hLineToLeftLeg.setX(width/2.0 + 550);

        ramp.getElements().add(moveTo);
        ramp.getElements().add(arcToInner);
        ramp.getElements().add(moveTo2);
        ramp.getElements().add(hLineToRightLeg);
        ramp.getElements().add(arcTo);
        ramp.getElements().add(hLineToLeftLeg);

        //initialize the ball
        ball = new Ball(ballColor, width/2.0-530, 270);
        paneAnimation.getChildren().addAll(ball, ramp);
        
        //initializes the variables
        mass = 10;
        initialHeight = 10;
        g = 9.8;
        
        ball.setMass(mass);
        
    }
    
    
}
