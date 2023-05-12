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
 * A class used to handle everything concerning the simulation, its FXML components, and its display.
 * @author Victor-Pen
 */
public class UniformCircularMotionController extends Stage{   
    /**The pause button of the simulation. */
    @FXML
    ImageView pauseButton;
    /**The play button of the simulation. */
    @FXML
    ImageView playButton;
    /**The reset button of the simulation. */
    @FXML
    ImageView resetButton;
    /**The TextField that will hold the value of the car's radius. */
    @FXML
    TextField radiusTextField;
    /**The Slider that will hold the value of the car's radius. */
    @FXML
    MFXSlider radiusSlider;
    /**The TextField that will hold the value of the car's speed. */
    @FXML
    TextField speedTextField;
    /**The Slider that will hold the value of the car's speed. */
    @FXML
    MFXSlider speedSlider;
    /**The TextField that will hold the value of the car's mass. */
    @FXML
    TextField massTextField;
    /**The Slider that will hold the value of the car's mass. */
    @FXML
    MFXSlider massSlider;
    /**The CheckBox that will hide the acceleration vector if clicked on. */
    @FXML
    MFXCheckbox accelerationMagnitudeCheckBox;
    /**The CheckBox that will hide the force vector if clicked on. */
    @FXML
    MFXCheckbox forceMagnitudeCheckBox;
    /**The Text that will display the value of the centripetal acceleration. */
    @FXML
    Text centripetalAccelerationText;
    /**The Text that will display the value of the force. */
    @FXML
    Text forceText;
    /**The Pane that contains the simulation. */
    @FXML
    Pane paneSimulate;
    /**The Text that will display the value of the angle the force and acceleration vectors make with respect to the center of the revolving path. */
    @FXML
    Text angleText;
    /**The Text that will display the value of the X component of the centripetal acceleration. */
    @FXML
    Text accelerationXText;
    /**The Text that will display the value of the Y component of the centripetal acceleration. */
    @FXML
    Text accelerationYText;
    /**The Text that will display the value of the X component of the force. */
    @FXML
    Text forceXText;
    /**The Text that will display the value of the Y component of the force. */
    @FXML
    Text forceYText;
    /**The Menu Button that, when clicked on, shows the user the options of going to another simulation or quitting the application. */
    @FXML
    MenuItem menuItemConservationEnergy;
    /**The Menu Item that, when clicked on, sends the user to the Stacked Blocks Simulation. */
    @FXML
    MenuItem menuItemStacked;
    /**The Menu Item that, when clicked on, sends the user to the Projectile Motion Simulation. */
    @FXML
    MenuItem menuItemProjectile;
    /**The Menu Item that, when clicked on, quits the window. */
    @FXML
    MenuItem menuItemQuit;
    /**The Menu Item that, when clicked on, lets the user change the color of the path. */
    @FXML
    MenuItem menuItemChangePathColor;   
    /**The Button that, when clicked on, sends the user back to the main menu. */
    @FXML
    ImageView buttonHome; 
    /**The Button that, when clicked on, shows the user the help page. */
    @FXML
    ImageView helpButton; 
    /**The BorderPane containing the simulation and all the sliders, Texts, and TextFields. */
    @FXML
    BorderPane uniformCircularMotionBorderPane;
    /**The Text that will display a warning message if the user goes above a certain value for the mass. */
    @FXML
    Text warningMassText = new Text();
    /**The Text that will display a warning message if the user goes above a certain value for the speed. */
    @FXML
    Text warningSpeedText = new Text();
    /**The Text that will display a warning message if the user goes above a certain value for the radius. */
    @FXML
    Text warningRadiusText = new Text();
    /**The Stage containing the simulation. */    
    Stage mainWindow = new Stage();
    /**The Car that is revolving in the simulation. */        
    Car car = new Car();
    /**The Path that the Car is revolving on. */        
    Path pathCar = new Path();
    /**The Path that the Force Vector is revolving on. */        
    Path pathForceVector = new Path();
    /**The Path that the Acceleration Vector is revolving on. */        
    Path pathAccelerationVector = new Path();   
    /**The Rectangle that is used to represent the car in the simulation. */        
    Rectangle rectTest = new Rectangle(50,30, Color.ORANGE);
    /**The Vector that represents the centripetal force vector of the car. */        
    Vector vectorForce = new Vector(0,0, 30, 60,Settings.VECTOR_TYPE[0]);
    /**The Vector that represents the centripetal acceleration vector of the car. */        
    Vector vectorAcceleration = new Vector(0,0, 30, 30, Settings.VECTOR_TYPE[1]);      
    /**The Center around which the car and arrows will revolve. */        
    Circle center = new Circle(Settings.CENTER_MARKER_X_COORDINATE, Settings.CENTER_MARKER_Y_COORDINATE, 2, Color.RED);
    /**The Group that houses all paths, and nodes that will be displayed and that will change with the parameters of the simulation. */        
    Group group = new Group();      
    /**The PathTransition that the Car is revolving on. */        
    PathTransition pathTransitionCircleCar = new PathTransition();
    /**The PathTransition that the Force Vector is revolving on. */        
    PathTransition pathTransitionCircleForceVector = new PathTransition();
    /**The PathTransition that the Acceleration Vector is revolving on. */        
    PathTransition pathTransitionCircleAccelerationVector = new PathTransition();  
    /**Indicates whether the simulation is playing (true) or not (false). */        
    private boolean playing = false;
    /**An instantiation of the class containing additional methods for the simulation that are more back end. */        
    SimulationBackEnd simulationBackEnd = new SimulationBackEnd();
    /**An animation timer that dynamically updates the values of force, acceleration, and its components depending on the parameters set. */        
    AnimationTimer timerAngle = new AnimationTimer() {      
        @Override
        public void handle(long l) {
            useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
        };
    };
    
