/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import edu.vanier.physnics.UniformCircularMotionSimulation.UniformCircularMotionController;
import edu.vanier.physnics.conservation.ConservationController;
import edu.vanier.physnics.mainmenu.MainMenuController;
import edu.vanier.physnics.stackedblock.BlockFrontEndController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Instantiates the FXML UI controls and shapes to be used in the main application.
 * Handles action events in the main application such as animation, opening new scenes, 
 * and handling sliders. 
 * 
 * @author vireshpatel43
 */
public class MainAppController {
    
    // List of all elements initially present in the FXML
    @FXML
    private MenuItem menuItemMainMenu,
            menuItemConservationOfEnergy,
            menuItemStackedBlock,
            menuItemUniformCircularMotion,
            menuItemExit;
    
    @FXML
    private Label maxHeightLabel,
            flightTimeLabel,
            distanceLabel,
            animationOffScreenLabel;

    @FXML
    private MFXSlider sliderInitialVelocity,
            sliderLaunchAngle,
            sliderGravity;

    @FXML
    private MFXButton buttonClear, buttonSettings;

    @FXML
    private ImageView buttonPlay,
            buttonPause,
            buttonReset,
            buttonHelp,
            buttonGraph,
            buttonHome,
            cannonBarrel;

    @FXML
    private Circle projectileBall;
    
    @FXML
    private MFXToggleButton showTrailToggleButton;
    
    @FXML
    private Pane paneAnimation;
    
    // Instance of animation class to be used in the controller
    Animation animation = new Animation();
    
    // Variables to hold slider values
    private double gravityMetersPerSecondSquared;
    private double initialVelocityMetersPerSecond;
    private double launchAngleDegrees;
    // List of all sliders in scene. 
    private List<MFXSlider> sliderList;

    // Establishes Pane boundaries.
    private final double MAX_PANE_WIDTH = 1662;
    private final double MAX_PANE_HEIGHT = 867;

    /**
     * Plays the animation. Gets the values from sliders, and uses them as
     * parameters for the animation. Sets the text of on-screen data on play.
     * 
     */
    public void handlePlay() {
        gravityMetersPerSecondSquared = sliderGravity.getValue();
        initialVelocityMetersPerSecond = sliderInitialVelocity.getValue();
        launchAngleDegrees = sliderLaunchAngle.getValue();
        animation.playAnimation(projectileBall, launchAngleDegrees, gravityMetersPerSecondSquared, initialVelocityMetersPerSecond);
        // Removes text from out of bounds label at beginning of every play
        animationOffScreenLabel.setText("");
        // handles exception if animation is out of bounds
        animation.handleBallOutOfBounds(animationOffScreenLabel);
        // Rounds data to 2 decimal places 
        DecimalFormat round = new DecimalFormat("#.00");
        // Sets the data on-screen for the user to see
        // Sets flight time on screen
        flightTimeLabel.setText("Flight Time (seconds): " + round.format(Equations.getFlightTimeSeconds(launchAngleDegrees, initialVelocityMetersPerSecond, gravityMetersPerSecondSquared)) + "s");
        // Sets max height on screen
        maxHeightLabel.setText("Max Height (meters): " + round.format(Equations.getMaxHeightMeters(launchAngleDegrees, initialVelocityMetersPerSecond, gravityMetersPerSecondSquared)) + "m");
        // Sets distance on screen
        distanceLabel.setText("Distance (meters): " + round.format(Equations.getXdisplacementMeters(launchAngleDegrees, initialVelocityMetersPerSecond, gravityMetersPerSecondSquared)) + "m");
        
    }

    /**
     * Pauses the animation
     */
    public void handlePause() {
        // Pause the simulation
        animation.pauseAnimation();
            
    }
    
    /**
     * Resets the the ball back to the default position on leftClick.
     *  
     */
    public void handleReset() {
        animation.resetBall(projectileBall);
        // Sets out of bounds label to blank
        animationOffScreenLabel.setText("");
        
    }
    
    
    /**
     * Opens the settings page
     */
    public void handleSettings() {
        // Loader for scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/projectileSettings.fxml"));
        Pane root = null;
        //Sets the controller
        SettingsController settingsController = new SettingsController(paneAnimation, projectileBall);
        loader.setController(settingsController);

        // Loads the scene graph into Pane
        try {
            root = loader.load();
        } catch (IOException exceptionLoadingFXML) {
            Logger.getLogger(HelpPage.class.getName()).log(Level.SEVERE, null, exceptionLoadingFXML);
        }

        // Creates scene based on scene graph
        Scene settingsWindow = new Scene(root);
        // New stage for scene
        Stage stage = new Stage();
        // Sets controller for stage
        settingsController.setStage(stage);
        // Applies scene to Stage
        stage.setScene(settingsWindow);
        stage.sizeToScene();
        stage.setTitle("Settings");
        stage.show();

    }

