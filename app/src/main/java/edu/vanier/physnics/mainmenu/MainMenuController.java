package edu.vanier.physnics.mainmenu;

import edu.vanier.physnics.App;
import edu.vanier.physnics.UCMSimulation.UCMController;
import edu.vanier.physnics.conservation.ConservationController;
import edu.vanier.physnics.projectilemotion.ProjectileController;
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

    public MainMenuController(Stage stage)
    {
        this.stage = stage;
    }

    public void openNewScene(String type)
    {
        FXMLLoader loader = null;
        if (type.equals("stackedblock"))
        {
            loader = new FXMLLoader(getClass().getResource("/fxml/stackedblock.fxml"));
            BlockFrontEndController controller = new BlockFrontEndController();
            loader.setController(controller);
        } else if (type.equals("conservation"))
        {
            loader = new FXMLLoader(getClass().getResource("/fxml/conservation.fxml"));
            ConservationController controller = new ConservationController();
            loader.setController(controller);
        } else if (type.equals("projectile"))
        {
            loader = new FXMLLoader(getClass().getResource("/fxml/projectile.fxml"));
            ProjectileController controller = new ProjectileController();
            loader.setController(controller);
        } else if (type.equals("UCM"))
        {
            loader = new FXMLLoader(getClass().getResource("/fxml/ucm-scene-graph.fxml"));
            UCMController controller = new UCMController();
            loader.setController(controller);
        } 
        
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, 1920, 1080);
            stage.setScene(scene);
        } catch (IOException ex)
        {
            System.out.println("Something went wrong changing scenes.");
        }

        stage.setMaximized(true);
        stage.sizeToScene();
        stage.getIcons().add(new Image("/images/app_icon.png"));
        stage.setFullScreen(true);
        stage.show();
    }

    public void handleHelpPressed(MouseEvent e)
    {
        System.out.println("Help was requested");
    }
    
    @FXML
    public void initialize()
    {
        /**
         * setOnMouseClicked() is used, not setOnAction(), since the buttons are
         * actually images.
         */

        buttonConservation.setOnMouseClicked(e ->
        {
            openNewScene("conservation");
        });
        buttonStackedBlock.setOnMouseClicked(e ->
        {
            openNewScene("stackedblock");
        });
        buttonProjectile.setOnMouseClicked(e ->
        {
            openNewScene("projectile");
        });
        buttonCentripetal.setOnMouseClicked(e ->
        {
            openNewScene("UCM");
        });
        buttonHelp.setOnMouseClicked(e -> handleHelpPressed(e));
    }
}