    /**
     * Initializes methods so that they can work in the simulation.
     */
    @FXML
    void initialize(){
        System.out.println("Booting up simulation...");
        setUp();
        //sends the user back to the main menu
        buttonHome.setOnMouseClicked((e) -> {        
            simulationBackEnd.switchSimulation("mainmenu", uniformCircularMotionBorderPane);
        });
        
        //sends the user to the projectile motion simulation
        menuItemProjectile.setOnAction((e) ->{
            simulationBackEnd.switchSimulation("projectile", uniformCircularMotionBorderPane);
        });
        
        //sends the user to the stacked block simulation
        menuItemStacked.setOnAction((e) ->{
            simulationBackEnd.switchSimulation("stackedblock", uniformCircularMotionBorderPane);
        });

        //sends the user to theenergy conservation simulation
        menuItemConservationEnergy.setOnAction((e) ->{
            simulationBackEnd.switchSimulation("conservation", uniformCircularMotionBorderPane);
        });
        
        //closes the application
        menuItemQuit.setOnAction((e) ->{
            simulationBackEnd.switchSimulation("quit", uniformCircularMotionBorderPane);
        });
    }
    
    /**
     * Changes the color of the path, using a color picker window that pops up.
     */
    @FXML
    public void changePathColor(){
        menuItemChangePathColor.setOnAction((event) -> {
            ChangeColorWindow changeColorWindow = new ChangeColorWindow(mainWindow);
            menuItemChangePathColor.setDisable(true);
            changeColorWindow.changeColor(pathCar);
            changeColorWindow.setDisableColorWindowButton(menuItemChangePathColor);
        });
    }
    
    /**
     * Pauses the simulation.
     */
    @FXML
    void pause(){
        pauseButton.setOnMouseClicked((event) -> {
            setImageViewsDisabling(true, false, false);
            pauseAllPathTransition();
            timerAngle.stop();
        });
    }
    
