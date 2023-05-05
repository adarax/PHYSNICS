package edu.vanier.physnics.stackedblock;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author adarax
 */
public class BlockHelpPageController {

    private final Stage helpWindow;

    @FXML
    private MFXButton buttonReturnToSimulation;

    public BlockHelpPageController(Stage stage)
    {
        this.helpWindow = stage;
    }

    @FXML
    public void initialize()
    {
        buttonReturnToSimulation.setOnAction(click -> handleReturnToSimulation());
    }

    private void handleReturnToSimulation()
    {
        String destination = "/fxml/stackedblock.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));

        BlockFrontEndController helpPageController = new BlockFrontEndController();
        loader.setController(helpPageController);

        try
        {
            Parent root = loader.load();
            Scene scene = new Scene(root, 1920, 1080);
            helpWindow.setScene(scene);
        } catch (IOException ex)
        {
            System.out.println("Something went wrong changing scenes.");
        }

        helpWindow.setFullScreenExitHint("");
        helpWindow.setFullScreen(true);
    }
}
