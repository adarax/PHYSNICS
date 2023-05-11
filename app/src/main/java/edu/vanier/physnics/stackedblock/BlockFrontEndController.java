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
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
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
import javafx.util.Duration;

/**
 *
 * @author adarax
 */
public class BlockFrontEndController {

    @FXML
    private MenuItem menubuttonCentripetal,
            menubuttonConservation,
            menubuttonExit,
            menubuttonMainMenu,
            menubuttonProjectile;

    @FXML
    private MFXSlider sliderAngleOnM1,
            sliderAngleOnM2,
            sliderForceOnM1,
            sliderForceOnM2,
            sliderFrictionFloor,
            sliderFrictionM1,
            sliderMassM1,
            sliderMassM2;

    @FXML
    private MFXToggleButton toggleShowVectors;
    
    @FXML
    private MFXButton buttonClear;

    @FXML
    private ImageView buttonDarkMode,
            buttonPlay,
            buttonPause,
            buttonReset,
            buttonHelp;

    @FXML
    private Pane paneAnimation, paneGridlines;

    private BlockAnimation blockAnimationHandler = new BlockAnimation();
    private BlockFormulas blockFormulasCalculator = new BlockFormulas();

    private Block topBlock = new Block(POSITION.TOP),
            bottomBlock = new Block(POSITION.BOTTOM);

    private ArrayList<MFXSlider> allSliders;

    private boolean isDark = false;
    private final Image LIGHT_MOON = new Image(getClass().getResourceAsStream("/images/light_moon_icon.png")),
            DARK_MOON = new Image(getClass().getResourceAsStream("/images/dark_moon_icon.png")),
            PLAY_BUTTON = new Image(getClass().getResourceAsStream("/images/play_button.png")),
            PLAY_BUTTON_PRESSED = new Image(getClass().getResourceAsStream("/images/play_button_pressed.png")),
            PAUSE_BUTTON = new Image(getClass().getResourceAsStream("/images/pause_button.png")),
            PAUSE_BUTTON_PRESSED = new Image(getClass().getResourceAsStream("/images/pause_button_pressed.png")),
            RESET_BUTTON = new Image(getClass().getResourceAsStream("/images/reset_button.png")),
            RESET_BUTTON_PRESSED = new Image(getClass().getResourceAsStream("/images/reset_button_pressed.png"));
    
    /* Testing various things to get lines to show */
    private ArrayList<Text> allTextElements = new ArrayList<>();
    private ArrayList<Parent> allPanes = new ArrayList<>();
    private ArrayList<Line> allLines = new ArrayList<>();