    /**
     * Plays the simulation and locks the play button, while making the pause and reset button enabled to be clicked on.
     */
    @FXML
    void play(){
        playButton.setOnMouseClicked((event) -> {
            if (car.getSpeedMetersPerSeconds() != 0) {
                setImageViewsDisabling(false, false, true);
                timerAngle.start();
                if (!playing) {
                    revolveCar();
                    playing = true;
                }
                pauseAllPathTransition();
                playAllPathTransition();                
            }
        });
    }

    /**
     * Pop ups the help page in another window. Freezes the help button while window is up.
     */
    @FXML
    void displayHelpPage(){
        helpButton.setOnMouseClicked((event) -> {
        helpButton.setDisable(true);
        Stage stage = new Stage();
                FXMLLoader uniformCircularMotionHelpLoader = new FXMLLoader(getClass().getResource("/fxml/helpUniformCircularMotion.fxml"));
                Pane root = null;               
            try {
                //loading the help page
                UniformCircularMotionHelpController uniformCircularMotionHelpController = new UniformCircularMotionHelpController(stage);
                uniformCircularMotionHelpLoader.setController(uniformCircularMotionHelpController);
                root = uniformCircularMotionHelpLoader.load();               
            } catch (IOException ex) {
                Logger.getLogger(UniformCircularMotionHelpController.class.getName()).log(Level.SEVERE, null, ex);
            } 
                //displaying the help page
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("About Uniform Circular Motion simulation...");
                stage.showAndWait();
                helpButton.setDisable(false);
        });
    }
    
    /**
     * Resets the simulation back to default settings.
     */
    @FXML
    void reset(){
        resetButton.setOnMouseClicked((event) -> {
            setImageViewsDisabling(true, true, false);
            stopAllPathTransition();
            group.getChildren().clear();
            //resetting back to default parameters
            setInitialParameters(Settings.CAR_INITIAL_RADIUS_METERS, Settings.CAR_INITIAL_SPEED_METERS_PER_SECONDS, Settings.CAR_INITIAL_MASS_KILOGRAMS);   
            useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
            timerAngle.stop();
            //clearing all paths, PathTransitions, and warning messages (basically making the simulation display blank)
            clearPathElements();
            clearPathTransitions();
            clearWarningMessages();
            playing = false;
      });
    }    
    
    /**
     * Sets up the necessary methods, and initial parameters for the simulation.
     */
    @FXML
    public void setUp(){
        setImageViewsDisabling(true, true, false);
        setInitialParameters(Settings.CAR_INITIAL_RADIUS_METERS, Settings.CAR_INITIAL_SPEED_METERS_PER_SECONDS, Settings.CAR_INITIAL_MASS_KILOGRAMS);   
        useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
        paneSimulate.getChildren().add(center);  
        setSliders();
        pause();
        play();
        reset();
        displayHelpPage();
        changePathColor();
        paneSimulate.getChildren().add(group);
        setVectorsVisibility();
    }
    
    /**
     * Hides or shows the acceleration or force vectors with their respective CheckBoxes.
     */
    @FXML
    public void setVectorsVisibility(){
        forceMagnitudeCheckBox.setOnAction((event) -> {
            if (forceMagnitudeCheckBox.isSelected()) {
                //hides the force vector if the Force magnitude CheckBox is checked
                System.out.println("Hiding force magnitude");
                vectorForce.getArrowBody().setVisible(false);
            }
            else{
                //shows the force vector if the Force magnitude CheckBox is unchecked
                vectorForce.getArrowBody().setVisible(true);
            }
        });
        accelerationMagnitudeCheckBox.setOnAction((event) -> {
            if (accelerationMagnitudeCheckBox.isSelected()) {
                //hides the accelertaion vector if the accelertaion magnitude CheckBox is checked
                System.out.println("Hiding acceleration magnitude");
                vectorAcceleration.getArrowBody().setOpacity(0);
            }
            else{
                //shows the accelertaion vector if the accelertaion magnitude CheckBox is unchecked
                vectorAcceleration.getArrowBody().setOpacity(1);
            }            
        });
    }
    
