/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;

/**
 * Instantiates the FXML UI controls and shapes to be used in the animation.
 * Handles action events.
 * @author vires
 */
public class ProjectileController {
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
    
    ProjectileAnimation animation = new ProjectileAnimation();
    
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
        
        System.out.println(ProjectileEquations.getFlightTime(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS));
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

    public void handleHelp(MouseEvent e) {
        // Open the help screen
    }

    public void handleClear(MouseEvent e) {
        // Reset the animation and bring the sliders to default values
    }


    @FXML
    public void initialize() {

        buttonClear.setOnAction(e -> {
            // Resets the animation and brings the sliders to default values
        });

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

        sliderLaunchAngle.setOnMouseDragged(e -> {
            // Change the launch angle
        });

        sliderGravity.setOnMouseDragged(e -> {
            // Change the force of gravity
        });

        buttonPlay.setOnMouseClicked(leftClick -> {
            handlePlay(leftClick);
        });

        buttonReset.setOnMouseClicked(leftClick -> {
            handleReset(leftClick);
        });

        buttonPause.setOnMouseClicked(leftClick -> {
            handlePause(leftClick);
        });

    }

}
