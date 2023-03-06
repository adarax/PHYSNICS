package edu.vanier.physnics.mainmenu;

import edu.vanier.physnics.App;
import edu.vanier.physnics.stackedblock.BlockFrontEndController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author adarax
 */
public class MainMenuController extends App {
    
    private Stage stage;
    
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stackedblock.fxml"));

        BlockFrontEndController bfec = new BlockFrontEndController();
        loader.setController(bfec);
        
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, 1920, 1080);
            stage.setScene(scene);
        }
        catch (IOException ex) {
            System.out.println("Something went wrong changing scenes.");
        }
        
        stage.setMaximized(true);
        stage.sizeToScene();
        stage.getIcons().add(new Image("/images/app_icon.png"));
        stage.setFullScreen(true);
        stage.show();
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
