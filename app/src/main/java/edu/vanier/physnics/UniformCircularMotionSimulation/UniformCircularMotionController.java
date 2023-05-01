/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import edu.vanier.physnics.conservation.ConservationController;
import edu.vanier.physnics.mainmenu.MainMenuController;
import edu.vanier.physnics.projectilemotion.MainAppController;
import edu.vanier.physnics.stackedblock.BlockFrontEndController;
import io.github.palexdev.materialfx.controls.MFXSlider;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
    @FXML
    MenuItem menuItemConservationEnergy;
    @FXML
    MenuItem menuItemStacked;
    @FXML
    MenuItem menuItemProjectile;
    @FXML
    MenuItem menuItemQuit;
    @FXML
    ImageView buttonHome; 
    @FXML
    ImageView helpButton; 
    @FXML
    BorderPane uniformCircularMotionBorderPane;
    @FXML
    Text warningMassText = new Text();
    @FXML
    Text warningSpeedText = new Text();
    @FXML
    Text warningRadiusText = new Text();
    

    Timeline timeline;
    
    Car car = new Car();
    Path path1 = new Path();
    Path path2 = new Path();
    
    Rectangle rectTest = new Rectangle(50,30, Color.ORANGE);
    Circle center = new Circle(Settings.CENTER_MARKER_X_COORDINATE, Settings.CENTER_MARKER_Y_COORDINATE, 2, Color.RED);
    Group group = new Group();    
    Vector vectorForce = new Vector(0,0, Settings.VECTOR_TYPE[0]);
    PathTransition pathTransitionCircle = new PathTransition();
    PathTransition pathTransitionCircle2 = new PathTransition();
    PathTransition pathTransitionCircle3 = new PathTransition();
    PathTransition pathTransitionCircle4 = new PathTransition();
    private boolean playing = false;
    
    AnimationTimer timerAngle = new AnimationTimer() {
        
        @Override
        public void handle(long l) {
            double coordinateX = rectTest.getTranslateX()+rectTest.getWidth()/2;
            double coordinateY = -center.getCenterY()+rectTest.getTranslateY()+rectTest.getHeight()/2;
            double angle = getAngle()*180/Math.PI;
            if (coordinateX > 0) {
                if (coordinateY > 0) {
                    if (angle > 0) {
                        angle = 360-Math.abs(angle);
                        System.out.println("1");                
                    }
                    else{
                        angle = 180+Math.abs(angle);
                        System.out.println("2");                        
                    }
                }
                else{
                    if (angle < 0) {
                        angle = Math.abs(angle);
                        System.out.println("3");                
                    }
                    else{
                        angle = 180-Math.abs(angle);
                        System.out.println("4");                        
                    }
                }
            }         
            angleText.setText(String.valueOf(round(angle)));
            centrAccelText.setText(String.valueOf(round(Formulas.calculateAccelerationCentripetal(car))));
            accelXText.setText(String.valueOf(round(Math.cos(Math.toRadians(angle))*Double.valueOf(centrAccelText.getText()))));
            accelYText.setText(String.valueOf(round(Math.sin(Math.toRadians(angle))*Double.valueOf(centrAccelText.getText()))));
            forceText.setText(String.valueOf(round(Formulas.calculateForce(car))));
            forceXText.setText(String.valueOf(round(Math.cos(Math.toRadians(angle))*Double.valueOf(forceText.getText()))));
            forceYText.setText(String.valueOf(round(Math.sin(Math.toRadians(angle))*Double.valueOf(forceText.getText()))));
        }
    };
    
    
    
    @FXML
    void initialize(){
        System.out.println("Booting up simulation...");
        setUp();
        buttonHome.setOnMouseClicked((e) -> {        
            switchSimulation("mainmenu");
        });
        
        menuItemProjectile.setOnAction((e) ->{
            switchSimulation("projectile");
        });
        
        menuItemStacked.setOnAction((e) ->{
            switchSimulation("stackedblock");
        });
         
        menuItemConservationEnergy.setOnAction((e) ->{
            switchSimulation("conservation");
        });
        
        menuItemQuit.setOnAction((e) ->{
            switchSimulation("quit");
        });
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
            timerAngle.start();
            enableSlidersImageViewsAndTextFields();        
            if (!playing) {
                revolveCar();
                playing = true;
            }
            pauseAllPathTransition();
            playAllPathTransition();
        });
    }

    @FXML
    void displayHelpPage(){
        helpButton.setOnMouseClicked((event) -> {
        Stage stage = new Stage();
                FXMLLoader uniformCircularMotionHelpLoader = new FXMLLoader(getClass().getResource("/fxml/helpUniformCircularMotion.fxml"));
                Pane root = null;
                
            try {
                UniformCircularMotionHelpController uniformCircularMotionHelpController = new UniformCircularMotionHelpController(stage);
                uniformCircularMotionHelpLoader.setController(uniformCircularMotionHelpController);
                root = uniformCircularMotionHelpLoader.load();               
            } catch (IOException ex) {
                Logger.getLogger(UniformCircularMotionHelpController.class.getName()).log(Level.SEVERE, null, ex);
            } 
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("About Uniform Circular Motion simulation...");
                stage.show();            
        });
    }
    
    @FXML
    void reset(){
        resetButton.setOnMouseClicked((event) -> {
            System.out.println("resetting...");
            disableSlidersImageViewsAndTextFields();
            stopAllPathTransition();
            group.getChildren().clear();
            radiusTextField.setText("25");
            massTextField.setText("10");
            speedTextField.setText("10");
            radiusSlider.setValue(25);
            massSlider.setValue(10);
            speedSlider.setValue(10);
            timerAngle.stop();
            clearPathElements();
            clearPathTransitions();
            clearWarningMessages();
            playing = false;
      });
    }    
    
    @FXML
    public void setUp(){
        pauseButton.setPickOnBounds(false);
        resetButton.setPickOnBounds(false);
        playButton.setPickOnBounds(true);
        radiusTextField.setText("20");
        massTextField.setText("10");
        speedTextField.setText("10");
        radiusSlider.setValue(20);
        massSlider.setValue(10);
        speedSlider.setValue(10);
        useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());
        paneSimulate.getChildren().add(center);  
        disableSlidersImageViewsAndTextFields();
        setSliders();
        pause();
        play();
        reset();
        displayHelpPage();
        paneSimulate.getChildren().add(group);
    }
    
    public void revolveCar(){       
        stopAllPathTransition();
        removePathAndNodes();
        if (!paneSimulate.getChildren().contains(rectTest)) {
            path1 = null;
            path2 = null;

            pathTransitionCircle.setNode(null);
            pathTransitionCircle.setPath(null);
            path1 = createEllipsePath(195+center.getCenterX(),
                    center.getCenterY(),200,
                    200, 0);
            path2 = createEllipsePath(195+center.getCenterX(),
                    center.getCenterY(),200,
                    200, 0);
            group.getChildren().addAll(rectTest, path1, path2, vectorForce.getArrowBody());                

            pathTransitionCircle = createPathTransitionCircle(path1, (Node) rectTest);
            pathTransitionCircle2 = createPathTransitionCircle(path2,  vectorForce.getArrowBody());      
        }
                
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
            pathTransitionCircle.stop();
            pathTransitionCircle2.stop();
            pathTransitionCircle3.stop();
            pathTransitionCircle4.stop();
            group.getChildren().clear();
            clearPathElements();
            clearPathTransitions();
            path1 = createEllipsePath(center.getCenterX()+200+8*(car.getRadius()-25),
                    center.getCenterY(),200+8*(car.getRadius()-25),
                    200+8*(car.getRadius()-25), 0);
            path2 = createEllipsePath(center.getCenterX()+200+8*(car.getRadius()-25),
                    center.getCenterY(),200+8*(car.getRadius()-25),
                    200+8*(car.getRadius()-25), 0);
 
            pathTransitionCircle.setNode(rectTest);
            pathTransitionCircle.setPath(path1);

            warningRadiusText.setText("");
            
            if (!pauseButton.isDisabled()) {
                playAllPathTransition();
            }
            else{
                pathTransitionCircle.playFromStart();
                stopAllPathTransition();
            }
            
            group.getChildren().addAll(rectTest, path1, path2,  vectorForce.getArrowBody());                
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
            warningSpeedText.setText("");
            if (!pauseButton.isDisabled()) {
               playAllPathTransition();
            }
        });
    }

    public void linkMassSliderToTextField(MFXSlider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
                textfield.setText(String.valueOf(round(slider.getValue())));
                useEnteredValuesToCalculate(massSlider.getValue(), speedSlider.getValue(), radiusSlider.getValue());            
                warningMassText.setText("");
    });        
    }

    public void linkRadiusTextFieldToSlider(MFXSlider slider, TextField textfield){
        textfield.setOnKeyTyped((event) -> {
            try {
                slider.setValue(Double.valueOf(textfield.getText()));
                useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
                pathTransitionCircle.stop();
                pathTransitionCircle2.stop();
                pathTransitionCircle3.stop();
                pathTransitionCircle4.stop();
                group.getChildren().clear();
                clearPathElements();
                clearPathTransitions();           
                clearPathTransitions();
                if (car.getRadius() <= 41) {
                    path1 = createEllipsePath(center.getCenterX()+200+8*(car.getRadius()-25),
                        center.getCenterY(),200+8*(car.getRadius()-25),
                        200+8*(car.getRadius()-25), 0);
                    path2 = createEllipsePath(center.getCenterX()+180+180/25*(car.getRadius()-25), 
                            center.getCenterY(), 180+180/25*(Double.valueOf(radiusTextField.getText())-25), 
                            180+180/25*(Double.valueOf(radiusTextField.getText())-25), 0);               
                    warningRadiusText.setText("");
                }
                else{
                    path1 = createEllipsePath(center.getCenterX()+200+8*(41-25),
                        center.getCenterY(),200+8*(41-25),
                        200+8*(41-25), 0);
                    path2 = createEllipsePath(center.getCenterX()+180+180/25*(41-25), 
                            center.getCenterY(), 180+180/25*(41-25), 
                            180+180/25*(41-25), 0);
                    warningRadiusText.setText(Settings.RADIUS_LIMIT_MESSAGE);
                    warningRadiusText.setFill(Color.RED);                
                }

                pathTransitionCircle.setNode(rectTest);
                pathTransitionCircle.setPath(path1);
                
                
                if (!pauseButton.isDisabled()) {
                    playAllPathTransition();
                }
                else{
                    playAllPathTransition();
                    stopAllPathTransition();
                }

                group.getChildren().addAll(rectTest, path1, path2,  vectorForce.getArrowBody());
        } catch (NumberFormatException e) {
                    if (!textfield.getText().isBlank()) {
                    System.out.println("error");
                    System.out.println(e);
                    radiusSlider.setValue(20);
                    radiusTextField.setText("20");
                    useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
                    popAlert("Invalid Radius Input. Please Try Again");
                        linkRadiusTextFieldToSlider(slider, textfield);
                    }
            }
        });
    }
    
    public void linkSpeedTextFieldToSlider(MFXSlider slider, TextField textfield){
        textfield.setOnKeyTyped((event) -> {
            try {
                slider.setValue(Double.valueOf(textfield.getText()));
                useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
                if (car.getSpeed() <= 200) {
                    pathTransitionCircle.setRate(0.1*car.getSpeed());
                    pathTransitionCircle2.setRate(0.1*car.getSpeed());
                    pathTransitionCircle3.setRate(0.1*car.getSpeed());
                    pathTransitionCircle4.setRate(0.1*car.getSpeed());                    
                    warningSpeedText.setText("");
                }
                else{
                    pathTransitionCircle.setRate(20);
                    pathTransitionCircle2.setRate(20);
                    pathTransitionCircle3.setRate(20);
                    pathTransitionCircle4.setRate(20);   
                    warningSpeedText.setText(Settings.SPEED_LIMIT_MESSAGE);
                    warningSpeedText.setFill(Color.RED);
                }
            } catch (NumberFormatException e) {
                    if (!textfield.getText().isBlank()) {
                        System.out.println("error");
                        System.out.println(e);
                        speedSlider.setValue(10);
                        speedTextField.setText("10");
                        popAlert("Invalid Speed Input. Please Try Again");
                        useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
                        linkSpeedSliderToTextField(slider, textfield);
                    }
            }
        });
    }

    public void linkMassTextFieldToSlider(MFXSlider slider, TextField textfield){
            textfield.setOnKeyTyped((event) -> {
                try {
                    slider.setValue(Double.valueOf(textfield.getText()));
                    useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
                    if (car.getMass() <= 40) {
                    }
                    else{
                        warningMassText.setText(Settings.MASS_LIMIT_MESSAGE);
                        warningMassText.setFill(Color.RED);                    
                    }
                } 
                catch (NumberFormatException e) {
                    if (!textfield.getText().isBlank()) {
                        System.out.println("Error");
                        System.out.println(e);
                        massTextField.setText("10");
                        massSlider.setValue(10);
                        popAlert("Invalid Mass Input. Please Try Again");                        
                        useEnteredValuesToCalculate(retrieveMassTextField(), retrieveSpeedTextField(), retrieveRadiusTextField());
                        linkMassTextFieldToSlider(slider, textfield);
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
        
        centrAccelText.setText(String.valueOf(round(Formulas.calculateAccelerationCentripetal(car))));
        forceText.setText(String.valueOf(round(Formulas.calculateForce(car))));    
    }
    
    /**
     * Rounds a double value to 2 decimal places.
     * @param value the number to round
     * @return the value, rounded to 2 decimal places
     */
    public double round(double value){
        //https://stackoverflow.com/questions/5710394/how-do-i-round-a-double-to-two-decimal-places-in-java
        double valueToRound = Math.round(value*100.00);
        valueToRound = valueToRound/100.00;
        return valueToRound;
    }
    
    /**
     * Returns the angle of the car with respect to the circle
     * @return the angle made from the car with respect to the center's horizontal axis
     */
    public double getAngle(){
        return Math.atan((rectTest.getTranslateY()-center.getCenterY()+rectTest.getHeight()/2)/(rectTest.getTranslateX()-center.getCenterX()+rectTest.getWidth()/2));
    }
    
    /**
     * Pauses all path transitions in the simulation.
     */
    public void pauseAllPathTransition(){
        pathTransitionCircle.pause();
        pathTransitionCircle2.pause();
        pathTransitionCircle3.pause();
        pathTransitionCircle4.pause();        
    }
    
    /**
     * Stops all path transitions in the simulation.
     */
    public void stopAllPathTransition(){
        pathTransitionCircle.stop();
        pathTransitionCircle2.stop();
        pathTransitionCircle3.stop();
        pathTransitionCircle4.stop();        
    }

    /**
     * PLays all path transitions in the simulation.
     */
    public void playAllPathTransition(){
        pathTransitionCircle.play();
        pathTransitionCircle2.play();
        pathTransitionCircle3.play();
        pathTransitionCircle4.play();        
    }
    
    /**
     * Removes all paths and nodes from the group.
     */
    public void removePathAndNodes(){
        group.getChildren().remove(rectTest);
        group.getChildren().remove(path1);       
    }
    
    /**
     * Disables the sliders, imageViews and the TextFields to be clicked on.
     */
    public void disableSlidersImageViewsAndTextFields(){
        pauseButton.setDisable(true);
        resetButton.setDisable(true);
        playButton.setDisable(false);
    }
    
    /**
     * Enables the sliders, imageViews and the TextFields to be clicked on.
     */
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
     * Wipes away all elements that are attached to the path variables, and resets the path variables to null.
     */
    public void clearPathElements(){
        path1.getElements().clear();
        path2.getElements().clear();      
        path1 = null;
        path2 = null;
    }
    
    /**
     * Wipes away all paths and nodes associated to the Path Transitions.
     */
    public void clearPathTransitions(){
        pathTransitionCircle.setNode(null);
        pathTransitionCircle.setPath(null);
        pathTransitionCircle2.setNode(null);
        pathTransitionCircle2.setPath(null);
        pathTransitionCircle3.setNode(null);
        pathTransitionCircle3.setPath(null);
        pathTransitionCircle4.setNode(null);
        pathTransitionCircle4.setPath(null);
    }
    
   public void clearWarningMessages(){
       warningMassText.setText("");
       warningSpeedText.setText("");
       warningRadiusText.setText("");
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
    
    /**
     * A method used to switch the present simulation to another simulation
     * @param simulationName the name of the simulation that is being switched to
     */
    public void switchSimulation(String simulationName){
        
            Stage currentStage = (Stage) uniformCircularMotionBorderPane.getScene().getWindow();

            String destination = "/fxml/" + simulationName + ".fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));

            if (simulationName.equals("quit")) {
                currentStage.close();
            }
            else{
                switch (simulationName)
                {
                    case "stackedblock" ->
                    {
                        BlockFrontEndController blockcontroller = new BlockFrontEndController();
                        loader.setController(blockcontroller);
                    }
                    case "projectile" ->
                    {
                        MainAppController projectileController = new MainAppController();
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
}
