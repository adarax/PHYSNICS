/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import edu.vanier.physnics.conservation.ConservationController;
import edu.vanier.physnics.mainmenu.MainMenuController;
import edu.vanier.physnics.projectilemotion.ProjectileController;
import edu.vanier.physnics.stackedblock.BlockFrontEndController;
import io.github.palexdev.materialfx.controls.MFXSlider;
import java.io.IOException;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
public class UniformCircularMotionController extends Stage{
       
    @FXML
    ImageView pauseButton;
    @FXML
    ImageView playButton;
    @FXML
    ImageView resetButton;  
    @FXML
    TextField radiusTextField;
    @FXML
    MFXSlider radiusSlider;
    @FXML
    TextField speedTextField;
    @FXML
    MFXSlider speedSlider;
    @FXML
    TextField massTextField;
    @FXML
    MFXSlider massSlider;
    @FXML
    Button submitButton;
    @FXML
    Text centrAccelText;
    @FXML
    Text forceText;
    @FXML
    Pane paneSimulate;
    @FXML
    Text angleText;
    @FXML
    Text accelXText;
    @FXML
    Text accelYText;
    @FXML
    Text forceXText;
    @FXML
    Text forceYText;
    @FXML
    MenuButton MenuButtonMenu;

    Timeline timeline;
    
    Car car = new Car();
    Path path1 = new Path();
    Path path2 = new Path();
    Path path3 = new Path();
    Path path4 = new Path();
    
    Rectangle rectTest = new Rectangle(50,30, Color.ORANGE);
    Circle center = new Circle(Settings.CENTER_MARKER_X_COORDINATE, Settings.CENTER_MARKER_Y_COORDINATE, 2, Color.RED);
    Group group = new Group();    
    Vector v;
    PathTransition pathTransitionCircle = new PathTransition();
    PathTransition pathTransitionCircle2 = new PathTransition();
    PathTransition pathTransitionCircle3 = new PathTransition();
    PathTransition pathTransitionCircle4 = new PathTransition();

    AnimationTimer timerAngle = new AnimationTimer() {
        @Override
        public void handle(long l) {
            double coordinateX = rectTest.getTranslateX()-center.getCenterX();
            double coordinateY = center.getCenterY()-rectTest.getTranslateY();
            double angle = Math.atan(getAngle())*180/Math.PI;
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
            angleText.setText(String.valueOf(angle));
            centrAccelText.setText(String.valueOf(Formulas.calculateAccelerationCentripetal(car)));
            accelXText.setText(String.valueOf(Math.cos(Math.toRadians(angle))*Double.valueOf(centrAccelText.getText())));
            accelYText.setText(String.valueOf(Math.sin(Math.toRadians(angle))*Double.valueOf(centrAccelText.getText())));
            forceText.setText(String.valueOf(Formulas.calculateForce(car)));
            forceXText.setText(String.valueOf(Math.cos(Math.toRadians(angle))*Double.valueOf(forceText.getText())));
            forceYText.setText(String.valueOf(Math.sin(Math.toRadians(angle))*Double.valueOf(forceText.getText())));
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
        pauseButton.setOnMouseClicked((event) -> {
            System.out.println("pausing...");
            pauseButton.setDisable(true);
            playButton.setDisable(false);
            resetButton.setDisable(false);
            pauseAllPathTransition();
            playAllPathTransition();
            pauseAllPathTransition();
            timerAngle.stop();
        });
    }
    
    @FXML
    void play(){
        playButton.setOnMouseClicked((event) -> {
            System.out.println("playing...");
            playButton.setDisable(true);
            pauseButton.setDisable(false);
            resetButton.setDisable(false);
            pauseAllPathTransition();
            playAllPathTransition();
            timerAngle.start();
        });
    }

    @FXML
    void reset(){
        resetButton.setOnMouseClicked((event) -> {
            System.out.println("resetting...");
            disableSlidersImageViewsAndTextFields();
            pauseAllPathTransition();
            group.getChildren().clear();
            radiusTextField.setText("25");
            massTextField.setText("10");
            speedTextField.setText("10");
            radiusSlider.setValue(25);
            massSlider.setValue(10);
            speedSlider.setValue(10);
            timerAngle.stop();
        });
    }    
    
    @FXML
    void submitSimulation(){
        submitButton.setOnAction((event) -> {
            useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
            enableSlidersImageViewsAndTextFields();
            revolveCar();
            timerAngle.start();
        });
    }
    
    @FXML
    public void setUp(){
        pauseButton.setPickOnBounds(false);
        resetButton.setPickOnBounds(false);
        playButton.setPickOnBounds(false);
        radiusTextField.setText("25");
        massTextField.setText("10");
        speedTextField.setText("10");
        radiusSlider.setValue(25);
        massSlider.setValue(10);
        speedSlider.setValue(10);
        useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());
        paneSimulate.getChildren().add(center);  
        disableSlidersImageViewsAndTextFields();
        setSliders();
        pause();
        play();
        reset();
        v = new Vector(center.getCenterX()+Settings.CENTER_MARKER_INITIAL_RADIUS, center.getCenterY()-Settings.CENTER_MARKER_INITIAL_RADIUS, 270);
        paneSimulate.getChildren().add(group);
    }
    