    /**
     * Sets the initial parameters of the radius, speed, and mass of the simulation into the sliders and TextFields.
     * @param initialRadius the initial value of the car's radius
     * @param initialSpeed the initial speed of the car's radius
     * @param initialMass the initial mass of the car's radius
     */
    public void setInitialParameters(double initialRadius, double initialSpeed, double initialMass){
        radiusTextField.setText(String.valueOf(initialRadius));
        radiusSlider.setValue(initialRadius);
        massTextField.setText(String.valueOf(initialMass));
        massSlider.setValue(initialMass);
        speedTextField.setText(String.valueOf(initialSpeed));
        speedSlider.setValue(initialSpeed);    
    }
    
    /**
     * Sets up the revolving of the car when the simulation starts.
     */
    public void revolveCar(){       
        stopAllPathTransition();
        vectorForce.setOpacity(0.5);
        if (!paneSimulate.getChildren().contains(rectTest)) {
            nullPaths();
            clearPathTransitions();           
            setPathTrajectories();            
            group.getChildren().addAll(rectTest, pathCar, vectorForce.getArrowBody(), vectorAcceleration.getArrowBody());
            //creates the path transitions for the car, and force and acceleration vectors
            pathTransitionCircleCar = simulationBackEnd.createPathTransitionCircle(pathCar, (Node) rectTest, car);
            pathTransitionCircleForceVector = simulationBackEnd.createPathTransitionCircle(pathForceVector,  vectorForce.getArrowBody(), car);      
            pathTransitionCircleAccelerationVector = simulationBackEnd.createPathTransitionCircle(pathAccelerationVector,  vectorAcceleration.getArrowBody(), car);      
        }               
        playAllPathTransition();
    }
    
    /**
     * Sets all the sliders correctly and make them link with their respective TextFields.
     */
    public void setSliders(){
        simulationBackEnd.setSliderRange(radiusSlider, 1, 25);
        simulationBackEnd.setSliderRange(massSlider, 0, 40);
        simulationBackEnd.setSliderRange(speedSlider, 0, 30);
        
        //links all sliders to their respective TextFields
        linkRadiusSliderToTextField();
        linkMassSliderToTextField();
        linkSpeedSliderToTextField();
        
        //links all TextFields to their respective sliders
        linkMassTextFieldToSlider();
        linkRadiusTextFieldToSlider();
        linkSpeedTextFieldToSlider();
    }
    
    /**
     * Synchronizes the values in the radius slider to its TextField.
     */
    public void linkRadiusSliderToTextField(){
        radiusSlider.setOnMouseDragged((event) -> {
            radiusTextField.setText(String.valueOf(Formulas.roundTwoDecimals(radiusSlider.getValue())));
            useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
            adjustCarRadius();
            if (!pauseButton.isDisabled()) {
                //when the animation is still playing
                playAllPathTransition();
            }
            else{
                //when the animation is paused
                pathTransitionCircleCar.playFromStart();
                pathTransitionCircleForceVector.playFromStart();
                pathTransitionCircleAccelerationVector.playFromStart();
                stopAllPathTransition();
            }    
            //to prevent anything from showing up before starting the simulation
            if (!resetButton.isDisabled()) {
                group.getChildren().addAll(rectTest, pathCar, vectorForce.getArrowBody(),vectorAcceleration.getArrowBody());                
            }
        });                
    }
    
    /**
     * Synchronizes the values in the speed slider to its TextField.
     */
    public void linkSpeedSliderToTextField(){
        speedSlider.setOnMouseDragged((event) -> {
            speedTextField.setText(String.valueOf(Formulas.roundTwoDecimals(speedSlider.getValue())));
            useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
            pauseAllPathTransition();
            adjustSimulationNodesAnimationRate();
            //to play the simulation only when it is not paused
            if (!pauseButton.isDisabled() && group.getChildren().contains(rectTest)) {
               playAllPathTransition();
            }
        });
    }

