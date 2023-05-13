package edu.vanier.physnics.mainmenu;

import edu.vanier.physnics.App;
import edu.vanier.physnics.UCMSimulation.UCMController;
import edu.vanier.physnics.conservation.ConservationController;
import edu.vanier.physnics.projectilemotion.ProjectileController;
import edu.vanier.physnics.stackedblock.BlockFrontEndController;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controller class for the main menu of the program.
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

    @FXML
    private ImageView buttonExit;

    private final Image IMAGE_EXIT_BUTTON = new Image(getClass().getResourceAsStream("/images/exit_icon.png"));

    /**
     * Default constructor for the MainMenuController. Takes a Stage object as a
     * parameter in order to place the main menu Scene onto it.
     *
     * @param stage the Stage holding the Scene
     */
    public MainMenuController(Stage stage)
    {
        this.stage = stage;
    }

    private void openNewScene(String type)
    {
        FXMLLoader loader = null;
        switch (type)
        {
            case "stackedblock" ->
            {
                loader = new FXMLLoader(getClass().getResource("/fxml/stackedblock.fxml"));
                BlockFrontEndController controller = new BlockFrontEndController();
                loader.setController(controller);
            }
            case "conservation" ->
            {
                loader = new FXMLLoader(getClass().getResource("/fxml/conservation.fxml"));
                ConservationController controller = new ConservationController();
                loader.setController(controller);
            }
            case "projectile" ->
            {
                loader = new FXMLLoader(getClass().getResource("/fxml/projectile.fxml"));
                ProjectileController controller = new ProjectileController();
                loader.setController(controller);
            }
            case "UCM" ->
            {
                loader = new FXMLLoader(getClass().getResource("/fxml/ucm-scene-graph.fxml"));
                UCMController controller = new UCMController();
                loader.setController(controller);
            }
            default ->
            {
            }
        }

        try
        {
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
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.show();
    }

    private void handleHelpPressed()
    {
        System.out.println("Help was requested");
    }

    /**
     * Initializes the home screen and sets event handlers to the various
     * buttons.
     */
    @FXML
    public void initialize()
    {
        buttonExit.setImage(IMAGE_EXIT_BUTTON);

        buttonExit.setOnMouseClicked(press -> handleExit());

        buttonConservation.setOnMouseClicked(press ->
        {
            openNewScene("conservation");
        });
        buttonStackedBlock.setOnMouseClicked(press ->
        {
            openNewScene("stackedblock");
        });
        buttonProjectile.setOnMouseClicked(press ->
        {
            openNewScene("projectile");
        });
        buttonCentripetal.setOnMouseClicked(press ->
        {
            openNewScene("UCM");
        });
        buttonHelp.setOnMouseClicked(press -> handleHelpPressed());
    }

    private void handleExit()
    {
        Platform.exit();
    }
}