    public void revolveCar(){      
        v.getVectorHeadLeft().setLayoutX(-2);
        //v.getVectorHeadLeft().setLayoutY(0);
        v.getVectorHeadRight().setLayoutX(2);
        //v.getVectorHeadRight().setLayoutY(0);
        rectTest.setLayoutX(0);
        rectTest.setLayoutY(0);        
        
        path1 = createEllipsePath(195+center.getCenterX(),
                center.getCenterY(),200,
                200, 0);
        path2 = createEllipsePath(175+center.getCenterX(), 
                center.getCenterY(), 180, 
                180, 0);
        path3 = createEllipsePath(155+center.getCenterX(),
                center.getCenterY(), 160,
                160, 0);
        path4 = createEllipsePath(155+center.getCenterX(),
                center.getCenterY(), 160,
                160, 0);
               
        group.getChildren().addAll(rectTest, path1, path2, path3, v.getVectorBody(), v.getVectorHeadLeft(), v.getVectorHeadRight());                

        pathTransitionCircle = createPathTransitionCircle(path1, rectTest);
        pathTransitionCircle2 = createPathTransitionCircle(path2, v.getVectorBody());
        pathTransitionCircle3 = createPathTransitionCircle(path3, v.getVectorHeadRight());
        pathTransitionCircle4 = createPathTransitionCircle(path4, v.getVectorHeadLeft());
                
        v.getVectorHeadLeft().setStroke(Color.RED);
        playAllPathTransition();
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
    
    public void linkRadiusSliderToTextField(MFXSlider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
                textfield.setText(String.valueOf(round(slider.getValue())));
                useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());       
                pauseAllPathTransition();
               removePathAndNodes();
                path1 = createEllipsePath(337.5+center.getCenterY()+8*(car.getRadius()-25),
                        center.getCenterY(),200+8*(car.getRadius()-25),
                        200+8*(car.getRadius()-25), 0);
                //path2 = createEllipsePath(317.5+center.getCenterY()+180/25*(Double.valueOf(radiusTextField.getText())-25), 
                //        center.getCenterY(), 180+180/25*(Double.valueOf(radiusTextField.getText())-25), 
                //        180+180/25*(Double.valueOf(radiusTextField.getText())-25), 0);
                //path3 = createEllipsePath(297.5+center.getCenterY()+160/25*(Double.valueOf(radiusTextField.getText())-25),
                //        center.getCenterY(), 160+160/25*(Double.valueOf(radiusTextField.getText())-25),
                //        160+160/25*(Double.valueOf(radiusTextField.getText())-25), 0);
                //path4 = createEllipsePath(297.5+center.getCenterY()+160/25*(Double.valueOf(radiusTextField.getText())-25),
                //        center.getCenterY(), 160+160/25*(Double.valueOf(radiusTextField.getText())-25),
                //       160+160/25*(Double.valueOf(radiusTextField.getText())-25), 0);
                updateRadiusSimulation(pathTransitionCircle, path1, rectTest);
                //updateRadiusSimulation(pathTransitionCircle2, path2, v.getVectorBody());
                //updateRadiusSimulation(pathTransitionCircle3, path3, v.getVectorHeadLeft());
                //updateRadiusSimulation(pathTransitionCircle4, path4, v.getVectorHeadRight());
                pauseAllPathTransition();
               if (!pauseButton.isDisabled()) {
                   playAllPathTransition();
                }                
            });                
    }
    
    public void linkSpeedSliderToTextField(MFXSlider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
            textfield.setText(String.valueOf(round(slider.getValue())));
            useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue()); 
            pauseAllPathTransition();
            pathTransitionCircle.setRate(0.1*car.getSpeed());
            pathTransitionCircle2.setRate(0.1*car.getSpeed());
            pathTransitionCircle3.setRate(0.1*car.getSpeed());
            pathTransitionCircle4.setRate(0.1*car.getSpeed());
            if (!pauseButton.isDisabled()) {
               playAllPathTransition();
            }
        });
    }

    public void linkMassSliderToTextField(MFXSlider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
                textfield.setText(String.valueOf(round(slider.getValue())));
                useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());            
                v.setOpacity(Double.valueOf(textfield.getText()));                    
    });        
    }

    public void linkRadiusTextFieldToSlider(MFXSlider slider, TextField textfield){
        textfield.setOnKeyTyped((event) -> {
            try {
                slider.setValue(Double.valueOf(textfield.getText()));
                useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
                stopAllPathTransition();
                removePathAndNodes();
                path1 = createEllipsePath(337.5+center.getCenterY()+8*(Double.valueOf(radiusTextField.getText())-25),
                        center.getCenterY(),200+8*(Double.valueOf(radiusTextField.getText())-25),
                        200+8*(Double.valueOf(radiusTextField.getText())-25), 0);
                //path2 = createEllipsePath(317.5+center.getCenterY()+180/25*(Double.valueOf(radiusTextField.getText())-25), 
                //        center.getCenterY(), 180+180/25*(Double.valueOf(radiusTextField.getText())-25), 
                //        180+180/25*(Double.valueOf(radiusTextField.getText())-25), 0);
                //path3 = createEllipsePath(297.5+center.getCenterY()+160/25*(Double.valueOf(radiusTextField.getText())-25),
                //        center.getCenterY(), 160+160/25*(Double.valueOf(radiusTextField.getText())-25),
                //        160+160/25*(Double.valueOf(radiusTextField.getText())-25), 0);
                //path4 = createEllipsePath(297.5+center.getCenterY()+160/25*(Double.valueOf(radiusTextField.getText())-25),
                //        center.getCenterY(), 160+160/25*(Double.valueOf(radiusTextField.getText())-25),
                 //       160+160/25*(Double.valueOf(radiusTextField.getText())-25), 0);
                updateRadiusSimulation(pathTransitionCircle, path1, rectTest);
                //updateRadiusSimulation(pathTransitionCircle2, path2, v.getVectorBody());
                //updateRadiusSimulation(pathTransitionCircle3, path3, v.getVectorHeadLeft());
                //updateRadiusSimulation(pathTransitionCircle4, path4, v.getVectorHeadRight());    
                stopAllPathTransition();
                
                if (!pauseButton.isDisabled()) {
                   playAllPathTransition();
                }
            } catch (NumberFormatException e) {
                    if (!textfield.getText().isBlank()) {
                    System.out.println("error");
                    System.out.println(e);
                    radiusSlider.setValue(25);
                    reset();
                    popAlert("Invalid Radius Input. Please Try Again");
                    }
            }
        });
    }
    
    public void linkSpeedTextFieldToSlider(MFXSlider slider, TextField textfield){
        textfield.setOnKeyTyped((event) -> {
            try {
                slider.setValue(Double.valueOf(textfield.getText()));
                useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
                pathTransitionCircle.setRate(0.1*car.getSpeed());
                pathTransitionCircle2.setRate(0.1*car.getSpeed());
                pathTransitionCircle3.setRate(0.1*car.getSpeed());
                pathTransitionCircle4.setRate(0.1*car.getSpeed());
            } catch (NumberFormatException e) {
                    if (!textfield.getText().isBlank()) {
                        System.out.println("error");
                        System.out.println(e);
                        speedSlider.setValue(10);
                        reset();
                        popAlert("Invalid Speed Input. Please Try Again");
                    }
            }
        });
    }

    public void linkMassTextFieldToSlider(MFXSlider slider, TextField textfield){
            textfield.setOnKeyTyped((event) -> {
                try {
                    slider.setValue(Double.valueOf(textfield.getText()));
                    useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
                    v.setOpacity(Double.valueOf(textfield.getText()));
                        } 
                catch (NumberFormatException e) {
                    if (!textfield.getText().isBlank()) {
                        System.out.println("Error");
                        System.out.println(e);
                        massTextField.setText("10");
                        reset();
                        popAlert("Invalid Mass Input. Please Try Again");                        
                        }
                    }
            });                    
    }

    public void setSliderRange(MFXSlider slider, double min, double max){
        slider.setMin(min);
        slider.setMax(max);
        slider.setShowMajorTicks(true);
        slider.setShowTicksAtEdges(true);
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
        
        //centrAccelText.setText(String.valueOf(round(Formulas.calculateAccelerationCentripetal(car))));
        //forceText.setText(String.valueOf(round(Formulas.calculateForce(car))));    
    }
    
    public double round(double value){
        //https://stackoverflow.com/questions/5710394/how-do-i-round-a-double-to-two-decimal-places-in-java
        double valueToRound = Math.round(value*100.00);
        valueToRound = valueToRound/100.00;
        return valueToRound;
    }
    
    public double getAngle(){
        return Math.atan((center.getCenterY()-rectTest.getTranslateY())/(rectTest.getTranslateX()-center.getCenterX()));
    }
    
    public void updateRadiusSimulation(PathTransition pathTransition, Path path, Node node){
        pathTransition.pause();
        pathTransition = createPathTransitionCircle(path, node);
        group.getChildren().add(path);
        group.getChildren().add(node);
        pathTransition.setRate(0.1*car.getSpeed());
        pathTransition.pause();
        System.out.println(pathTransition.getStatus());
        if (!pauseButton.isDisabled()) {
            pathTransition.play();    
        }
        else{
            pathTransition.play();    
            pathTransition.pause();    
        }
    }
    
    public void pauseAllPathTransition(){
        pathTransitionCircle.pause();
        pathTransitionCircle2.pause();
        pathTransitionCircle3.pause();
        pathTransitionCircle4.pause();        
    }
    
    public void stopAllPathTransition(){
        pathTransitionCircle.stop();
        pathTransitionCircle2.stop();
        pathTransitionCircle3.stop();
        pathTransitionCircle4.stop();        
    }

    public void playAllPathTransition(){
        pathTransitionCircle.play();
        pathTransitionCircle2.play();
        pathTransitionCircle3.play();
        pathTransitionCircle4.play();        
    }

    public void removePathAndNodes(){
        group.getChildren().remove(rectTest);
        group.getChildren().remove(path1);      
        
        group.getChildren().remove(v.getVectorBody());    
        group.getChildren().remove(path2);    

        group.getChildren().remove(v.getVectorHeadLeft());    
        group.getChildren().remove(path3);    

        group.getChildren().remove(v.getVectorHeadRight());    
        group.getChildren().remove(path4);    
    }
    
    public void disableSlidersImageViewsAndTextFields(){
        pauseButton.setDisable(true);
        resetButton.setDisable(true);
        playButton.setDisable(true);
        radiusSlider.setDisable(true);
        massSlider.setDisable(true);
        speedSlider.setDisable(true);
        radiusTextField.setDisable(true);
        massTextField.setDisable(true);
        speedTextField.setDisable(true);
    }
    
    public void enableSlidersImageViewsAndTextFields(){
        pauseButton.setDisable(false);
        resetButton.setDisable(false);
        playButton.setDisable(false);
        radiusSlider.setDisable(false);
        massSlider.setDisable(false);
        speedSlider.setDisable(false);
        radiusTextField.setDisable(false);
        massTextField.setDisable(false);
        speedTextField.setDisable(false);
    }

    
    
    /**
     * Makes an alert pop-up
     * @param string the String to display in the pop-up message
     */
    public void popAlert(String string){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(string);
        a.show();
    }    
    
    public void switchSimulation(String simulationName)
        {
            Stage currentStage = (Stage) paneSimulate.getScene().getWindow();

            String destination = "/fxml/" + simulationName + ".fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));

            switch (simulationName)
            {
                case "stackedblock" ->
                {
                    BlockFrontEndController blockcontroller = new BlockFrontEndController();
                    loader.setController(blockcontroller);
                }
                case "projectile" ->
                {
                    ProjectileController projectileController = new ProjectileController();
                    loader.setController(projectileController);
                }
                case "ucm-scene-graph" ->
                {
                    UniformCircularMotionController ucmController = new UniformCircularMotionController();
                    loader.setController(ucmController);
                }
                case "conservation" ->
                {
                    ConservationController controller = new ConservationController();
                    loader.setController(controller);
                }
                case "mainmenu" ->
                {
                    MainMenuController menuController = new MainMenuController(currentStage);
                    loader.setController(menuController);
                }
                default ->
                    System.out.println("Invalid simulation name");
            }

            try
            {
                Parent root = loader.load();
                Scene scene = new Scene(root, 1920, 1080);
                currentStage.setScene(scene);
            } catch (IOException ex)
            {
                System.out.println("Something went wrong changing scenes.");
            }
        }

}
