/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.MoveTo;

/**
 * Instantiates the FXML UI controls and shapes to be used in the animation.
 * Handles action events.
 * @author vires
 */
public class MainAppController {
    @FXML
    private MenuItem menubuttonExit;

    @FXML
    private MenuItem menubuttonMainMenu;

    @FXML
    private MenuItem menubuttonConservation;

    @FXML
    private MenuItem menubuttonCentripetal;

    @FXML
    private MenuItem menubuttonProjectile;

    @FXML
    private ImageView buttonDarkMode;

    @FXML
    private MFXSlider sliderInitialVelocity;

    @FXML
    private MFXSlider sliderLaunchAngle;

    @FXML
    private MFXSlider sliderGravity;

    @FXML
    private MFXButton buttonClear;

    @FXML
    private ImageView buttonPlay;

    @FXML
    private ImageView buttonPause;

    @FXML
    private ImageView buttonReset;

    @FXML
    private ImageView buttonHelp;

    @FXML
    private ImageView buttonGraph;

    @FXML
    private ImageView buttonLogo;

    @FXML
    private Circle projectileBall;
    
    @FXML 
    private Ellipse cannon;
    
    Animation animation = new Animation();
    
    /**
     * Plays the animation. Gets the values from sliders, and uses them as
     * parameters for the animation.
     * 
     * @param leftClick 
     */
    public void handlePlay(MouseEvent leftClick) {
        // Play the simulation
        System.out.println("Play");
        double gravityAccelMPSS = sliderGravity.getValue();
        double initialVelocityMPS = sliderInitialVelocity.getValue();
        double launchAngleDeg = sliderLaunchAngle.getValue();
        
        System.out.println(Equations.getFlightTime(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS));
        animation.playAnimation(projectileBall, launchAngleDeg, gravityAccelMPSS, initialVelocityMPS);
    }

    public void handlePause(MouseEvent leftClick) {
        // Pause the simulation
        animation.pauseAnimation();
            
    }
    
    
    /**
     * Resets the the ball back to the default position on leftClick.
     * 
     * @param leftClick  
     */
    public void handleReset(MouseEvent leftClick) {
        projectileBall.setCenterX(0);
        projectileBall.setCenterY(0);
    }

    public void handleHelp(MouseEvent leftClick) {
        HelpPage PHP = new HelpPage();
        PHP.openHelpWindow();
    }

    public void handleClear(MouseEvent leftClick, List<MFXSlider> sliderList) {
        for (MFXSlider slider : sliderList) {
            slider.setValue(slider.getMin());
        }
    }
    
    public void handleRotateCannon(MouseEvent leftClick, Ellipse cannon) {
        double launchAngle = sliderLaunchAngle.getValue();
        animation.rotateCannon(cannon, launchAngle);
    }


    @FXML
    public void initialize() {

        List<MFXSlider> sliderList = new ArrayList<MFXSlider>();
        sliderList.add(sliderGravity);
        sliderList.add(sliderInitialVelocity);
        sliderList.add(sliderLaunchAngle);

        
        double launchAngle = sliderLaunchAngle.getValue();
        sliderLaunchAngle.setOnMouseDragged(drag -> animation.rotateCannon(cannon, launchAngle));

        menubuttonCentripetal.setOnAction(e -> {
            // Go to centripetal force screen
        });

        menubuttonConservation.setOnAction(e -> {
            // Go to conservation of energy screen
        });

        menubuttonExit.setOnAction(e -> {
            // Exit the app
        });

        menubuttonProjectile.setOnAction(e -> {
            // Go to projectile motion screen
        });

        sliderInitialVelocity.setOnMouseDragged(e -> {
            // Change the initial velocity
        });

        sliderLaunchAngle.setOnMouseDragged(leftClick -> {
            handleRotateCannon(leftClick, cannon);
        });

        sliderGravity.setOnMouseDragged(e -> {
            // Change the force of gravity
        });

        buttonPlay.setOnMouseClicked(leftClick -> {
            handlePlay(leftClick);
            disableSliders(sliderList);
        });

        buttonReset.setOnMouseClicked(leftClick -> {
            handleReset(leftClick);
            enableSliders(sliderList);
        });

        buttonPause.setOnMouseClicked(leftClick -> {
            handlePause(leftClick);
            disableSliders(sliderList);
        });
        
        buttonHelp.setOnMouseClicked(leftClick -> {
            handleHelp(leftClick);
        });
        
        buttonClear.setOnMouseClicked(leftClick -> {
            // Resets the animation and brings the sliders to default values
            handleClear(leftClick, sliderList);
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
    

}
