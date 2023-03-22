package edu.vanier.physnics.stackedblock;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author adarax
 */
public class BlockFrontEndController {

    @FXML
    private MFXButton buttonClear;

    @FXML
    private MenuItem menubuttonCentripetal;

    @FXML
    private MenuItem menubuttonConservation;

    @FXML
    private MenuItem menubuttonExit;

    @FXML
    private MenuItem menubuttonMainMenu;

    @FXML
    private MenuItem menubuttonProjectile;

    @FXML
    private MFXSlider sliderAngleOnM1;

    @FXML
    private MFXSlider sliderAngleOnM2;

    @FXML
    private MFXSlider sliderForceOnM1;

    @FXML
    private MFXSlider sliderForceOnM2;

    @FXML
    private MFXSlider sliderFrictionFloor;

    @FXML
    private MFXSlider sliderFrictionM1;

    @FXML
    private MFXSlider sliderMassM1;

    @FXML
    private MFXSlider sliderMassM2;

    @FXML
    private MFXToggleButton toggleShowVectors;

    @FXML
    private ImageView buttonDarkMode;

    @FXML
    private ImageView buttonPlay;

    @FXML
    private ImageView buttonPause;

    @FXML
    private ImageView buttonReset;

    @FXML
    private ImageView buttonHelp;
    
    @FXML
    private MFXButton buttonSet;

    @FXML
    private Pane paneAnimation;

    private boolean isDark = false;
    private final Image LIGHT_MOON = new Image(getClass().getResourceAsStream("/images/light_moon_icon.png"));
    private final Image DARK_MOON = new Image(getClass().getResourceAsStream("/images/dark_moon_icon.png"));

    @FXML
    public void initialize()
    {

        // Calls this after everything is rendered, eventually this won't be needed
        // because the methods are only going to be called from events
//        Platform.runLater(() ->
//        {
//            // Testing animation methods
//        });
//        ///

        buttonDarkMode.setOnMouseClicked(e -> handleDarkMode(e));

        buttonClear.setOnAction(e -> handleClear());
        
        buttonSet.setOnAction(e -> handleSet());

        menubuttonCentripetal.setOnAction(e -> goToCentripetalForce());

        menubuttonConservation.setOnAction(e -> goToConservationOfEnergy());

        menubuttonProjectile.setOnAction(e -> goToProjectileMotion());

        menubuttonExit.setOnAction(e -> handleExitOfApplication());

        menubuttonMainMenu.setOnAction(e ->
        {
            // Return to main menu
        });

        sliderAngleOnM1.setOnMouseDragged(e ->
        {
            // Change the angle of M1
        });

        sliderAngleOnM2.setOnMouseDragged(e ->
        {
            // Change the angle of M2
        });

        sliderForceOnM1.setOnMouseDragged(e ->
        {
            // Change the force on M1
        });

        sliderForceOnM2.setOnMouseDragged(e ->
        {
            // Change the force on M2
        });

        sliderFrictionFloor.setOnMouseDragged(e ->
        {
            // Change the friction on the floor
        });

        sliderFrictionM1.setOnMouseDragged(e ->
        {
            // Change the friction on M1
        });

        sliderMassM1.setOnMouseDragged(e ->
        {
            // Change the mass of M1
        });

        sliderMassM2.setOnMouseDragged(e ->
        {
            // Change the mass of M2
        });

        toggleShowVectors.setOnAction(e ->
        {
            // Show or hide the vectors
        });

        buttonPlay.setOnMouseClicked(e ->
        {
            handlePlay(e);
        });

        buttonPause.setOnMouseClicked(e ->
        {
            handlePause(e);
        });

        buttonReset.setOnMouseClicked(e ->
        {
            handleReset(e);
        });

        buttonHelp.setOnMouseClicked(e ->
        {
            handleHelp(e);
        });

    }

    public void handleDarkMode(MouseEvent e)
    {
        if (this.isDark)
        {
            // Go to light mode (on rest of app)

            buttonDarkMode.setImage(DARK_MOON);
        } else
        {
            // Go to dark mode (on rest of app)

            buttonDarkMode.setImage(LIGHT_MOON);
        }

        // Change boolean value
        isDark = !isDark;
    }

    public void handlePlay(MouseEvent e)
    {
        // Play the simulation
    }

    public void handlePause(MouseEvent e)
    {
        // Pause the simulation
    }

    public void handleReset(MouseEvent e)
    {

    }

    public void handleHelp(MouseEvent e)
    {
        // Open the help screen
    }

    public void handleClear()
    {
        // Get all sliders
        // Set values to 0
        // Set "show vectors" to OFF
        // Reset animation
    }
    
    
    // TODO: draw floor separately: as soon as program opens
    public void handleSet()
    {
        // Set simulation based on parameters (sliders values)
        
        BlockAnimation blockAnimationHandler = new BlockAnimation();
        
        // Eventually this must use slider values, and computed forces
        blockAnimationHandler.situateBlocks(new Block(10, 0, new ArrayList<>()), new Block(20, 1, new ArrayList<>()), this.paneAnimation);

    }

    public void handleExitOfApplication()
    {

    }

    public void goToCentripetalForce()
    {
        // Go to centripetal force screen
    }

    public void goToConservationOfEnergy()
    {
        // Go to conservation of energy screen
    }

    public void goToMainMenu()
    {
        // Go to main menu screen
    }

    public void goToProjectileMotion()
    {
        // Go to projectile motion screen
    }

}
