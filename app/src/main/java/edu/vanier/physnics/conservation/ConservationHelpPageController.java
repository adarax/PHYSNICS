package edu.vanier.physnics.conservation;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Controller for the help menu window.
 * Initializes FXML elements and sets the text in the text box.
 * @author Benjamin Pratt
 */
public class ConservationHelpPageController {

    /// Consider putting this in a resource files and loading it dynamically. This is not really human readable.
    /// Plus, you lose access to spellcheckers.
    /**
     * Text block that will be displayed in the help window
     */
    private final String HELP_PARAGRAPH = """
                                          Select the value of the gravitational acceleration from a list of different planets.\n
                                          Drag the mass sliders to choose the mass of the ball.\n
                                          Select the friction coefficient to apply it of the surface of the ramp.\n
                                          Drag the height slider to adjust the height of the ramp.\n
                                          Check or uncheck the friction box to either apply friction to the ramp or have the ramp be frictioneless.\n
                                          Press the graph button to open the graph window to measure the total mechanical energy and the instantaneous kinetic, potential and friction energies.\n
                                          The play button plays the animation of the simulation.\n
                                          The pause button pauses the animation of the simulation. The simulation can be started again using the play button.\n
                                          The stop button stops the simulation and resets it to the beginning.\n
                                          Click on the ramp and the ball while the animation is paused to open a color picker to change their colors.\n
                                          Press on the home button to return to the main menu.\n
                                          The menu button allows to switch between the different simulations or quit the program.""";

    @FXML
    private MFXButton buttonReturnToSimulation;

    @FXML
    private TextFlow textFlowTextArea;

    @FXML
    private GridPane gridPaneHelpPage;
    
    @FXML
    private Text textPageTitle;

    
    /**
     * Initializes the text in text box, the borders and the background color.
     * Adds a eventHandler to the return home button
     */
    @FXML
    public void initialize()
    {
        String nearWhite = "#f4f4f4";
        Color borderColor =  Color.BLACK;

        gridPaneHelpPage.setStyle("-fx-background-color:" + (nearWhite));
        buttonReturnToSimulation.setStyle("-fx-font-size: 22.5px; -fx-text-fill: black; -fx-background-color: white");

        Text helpText = new Text(HELP_PARAGRAPH);
        helpText.setStyle("-fx-font-size: 21; -fx-text-alignment: justify; -fx-line-spacing: 2;");
        helpText.setFill(Color.BLACK);
        textFlowTextArea.getChildren().add(helpText);

        textPageTitle.setFill(Color.BLACK);

        BorderStroke borderStroke = new BorderStroke(borderColor, BorderStrokeStyle.SOLID,
                new CornerRadii(5), new BorderWidths(1));
        Border border = new Border(borderStroke);

        textFlowTextArea.setStyle("-fx-padding: 10px;");
        textFlowTextArea.setBorder(border);

        buttonReturnToSimulation.setOnAction(click -> handleReturnToSimulation());
    }

    private void handleReturnToSimulation()
    {
        Stage helpStage = (Stage) gridPaneHelpPage.getScene().getWindow();
        helpStage.hide();
    }
}
