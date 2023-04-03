package edu.vanier.physnics.stackedblock;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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

    // Basic initial setup of blocks
    private final Block topBlock = new Block(1);
    private final Block bottomBlock = new Block(0);

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

        buttonDarkMode.setOnMouseClicked(e -> handleDarkMode());

        buttonClear.setOnAction(e -> handleClear());

        menubuttonCentripetal.setOnAction(e -> goToCentripetalForce());

        menubuttonConservation.setOnAction(e -> goToConservationOfEnergy());

        menubuttonProjectile.setOnAction(e -> goToProjectileMotion());

        menubuttonExit.setOnAction(e -> handleExitOfApplication());

        menubuttonMainMenu.setOnAction(e ->
        {
            // Return to main menu
        });

        toggleShowVectors.setOnAction(e ->
        {
            handleShowVectors();
        });

        buttonPlay.setOnMouseClicked(e ->
        {
            handlePlay();
        });

        buttonPause.setOnMouseClicked(e ->
        {
            handlePause();
        });

        buttonReset.setOnMouseClicked(e ->
        {
            handleReset();
        });

        buttonHelp.setOnMouseClicked(e ->
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
    }

    public void updateBlocks()
    {
        topBlock.setMass(sliderMassM2.getValue());
        bottomBlock.setMass(sliderMassM1.getValue());

        topBlock.setForcesExperienced(getForcesExperienced(topBlock.getBlockNumber()));
        bottomBlock.setForcesExperienced(getForcesExperienced(bottomBlock.getBlockNumber()));
    }

    public ArrayList<Vector> getForcesExperienced(int blockNumber)
    {
        return blockFormulasCalculator.determineForcesExperienced(topBlock,
                bottomBlock,
                sliderForceOnM1.getValue(),
                sliderForceOnM2.getValue(),
                sliderAngleOnM1.getValue(),
                sliderAngleOnM2.getValue(),
                sliderFrictionFloor.getValue(),
                sliderFrictionM1.getValue(),
                blockNumber);
    }

    public void handleExitOfApplication()
    {

    }

    public void goToCentripetalForce()
    {
        // Go to centripetal force screen
    }

    public void goToConservationOfEnergy()
    {
        // Go to conservation of energy screen
    }

    public void goToMainMenu()
    {
        // Go to main menu screen
    }

    public void goToProjectileMotion()
    {
        // Go to projectile motion screen
    }

    private void handleShowVectors()
    {
        isVectorShowing = !isVectorShowing;
        
        if (isVectorShowing)
        {
            topBlock.drawFreeBodyDiagram(paneAnimation);
            bottomBlock.drawFreeBodyDiagram(paneAnimation);
        }
        
        // Otherwise clear FBD
    }

    // Might not be needed
    public enum POSITION {
        TOP,
        BOTTOM
    }
}