    /**
     * Synchronizes the values in the mass slider to its TextField.
     */
    public void linkMassSliderToTextField(){
        massSlider.setOnMouseDragged((event) -> {
                massTextField.setText(String.valueOf(Formulas.roundTwoDecimals(massSlider.getValue())));
                useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
                if (!forceMagnitudeCheckBox.isSelected()) {
                    adjustForceVectorOpacity();                
                }
        });        
    }

    /**
     * Synchronizes the values in the radius TextField to its slider.
     */
    public void linkRadiusTextFieldToSlider(){
        radiusTextField.setOnKeyTyped((event) -> {
            try {
                useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
                radiusSlider.setValue(Double.valueOf(radiusTextField.getText()));          
                //prevents the user from having too big or small values that will make the simulation look weird
                if (Double.valueOf(radiusTextField.getText()) < 1 || Double.valueOf(radiusTextField.getText()) > Settings.SIMULATION_INPUT_MAXIMUM) {
                    throw new NumberFormatException();
                }
                adjustCarRadius();  
                //to prevent anything from showing up before starting the simulation
                if (!resetButton.isDisabled()) {
                    group.getChildren().addAll(rectTest, pathCar, vectorForce.getArrowBody(),vectorAcceleration.getArrowBody());                
                }  
            } catch (NumberFormatException e) {
                    //error in case there is any erroneous value put in the textfield
                    if (!radiusTextField.getText().isBlank()) {
                    simulationBackEnd.showErrorAlertAndReset(radiusTextField, radiusSlider, 20, "Invalid Radius Input. Please Try Again");
                    useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
                        linkRadiusTextFieldToSlider();
                    }
            }
        });
    }
    
    /**
     * Synchronizes the values in the speed TextField to its slider.
     */
    public void linkSpeedTextFieldToSlider(){
        speedTextField.setOnKeyTyped((event) -> {
            try {
                speedSlider.setValue(Double.valueOf(speedTextField.getText()));
                useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
                //prevents the user from having too big or small values that will make the calculations be wrong
                if (Double.valueOf(speedTextField.getText()) > Settings.SIMULATION_INPUT_MAXIMUM) {
                    throw new NumberFormatException();
                }
                adjustSimulationNodesAnimationRate();
            } catch (NumberFormatException e) {
                    //error in case there is any erroneous value put in the textfield
                if (!speedTextField.getText().isBlank()) {
                    simulationBackEnd.showErrorAlertAndReset(speedTextField, speedSlider, 10, "Invalid Speed Input. Please Try Again");
                    useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
                    linkSpeedSliderToTextField();
                }
            }
        });
    }

    /**
     * Synchronizes the values in the mass TextField to its slider.
     */
    public void linkMassTextFieldToSlider(){
        massTextField.setOnKeyTyped((event) -> {
            try {
                massSlider.setValue(Double.valueOf(massTextField.getText()));
                useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
                if (!forceMagnitudeCheckBox.isSelected()) {
                    adjustForceVectorOpacity();                
                }
            } catch (NumberFormatException e) {
                //error in case there is any erroneous value put in the textfield
                if (!massTextField.getText().isBlank()) {
                    simulationBackEnd.showErrorAlertAndReset(massTextField, massSlider, 20, "Invalid Mass Input. Please Try Again");
                    useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
                    linkMassTextFieldToSlider();
                    }
                }
        });                    
    }
    
