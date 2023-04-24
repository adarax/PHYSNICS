/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UCMSimulation;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 * @author Admin
 */
public class UCMController extends Stage{
        
    @FXML
    Button pauseButton;
    @FXML
    Button playButton;
    @FXML
    Button resetButton;  
    @FXML
    TextField radiusTextField;
    @FXML
    Slider radiusSlider;
    @FXML
    TextField speedTextField;
    @FXML
    Slider speedSlider;
    @FXML
    TextField massTextField;
    @FXML
    Slider massSlider;
    @FXML
    Button submitButton;
    @FXML
    Text centrAccelText;
    @FXML
    Text forceText;
    @FXML
    Pane paneUCMSimulate;
    
    Car car = new Car();
    Path path1 = new Path();
    Path path2 = new Path();
    PathTransition pathTransitionCircle = new PathTransition();
    Rectangle rectTest = new Rectangle(50,30, Color.ORANGE);
    Group group = new Group();    
    
    @FXML
    void initialize(){
        System.out.println("Booting up simulation...");
        submitSimulation();
        setUp();
    }
    
    @FXML
    public double retrieveRadiusTextField(){
        double d = 0;
        try {
            d = Double.valueOf(radiusTextField.getText());
        } catch (Exception e) {
            System.out.println("Error");
        }
        return d;
    }
    
    @FXML
    public double retrieveSpeedTextField(){
        double d = 0;
        try {
            d = Double.valueOf(speedTextField.getText());
        } catch (Exception e) {
            System.out.println("Error");
        }
        return d;
    }

    @FXML
    public double retrieveMassTextField(){
        double d = 0;
        try {
            d = Double.valueOf(massTextField.getText());
        } catch (Exception e) {
            System.out.println("Error");
        }
        return d;
    }
    
    @FXML
    void pause(){
        pauseButton.setOnAction((event) -> {
            System.out.println("pausing...");
            pauseButton.setDisable(true);
            playButton.setDisable(false);
            resetButton.setDisable(false);
            pathTransitionCircle.pause();
        });
    }
    
    @FXML
    void play(){
        playButton.setOnAction((event) -> {
            System.out.println("playing...");
            playButton.setDisable(true);
            pauseButton.setDisable(false);
            resetButton.setDisable(false);
            pathTransitionCircle.play();
        });
    }

    @FXML
    void reset(){
        resetButton.setOnAction((event) -> {
            System.out.println("resetting...");
            resetButton.setDisable(true);
            pauseButton.setDisable(true);
            playButton.setDisable(true);
            submitButton.setDisable(false);
            group.getChildren().removeAll(rectTest, path1);
        });
    }    
    
    @FXML
    void submitSimulation(){
        submitButton.setOnAction((event) -> {
            useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
            pauseButton.setDisable(false);
            resetButton.setDisable(false);
            playButton.setDisable(true);
            submitButton.setDisable(true);
            pathTransitionCircle.setDuration(Duration.seconds(20/car.getSpeed()));            
            revolveCar();
        });
    }
    
    @FXML
    public void setUp(){
        pauseButton.setDisable(true);
        resetButton.setDisable(true);
        playButton.setDisable(true);
        radiusTextField.setText("5");
        massTextField.setText("10");
        speedTextField.setText("10");
        radiusSlider.setValue(5);
        massSlider.setValue(10);
        speedSlider.setValue(10);
        useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());
        Circle center = new Circle(250, 250, 2, Color.RED);
        paneUCMSimulate.getChildren().add(center);  
        setSliders();
        pause();
        play();
        reset();
    }
    
    public void revolveCar(){      
        rectTest.setLayoutX(200);
        rectTest.setLayoutY(160); 
        
        path1 = createEllipsePath(250, 90, 200, 200, 0);
        pathTransitionCircle = new PathTransition();
        pathTransitionCircle.setDuration(Duration.seconds(50/car.getSpeed()));
        pathTransitionCircle.setPath(path1);
        pathTransitionCircle.setNode(rectTest);
        pathTransitionCircle.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransitionCircle.setCycleCount(Timeline.INDEFINITE);
        pathTransitionCircle.setAutoReverse(false);
        pathTransitionCircle.setInterpolator(Interpolator.LINEAR);      

        Point2D accelVector = new Point2D(300, 300);

        
        path2 = createEllipsePath(450, 250, 200, 200, 0);
        // group.getChildren().addAll(rectTest, path2, accelVector);               
        paneUCMSimulate.getChildren().add(group);
        
        pathTransitionCircle.play();
    }
    
    private Path createEllipsePath(double centerX, double centerY, double radiusX, double radiusY, double rotate)
    {
        ArcTo arcTo = new ArcTo();
        arcTo.setX(centerX - radiusX + 1); // to simulate a full 360 degree celcius circle.
        arcTo.setY(centerY - radiusY);
        arcTo.setSweepFlag(false);
        arcTo.setLargeArcFlag(true);
        arcTo.setRadiusX(radiusX);
        arcTo.setRadiusY(radiusY);
        arcTo.setXAxisRotation(rotate);
        
        Path path = new Path();
        path.getElements().addAll(
                new MoveTo(centerX - radiusX, centerY - radiusY),
                arcTo,
                new ClosePath()); // close 1 px gap.
        path.setStroke(Color.DODGERBLUE);
        path.getStrokeDashArray().setAll(5d, 5d);
        return path;
    }
    
    public void setSliders(){
        setSliderRange(radiusSlider, 1, 25);
        setSliderRange(massSlider, 0, 25);
        setSliderRange(speedSlider, 0, 30);
        
        linkSliderToTextField(radiusSlider, radiusTextField);
        linkSliderToTextField(massSlider, massTextField);
        linkSpeedSliderToTextField(speedSlider, speedTextField);
        
        linkTextFieldToSlider(massSlider, massTextField);
        linkTextFieldToSlider(radiusSlider, radiusTextField);
        linkSpeedTextFieldToSlider(speedSlider, speedTextField);
    }
    
    public void linkSliderToTextField(Slider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
            textfield.setText(String.valueOf(slider.getValue()));
            useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());            
        });
    }
    
    public void linkSpeedSliderToTextField(Slider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
            textfield.setText(String.valueOf(slider.getValue()));
            useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());            
            pathTransitionCircle.setRate(0.1*car.getSpeed());
        });
    }

    public void linkTextFieldToSlider(Slider slider, TextField textfield){
        textfield.setOnKeyTyped((event) -> {
            slider.setValue(Double.valueOf(textfield.getText()));
            useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
        });
    }
    
    public void linkSpeedTextFieldToSlider(Slider slider, TextField textfield){
        textfield.setOnKeyTyped((event) -> {
            slider.setValue(Double.valueOf(textfield.getText()));
            useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
            pathTransitionCircle.setRate(0.1*car.getSpeed());
        });
    }

    public void setSliderRange(Slider slider, double min, double max){
        slider.setMin(min);
        slider.setMax(max);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
    }
    
    public void useEnteredValuesToCalculate(Double mass, Double speed, Double radius){
        System.out.println("Printing: " 
                            + "\nRadius: " + radius
                            + "\nSpeed: " + speed
                            + "\nMass: " +mass);

        car.setMass(mass);
        car.setSpeed(speed);
        car.setRadius(radius);

        centrAccelText.setText(String.valueOf(Formulas.calculateAccelerationCentr(car)));
        forceText.setText(String.valueOf(Formulas.calculateForce(car)));    
    }
}
