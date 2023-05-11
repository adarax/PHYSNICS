/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXSlider;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


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
    MFXCheckbox accelerationMagnitudeCheckBox;
    @FXML
    MFXCheckbox forceMagnitudeCheckBox;
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
    MenuItem menuItemChangePathColor;   
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
    
    Stage mainWindow = new Stage();
    
    Car car = new Car();
    Path path1 = new Path();
    Path path2 = new Path();
    Path path3 = new Path();
    Path path4 = new Path();
    
    Rectangle rectTest = new Rectangle(50,30, Color.ORANGE);
    Vector vectorForce = new Vector(0,0, 30, 60,Settings.VECTOR_TYPE[0]);
    Vector vectorAcceleration = new Vector(0,0, 30, 30, Settings.VECTOR_TYPE[1]);    
    
    Circle center = new Circle(Settings.CENTER_MARKER_X_COORDINATE, Settings.CENTER_MARKER_Y_COORDINATE, 2, Color.RED);
    Group group = new Group();    
    
    PathTransition pathTransitionCircle = new PathTransition();
    PathTransition pathTransitionCircle2 = new PathTransition();
    PathTransition pathTransitionCircle3 = new PathTransition();
    PathTransition pathTransitionCircle4 = new PathTransition();
    
    private boolean playing = false;
    SimulationBackEnd animationBackEnd = new SimulationBackEnd();
    
    AnimationTimer timerAngle = new AnimationTimer() {
        
        @Override
        public void handle(long l) {
            double coordinateX = rectTest.getTranslateX()+rectTest.getWidth()/2;
            double coordinateY = -center.getCenterY()+rectTest.getTranslateY()+rectTest.getHeight()/2;
            double angle = Formulas.getAngle((rectTest.getTranslateX()-center.getCenterX()+rectTest.getWidth()/2),(rectTest.getTranslateY()-center.getCenterY()+rectTest.getHeight()/2))*180/Math.PI;
            angle = Formulas.determineQuadrant(angle, coordinateX, coordinateY);
            angleText.setText(String.valueOf(Formulas.roundTwoDecimals(angle)));
            centrAccelText.setText(String.valueOf(Formulas.roundTwoDecimals(Formulas.calculateAccelerationCentripetal(car))));
            accelXText.setText(String.valueOf(Formulas.roundTwoDecimals(-Formulas.returnMagnitudeXComponent(Double.valueOf(centrAccelText.getText()), angle))));
            accelYText.setText(String.valueOf(Formulas.roundTwoDecimals(-Formulas.returnMagnitudeYComponent(Double.valueOf(centrAccelText.getText()), angle))));
            forceText.setText(String.valueOf(Formulas.roundTwoDecimals(Formulas.calculateForce(car))));
            forceXText.setText(String.valueOf(Formulas.roundTwoDecimals(-Formulas.returnMagnitudeXComponent(Double.valueOf(forceText.getText()), angle))));
            forceYText.setText(String.valueOf(Formulas.roundTwoDecimals(-Formulas.returnMagnitudeYComponent(Double.valueOf(forceText.getText()), angle))));
        }
    };
    
    @FXML
    void initialize(){
        System.out.println("Booting up simulation...");
        setUp();
        buttonHome.setOnMouseClicked((e) -> {        
            animationBackEnd.switchSimulation("mainmenu", uniformCircularMotionBorderPane);
        });
        
        menuItemProjectile.setOnAction((e) ->{
            animationBackEnd.switchSimulation("projectile", uniformCircularMotionBorderPane);
        });
        
        menuItemStacked.setOnAction((e) ->{
            animationBackEnd.switchSimulation("stackedblock", uniformCircularMotionBorderPane);
        });
         
        menuItemConservationEnergy.setOnAction((e) ->{
            animationBackEnd.switchSimulation("conservation", uniformCircularMotionBorderPane);
        });
        
        menuItemQuit.setOnAction((e) ->{
            animationBackEnd.switchSimulation("quit", uniformCircularMotionBorderPane);
        });
    }
    
    @FXML
    public void changePathColor(){
        menuItemChangePathColor.setOnAction((event) -> {
            ChangeColorWindow changeColorWindow = new ChangeColorWindow(mainWindow);
            menuItemChangePathColor.setDisable(true);
            changeColorWindow.changeColor(path1);
            changeColorWindow.setDisableColorWindowButton(menuItemChangePathColor);
        });
    }
    
    @FXML
    public double retrieveTextField(TextField textfield){
        double valueInTextField = 0;
        try {
            valueInTextField = Double.valueOf(textfield.getText());
        } catch (Exception e) {
            System.out.println("Error");
        }
        return valueInTextField;
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
            radiusTextField.setText("20");
            massTextField.setText("2");
            speedTextField.setText("10");
            radiusSlider.setValue(20);
            massSlider.setValue(2);
            speedSlider.setValue(10);
            useEnteredValuesToCalculate(retrieveTextField(massTextField), retrieveTextField(speedTextField), retrieveTextField(radiusTextField));
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
        massTextField.setText("2");
        speedTextField.setText("10");
        radiusSlider.setValue(20);
        massSlider.setValue(2);
        speedSlider.setValue(10);
        useEnteredValuesToCalculate(retrieveTextField(massTextField), retrieveTextField(speedTextField), retrieveTextField(radiusTextField));
        paneSimulate.getChildren().add(center);  
        disableSlidersImageViewsAndTextFields();
        setSliders();
        pause();
        play();
        reset();
        displayHelpPage();
        changePathColor();
        paneSimulate.getChildren().add(group);
        setVectorsVisibility();
    }
    
    @FXML
    public void setVectorsVisibility(){
        forceMagnitudeCheckBox.setOnAction((event) -> {
            if (forceMagnitudeCheckBox.isSelected()) {
                System.out.println("Hiding force magnitude");
                vectorForce.getArrowBody().setOpacity(0);
            }
            else{
                vectorForce.getArrowBody().setOpacity(1);
            }
        });
        accelerationMagnitudeCheckBox.setOnAction((event) -> {
            if (accelerationMagnitudeCheckBox.isSelected()) {
                System.out.println("Hiding acceleration magnitude");
                vectorAcceleration.getArrowBody().setOpacity(0);
            }
            else{
                vectorAcceleration.getArrowBody().setOpacity(1);
            }            
        });
    }
    
    public void revolveCar(){       
        stopAllPathTransition();
        removePathAndNodes();
        if (!paneSimulate.getChildren().contains(rectTest)) {
            nullPaths();
            clearPathTransitions();
            
            setPathTrajectories();
            
            group.getChildren().addAll(rectTest, path1, vectorForce.getArrowBody(), vectorAcceleration.getArrowBody());                
            pathTransitionCircle = animationBackEnd.createPathTransitionCircle(path1, (Node) rectTest, car);
            pathTransitionCircle2 = animationBackEnd.createPathTransitionCircle(path2,  vectorForce.getArrowBody(), car);      
            pathTransitionCircle3 = animationBackEnd.createPathTransitionCircle(path3,  vectorAcceleration.getArrowBody(), car);      
        }               
        playAllPathTransition();
    }
    
    public void setSliders(){
        setSliderRange(radiusSlider, 1, 25);
        setSliderRange(massSlider, 0, 40);
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
            textfield.setText(String.valueOf(Formulas.roundTwoDecimals(slider.getValue())));
            useEnteredValuesToCalculate(retrieveTextField(massTextField), retrieveTextField(speedTextField), retrieveTextField(radiusTextField));
            stopAllPathTransition();
            group.getChildren().clear();
            clearPathElements();
            clearPathTransitions();
            setPathTrajectories();
            
            pathTransitionCircle.setNode(rectTest);
            pathTransitionCircle.setPath(path1);

            pathTransitionCircle2.setNode(vectorForce.getArrowBody());
            pathTransitionCircle2.setPath(path2);

            pathTransitionCircle3.setNode(vectorAcceleration.getArrowBody());
            pathTransitionCircle3.setPath(path3);

            warningRadiusText.setText("");
            
            if (!pauseButton.isDisabled()) {
                playAllPathTransition();
            }
            else{
                pathTransitionCircle.playFromStart();
                pathTransitionCircle2.playFromStart();
                pathTransitionCircle3.playFromStart();
                pathTransitionCircle4.playFromStart();
                stopAllPathTransition();
            }    
            group.getChildren().addAll(rectTest, path1,  vectorForce.getArrowBody(),vectorAcceleration.getArrowBody());                
            });                
    }
    
    public void linkSpeedSliderToTextField(MFXSlider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
            textfield.setText(String.valueOf(Formulas.roundTwoDecimals(slider.getValue())));
            useEnteredValuesToCalculate(retrieveTextField(massTextField), retrieveTextField(speedTextField), retrieveTextField(radiusTextField));
            pauseAllPathTransition();
            setSimulationNodesAnimationRate();
            if (!pauseButton.isDisabled()) {
               playAllPathTransition();
            }
        });
    }

    public void linkMassSliderToTextField(MFXSlider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
                textfield.setText(String.valueOf(Formulas.roundTwoDecimals(slider.getValue())));
                useEnteredValuesToCalculate(retrieveTextField(massTextField), retrieveTextField(speedTextField), retrieveTextField(radiusTextField));
                    if (car.getMass() <= 40 && car.getMass()> 0) {
                        vectorForce.getArrowBody().setVisible(true);               
                        vectorForce.setOpacity(0.025*car.getMass());
                        warningMassText.setText("");
                    }
                    else if (car.getMass() == 0) {
                        System.out.println("Making arrow invisible");
                        vectorForce.getArrowBody().setVisible(false);               
                        vectorForce.setOpacity(0.025*car.getMass());
                        warningMassText.setText("");
                    }
                    else{
                        vectorForce.getArrowBody().setVisible(true);               
                        vectorForce.getArrowBody().setOpacity(1);               
                        warningMassText.setText(Settings.MASS_LIMIT_MESSAGE);
                        warningMassText.setFill(Color.RED);                    
                    }
    });        
    }

    public void linkRadiusTextFieldToSlider(MFXSlider slider, TextField textfield){
        textfield.setOnKeyTyped((event) -> {
            try {
                slider.setValue(Double.valueOf(textfield.getText()));
                useEnteredValuesToCalculate(retrieveTextField(massTextField), retrieveTextField(speedTextField), retrieveTextField(radiusTextField));
                stopAllPathTransition();
                group.getChildren().clear();
                clearPathElements();
                clearPathTransitions();           
                clearPathTransitions();
                if (car.getRadius() <= 41) {
                    setPathTrajectories();
                }
                else{
                    setPathTrajectories(true);
                    warningRadiusText.setText(Settings.RADIUS_LIMIT_MESSAGE);
                    warningRadiusText.setFill(Color.RED);                
                }

                pathTransitionCircle.setNode(rectTest);
                pathTransitionCircle.setPath(path1);           
                pathTransitionCircle2.setNode(vectorForce.getArrowBody());
                pathTransitionCircle2.setPath(path2); 
                pathTransitionCircle3.setNode(vectorAcceleration.getArrowBody());
                pathTransitionCircle3.setPath(path3); 
                
                if (!pauseButton.isDisabled()) {
                    playAllPathTransition();
                }
                else{
                    playAllPathTransition();
                    stopAllPathTransition();
                }

                group.getChildren().addAll(rectTest, path1, path2,  vectorForce.getArrowBody(), vectorAcceleration.getArrowBody());
        } catch (NumberFormatException e) {
                    if (!textfield.getText().isBlank()) {
                    System.out.println("error");
                    System.out.println(e);
                    radiusSlider.setValue(20);
                    radiusTextField.setText("20");
                    useEnteredValuesToCalculate(retrieveTextField(massTextField), retrieveTextField(speedTextField), retrieveTextField(radiusTextField));
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
                useEnteredValuesToCalculate(retrieveTextField(massTextField), retrieveTextField(speedTextField), retrieveTextField(radiusTextField));
                setSimulationNodesAnimationRate();
            } catch (NumberFormatException e) {
                    if (!textfield.getText().isBlank()) {
                        System.out.println("error");
                        System.out.println(e);
                        speedSlider.setValue(10);
                        speedTextField.setText("10");
                        popAlert("Invalid Speed Input. Please Try Again");
                        useEnteredValuesToCalculate(retrieveTextField(massTextField), retrieveTextField(speedTextField), retrieveTextField(radiusTextField));
                        linkSpeedSliderToTextField(slider, textfield);
                    }
            }
        });
    }

    public void linkMassTextFieldToSlider(MFXSlider slider, TextField textfield){
            textfield.setOnKeyTyped((event) -> {
                try {
                    slider.setValue(Double.valueOf(textfield.getText()));
                    useEnteredValuesToCalculate(retrieveTextField(massTextField), retrieveTextField(speedTextField), retrieveTextField(radiusTextField));
                    if (car.getMass() <= 4 && car.getMass()> 0) {
                        vectorForce.getArrowBody().setVisible(true);               
                        vectorForce.setOpacity(0.025*car.getMass());
                        warningMassText.setText("");
                    }
                    else if (car.getMass() == 0) {
                        vectorForce.getArrowBody().setVisible(false);               
                        vectorForce.setOpacity(0.025*car.getMass());
                        warningMassText.setText("");
                    }
                    else if (car.getMass() > Double.MAX_VALUE) {
                        vectorForce.setOpacity(1);
                        throw new NumberFormatException();
                    }
                    else{
                        vectorForce.getArrowBody().setVisible(true);               
                        linkRadiusSliderToTextField(slider, textfield);
                        warningMassText.setText(Settings.MASS_LIMIT_MESSAGE);
                        warningMassText.setFill(Color.RED);                    
                    }
                } 
                catch (NumberFormatException e) {
                    if (!textfield.getText().isBlank()) {
                        System.out.println("Error");
                        System.out.println(e);
                        massTextField.setText("2");
                        massSlider.setValue(2);
                        popAlert("Invalid Mass Input. Please Try Again");                        
                        useEnteredValuesToCalculate(retrieveTextField(massTextField), retrieveTextField(speedTextField), retrieveTextField(radiusTextField));
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
                            + "\nMass: " +Formulas.roundTwoDecimals(mass));
        car.setMass(mass);
        car.setSpeed(speed);
        car.setRadius(radius);

        System.out.println(Formulas.roundTwoDecimals(mass));
        
        centrAccelText.setText(String.valueOf(Formulas.roundTwoDecimals(Formulas.calculateAccelerationCentripetal(car))));
        forceText.setText(String.valueOf(Formulas.roundTwoDecimals(Formulas.calculateForce(car))));    
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
        path3.getElements().clear();      
        path4.getElements().clear();      
    }
    
    public void nullPaths(){
        path1 = null;
        path2 = null;
        path3 = null;
        path4 = null;    
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
    
    public void setPathTrajectories(){
        path1 = animationBackEnd.createEllipsePath(center.getCenterX()+200+8*(car.getRadius()-25),
                center.getCenterY(),200+8*(car.getRadius()-25),
                200+8*(car.getRadius()-25));
        path2 = animationBackEnd.createEllipsePath(center.getCenterX()+180+180/25*(car.getRadius()-25)-15*(car.getMass()-2),
                center.getCenterY(),180+180/25*(car.getRadius()-25)-15*(car.getMass()-2),
                180+180/25*(car.getRadius()-25)-15*(car.getMass()-2));  
        path3 = animationBackEnd.createEllipsePath(center.getCenterX()+180+180/25*(car.getRadius()-25),
                center.getCenterY(),180+180/25*(car.getRadius()-25),
                180+180/25*(car.getRadius()-25));     
        path4 = path3;
    }
    
    public void setPathTrajectories(boolean b){
        path1 = animationBackEnd.createEllipsePath(center.getCenterX()+200+8*(41-25),
            center.getCenterY(),200+8*(41-25),
            200+8*(41-25));            
        path2 = animationBackEnd.createEllipsePath(center.getCenterX()+180+180/25*(41-25),
            center.getCenterY(),180+180/25*(41-25),
            180+180/25*(41-25));
        path3 = animationBackEnd.createEllipsePath(center.getCenterX()+180+180/25*(41-25),
            center.getCenterY(),180+180/25*(41-25),
            180+180/25*(41-25));         
        path4 = path3;
    }
    
    public void setSimulationNodesAnimationRate(){
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
    }
}