    /**
     * Calculates the acceleration of the car using the entered parameters.
     * @param mass the current mass of the car
     * @param speed the current speed of the car
     * @param radius the current radius of the car
     */
    public void useEnteredValuesToCalculate(Double mass, Double speed, Double radius){
        //setting the variables of the car
        car.setMassKilograms(mass);
        car.setSpeedMetersPerSeconds(speed);
        car.setRadiusMeters(radius);
        
        //determining the angle
        double coordinateX = rectTest.getTranslateX()+rectTest.getWidth()/2;
        double coordinateY = -center.getCenterY()+rectTest.getTranslateY()+rectTest.getHeight()/2;
        double angle = Formulas.getAngleDegrees((rectTest.getTranslateX()-center.getCenterX()+rectTest.getWidth()/2),(rectTest.getTranslateY()-center.getCenterY()+rectTest.getHeight()/2))*180/Math.PI;
        angle = Formulas.determineQuadrantDegrees(angle, coordinateX, coordinateY);
        //setting the values for the texts of the force, acceleration and their components
        angleText.setText(String.valueOf(Formulas.roundTwoDecimals(angle)));
        centripetalAccelerationText.setText(String.valueOf(Formulas.roundTwoDecimals(Formulas.calculateAccelerationCentripetalMetersPerSecondsSquared(car))));
        accelerationXText.setText(String.valueOf(-Formulas.roundTwoDecimals(-Formulas.returnMagnitudeXComponent(Double.valueOf(centripetalAccelerationText.getText()), angle))));
        accelerationYText.setText(String.valueOf(-Formulas.roundTwoDecimals(-Formulas.returnMagnitudeYComponent(Double.valueOf(centripetalAccelerationText.getText()), angle))));
        forceText.setText(String.valueOf(Formulas.roundTwoDecimals(Formulas.calculateForceNewtons(car))));
        forceXText.setText(String.valueOf(-Formulas.roundTwoDecimals(-Formulas.returnMagnitudeXComponent(Double.valueOf(forceText.getText()), angle))));
        forceYText.setText(String.valueOf(-Formulas.roundTwoDecimals(-Formulas.returnMagnitudeYComponent(Double.valueOf(forceText.getText()), angle))));        
    }
    
    /** Pauses all path transitions in the simulation. */
    public void pauseAllPathTransition(){
        pathTransitionCircleCar.pause();
        pathTransitionCircleForceVector.pause();
        pathTransitionCircleAccelerationVector.pause();
    }
    
    /**
     * Stops all path transitions in the simulation.
     */
    public void stopAllPathTransition(){
        pathTransitionCircleCar.stop();
        pathTransitionCircleForceVector.stop();
        pathTransitionCircleAccelerationVector.stop();
    }

    /** Plays all path transitions in the simulation. */
    public void playAllPathTransition(){
        pathTransitionCircleCar.play();
        pathTransitionCircleForceVector.play();
        pathTransitionCircleAccelerationVector.play();
    }
    
    /**
     * Sets the pause, reset, and play button to whether they can be clicked on or not
     * @param pauseBoolean whether the pause button can be clicked on (false) or not (true) 
     * @param resetBoolean whether the reset button can be clicked on (false) or not (true) 
     * @param playBoolean whether the play button can be clicked on (false) or not (true) 
     */
    public void setImageViewsDisabling(boolean pauseBoolean, boolean resetBoolean, boolean playBoolean){
        pauseButton.setDisable(pauseBoolean);
        resetButton.setDisable(resetBoolean);
        playButton.setDisable(playBoolean);
    }

    /**
     * Wipes away all elements that are attached to the path variables, and resets the path variables to null.
     */
    public void clearPathElements(){
        pathCar.getElements().clear();
        pathForceVector.getElements().clear();      
        pathAccelerationVector.getElements().clear();      
    }
    
    /**
     * Removes all paths.
     */
    public void nullPaths(){
        pathCar = null;
        pathForceVector = null;
        pathAccelerationVector = null;
    }
    
