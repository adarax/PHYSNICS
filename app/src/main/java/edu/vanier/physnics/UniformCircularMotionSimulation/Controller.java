/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
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
public class Controller extends Stage{
        
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
    Pane paneSimulate;
    @FXML
    Text angleTextField;
    @FXML
    Text accelXTextField;
    @FXML
    Text accelYTextField;
    
    Timeline timeline;
    
    Car car = new Car();
    Path path1 = new Path();
    Path path2 = new Path();
    Path path3 = new Path();
    Path path4 = new Path();
    
    Rectangle rectTest = new Rectangle(50,30, Color.ORANGE);
    Circle center = new Circle(250, 250, 2, Color.RED);
    Group group = new Group();    
    Vector v;
    PathTransition pathTransitionCircle = new PathTransition();
    PathTransition pathTransitionCircle2 = new PathTransition();
    PathTransition pathTransitionCircle3 = new PathTransition();
    PathTransition pathTransitionCircle4 = new PathTransition();

    AnimationTimer timerAngle = new AnimationTimer() {
        @Override
        public void handle(long l) {
            double coordinateX = v.getVectorBody().getTranslateX();
            double coordinateY = center.getRadius()-v.getVectorBody().getTranslateY();
            double angle = Math.atan((coordinateY/coordinateX))*180/Math.PI;
            if (coordinateX > 0) {
                if (coordinateY > 0) {
                    angle = Math.abs(angle);
                }
                else{
                    angle = 360-Math.abs(angle);
                }
            }
            else{
                if (coordinateY > 0) {
                    angle = 180-Math.abs(angle);
                }
                else{
                    angle = 180+Math.abs(angle);
                }            
            }           
            angleTextField.setText(String.valueOf(angle));
            accelXTextField.setText(String.valueOf(-Math.cos(Math.toRadians(angle))*Double.valueOf(centrAccelText.getText())));
            accelYTextField.setText(String.valueOf(-Math.sin(Math.toRadians(angle))*Double.valueOf(centrAccelText.getText())));
        }
    };
    
    
    
    @FXML
    void initialize(){
        System.out.println("Booting up simulation...");
        submitSimulation();
        setUp();
    }
    
    
    @FXML
    public double retrieveRadiusTextField(){
        double radius = 0;
        try {
            radius = Double.valueOf(radiusTextField.getText());
        } catch (Exception e) {
            System.out.println("Error");
        }
        return radius;
    }
    
    @FXML
    public double retrieveSpeedTextField(){
        double speed = 0;
        try {
            speed = Double.valueOf(speedTextField.getText());
        } catch (Exception e) {
            System.out.println("Error");
        }
        return speed;
    }

    @FXML
    public double retrieveMassTextField(){
        double mass = 0;
        try {
            mass = Double.valueOf(massTextField.getText());
        } catch (Exception e) {
            System.out.println("Error");
        }
        return mass;
    }
    
    @FXML
    void pause(){
        pauseButton.setOnAction((event) -> {
            System.out.println("pausing...");
            pauseButton.setDisable(true);
            playButton.setDisable(false);
            resetButton.setDisable(false);
            pathTransitionCircle.pause();
            pathTransitionCircle2.pause();
            pathTransitionCircle3.pause();
            pathTransitionCircle4.pause();
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
            pathTransitionCircle2.play();
            pathTransitionCircle3.play();
            pathTransitionCircle4.play();
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
            pathTransitionCircle.pause();
            pathTransitionCircle2.pause();
            pathTransitionCircle3.pause();
            pathTransitionCircle4.pause();
            group.getChildren().clear();
            radiusTextField.setText("25");
            massTextField.setText("10");
            speedTextField.setText("10");
            radiusSlider.setValue(25);
            massSlider.setValue(10);
            speedSlider.setValue(10);
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
            revolveCar();
            timerAngle.start();
        });
    }
    
