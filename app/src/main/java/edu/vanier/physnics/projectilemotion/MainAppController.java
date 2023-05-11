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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Instantiates the FXML UI controls and shapes to be used in the animation.
 * Handles action events.
 * @author vires
 */
public class MainAppController {
    @FXML
    private MenuItem menuItemMainMenu,
            menuItemConservationOfEnergy,
            menuItemStackedBlock,
            menuItemUniformCircularMotion,
            menuItemExit;

    @FXML
    private ImageView buttonDarkMode;

    @FXML
    private MFXSlider sliderInitialVelocity;

    @FXML
    private MFXSlider sliderLaunchAngle;

    @FXML
    private MFXSlider sliderGravity;

    @FXML
    private MFXButton buttonClear, buttonSettings;

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
    private ImageView cannonBarrel;
    
    @FXML
    private MFXToggleButton showTrailToggleButton;
    
    @FXML
    private Pane paneAnimation;
    
    Animation animation = new Animation();
    
    private double gravityAccelMPSS;
    private double initialVelocityMPS;
    private double launchAngleDeg;
    
    /**
     * Plays the animation. Gets the values from sliders, and uses them as
     * parameters for the animation.
     * 
     * @param leftClick 
     */
    public void handlePlay() {
        gravityAccelMPSS = sliderGravity.getValue();
        initialVelocityMPS = sliderInitialVelocity.getValue();
        launchAngleDeg = sliderLaunchAngle.getValue();
        animation.playAnimation(projectileBall, launchAngleDeg, gravityAccelMPSS, initialVelocityMPS);
        animation.drawTrail(paneAnimation, launchAngleDeg, gravityAccelMPSS, initialVelocityMPS);
    }

    public void handlePause() {
        // Pause the simulation
        animation.pauseAnimation();
            
    }
    
    /**
     * Resets the the ball back to the default position on leftClick.
     * 
     * @param leftClick  
     */
    public void handleReset() {
        animation.resetBall(projectileBall);
    }
    
    public void handleSettings() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/projectileSettings.fxml"));
        Pane root = null;
        SettingsController settingsController = new SettingsController(paneAnimation, projectileBall);
        loader.setController(settingsController);

        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(HelpPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        Scene settingsWindow = new Scene(root);
        Stage stage = new Stage();
        settingsController.setStage(stage);
        stage.setScene(settingsWindow);
        stage.sizeToScene();
        stage.setTitle("Settings");
        stage.show();

    }

    public void handleHelp() {
        HelpPage helpPage = new HelpPage();
        helpPage.openHelpWindow();
    }
    
    public void handleGraphs() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/projectileGraphs.fxml"));
        Pane root = null;
        GraphsController graphsPageController = new GraphsController(sliderInitialVelocity.getValue(), sliderLaunchAngle.getValue(), sliderGravity.getValue());
        loader.setController(graphsPageController);

        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(HelpPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        Scene graphsPage = new Scene(root);
        Stage stage = new Stage();
        graphsPageController.setStage(stage);
        stage.setScene(graphsPage);
        stage.sizeToScene();
        stage.setTitle("Graphs");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


    public void handleClear(List<MFXSlider> sliderList) {
        for (MFXSlider slider : sliderList) {
            slider.setValue(slider.getMin());
        }
    }
    
    public void handleRotateCannon(ImageView cannonBarrel) {
        double launchAngle = sliderLaunchAngle.getValue();
        animation.rotateCannon(cannonBarrel, launchAngle);
    }


    @FXML
    public void initialize() {

        List<MFXSlider> sliderList = new ArrayList<MFXSlider>();
        sliderList.add(sliderGravity);
        sliderList.add(sliderInitialVelocity);
        sliderList.add(sliderLaunchAngle);
        
        Image defaultImage = new Image(getClass().getResourceAsStream("/images/settings/cartoon_field.png"));
        BackgroundImage backgroundImage = new BackgroundImage(defaultImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        paneAnimation.setBackground(new Background(backgroundImage));

        double launchAngle = sliderLaunchAngle.getValue();
        
        sliderLaunchAngle.setOnMouseDragged(drag -> animation.rotateCannon(cannonBarrel, launchAngle));
        

        sliderInitialVelocity.setOnMouseDragged(e -> {
            // Change the initial velocity
        });
        

        sliderLaunchAngle.setOnMouseDragged(leftClick -> {
            handleRotateCannon(cannonBarrel);
        });
        
        sliderLaunchAngle.setOnMouseClicked(leftClick -> {
            handleRotateCannon(cannonBarrel);
        });
        
        sliderLaunchAngle.setOnKeyPressed(arrowPressed -> {
            handleRotateCannon(cannonBarrel);
        });

        sliderGravity.setOnMouseDragged(e -> {
            // Change the force of gravity
            animation.drawTrail(paneAnimation, launchAngleDeg, gravityAccelMPSS, initialVelocityMPS);
        });

        buttonPlay.setOnMouseClicked(leftClick -> {
            handlePlay();
            disableSliders(sliderList);
        });

        buttonReset.setOnMouseClicked(leftClick -> {
            handleReset();
            enableSliders(sliderList);
        });

        buttonPause.setOnMouseClicked(leftClick -> {
            handlePause();
            disableSliders(sliderList);
        });
        
        buttonHelp.setOnMouseClicked(leftClick -> {
            handleHelp();
        });
        
        buttonSettings.setOnAction(leftClick -> {
            handleSettings();
        });
        
        buttonClear.setOnMouseClicked(leftClick -> {
            // Resets the animation and brings the sliders to default values
            handleClear(sliderList);
        });
        
        buttonGraph.setOnMouseClicked(leftClick -> {
            handleGraphs();
        });
        
        menuItemConservationOfEnergy.setOnAction(leftClick -> {
            switchSimulation("conservation");
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
    
    
    public double getGravitySliderValue() {
        return sliderGravity.getValue();
    }

    public double getInitialVelocitySliderValue() {
        return sliderInitialVelocity.getValue();
    }

    public double getLaunchAngleSliderValue() {
        return sliderLaunchAngle.getValue();
    }
    
    public void switchSimulation(String simulationName)
    {
        Stage currentStage = (Stage) paneAnimation.getScene().getWindow();
        String destination = "/fxml/" + simulationName + ".fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));

        switch (simulationName) {
            case "stackedblock" -> {
                BlockFrontEndController blockcontroller = new BlockFrontEndController();
                loader.setController(blockcontroller);
            }
            case "uniform-circular-motion" -> {
                UniformCircularMotionController controllerUCM = new UniformCircularMotionController();
                loader.setController(controllerUCM);
            }
            case "conservation" -> {
                ConservationController conservationController = new ConservationController();
                loader.setController(conservationController);
            }
            case "mainmenu" -> {
                MainMenuController menuController = new MainMenuController(currentStage);
                loader.setController(menuController);
            }
            default ->
                System.out.println("Invalid simulation name");
        }
        
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, 1920, 1080);
            currentStage.setScene(scene);
            currentStage.setFullScreen(true);
            currentStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        } catch (IOException ex) {
            System.out.println("Something went wrong changing scenes.");
        }
    }


}
