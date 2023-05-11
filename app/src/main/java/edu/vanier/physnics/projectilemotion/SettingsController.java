/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author vires
 */
public class SettingsController {
    @FXML
    ImageView buttonField, buttonWinter, buttonDesert;
    
    @FXML
    MFXButton buttonExitSettings;
    
    @FXML
    ColorPicker ballColour, trailColour;
    
    private Stage stage;
    
    private Pane paneAnimation;
    
    private Circle projectileBall;
    
    private final Image FIELD_BACKGROUND = new Image(getClass().getResourceAsStream("/images/settings/cartoon_field.png"));
    private final Image WINTER_BACKGROUND = new Image(getClass().getResourceAsStream("/images/settings/cartoon_winter.png"));
    private final Image DESERT_BACKGROUND = new Image(getClass().getResourceAsStream("/images/settings/desert.png"));

    public SettingsController(Pane paneAnimation, Circle projectileBall) {
        this.paneAnimation = paneAnimation;
        this.projectileBall = projectileBall;
    }
    
    
    
    /**
     * Used by the Settings Class to set the stage of the SettingsController.
     * This is essential to access the properties of the stage from
     * GraphsController.
     *
     * @param stage The stage parameter is passed in from the MainAppController
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        
        buttonExitSettings.setOnAction(leftClick -> {
            handleExit();
        });
        
        buttonField.setOnMouseClicked(leftClick -> {
            setBackground(FIELD_BACKGROUND);
        });

        buttonWinter.setOnMouseClicked(leftClick -> {
            setBackground(WINTER_BACKGROUND);
        });

        buttonDesert.setOnMouseClicked(leftClick -> {
            setBackground(DESERT_BACKGROUND);
        });
        
        ballColour.setValue((Color)projectileBall.getFill());
        
        ballColour.setOnAction(leftClick -> {
            Color colour = ballColour.getValue();
            projectileBall.setFill(colour);
        });

    }
        
    
    /**
     * Method that takes an image as a parameter and converts it into a usable 
     * background for the paneAnimation(parent layout container). 
     * 
     * @param image image object that gets passed as a background 
     */
    public void setBackground(Image image) {
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        paneAnimation.setBackground(new Background(backgroundImage));
    }

    public void handleExit() {
        stage.close();
    }
    
}
