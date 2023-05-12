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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
 * A class to communicate the user's interactions on the front end with the back
 * end of the program.
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

    /**
     * Method that it called first when the scene is loaded. It sets up the
     * scene and adds event handlers to the buttons and sliders.
     */
    @FXML
    public void initialize()
    {
        /*
         * Calls updateScene() after everything is rendered, which is needed 
         * because otherwise certain width and height values required for a
         * proper render are not yet available.
         */
        Platform.runLater(() ->
        {
            updateScene();
        });

        addSliderEventHandlers();

        menubuttonCentripetal.setOnAction(press -> switchSimulation("ucm-scene-graph"));
        menubuttonConservation.setOnAction(press -> switchSimulation("conservation"));
        menubuttonProjectile.setOnAction(press -> switchSimulation("projectile"));
        menubuttonMainMenu.setOnAction(press -> switchSimulation("mainmenu"));
        menubuttonExit.setOnAction(press -> handleExitOfApplication());
        toggleShowVectors.setOnAction(toggle -> updateScene());
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
            slider.setOnMouseDragged(drag -> updateScene());
        }
    }

    // TODO: lines just don't display and i don't get it

    /**
     * Method that collects all elements that need to be changed to dark mode
     * and adds them to the allTextElements and allPanes ArrayLists. Then, it
     * sets those elements to light/dark mode depending on the current state 
     * of the application.
     */
    private void handleDarkMode()
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
    
    private void handlePlay()
    {
        animateButtonPress(buttonPlay, PLAY_BUTTON, PLAY_BUTTON_PRESSED);
        
        if (!isAnimationInitialized)
        {
            blockAnimationHandler.createBlockAnimations(new ArrayList<>(List.of(topBlock, bottomBlock)), paneAnimation);
            isAnimationInitialized = true;
        }
        
        toggleShowVectors.setSelected(false);
        updateScene();
        
        blockAnimationHandler.play();
        toggleFieldState(ANIMATION_STATE.PLAYING);
    }

    private void handlePause()
    {
        animateButtonPress(buttonPause, PAUSE_BUTTON, PAUSE_BUTTON_PRESSED);
        blockAnimationHandler.pause();
        toggleFieldState(ANIMATION_STATE.PAUSED);
    }

    private void handleReset()
    {
        animateButtonPress(buttonReset, RESET_BUTTON, RESET_BUTTON_PRESSED);
        blockAnimationHandler.reset();
        toggleFieldState(ANIMATION_STATE.RESET);
        isAnimationInitialized = false;
        
        this.topBlock = new Block(POSITION.TOP);
        this.bottomBlock = new Block(POSITION.BOTTOM);
        
        updateScene();
    }
    
    /**
     * Uses a simple Timeline to show a pressed version of the button and then
     * quickly reverts to the normal look of the button. This provides feedback
     * to the user that the button has been pressed.
     * 
     * @param target the button being pressed
     * @param notPressed what the button looks like unpressed
     * @param pressed what the button looks like while being pressed
     */
    private void animateButtonPress(ImageView target, Image notPressed, Image pressed)
    {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, action -> target.setImage(pressed)),
                new KeyFrame(Duration.seconds(0.2), action -> target.setImage(notPressed))
        );
        timeline.play();
    }

    /**
     * Enables / disables the buttons and sliders depending on the state of
     * the animation. While the animation is playing or paused, properties of
     * the Blocks or forces cannot be changed. These features are re-enabled
     * when the animation is reset. Furthermore, this protects again the 'play'
     * or 'pause' button being pressed while in that state already, which can
     * cause errors.
     * 
     * @param currentState the current state of the animation (PLAYING, PAUSED, RESET)
     */
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
    
    private void handleHelp()
    {
        switchSimulation("stackedblock_help");
    }

    private void handleClear()
    {
        allSliders.forEach(slider ->
        {
            slider.setValue(slider.getMin());
        });

        updateScene();
    }

    /**
     * This method redraws the scene. It is called when the user makes a change
     * that affects the scene, such as changing the value of a slider.
     * 
     * @see drawVectors
     */
    private void updateScene()
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

    /**
     * This method returns a list of the forces experienced by the Block.
     * 
     * @param blockId the block for which the forces are to be determined (TOP or BOTTOM)
     * @return an ArrayList of the forces experienced by the Block
     */
    protected ArrayList<Vector> getForcesExperienced(POSITION blockId)
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

    private void handleExitOfApplication()
    {
        Platform.exit();
    }

    /**
     * Switch to the specified simulation based on the parameter. The stage is
     * preserved but the scene is changed.
     * 
     * @param simulationName the name of the simulation to be switched to as a String
     */
    private void switchSimulation(String simulationName)
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
     * @param currentNode the current Node being analyzed
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
    protected enum POSITION {
        TOP,
        BOTTOM
    }
    
    private enum ANIMATION_STATE {
        PLAYING,
        PAUSED,
        RESET
    }
}
