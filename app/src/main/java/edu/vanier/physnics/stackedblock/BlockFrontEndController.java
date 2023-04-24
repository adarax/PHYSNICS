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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author adarax
 */
public class BlockFrontEndController {

    @FXML
    private MFXButton buttonClear;

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

    private final BlockAnimation blockAnimationHandler = new BlockAnimation();
    private final BlockFormulas blockFormulasCalculator = new BlockFormulas();
    
    private final Block topBlock = new Block(POSITION.TOP);
    private final Block bottomBlock = new Block(POSITION.BOTTOM);

    private ArrayList<MFXSlider> allSliders;

    private boolean isDark = false;
    private final Image LIGHT_MOON = new Image(getClass().getResourceAsStream("/images/light_moon_icon.png"));
    private final Image DARK_MOON = new Image(getClass().getResourceAsStream("/images/dark_moon_icon.png"));

    private boolean isVectorShowing = false;

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
            updateScene();
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

        buttonHelp.setOnMouseClicked(press ->
        {
            handleHelp();
        });
    }

    public void addSliderEventHandlers()
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

    public void handleDarkMode()
    {
        if (isDark)
        {
            // Go to light mode (on rest of app)

            buttonDarkMode.setImage(DARK_MOON);
        } else
        {
            // Go to dark mode (on rest of app)

            buttonDarkMode.setImage(LIGHT_MOON);
        }

        isDark = !isDark;
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

    public void handleHelp()
    {
        // Open the help screen
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
            handleShowVectors(false);
    }

    public void updateBlocks()
    {
        topBlock.setMass(sliderMassM2.getValue());
        bottomBlock.setMass(sliderMassM1.getValue());

        topBlock.setForcesExperienced(getForcesExperienced(POSITION.TOP));
        bottomBlock.setForcesExperienced(getForcesExperienced(POSITION.BOTTOM));
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
        }
        else
        {
            updateScene();
        }
    }

    public enum POSITION {
        TOP,
        BOTTOM
    }
}
