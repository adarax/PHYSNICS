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
import javafx.application.Platform;
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
import javafx.scene.control.MenuItem;
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
    private ImageView btnGraph;

    @FXML
    private MenuItem menuItemQuit;

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
    
   
    
    //color of the ramp and the ball
    private Color rampColor;
    private Color ballColor;
    
    //ball object
    private Ball ball;
    
    //physics variables (TODO: add units to variables and rename)
    private double g; //m/s^2
    private double u; //no units
    private double rampHeight; //m
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
            animBackend.play(ball, rampHeight, g);
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
                rampHeight = sliderHeight.getValue();
                textHeight.setText("Height: " + rampHeight + " m");
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
        
        menuItemQuit.setOnAction((e) -> {
            Platform.exit();
        });
        
        
        
    }
    
    public void setup(){
        //initialize the animation backend
        animBackend = new AnimationBackend();
        
        //setup color of the ramp and the ball
        rampColor = Color.BLACK;
        ballColor = Color.RED;
        
        //initialize the ball and ramp
        ball = new Ball(Settings.BALL_RADIUS, ballColor);
        
        //no friction on initialize
        friction = false;
        
        //draw the ramp
        ramp = new Ramp(Settings.RAMP_RADIUS, Settings.RAMP_THICKNESS, 
                Settings.RAMP_POSITION_X, Settings.RAMP_POISTION_Y, rampColor);
        
        //set the path of the ball
        ramp.createBallPath(ball);
        
        paneAnimation.getChildren().addAll(ball, ramp);
        
        //add the options to the choiceboxes
        for(int i = 0; i<Settings.GRAVITATIONAL_CONSTANTS.length; i++){
            choiceBoxg.getItems().add(Settings.GRAVITATIONAL_CONSTANTS[i]);
        }
        choiceBoxg.setValue(Settings.GRAVITATIONAL_CONSTANTS[0]);
        
         //add the options to the choiceboxes
        for(int i = 0; i<Settings.FRICTION_COEFFICIENTS.length; i++){
            choiceBoxu.getItems().add(Settings.FRICTION_COEFFICIENTS[i]);
        }
        choiceBoxu.setValue(Settings.FRICTION_COEFFICIENTS[0]);
        
        //initializes the variables
        mass = 10;
        rampHeight = 10;
        g = 9.8;
        
        sliderMass.setValue(mass);
        sliderHeight.setValue(rampHeight);
        
        ball.setMass(mass);
        
        setValueIndicators();
        
    }
    
    public void setValueIndicators(){
        //height text placed to the left of the ramp
        textHeight = new Text("Height: " + rampHeight + " m");
        textHeight.setFont(new Font(Settings.TEXT_FONT, Settings.FONT_SIZE));
        paneAnimation.getChildren().add(textHeight);
        textHeight.setX(Settings.HEIGHT_TEXT_POSITION_X);
        textHeight.setY(Settings.HEIGHT_TEXT_POSITION_Y);
        
        //mass text placed on top of the ramp
        textMass = new Text("Mass of the ball: " + mass + " kg");
        textMass.setFont(new Font(Settings.TEXT_FONT, Settings.FONT_SIZE));
        paneAnimation.getChildren().add(textMass);
        textMass.setX(Settings.MASS_TEXT_POSITION_X);
        textMass.setY(Settings.MASS_TEXT_POSITION_Y);
        
        //acceleration text placed to the right of the ramp
        textg = new Text("Gravitational\n acceleration: \n" + g + " m/s^2");
        textg.setFont(new Font(Settings.TEXT_FONT, Settings.FONT_SIZE));
        paneAnimation.getChildren().add(textg);
        textg.setX(Settings.ACCELERATION_TEXT_POSITION_X);
        textg.setY(Settings.ACCELERATION_TEXT_POSITION_Y);
       
        drawArrow(Settings.ARROW_POSITION_X, 
                Settings.ARROW_POSITION_Y, Settings.ARROW_LENGTH, 
                Settings.ARROW_THICKNESS, Settings.ARROW_POINT_WIDTH);
        
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
        ball = new Ball(Settings.BALL_RADIUS, ballColor);
        ramp.createBallPath(ball);
        paneAnimation.getChildren().add(ball);
            
    }
    
    private void openMainWindow() {
        Stage stage = (Stage) paneAnimation.getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainmenu.fxml"));
        MainMenuController mwc = new MainMenuController(stage);
        loader.setController(mwc);
        
        Scene scene = null;
         try {
             scene = new Scene(loader.load());
         } catch (IOException ex) {
            System.out.println("Main stage could not be opened");
         }
         stage.setScene(scene);
        
        stage.show();
    }
    
    public void drawArrow(double posx, double posy, double length, double width, double pointWidth){
        //draw arrowShaft and the two points for gravitational acceleration
        Line arrowShaft = new Line();
        arrowShaft.setStartY(posy);
        arrowShaft.setStartX(posx);
        arrowShaft.setEndX(posx);
        arrowShaft.setEndY(posy+length);
        arrowShaft.setStrokeWidth(width);
        
        Line leftPoint = new Line(posx, posy+length, posx+pointWidth , posy+length-pointWidth);
        leftPoint.setStrokeWidth(width);
        
        Line rightPoint = new Line(posx, posy+length, posx-pointWidth , posy+length-pointWidth);
        rightPoint.setStrokeWidth(width);
        
        paneAnimation.getChildren().addAll(arrowShaft, leftPoint, rightPoint);
    }
}
