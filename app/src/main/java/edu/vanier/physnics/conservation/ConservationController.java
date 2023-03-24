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
        
        
        btnMenu.setOnAction((e) -> {
            animBackend.play();
        });
        
        btnPause.setOnAction((e) -> {
            
        });
        
        btnReset.setOnAction((e) -> {
            
        });
        
    }
    
    public void setup(){
        //initialize the ball and ramp
        ball = new Ball();
        paneAnimation.getChildren().addAll(ball, rampAndBallPath());
        
        //initializes the variables
        mass = 10;
        initialHeight = 10;
        g = 9.8;
        
        ball.setMass(mass);
        
        animBackend.createAnimation(ball);
        
    }
    
    public Path rampAndBallPath(){
        //draw the ramp
        double[] initialPosition = {250, 250};
        double[] finalPosition = {1350, 250};
        double radiusInner = 550;
        double radiusOuter = 570;
        double[] ballPosition = {270, 270};
        
        Path ramp = new Path();
        ramp.setFill(rampColor);
        ramp.setStroke(rampColor);
        ramp.setFillRule(FillRule.EVEN_ODD);

        MoveTo initialMoveTo = new MoveTo();
        initialMoveTo.setX(initialPosition[0]);
        initialMoveTo.setY(initialPosition[1]);

        ArcTo arcToInner = new ArcTo();
        arcToInner.setX(width/2.0 + 550);
        arcToInner.setY(250);
        arcToInner.setRadiusX(radiusInner);
        arcToInner.setRadiusY(radiusInner);

        MoveTo moveTo2 = new MoveTo();
        moveTo2.setX(initialPosition[0]);
        moveTo2.setY(initialPosition[1]);

        HLineTo hLineToRightLeg = new HLineTo();
        hLineToRightLeg.setX(width/2.0 - 570);

        ArcTo arcTo = new ArcTo();
        arcTo.setX(width/2.0 + 570);
        arcTo.setY(finalPosition[1]);
        arcTo.setRadiusX(radiusOuter);
        arcTo.setRadiusY(radiusOuter);

        HLineTo hLineToLeftLeg = new HLineTo();
        hLineToLeftLeg.setX(finalPosition[0]);

        ramp.getElements().add(initialMoveTo);
        ramp.getElements().add(arcToInner);
        ramp.getElements().add(moveTo2);
        ramp.getElements().add(hLineToRightLeg);
        ramp.getElements().add(arcTo);
        ramp.getElements().add(hLineToLeftLeg);
        
        //set the path of the ball
        ball.setCenterX(initialPosition[0] + 20);
        ball.setCenterY(initialPosition[1] + 20);
        ball.setFill(ballColor);
        
        //create path for the ball
        
        //starting position
        MoveTo initialBallPos = new MoveTo();
        initialBallPos.setX(ballPosition[0]);
        initialBallPos.setY(ballPosition[1]);
        
        ArcTo ballArc = new ArcTo();
        ballArc.setX(width/2.0 + 550 - 20);
        ballArc.setY(ballPosition[1]);
        //I dont understand how this works
        ballArc.setRadiusX(200);
        ballArc.setRadiusY(200);
        ballArc.setXAxisRotation(180);
        
        Path ballPath = new Path();
        ballPath.getElements().add(initialBallPos);
        ballPath.getElements().add(ballArc);
        ball.setBallPath(ballPath);
        
        return ramp;

    }
    
}
