package edu.vanier.physnics.mainmenu;

import edu.vanier.physnics.App;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author adarax
 */
public class MainMenuController extends App {
    
    @FXML
    private ImageView buttonHelp;
    
    @FXML
    private ImageView buttonConservation;
    
    @FXML
    private ImageView buttonStackedBlock;
    
    @FXML
    private ImageView buttonProjectile;
    
    @FXML
    private ImageView buttonCentripetal;

    
    public void handleConservationPressed(MouseEvent e) {
        System.out.println("Conservation of energy pressed");

    }
    
    public void handleStackedBlockPressed(MouseEvent e) {
        System.out.println("Stacked block pressed");

    }
    
    public void handleProjectilePressed(MouseEvent e) {
        System.out.println("Projectile motion pressed");

    }
    
    public void handleCentripetalPressed(MouseEvent e) {
        System.out.println("Centripetal pressed");

    }
    
    public void handleHelpPressed(MouseEvent e) {
        System.out.println("Help was requested");
    }
    
    @FXML
    public void initialize() {
        /**
         * setOnMouseClicked() is used, not setOnAction(), since the buttons are
         * actually images.
         */
        
        buttonConservation.setOnMouseClicked(e -> handleConservationPressed(e));
        buttonStackedBlock.setOnMouseClicked(e -> handleStackedBlockPressed(e));
        buttonProjectile.setOnMouseClicked(e -> handleProjectilePressed(e));
        buttonCentripetal.setOnMouseClicked(e -> handleCentripetalPressed(e));
        buttonHelp.setOnMouseClicked(e -> handleHelpPressed(e));
    }
}