    @FXML
    public void setUp(){
        pauseButton.setDisable(true);
        resetButton.setDisable(true);
        playButton.setDisable(true);
        radiusTextField.setText("25");
        massTextField.setText("10");
        speedTextField.setText("10");
        radiusSlider.setValue(25);
        massSlider.setValue(10);
        speedSlider.setValue(10);
        useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());
        paneSimulate.getChildren().add(center);  
        setSliders();
        pause();
        play();
        reset();
        v = new Vector(250, 200, 270);
        paneSimulate.getChildren().add(group);
    }
    
    public void revolveCar(){      
        v.getVectorHeadLeft().setLayoutX(-2);
        //v.getVectorHeadLeft().setLayoutY(0);
        v.getVectorHeadRight().setLayoutX(2);
        //v.getVectorHeadRight().setLayoutY(0);
        
        path1 = createEllipsePath(450, 250, 200, 200, 0);
        path2 = createEllipsePath(430, 250, 180, 180, 0);
        path3 = createEllipsePath(410, 250, 160, 160, 0);
        path4 = createEllipsePath(410, 250, 160, 160, 0);
        
        pathTransitionCircle = createPathTransitionCircle(path1, rectTest);
        pathTransitionCircle2 = createPathTransitionCircle(path2, v.getVectorBody());
        pathTransitionCircle3 = createPathTransitionCircle(path3, v.getVectorHeadRight());
        pathTransitionCircle4 = createPathTransitionCircle(path4, v.getVectorHeadLeft());
                
        group.getChildren().addAll(rectTest, path1, path2, path3, v.getVectorBody(), v.getVectorHeadLeft(), v.getVectorHeadRight());                
        v.getVectorHeadLeft().setStroke(Color.RED);
        pathTransitionCircle.play();
        pathTransitionCircle2.play();
        pathTransitionCircle3.play();
        pathTransitionCircle4.play();
    }
    
    private PathTransition createPathTransitionCircle(Path path, Node node){
        PathTransition pathTransitionCircle = new PathTransition();
        pathTransitionCircle.setDuration(Duration.seconds(50/car.getSpeed()));
        pathTransitionCircle.setPath(path);
        pathTransitionCircle.setNode(node);
        pathTransitionCircle.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransitionCircle.setCycleCount(Timeline.INDEFINITE);
        pathTransitionCircle.setAutoReverse(false);
        pathTransitionCircle.setInterpolator(Interpolator.LINEAR);       
        return pathTransitionCircle;
    }
    
    private Path createEllipsePath(double centerX, double centerY, double radiusX, double radiusY, double rotate)
    {
        //https://stackoverflow.com/questions/14171856/javafx-2-circle-path-for-animation
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
        
        linkRadiusSliderToTextField(radiusSlider, radiusTextField);
        linkMassSliderToTextField(massSlider, massTextField);
        linkSpeedSliderToTextField(speedSlider, speedTextField);
        
        linkMassTextFieldToSlider(massSlider, massTextField);
        linkRadiusTextFieldToSlider(radiusSlider, radiusTextField);
        linkSpeedTextFieldToSlider(speedSlider, speedTextField);
    }
    
    public void linkRadiusSliderToTextField(Slider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
            textfield.setText(String.valueOf(round(slider.getValue())));
            useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());            
            pathTransitionCircle.pause();
            group.getChildren().remove(path1);
            group.getChildren().remove(rectTest);
            path1 = createEllipsePath(450+8*(retrieveRadiusTextField()-25), 250, 200+8*(retrieveRadiusTextField()-25), 200+8*(retrieveRadiusTextField()-25), 0);
            group.getChildren().addAll(path1, rectTest);

            pathTransitionCircle = createPathTransitionCircle(path1, rectTest);
            pathTransitionCircle.play();
        });
    }
    
    public void linkSpeedSliderToTextField(Slider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
            textfield.setText(String.valueOf(round(slider.getValue())));
            useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());            
            pathTransitionCircle.setRate(0.1*car.getSpeed());
        });
    }

    public void linkMassSliderToTextField(Slider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
            textfield.setText(String.valueOf(round(slider.getValue())));
            useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());            
            v.setOpacity(Double.valueOf(textfield.getText()));
        });
    }

    public void linkRadiusTextFieldToSlider(Slider slider, TextField textfield){
        textfield.setOnKeyTyped((event) -> {
            slider.setValue(Double.valueOf(textfield.getText()));
            useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
            pathTransitionCircle.pause();
            group.getChildren().remove(rectTest);
            group.getChildren().remove(path1);
            path1 = createEllipsePath(450+8*(retrieveRadiusTextField()-25), 250, 200+8*(retrieveRadiusTextField()-25), 200+8*(retrieveRadiusTextField()-25), 0);
            group.getChildren().addAll(path1, rectTest);
            pathTransitionCircle = createPathTransitionCircle(path1, rectTest);
            pathTransitionCircle.play();
        });
    }
    
    public void linkSpeedTextFieldToSlider(Slider slider, TextField textfield){
        textfield.setOnKeyTyped((event) -> {
            slider.setValue(Double.valueOf(textfield.getText()));
            useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
            pathTransitionCircle.setRate(0.1*car.getSpeed());
        });
    }

    public void linkMassTextFieldToSlider(Slider slider, TextField textfield){
        textfield.setOnKeyTyped((event) -> {
            slider.setValue(Double.valueOf(textfield.getText()));
            useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
            v.setOpacity(Double.valueOf(textfield.getText()));
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
                            + "\nMass: " +round(mass));

        car.setMass(mass);
        car.setSpeed(speed);
        car.setRadius(radius);

        System.out.println(round(mass));
        
        centrAccelText.setText(String.valueOf(round(Formulas.calculateAccelerationCentripetal(car))));
        forceText.setText(String.valueOf(round(Formulas.calculateForce(car))));    
    }
    
    public double round(double value){
        //https://stackoverflow.com/questions/5710394/how-do-i-round-a-double-to-two-decimal-places-in-java
        double valueToRound = Math.round(value*100.00);
        valueToRound = valueToRound/100.00;
        return valueToRound;
    }
    
    public double getAngle(){
        return Math.atan((rectTest.getLayoutY()-center.getCenterY())/rectTest.getLayoutX()-center.getCenterX());
    }
}