    /**
     * Wipes away all paths and nodes associated to the three Path Transitions.
     */
    public void clearPathTransitions(){
        pathTransitionCircleCar.setNode(null);
        pathTransitionCircleCar.setPath(null);
        pathTransitionCircleForceVector.setNode(null);
        pathTransitionCircleForceVector.setPath(null);
        pathTransitionCircleAccelerationVector.setNode(null);
        pathTransitionCircleAccelerationVector.setPath(null);
    }
    
    /**
     * Removes all warning messages from the window.
     */
    public void clearWarningMessages(){
       warningMassText.setText("");
       warningSpeedText.setText("");
       warningRadiusText.setText("");
    }
    
    /**
     * Sets the trajectory of the paths 
     */
    public void setPathTrajectories(){
        //setting the path for the car
        pathCar = simulationBackEnd.createEllipsePath(center.getCenterX()+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_CAR+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_CAR/Settings.CAR_INITIAL_RADIUS_METERS*(car.getRadiusMeters()-Settings.CAR_INITIAL_RADIUS_METERS),
                center.getCenterY(),Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_CAR+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_CAR/Settings.CAR_INITIAL_RADIUS_METERS*(car.getRadiusMeters()-Settings.CAR_INITIAL_RADIUS_METERS),
                Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_CAR+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_CAR/Settings.CAR_INITIAL_RADIUS_METERS*(car.getRadiusMeters()-Settings.CAR_INITIAL_RADIUS_METERS));
        //setting the path for the force vector
        pathForceVector = simulationBackEnd.createEllipsePath(center.getCenterX()+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS/Settings.CAR_INITIAL_RADIUS_METERS*(car.getRadiusMeters()-Settings.CAR_INITIAL_RADIUS_METERS),
                center.getCenterY(),Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS/Settings.CAR_INITIAL_RADIUS_METERS*(car.getRadiusMeters()-Settings.CAR_INITIAL_RADIUS_METERS),
                Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS/Settings.CAR_INITIAL_RADIUS_METERS*(car.getRadiusMeters()-Settings.CAR_INITIAL_RADIUS_METERS));     
        //setting the path for the acceleration vector
        pathAccelerationVector = simulationBackEnd.createEllipsePath(center.getCenterX()+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS/Settings.CAR_INITIAL_RADIUS_METERS*(car.getRadiusMeters()-Settings.CAR_INITIAL_RADIUS_METERS),
                center.getCenterY(),Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS/Settings.CAR_INITIAL_RADIUS_METERS*(car.getRadiusMeters()-Settings.CAR_INITIAL_RADIUS_METERS),
                180+Settings.CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS/Settings.CAR_INITIAL_RADIUS_METERS*(car.getRadiusMeters()-Settings.CAR_INITIAL_RADIUS_METERS)); 
        
    }
    
    /**
     * Sets the trajectories for the paths when the radius of the car is bigger than 41 m.
     * @param b a parameter Boolean that is used to override the setPathTrajectories() method.
     */
    public void setPathTrajectories(boolean b){
        pathCar = simulationBackEnd.createEllipsePath(center.getCenterX()+Settings.MAX_RADIUS_RECTANGLE,
            center.getCenterY(),Settings.MAX_RADIUS_RECTANGLE,
            Settings.MAX_RADIUS_RECTANGLE);            
        pathForceVector = simulationBackEnd.createEllipsePath(center.getCenterX()+Settings.MAX_RADIUS_VECTORS,
            center.getCenterY(),Settings.MAX_RADIUS_VECTORS,
            Settings.MAX_RADIUS_VECTORS); 
        pathAccelerationVector = simulationBackEnd.createEllipsePath(center.getCenterX()+Settings.MAX_RADIUS_VECTORS,
            center.getCenterY(),Settings.MAX_RADIUS_VECTORS,
            Settings.MAX_RADIUS_VECTORS);         
    }
    
