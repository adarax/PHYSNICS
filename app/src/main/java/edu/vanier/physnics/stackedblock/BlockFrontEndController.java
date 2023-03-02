package edu.vanier.physnics.stackedblock;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author adarax
 */
public class BlockFrontEndController {
    
    @FXML
    private ImageView buttonDarkMode;
    
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
    
    @FXML
    public void initialize() {
        buttonDarkMode.setOnMouseClicked(e -> handleDarkMode(e));
        
    }
}