    @FXML
    public void initialize()
    {
        /*
         * Calls resetScene() after everything is rendered, which is needed 
         * because otherwise certain width and height values required for a
         * proper render are not yet available.
         */
        Platform.runLater(() ->
        {
            resetScene();
        });

        addSliderEventHandlers();

        menubuttonCentripetal.setOnAction(press -> switchSimulation("ucm-scene-graph"));
        menubuttonConservation.setOnAction(press -> switchSimulation("conservation"));
        menubuttonProjectile.setOnAction(press -> switchSimulation("projectile"));
        menubuttonMainMenu.setOnAction(press -> switchSimulation("mainmenu"));
        menubuttonExit.setOnAction(press -> handleExitOfApplication());
        toggleShowVectors.setOnAction(toggle -> resetScene());
        buttonClear.setOnAction(press -> handleClear());
        buttonDarkMode.setOnMouseClicked(press -> handleDarkMode());
        buttonHelp.setOnMouseClicked(press -> handleHelp());
        buttonPause.setOnMouseClicked(press -> handlePause());
        buttonPlay.setOnMouseClicked(press -> handlePlay());
        buttonReset.setOnMouseClicked(press -> handleReset());
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
            slider.setOnMouseDragged(drag -> resetScene());
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
                // Nearly white, default JavaFX background color of a Pane
                pane.setStyle("-fx-background-color: #f4f4f4;");
            });

            allLines.forEach(line ->
            {
                line.setStroke(Color.RED);
            });
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

    private boolean isAnimationInitialized = false;
    
    public void handlePlay()
    {
        animateButtonPress(buttonPlay, PLAY_BUTTON, PLAY_BUTTON_PRESSED);
        
        if (!isAnimationInitialized)
        {
            blockAnimationHandler.createBlockAnimations(new ArrayList<>(List.of(topBlock, bottomBlock)), paneAnimation);
            isAnimationInitialized = true;
        }
        
        toggleShowVectors.setSelected(false);
        resetScene();
        
        blockAnimationHandler.play();
        toggleFieldState(ANIMATION_STATE.PLAYING);
    }

    public void handlePause()
    {
        animateButtonPress(buttonPause, PAUSE_BUTTON, PAUSE_BUTTON_PRESSED);
        blockAnimationHandler.pause();
        toggleFieldState(ANIMATION_STATE.PAUSED);
    }

    public void handleReset()
    {
        animateButtonPress(buttonReset, RESET_BUTTON, RESET_BUTTON_PRESSED);
        blockAnimationHandler.reset();
        toggleFieldState(ANIMATION_STATE.RESET);
        isAnimationInitialized = false;
        
        this.topBlock = new Block(POSITION.TOP);
        this.bottomBlock = new Block(POSITION.BOTTOM);
        
        resetScene();
    }
    
    private void animateButtonPress(ImageView target, Image notPressed, Image pressed)
    {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, action -> target.setImage(pressed)),
                new KeyFrame(Duration.seconds(0.2), action -> target.setImage(notPressed))
        );
        timeline.play();
    }

    private void toggleFieldState(ANIMATION_STATE currentState)
    {
        switch (currentState)
        {
            case PLAYING ->
            {
                allSliders.forEach(slider -> slider.setDisable(true));
                buttonClear.setDisable(true);
                toggleShowVectors.setDisable(true);
                buttonPlay.setDisable(true);
                buttonPause.setDisable(false);
                buttonReset.setDisable(false);
            }
            case PAUSED ->
            {
                allSliders.forEach(slider -> slider.setDisable(true));
                buttonClear.setDisable(true);
                toggleShowVectors.setDisable(true);
                buttonPlay.setDisable(false);
                buttonPause.setDisable(true);
            }
            case RESET ->
            {
                allSliders.forEach(slider -> slider.setDisable(false));
                buttonClear.setDisable(false);
                toggleShowVectors.setDisable(false);
                buttonPlay.setDisable(false);
                buttonPause.setDisable(true);
                buttonReset.setDisable(true);
            }
            default ->
            {
                throw new IllegalArgumentException("Invalid argument!");
            }
        }
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

        resetScene();
    }

    public void resetScene()
    {
        paneAnimation.getChildren().clear();

        updateBlocks();

        blockAnimationHandler.drawFloor(paneAnimation, this.isDark);
        blockAnimationHandler.situateBlocks(topBlock, bottomBlock, paneAnimation);
        
        if (toggleShowVectors.isSelected())
        {
            drawVectors();
        }
    }
    
    private void updateBlocks()
    {
        topBlock.setMass(sliderMassM2.getValue());
        bottomBlock.setMass(sliderMassM1.getValue());

        topBlock.setForcesExperienced(getForcesExperienced(POSITION.TOP));
        bottomBlock.setForcesExperienced(getForcesExperienced(POSITION.BOTTOM));
    }

    private void drawVectors()
    {
        topBlock.drawFreeBodyDiagram(paneAnimation);
        bottomBlock.drawFreeBodyDiagram(paneAnimation);
    }
    
    // TODO: problem is most likely here, i dont understand why lines wont assemble
    private void drawLines()
    {
        paneGridlines.getChildren().clear();

//        System.out.println(allLines);
        
        allLines.forEach(line ->
        {
            line.toFront();
            paneGridlines.getChildren().add(line);
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
                BlockHelpPageController helpPageController = new BlockHelpPageController(currentStage, this.isDark);
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
    
    private enum ANIMATION_STATE {
        PLAYING,
        PAUSED,
        RESET
    }
}
