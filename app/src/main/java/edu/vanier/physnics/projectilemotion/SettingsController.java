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
 * Controller class for the Settings Page. Method to change background and 
 * ball color. 
 * 
 * @author vireshpatel43
 */
public class SettingsController {
    // Instantiates the FXML UI Controls present in the scene. 
    @FXML
    private ImageView buttonField, buttonWinter, buttonDesert;
    
    @FXML
    private MFXButton buttonExitSettings;
    
    @FXML
    private ColorPicker ballColourPicker;
    
    private Stage stage;
    
    // Main animation pane to change backgrounds
    private Pane paneAnimation;
    
    // Ball used in the animation
    public Circle projectileBall;
    
    // Sets the image object for all background options
    private final Image FIELD_BACKGROUND = new Image(getClass().getResourceAsStream("/images/settings/cartoon_field.png"));
    private final Image WINTER_BACKGROUND = new Image(getClass().getResourceAsStream("/images/settings/cartoon_winter.png"));
    private final Image DESERT_BACKGROUND = new Image(getClass().getResourceAsStream("/images/settings/desert.png"));

    /**
     * Constructor for the settings controller to gain access to the animation
     * pane and the ball.
     * @param paneAnimation main Pane
     * @param projectileBall ball present in scene
     */
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

    /**
     * Sets action events for the color picker, exit button and background buttons. 
     */
    @FXML
    public void initialize() {     
        
        // Closes Stage
        buttonExitSettings.setOnAction(leftClick -> {
            handleExit();
        });
        
        // Sets the background when image is clicked
        buttonField.setOnMouseClicked(leftClick -> {
            setBackground(FIELD_BACKGROUND);
        });

        buttonWinter.setOnMouseClicked(leftClick -> {
            setBackground(WINTER_BACKGROUND);
        });

        buttonDesert.setOnMouseClicked(leftClick -> {
            setBackground(DESERT_BACKGROUND);
        });
        
        // Sets the color on the colorPicker to the color of the ball
        ballColourPicker.setValue((Color)projectileBall.getFill());
        
        // Changes the color of the ball
        ballColourPicker.setOnAction(leftClick -> {
            Color colour = ballColourPicker.getValue();
            projectileBall.setFill(colour);
        });     
    }
        
    
    /**
     * Method that takes an image as a parameter and converts it into a usable 
     * background for the paneAnimation(parent layout container). 
     * 
     * @param image image object that gets set as background.
     */
    public void setBackground(Image image) {
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        paneAnimation.setBackground(new Background(backgroundImage));
    }

    /**
     * Closes the stage upon button pressed
     */
    public void handleExit() {
        stage.close();
    }

}
