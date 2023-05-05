package edu.vanier.physnics.stackedblock;

import edu.vanier.physnics.UCMSimulation.UCMController;
import edu.vanier.physnics.conservation.ConservationController;
import edu.vanier.physnics.mainmenu.MainMenuController;
import edu.vanier.physnics.projectilemotion.ProjectileController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author adarax
 */
public class BlockFrontEndController {

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
    private MFXButton buttonClear;

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
    private Pane paneAnimation;

    @FXML
    private Pane paneGridlines;

    private BlockAnimation blockAnimationHandler = new BlockAnimation();
    private BlockFormulas blockFormulasCalculator = new BlockFormulas();

    private Block topBlock = new Block(POSITION.TOP);
    private Block bottomBlock = new Block(POSITION.BOTTOM);

    private ArrayList<MFXSlider> allSliders;

    private boolean isDark = false;
    private final Image LIGHT_MOON = new Image(getClass().getResourceAsStream("/images/light_moon_icon.png"));
    private final Image DARK_MOON = new Image(getClass().getResourceAsStream("/images/dark_moon_icon.png"));

    private boolean isVectorShowing = false;

    private ArrayList<Text> allTextElements = new ArrayList<>();
    private ArrayList<Parent> allPanes = new ArrayList<>();
    private ArrayList<Line> allLines = new ArrayList<>();

    @FXML
    public void initialize()
    {
        /*
         * Calls this after everything is rendered, which is needed because
         * otherwise the height of the Panes is 0
         */
        Platform.runLater(() ->
        {
            // Put blocks and floor into scene on launch
            blockAnimationHandler.drawFloor(paneAnimation);
            updateBlocks();
        });

        addSliderEventHandlers();

        buttonDarkMode.setOnMouseClicked(press -> handleDarkMode());

        buttonClear.setOnAction(press -> handleClear());

        menubuttonCentripetal.setOnAction(press -> switchSimulation("ucm-scene-graph"));

        menubuttonConservation.setOnAction(press -> switchSimulation("conservation"));

        menubuttonProjectile.setOnAction(press -> switchSimulation("projectile"));

        menubuttonMainMenu.setOnAction(press -> switchSimulation("mainmenu"));

        menubuttonExit.setOnAction(press -> handleExitOfApplication());

        toggleShowVectors.setOnAction(toggle -> handleShowVectors(true));

        buttonPlay.setOnMouseClicked(press ->
        {
            handlePlay();
        });

        buttonPause.setOnMouseClicked(press ->
        {
            handlePause();
        });

        buttonReset.setOnMouseClicked(press ->
        {
            handleReset();
        });

        buttonHelp.setOnMouseClicked(press -> handleHelp());
    }

    private void addSliderEventHandlers()
    {
        allSliders = new ArrayList<>() {
            {
                add(sliderAngleOnM1);
                add(sliderAngleOnM2);
                add(sliderForceOnM1);
                add(sliderForceOnM2);
                add(sliderFrictionFloor);
                add(sliderFrictionM1);
                add(sliderMassM1);
                add(sliderMassM2);
            }
        };

        for (MFXSlider slider : allSliders)
        {
            slider.setOnMouseDragged(drag -> updateScene());
        }
    }

    // TODO: lines just don't display and i don't get it
    public void handleDarkMode()
    {
        // The elements only need to be found once, and both
        // allTextElements and allPanes are filled in the same method
        if (allTextElements.isEmpty())
        {
            collectElementsByType(paneAnimation.getScene().getRoot());
            getAllLines();
        }

        isDark = !isDark;

        if (isDark)
        {
            // Go to light mode
            buttonDarkMode.setImage(LIGHT_MOON);

            allTextElements.forEach(textElement ->
            {
                textElement.setFill(Color.WHITE);
            });

            allPanes.forEach(pane ->
            {
                // Set to a color one shade shy of black
                pane.setStyle("-fx-background-color: #101518;");
            });

            allLines.forEach(line ->
            {
                line.setStroke(Color.BLACK);
            });

            buttonClear.setTextFill(Color.WHITE);
            buttonClear.setStyle("-fx-background-color: darkGrey;");

        } else
        {
            // Go to dark mode
            buttonDarkMode.setImage(DARK_MOON);

            allTextElements.forEach(textElement ->
            {
                textElement.setFill(Color.BLACK);
            });

            allPanes.forEach(pane ->
            {
                pane.setStyle("-fx-background-color: white;");
            });

            allLines.forEach(line ->
            {
                line.setStroke(Color.RED);
            });

            buttonClear.setTextFill(Color.BLACK);
            buttonClear.setStyle("-fx-background-color: white;");
        }

        drawLines();
    }

    private void getAllLines()
    {
        paneGridlines.getChildren().forEach(child ->
        {
            if (child instanceof Line line)
            {
                allLines.add(line);
            }
        });
    }

