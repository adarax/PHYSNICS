/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import edu.vanier.physnics.App;
import edu.vanier.physnics.mainmenu.MainMenuController;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXSlider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private ImageView btnHelp;
    
    @FXML 
    private ImageView buttonHome;

    @FXML
    private CheckBox checkBoxFriction;

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
    private final String[] GRAVITATIONAL_CONSTANTS = {
        "Earth: 9.8", "Moon: 1.6", "Mars: 3.7", "Venus: 8.87", "Jupiter: 24.5", "Sun: 275"};
    
    /*
    values for u obtained from https://www.engineersedge.com/coeffients_of_friction.htm
    Ball is assumed to be made of steel (TODO: find better values)
    */
    private final String[] FRICTION_COEFFICIENTS = 
    {"Aluminium: 0.61", "Brass: 0.5", "Cast Iron: 0.4", "Copper: 0.53", "Steel: 0.8"};
    
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
    
    //text objects for different values
    private Text textHeight;
    private Text textMass;
    private Text textg;
    
    private boolean friction;
    
    private Ramp ramp;
    
    @FXML
    public void initialize(){
        
        //setup the ramp and the ball, and the values
        setup();
        
        
        btnPlay.setOnMouseClicked((e) -> {
            animBackend.play(ball, initialHeight, g);
        });
        
        btnPause.setOnMouseClicked((e) -> {
            animBackend.pause();
        });
        
        btnReset.setOnMouseClicked((e) -> {
            resetBall();
        });
        
        btnGraph.setOnMouseClicked((e) -> {
            //open graph window
        });
        
        btnHelp.setOnMouseClicked((e) -> {
        
        });
        
        buttonHome.setOnMouseClicked((e) -> {        
                openMainWindow();
        });
             
        sliderMass.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                mass = sliderMass.getValue();
                textMass.setText("Mass of the ball: " + mass + " kg");
                
            } 
        });
        
        sliderHeight.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                initialHeight = sliderHeight.getValue();
                textHeight.setText("Height: " + initialHeight + " m");
            } 
        });
        
        choiceBoxg.setOnAction((e) -> {
            g = getNumber(choiceBoxg.getValue());
            textg.setText("Gravitational\n acceleration: " + g + " m/s^2");
            
        });
        
        choiceBoxu.setOnAction((e) -> {
            u = getNumber(choiceBoxu.getValue());
        });
        
        checkBoxFriction.setOnAction((e) -> {
            friction = !friction;
            
        });
        
        
        
    }
    
    public void setup(){
        //initialize the animation backend
        animBackend = new AnimationBackend();
        
        //setup color of the ramp and the ball
        rampColor = Color.BLACK;
        ballColor = Color.RED;
        
        //initialize the ball and ramp
        ball = new Ball(20, ballColor);
        
        //no friction on initialize
        friction = false;
        
        //draw the ramp
        ramp = new Ramp(500, 20, width/2, height/2+300, rampColor);
        
        //set the path of the ball
        ramp.createBallPath(ball);
        
        paneAnimation.getChildren().addAll(ball, ramp);
        
        //add the options to the choiceboxes
        for(int i = 0; i<GRAVITATIONAL_CONSTANTS.length; i++){
            choiceBoxg.getItems().add(GRAVITATIONAL_CONSTANTS[i]);
        }
        choiceBoxg.setValue(GRAVITATIONAL_CONSTANTS[0]);
        
         //add the options to the choiceboxes
        for(int i = 0; i<FRICTION_COEFFICIENTS.length; i++){
            choiceBoxu.getItems().add(FRICTION_COEFFICIENTS[i]);
        }
        choiceBoxu.setValue(FRICTION_COEFFICIENTS[0]);
        
        //initializes the variables
        mass = 10;
        initialHeight = 10;
        g = 9.8;
        
        sliderMass.setValue(10);
        sliderHeight.setValue(10);
        
        ball.setMass(mass);
        
        setValueIndicators();
        
    }
    
    public void setValueIndicators(){
        //height text placed to the left of the ramp
        textHeight = new Text("Height: " + initialHeight + " m");
        textHeight.setFont(new Font("Times new roman", 30));
        paneAnimation.getChildren().add(textHeight);
        textHeight.setX(50);
        textHeight.setY(height/2);
        
        //mass text placed on top of the ramp
        textMass = new Text("Mass of the ball: " + mass + " kg");
        textMass.setFont(new Font("Times new roman", 30));
        paneAnimation.getChildren().add(textMass);
        textMass.setX(width/2-100);
        textMass.setY(height/7);
        
        //acceleration text placed to the right of the ramp
        textg = new Text("Gravitational\n acceleration: " + g + " m/s^2");
        textg.setFont(new Font("Times new roman", 25));
        paneAnimation.getChildren().add(textg);
        textg.setX(width-150);
        textg.setY(height/4);
        
        //draw arrowShaft and the two points for gravitational acceleration
        Line arrowShaft = new Line();
        arrowShaft.setStartY(300);
        arrowShaft.setStartX(width-75);
        arrowShaft.setEndX(width-75);
        arrowShaft.setEndY(height-200);
        arrowShaft.setStrokeWidth(5);
        
        Line leftPoint = new Line(width-75, height-200, width-50, height-220);
        leftPoint.setStrokeWidth(5);
        
        Line rightPoint = new Line(width-75, height-200, width-100, height-220);
        rightPoint.setStrokeWidth(5);
        
        paneAnimation.getChildren().addAll(arrowShaft, leftPoint, rightPoint);
        
    }
    
    public double getNumber(String option){
        String value = "";
        for(int i = 0; i<option.length(); i++){
            if(Character.isDigit(option.charAt(i)) || option.charAt(i) == '.' ){
                value += option.charAt(i);
            }
        }
            
        return Double.parseDouble(value);
    }
    
    private void resetBall(){
        animBackend.reset();
        paneAnimation.getChildren().remove(ball);
        ball = null;
        ball = new Ball(20, ballColor);
        ramp.createBallPath(ball);
        paneAnimation.getChildren().add(ball);
            
    }
    
    private void openMainWindow() {
        
    }
}