    /**
     * Opens the help page by creating instance of HelpPage and 
     * calling openHelpWindow().
     */
    public void handleHelp() {
        HelpPage helpPage = new HelpPage();
        helpPage.openHelpWindow();
    }
    
    /**
     * Opens the graphs page
     */
    public void handleGraphs() {
        // loader for scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/projectileGraphs.fxml"));
        Pane root = null;
        // Sets the controller
        GraphsController graphsPageController = new GraphsController(sliderInitialVelocity.getValue(), sliderLaunchAngle.getValue(), sliderGravity.getValue());
        loader.setController(graphsPageController);

        // Loads the scene graph into Pane
        try {
            root = loader.load();
        } catch (IOException exceptionLoadingFXML) {
            Logger.getLogger(HelpPage.class.getName()).log(Level.SEVERE, null, exceptionLoadingFXML);
        }

        // Creates scene based on scene graph
        Scene graphsPage = new Scene(root);
        // New stage for scene
        Stage stage = new Stage();
        // Sets controller for stage
        graphsPageController.setStage(stage);
        // Applies scene to Stage
        stage.setScene(graphsPage);
        stage.sizeToScene();
        stage.setTitle("Graphs");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


    /**
     * Resets all slider values to minimum
     * @param sliderList list of all sliders
     */
    public void handleClear(List<MFXSlider> sliderList) {
        for (MFXSlider slider : sliderList) {
            slider.setValue(slider.getMin());
        }
    }
    
    /**
     * Rotates the cannon using the rotateCanon method and the
     * angle given by user
     * @param cannonBarrel image to be rotated
     */
    public void handleRotateCannon(ImageView cannonBarrel) {
        double launchAngle = sliderLaunchAngle.getValue();
        animation.rotateCannon(cannonBarrel, launchAngle);
    }


    /**
     * Initializes all FXML components. Sets action events for sliders, buttons.
     * Sets initial values for parameters.
     */
    @FXML
    public void initialize() {
        // Adds all sliders to sliderList
        sliderList = new ArrayList<MFXSlider>();
        sliderList.add(sliderGravity);
        sliderList.add(sliderInitialVelocity);
        sliderList.add(sliderLaunchAngle);
        
        // Initial angle for cannon
        cannonBarrel.rotateProperty().set(-20);
        
        // Initializes offScreenLabel as blank
        animationOffScreenLabel.setText("");
        
        // Sets boundaries for paneAnimation so that animation does not spill over to other sections.
        Rectangle clipRectangle = new Rectangle(MAX_PANE_WIDTH, MAX_PANE_HEIGHT);
        paneAnimation.setClip(clipRectangle);

        // Sets initial background for animation
        Image defaultImage = new Image(getClass().getResourceAsStream("/images/settings/cartoon_field.png"));
        BackgroundImage backgroundImage = new BackgroundImage(defaultImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        paneAnimation.setBackground(new Background(backgroundImage));
        
        // Toggles the trail.
        showTrailToggleButton.setOnAction(leftClick -> {
            updateTrail();
        });
        
        // Action events for sliders. Updates the trail when slider is updates or updates the cannon angle when angle is changed. 
        sliderInitialVelocity.setOnMouseDragged(leftClick -> {
            updateTrail();
        });
        
        sliderInitialVelocity.setOnKeyPressed(arrowPressed -> {
            updateTrail();
        });
        
        sliderInitialVelocity.setOnMouseClicked(leftClick -> {
            updateTrail();
        });

        sliderLaunchAngle.setOnMouseDragged(leftClick -> {
            handleRotateCannon(cannonBarrel);
            updateTrail();
        });
        
        sliderLaunchAngle.setOnMouseClicked(leftClick -> {
            handleRotateCannon(cannonBarrel);
            updateTrail();
        });
        
        sliderLaunchAngle.setOnKeyPressed(arrowPressed -> {
            handleRotateCannon(cannonBarrel);
            updateTrail();
        });

        sliderGravity.setOnMouseDragged(leftClick -> {
            updateTrail();
        });
        
        sliderGravity.setOnKeyPressed(arrowPressed -> {
            updateTrail();
        });
        
        sliderGravity.setOnMouseClicked(leftClick -> {
            updateTrail();
        });
        

        // Action event for play
        buttonPlay.setOnMouseClicked(leftClick -> {
            handlePlay();
            disableSliders(sliderList);
        });

        // Action event for reset
        buttonReset.setOnMouseClicked(leftClick -> {
            handleReset();
            enableSliders(sliderList);
        });

        // Action event for pause
        buttonPause.setOnMouseClicked(leftClick -> {
            handlePause();
            disableSliders(sliderList);
        });
        
        // Action event to open help page
        buttonHelp.setOnMouseClicked(leftClick -> {
            handleHelp();
        });
        
        // Action event to open settings page
        buttonSettings.setOnAction(leftClick -> {
            handleSettings();
        });
        
        // Action event to clear sliders
        buttonClear.setOnMouseClicked(leftClick -> {
            // Resets the animation and brings the sliders to default values
            handleClear(sliderList);
            handleRotateCannon(cannonBarrel);
            updateTrail();
        });
        
        // Action event to open graphs page
        buttonGraph.setOnMouseClicked(leftClick -> {
            handleGraphs();
        });
        
        // Action events for menu items to switch scenes.
        menuItemConservationOfEnergy.setOnAction(leftClick -> {
            switchSimulation("conservation");
        });
        
        buttonHome.setOnMouseClicked(leftClick -> {
            switchSimulation("mainmenu");
        });
        
        menuItemMainMenu.setOnAction(leftClick -> {
            switchSimulation("mainmenu");
        });
        
        menuItemStackedBlock.setOnAction(leftClick -> {
            switchSimulation("stackedblock");
        });
        
        menuItemUniformCircularMotion.setOnAction(leftClick -> {
            switchSimulation("uniform-circular-motion");
        });
        
        menuItemExit.setOnAction(leftClick -> {
            System.exit(0);
        });

    }
    
    
    /**
     * Method that disables all sliders present in the scene. 
     * 
     * @param sliderList is a list of all sliders
     */
    private void disableSliders(List<MFXSlider> sliderList) {
        for(MFXSlider slider : sliderList) {
            slider.setDisable(true);
        }
    }
    
     /**
     * Method that enables all sliders present in the scene. 
     * 
     * @param sliderList is a list of all sliders
     */
    private void enableSliders(List<MFXSlider> sliderList) {
        for(MFXSlider slider : sliderList) {
            slider.setDisable(false);
        }
    }
    
    /**
     * Updates the trail based on slider values. Gets called by sliders so that trail
     * adapts dynamically.
     */
    private void updateTrail() {
        // Draws trail only if toggle button is selected
        if (showTrailToggleButton.isSelected()) {
            gravityMetersPerSecondSquared = sliderGravity.getValue();
            initialVelocityMetersPerSecond = sliderInitialVelocity.getValue();
            launchAngleDegrees = sliderLaunchAngle.getValue();
            // Ensures that Path never leaves the animation Pane


            // Draws the trail based on slider values
            animation.drawTrail(paneAnimation, launchAngleDegrees, gravityMetersPerSecondSquared, initialVelocityMetersPerSecond);
        // Removes all Paths when toggle is not selected. 
        } else {
            paneAnimation.getChildren().removeIf(trail -> trail instanceof Path);
        }
    }
    
    /**
     * Getter for gravity slider
     * @return gravity in meters per second squared
     */
    public double getGravitySliderValue() {
        return sliderGravity.getValue();
    }
    
    /**
     * Getter for initial velocity slider
     * @return initial velocity in meters per second
     */
    public double getInitialVelocitySliderValue() {
        return sliderInitialVelocity.getValue();
    }

    /**
     * Getter for launch angle slider
     * @return launch angle in degrees
     */
    public double getLaunchAngleSliderValue() {
        return sliderLaunchAngle.getValue();
    }
    
    /**
     * Method that switches between simulation and main menu
     * @param simulationName string assigned to each simulation 
     */
    public void switchSimulation(String simulationName)
    {
        // Creates new Stage for simulation
        Stage currentStage = (Stage) paneAnimation.getScene().getWindow();
        // file path for each fxml file
        String destination = "/fxml/" + simulationName + ".fxml";
        // Loads fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));

        // Loads controller for each simulation
        switch (simulationName) {
            // Switches to stacked block simulation
            case "stackedblock" -> {
                BlockFrontEndController blockcontroller = new BlockFrontEndController();
                loader.setController(blockcontroller);
            }
            // Switches to uniform circular motion simulation
            case "uniform-circular-motion" -> {
                UniformCircularMotionController controllerUCM = new UniformCircularMotionController();
                loader.setController(controllerUCM);
            }
            // Switches to conservation of energy simulation
            case "conservation" -> {
                ConservationController conservationController = new ConservationController();
                loader.setController(conservationController);
            }
            // Switches to main menu
            case "mainmenu" -> {
                MainMenuController menuController = new MainMenuController(currentStage);
                loader.setController(menuController);
            }
            default ->
                System.out.println("Invalid simulation name");
        }
        
        try {
            // Loads stage based on scene
            Parent root = loader.load();
            Scene scene = new Scene(root, 1920, 1080);
            currentStage.setScene(scene);
            currentStage.setFullScreen(true);
            currentStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        } catch (IOException problemChangingScenes) {
            System.out.println("Something went wrong changing scenes.");
        }
    }


}