    public void handlePlay()
    {
        // Play the simulation
    }

    public void handlePause()
    {
        // Pause the simulation
    }

    public void handleReset()
    {
        // Reset the simulation
    }

    /**
     * Opens the help page scene.
     */
    public void handleHelp()
    {
        switchSimulation("stackedblock_help");
    }

    /**
     * Set all sliders back to their minimum values and resets the animation.
     */
    public void handleClear()
    {
        allSliders.forEach(slider ->
        {
            slider.setValue(slider.getMin());
        });

        handleReset();
        updateScene();
    }

    public void updateScene()
    {
        paneAnimation.getChildren().clear();

        updateBlocks();

        blockAnimationHandler.drawFloor(paneAnimation);
        blockAnimationHandler.situateBlocks(topBlock, bottomBlock, paneAnimation);

        // If the vectors should be drawn, draw the vectors
        if (isVectorShowing)
        {
            handleShowVectors(false);
        }
    }

    private void updateBlocks()
    {
        topBlock.setMass(sliderMassM2.getValue());
        bottomBlock.setMass(sliderMassM1.getValue());

        topBlock.setForcesExperienced(getForcesExperienced(POSITION.TOP));
        bottomBlock.setForcesExperienced(getForcesExperienced(POSITION.BOTTOM));
    }

    // TODO: problem is most likely here, i dont understand why lines wont draw
    private void drawLines()
    {
        paneGridlines.getChildren().clear();
        
        allLines.forEach(line ->
        {
            paneGridlines.getChildren().add(line);
            line.toFront();
        });
    }

    public ArrayList<Vector> getForcesExperienced(POSITION blockId)
    {
        return blockFormulasCalculator.determineForcesExperienced(topBlock,
                bottomBlock,
                sliderForceOnM1.getValue(),
                sliderForceOnM2.getValue(),
                sliderAngleOnM1.getValue(),
                sliderAngleOnM2.getValue(),
                sliderFrictionFloor.getValue(),
                sliderFrictionM1.getValue(),
                blockId);
    }

    public void handleExitOfApplication()
    {
        Platform.exit();
    }

    public void switchSimulation(String simulationName)
    {
        Stage currentStage = (Stage) paneAnimation.getScene().getWindow();

        String destination = "/fxml/" + simulationName + ".fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));

        switch (simulationName)
        {
            case "conservation" ->
            {
                ConservationController conservationController = new ConservationController();
                loader.setController(conservationController);
            }
            case "projectile" ->
            {
                ProjectileController projectileController = new ProjectileController();
                loader.setController(projectileController);
            }
            case "ucm-scene-graph" ->
            {
                UCMController ucmController = new UCMController();
                loader.setController(ucmController);
            }
            case "mainmenu" ->
            {
                MainMenuController menuController = new MainMenuController(currentStage);
                loader.setController(menuController);
            }
            case "stackedblock_help" ->
            {
                BlockHelpPageController helpPageController = new BlockHelpPageController(currentStage);
                loader.setController(helpPageController);
            }
            default ->
                System.out.println("Invalid simulation name");
        }

        try
        {
            Parent root = loader.load();
            Scene scene = new Scene(root, 1920, 1080);
            currentStage.setScene(scene);
        } catch (IOException ex)
        {
            System.out.println("Something went wrong changing scenes.");
        }

        currentStage.setFullScreenExitHint("");
        currentStage.setFullScreen(true);
    }

    private void handleShowVectors(boolean toggled)
    {
        // Change boolean value since the button was toggled
        if (toggled)
        {
            isVectorShowing = !isVectorShowing;
        }

        if (isVectorShowing)
        {
            topBlock.drawFreeBodyDiagram(paneAnimation);
            bottomBlock.drawFreeBodyDiagram(paneAnimation);
        } else
        {
            updateScene();
        }
    }

    /**
     * Analyze all Nodes starting at the root of the scene graph and add them to
     * different ArrayLists depending on their type.
     *
     * This is to help with the light/dark mode feature.
     *
     * @param currentNode
     */
    private void collectElementsByType(Node currentNode)
    {
        if (currentNode instanceof Text text)
        {
            this.allTextElements.add(text);
        } else if (currentNode instanceof GridPane gridPane)
        {
            this.allPanes.add(gridPane);

            for (Node child : gridPane.getChildren())
            {
                collectElementsByType(child);
            }
        } else if (currentNode instanceof Pane pane)
        {
            this.allPanes.add(pane);

            for (Node child : pane.getChildren())
            {
                collectElementsByType(child);
            }
        }
    }

    /**
     * An enum to standardize the naming of the top and bottom block in the
     * simulation.
     */
    public enum POSITION {
        TOP,
        BOTTOM
    }
}
