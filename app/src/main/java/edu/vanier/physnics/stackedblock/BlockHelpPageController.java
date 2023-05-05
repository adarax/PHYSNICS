package edu.vanier.physnics.stackedblock;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author adarax
 */
public class BlockHelpPageController {

    private Stage helpWindow;

    private final String HELP_PARAGRAPH = """
                                          Drag the force sliders to adjust the forces applied on M1 and M2.\n
                                          Drag the angle sliders to adjust the angle (to the horizontal) that the forces will have.\n
                                          Drag the friction sliders to adjust the friction coefficient of that surface.\n
                                          Drag the mass sliders to adjust the masses of M1 and M2.\n
                                          Check or uncheck the show/hide vectors button to show or hide the free body diagram of the forces in the block system.\n
                                          Press the clear values button to reset all sliders to their default values.\n
                                          The play button plays the animation of the simulation.\n
                                          The pause button pauses the animation of the simulation. The simulation can be started again using the play button.\n
                                          The stop button stops the simulation and resets it to the beginning.\n
                                          The settings tab has the option to change the colors of various objects in the simulation, such as the background and the color of the blocks / force vectors.\n
                                          The file tab has buttons to quit the program or to return to the main menu.\n
                                          The \u201cmoon shaped\u201d button sets the application theme to dark mode if on light mode and vice versa.""";

    @FXML
    private MFXButton buttonReturnToSimulation;

    @FXML
    private TextFlow textFlowTextArea;
    
    public BlockHelpPageController(Stage stage)
    {
        this.helpWindow = stage;
    }

    @FXML
    public void initialize()
    {
        Text helpText = new Text(HELP_PARAGRAPH);
        helpText.setStyle("-fx-font-size: 21; -fx-text-alignment: justify; -fx-line-spacing: 2;");        
        textFlowTextArea.getChildren().add(helpText);
        
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                new CornerRadii(5), new BorderWidths(1));
        Border border = new Border(borderStroke);
        
        textFlowTextArea.setStyle("-fx-padding: 10px;");
        textFlowTextArea.setBorder(border);
        
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