    /**
     * Adjusts the rate that the nodes are traveling at.
     */
    public void adjustSimulationNodesAnimationRate(){
        if (car.getSpeedMetersPerSeconds() <= Settings.SIMULATION_MAXIMUM_SPEED_METERS_PER_SECONDS) {
            //if the speed is within the range that is supported by the simulation
            pathTransitionCircleCar.setRate(0.1*car.getSpeedMetersPerSeconds());
            pathTransitionCircleForceVector.setRate(0.1*car.getSpeedMetersPerSeconds());
            pathTransitionCircleAccelerationVector.setRate(0.1*car.getSpeedMetersPerSeconds());
            warningSpeedText.setText("");
        }
        else{
            //fix the rate if the speed is above the limit (200 m/s)
            pathTransitionCircleCar.setRate(20);
            pathTransitionCircleForceVector.setRate(20);
            pathTransitionCircleAccelerationVector.setRate(20);
            warningSpeedText.setText(Settings.SPEED_LIMIT_MESSAGE);
            warningSpeedText.setFill(Color.RED);
        }    
    }
    
    /**
     * Adjusts the opacity of the force vector.
     */
    public void adjustForceVectorOpacity(){
        if (car.getMassKilograms() <= Settings.SIMULATION_MAXIMUM_MASS_KILOGRAMS && car.getMassKilograms()> 0) {
            //if the mass is within the range that is supported by the simulation
            vectorForce.getArrowBody().setVisible(true);               
            vectorForce.setOpacity(0.025*car.getMassKilograms());
            warningMassText.setText("");
        }
        else if (car.getMassKilograms() == 0) {
            //for when the mass is 0 kg (no mass)
            vectorForce.getArrowBody().setVisible(false);               
            vectorForce.setOpacity(0.025*car.getMassKilograms());
            warningMassText.setText("");
        }
        else if (car.getMassKilograms() > Settings.SIMULATION_INPUT_MAXIMUM) {
            //if the mass exceeds the maximum limit supported by the possible calculations in the simulation
            vectorForce.setOpacity(1);
            throw new NumberFormatException();
        }
        else{
            //if the mass exceeds the limit of the simulation, but is still within the range of calculations possible.
            vectorForce.getArrowBody().setVisible(true);               
            linkRadiusSliderToTextField();
            warningMassText.setText(Settings.MASS_LIMIT_MESSAGE);
            warningMassText.setFill(Color.RED);                    
        }  
    }
    
    /** Adjusts the opacity of the car radius depending on the parameters that have been set.*/
    public void adjustCarRadius(){
        useEnteredValuesToCalculate(simulationBackEnd.retrieveTextField(massTextField), simulationBackEnd.retrieveTextField(speedTextField), simulationBackEnd.retrieveTextField(radiusTextField));
        stopAllPathTransition();
        group.getChildren().clear();
        clearPathElements();
        clearPathTransitions();           
        if (car.getRadiusMeters() <= Settings.SIMULATION_MAXIMUM_RADIUS_METERS) {
            //if the radius is within the range that is supported by the simulation
            setPathTrajectories();
            warningRadiusText.setText("");
        }
        else{
            //if the radius exceeds the range that is supported by the simulation
            setPathTrajectories(true);
            warningRadiusText.setText(Settings.RADIUS_LIMIT_MESSAGE);
            warningRadiusText.setFill(Color.RED);                
        }

        pathTransitionCircleCar.setNode(rectTest);
        pathTransitionCircleCar.setPath(pathCar);           
        pathTransitionCircleForceVector.setNode(vectorForce.getArrowBody());
        pathTransitionCircleForceVector.setPath(pathForceVector); 
        pathTransitionCircleAccelerationVector.setNode(vectorAcceleration.getArrowBody());
        pathTransitionCircleAccelerationVector.setPath(pathAccelerationVector); 
        
        //play the simulation if the simulation was still playing, else it pauses
        if (!pauseButton.isDisabled()) {
            playAllPathTransition();
        }
        else{
            playAllPathTransition();
            stopAllPathTransition();
        }        
    }
}