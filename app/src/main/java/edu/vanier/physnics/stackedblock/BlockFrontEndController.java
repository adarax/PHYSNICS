package edu.vanier.physnics.stackedblock;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
    
    private boolean isDark = false;
    private final Image LIGHT_MOON = new Image(getClass().getResourceAsStream("/images/light_moon_icon.png"));
    private final Image DARK_MOON = new Image(getClass().getResourceAsStream("/images/dark_moon_icon.png"));
    
    public void handleDarkMode(MouseEvent e) {
        if (this.isDark) {
            // Go to light mode (on rest of app)
            
            buttonDarkMode.setImage(DARK_MOON);
        }
        else {
            // Go to dark mode (on rest of app)
            
            buttonDarkMode.setImage(LIGHT_MOON);
        }
        
        // Change boolean value
        isDark = !isDark;
    }

    public void handlePlay(MouseEvent e) {
        // Play the simulation
    }

    public void handlePause(MouseEvent e) {
        // Pause the simulation
    }

    public void handleReset(MouseEvent e) {
        // Reset the simulation
    }

    public void handleHelp(MouseEvent e) {
        // Open the help screen
    }

    public void handleClear(MouseEvent e) {
        // Clear the screen
    }

    public void goToCentripetalForce(MouseEvent e) {
        // Go to centripetal force screen
    }

    public void goToConservationOfEnergy(MouseEvent e) {
        // Go to conservation of energy screen
    }

    public void goToMainMenu(MouseEvent e) {
        // Go to main menu screen
    }

    public void goToProjectileMotion(MouseEvent e) {
        // Go to projectile motion screen
    }
    
    @FXML
    public void initialize() {
        buttonDarkMode.setOnMouseClicked(e -> handleDarkMode(e));
        
        // Create a setOnAction for each FXML item above

        buttonClear.setOnAction(e -> {
            // Clear the screen
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

        sliderAngleOnM1.setOnMouseDragged(e -> {
            // Change the angle of M1
        });

        sliderAngleOnM2.setOnMouseDragged(e -> {
            // Change the angle of M2
        });

        sliderForceOnM1.setOnMouseDragged(e -> {
            // Change the force on M1
        });

        sliderForceOnM2.setOnMouseDragged(e -> {
            // Change the force on M2
        });

        sliderFrictionFloor.setOnMouseDragged(e -> {
            // Change the friction on the floor
        });

        sliderFrictionM1.setOnMouseDragged(e -> {
            // Change the friction on M1
        });

        sliderMassM1.setOnMouseDragged(e -> {
            // Change the mass of M1
        });

        sliderMassM2.setOnMouseDragged(e -> {
            // Change the mass of M2
        });

        toggleShowVectors.setOnAction(e -> {
            // Show or hide the vectors
        });

    }
}
