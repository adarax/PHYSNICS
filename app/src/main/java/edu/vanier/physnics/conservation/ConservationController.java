/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import edu.vanier.physnics.UniformCircularMotionSimulation.UniformCircularMotionController;
import edu.vanier.physnics.conservation.graphs.ConservationGraphsController;
import edu.vanier.physnics.conservation.graphs.GraphSettings;
import edu.vanier.physnics.mainmenu.MainMenuController;
import edu.vanier.physnics.projectilemotion.MainAppController;
import edu.vanier.physnics.stackedblock.BlockFrontEndController;
import io.github.palexdev.materialfx.controls.MFXSlider;
import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main controller for the Conservation of energy window. Instantiates all FXML
 * elements and provides their functionalities.
 *
 * @author Benjamin Pratt
 */
public class ConservationController {

    @FXML
    private ImageView buttonGraph;

    @FXML
    private MenuItem menuItemProjectile;

    @FXML
    private MenuItem menuItemQuit;

    @FXML
    private MenuItem menuItemStacked;

    @FXML
    private MenuItem menuItemUCM;

    @FXML
    private ImageView buttonPause;

    @FXML
    private ImageView buttonPlay;

    @FXML
    private ImageView buttonReset;

    @FXML
    private ImageView buttonHelp;

    @FXML
    private ImageView buttonHome;

    @FXML
    private ChoiceBox<String> choiceBoxGravitationalAcceleration;

    @FXML
    private Pane paneAnimation;

    @FXML
    private MFXSlider sliderHeight;

    @FXML
    private MFXSlider sliderMass;

    private Color rampColor;
    private Color ballColor;

    private Ball ball;

    private double gravitationalAccelerationMetersPerSecondSquared;
    private double rampHeightM;
    private double massKg;

    private AnimationBackend animBackend;

    private Text textHeight;
    private Text textMass;
    private Text textGravitationalAcceleration;

    private Ramp ramp;

    private ConservationGraphsController graphController;

    private AnimationTimer valueListener;

    private EventHandler changeRampColor;
    private EventHandler changeBallColor;

    private double totalMechanicalEnergy;

    /**
     * Initializes methods for the main conservation window. Gives functionality
     * to every UI element in the FXML
     */
    @FXML
    public void initialize() {

        //setup the ramp and the ball, and the values
        setup();

        /**
         * Creates and plays the animation for the ball in the ramp and for the
         * energy level graphs. Starts the animation timer to update the
         * variables dynamically. Disables the sidebar while the animation is
         * playing.
         */
        buttonPlay.setOnMouseClicked((clicked) -> {

            animBackend.playBallAnimation(ball, ramp, rampHeightM, gravitationalAccelerationMetersPerSecondSquared, graphController.getKineticEnergyGraph(),
                    graphController.getPotentialEnergyGraph()
            );

            graphController.setTotalEnergyText(totalMechanicalEnergy);
            valueListener.start();
            disableSidebar(true);
        });

        /**
         * Pauses the animation.
         */
        buttonPause.setOnMouseClicked((clicked) -> {
            animBackend.pause();
        });

        /**
         * Resets the animation and enables the sidebar to allow the user to
         * adjust the variables.
         */
        buttonReset.setOnMouseClicked((clicked) -> {
            resetBall();
            disableSidebar(false);
            animBackend.setPlaying(false);
            graphController.setup();
            valueListener.stop();
        });

        /**
         * Shows the graphs window.
         */
        buttonGraph.setOnMouseClicked((clicked) -> {
            
            graphController.show();
        });

        /**
         * Opens the help window.
         */
        buttonHelp.setOnMouseClicked((clicked) -> {
            openHelpMenu();
        });

        /**
         * Sets the mass variable to its new value when the mass slider is
         * interacted with.
         */
        sliderMass.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            massKg = sliderMass.getValue();
            ball.setMassKg(massKg);
            textMass.setText("Mass of the ball: " + massKg + " kg");
        });

        /**
         * Sets the height value of the ramp and changes its size to reflect the
         * change.
         */
        sliderHeight.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            paneAnimation.getChildren().remove(ramp);
            ramp = null;
            rampHeightM = sliderHeight.getValue();
            createRamp();
            textHeight.setText("Height: " + rampHeightM + " m");
        });

        /**
         * Changes the gravitational acceleration constant to the new value that
         * the user selected.
         */
        choiceBoxGravitationalAcceleration.setOnAction((clicked) -> {
            gravitationalAccelerationMetersPerSecondSquared = getNumber(choiceBoxGravitationalAcceleration.getValue());
            textGravitationalAcceleration.setText("Gravitational\n acceleration:\n" + gravitationalAccelerationMetersPerSecondSquared + " m/s^2");
        });

        /**
         * Exits the program.
         */
        menuItemQuit.setOnAction((clicked) -> {
            Platform.exit();
        });

        /**
         * Returns back to main menu.
         */
        buttonHome.setOnMouseClicked((e) -> {
            switchSimulation("mainmenu");
        });

        /**
         * goes to projectile simulation window
         */
        menuItemProjectile.setOnAction((e) -> {
            switchSimulation("projectile");
        });

        /**
         * Goes to stacked block simulation
         */
        menuItemStacked.setOnAction((e) -> {
            switchSimulation("stackedblock");
        });

        /**
         * goes to uniform circular motion simulation
         */
        menuItemUCM.setOnAction((e) -> {
            switchSimulation("ucm-scene-graph");
        });

        /**
         * Opens the color picker window to allow the user to select a new color
         * for the ramp
         */
        changeRampColor = (EventHandler) (Event event) -> {
            if (!animBackend.isPlaying()) {
                getNewColor(rampColor, "Change ramp color", "ramp");
                paneAnimation.getChildren().remove(ramp);
                ramp = null;
                createRamp();
            }
        };

        ramp.setOnMouseClicked(changeRampColor);

        /**
         * Opens the color picker window to allow the user to select a new color
         * for the ball
         */
        changeBallColor = (EventHandler) (Event event) -> {
            if (!animBackend.isPlaying()) {
                getNewColor((Color) ball.getFill(), "Change ball color", "ball");
                ball.setFill(ballColor);
            }
        };

        ball.setOnMouseClicked(changeBallColor);

    }

    /**
     * @author benja Initializes all the variables and non-FXML UI elements.
     * Adds options to the different choice boxes. Creates the animation timer
     * that will monitor the position of the ball during the animation and
     * update values accordingly
     */
    public void setup() {
        choiceBoxGravitationalAcceleration.setStyle("-fx-font-size: 20;");
        //initializes the variables
        massKg = Settings.DEFAULT_MASS;
        rampHeightM = Settings.DEFAULT_HEIGHT;
        gravitationalAccelerationMetersPerSecondSquared = Settings.DEFAULT_GRAVITATIONAL_ACCELERATION;

        totalMechanicalEnergy = ConservationFormulas.potentialEnergy(massKg, gravitationalAccelerationMetersPerSecondSquared, rampHeightM);

        openGraphWindow();
        //initialize the animation backend
        animBackend = new AnimationBackend();

        //setup color of the ramp and the ball
        rampColor = Settings.INITTIAL_RAMP_COLOR;
        ballColor = Settings.INITTIAL_BALL_COLOR;

        //initialize the ball and ramp
        ball = new Ball(Settings.BALL_RADIUS, ballColor);

        paneAnimation.getChildren().add(ball);

        //draw the ramp
        createRamp();

        //add the options to the choiceboxe
        for (int i = 0; i < Settings.GRAVITATIONAL_ACCELERATION_CONSTANTS.length; i++) {
            choiceBoxGravitationalAcceleration.getItems().add(Settings.GRAVITATIONAL_ACCELERATION_CONSTANTS[i]);
        }
        choiceBoxGravitationalAcceleration.setValue(Settings.GRAVITATIONAL_ACCELERATION_CONSTANTS[0]);

        sliderMass.setValue(massKg);
        sliderHeight.setValue(rampHeightM);

        ball.setMassKg(massKg);

        setValueIndicators();

        valueListener = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateValues();

            }
        };
    }

    /**
     * Draws a new ramp on the animation pane
     */
    public void createRamp() {
        //draw the ramp
        ramp = new Ramp(rampHeightM, Settings.RAMP_THICKNESS,
                Settings.RAMP_POSITION_X, Settings.RAMP_POISTION_Y, rampColor);

        //set the path of the ball
        ramp.createBallPath(ball);
        ramp.setOnMouseClicked(changeRampColor);

        paneAnimation.getChildren().add(ramp);
    }

    /**
     * Updates the potential, kinetic and friction energy based on the current
     * ball position.
     */
    public void updateValues() {
        double currentHeight = rampHeightM - (ball.getTranslateY() / (ramp.getRadius() + 
                ball.getRadius()-ramp.getThickness()) * rampHeightM);
        double potentialEnergy = ConservationFormulas.potentialEnergy(massKg, gravitationalAccelerationMetersPerSecondSquared, currentHeight);
        double currentVelocity = ConservationFormulas.getCurrentVelocity(totalMechanicalEnergy, potentialEnergy, massKg);
        double kineticEnergy = ConservationFormulas.kineticEnergy(massKg, currentVelocity);

        graphController.setCurrentHeightText(currentHeight);
        graphController.setKineticEnergyText(kineticEnergy);
        graphController.setVelocityText(currentVelocity);
        graphController.setPotentialEnergy(potentialEnergy);

    }

    /**
     * Creates and adds to the animation pane the value indicators for height of
     * the ramp, mass of the ball and gravitational acceleration
     */
    public void setValueIndicators() {
        //height text placed to the left of the ramp
        textHeight = new Text("Height: " + rampHeightM + " m");
        textHeight.setFont(Settings.TEXT_FONT);
        paneAnimation.getChildren().add(textHeight);
        textHeight.setX(Settings.RAMP_HEIGHT_TEXT_POSITION_X);
        textHeight.setY(Settings.RAMP_HEIGHT_TEXT_POSITION_Y);

        //mass text placed on top of the ramp
        textMass = new Text("Mass of the ball: " + massKg + " kg");
        textMass.setFont(Settings.TEXT_FONT);
        paneAnimation.getChildren().add(textMass);
        textMass.setX(Settings.MASS_TEXT_POSITION_X);
        textMass.setY(Settings.MASS_TEXT_POSITION_Y);

        //acceleration text placed to the right of the ramp
        textGravitationalAcceleration = new Text("Gravitational\n acceleration: \n" + gravitationalAccelerationMetersPerSecondSquared + " m/s^2");
        textGravitationalAcceleration.setFont(Settings.TEXT_FONT);
        paneAnimation.getChildren().add(textGravitationalAcceleration);
        textGravitationalAcceleration.setX(Settings.ACCELERATION_TEXT_POSITION_X);
        textGravitationalAcceleration.setY(Settings.ACCELERATION_TEXT_POSITION_Y);

        drawArrow(Settings.ARROW_POSITION_X,
                Settings.ARROW_POSITION_Y, Settings.ARROW_LENGTH,
                Settings.ARROW_THICKNESS, Settings.ARROW_POINT_WIDTH);

    }

    /**
     * Gets the number from a string option. Used to get from the array of
     * friction coefficients and from the array of gravitational accelerations
     *
     * @param option Inputted string that a number needs to be extracted from
     * @return
     */
    public double getNumber(String option) {
        String value = "";
        for (int i = 0; i < option.length(); i++) {
            if (Character.isDigit(option.charAt(i)) || option.charAt(i) == '.') {
                value += option.charAt(i);
            }
        }

        return Double.parseDouble(value);
    }

    /**
     * Resets the ball to its original position
     */
    public void resetBall() {
        animBackend.reset();
        paneAnimation.getChildren().remove(ball);
        ball = null;
        ball = new Ball(Settings.BALL_RADIUS, ballColor);
        ramp.createBallPath(ball);
        ball.setMassKg(massKg);
        ball.setOnMouseClicked(changeBallColor);
        paneAnimation.getChildren().add(ball);

    }

    /**
     * Switches to a different simulation or back to main menu
     *
     * @param simulationName Name of the simulation that the user wishes to switch to
     */
    public void switchSimulation(String simulationName) {
        Stage currentStage = (Stage) paneAnimation.getScene().getWindow();

        String destination = "/fxml/" + simulationName + ".fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));

        switch (simulationName) {
            case "stackedblock" -> {
                BlockFrontEndController blockcontroller = new BlockFrontEndController();
                loader.setController(blockcontroller);
            }
            case "projectile" -> {
                MainAppController projectileController = new MainAppController();
                loader.setController(projectileController);
            }
            case "ucm-scene-graph" -> {
                UniformCircularMotionController ucmController = new UniformCircularMotionController();
                loader.setController(ucmController);
            }
            case "conservation" -> {
                ConservationController controller = new ConservationController();
                loader.setController(controller);
            }
            case "mainmenu" -> {
                MainMenuController menuController = new MainMenuController(currentStage);
                loader.setController(menuController);
            }
            default ->
                System.out.println("Invalid simulation name");
        }

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, 1920, 1080);
            currentStage.setScene(scene);
        } catch (IOException ex) {
            System.out.println("Something went wrong changing scenes.");
        }
    }

    /**
     * Opens the graph window at the start of the program, but keeps it hidden
     * until the user presses the graph button
     */
    public void openGraphWindow() {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/conservation_graphs.fxml"));
        graphController = new ConservationGraphsController();
        loader.setController(graphController);

        Scene scene = null;
        try {
            scene = new Scene(loader.load(), GraphSettings.PANE_WIDTH, GraphSettings.PANE_HEIGHT);
        } catch (IOException ex) {
            System.out.println("Graph stage could not be opened");
        }

        stage.setScene(scene);
        stage.setTitle("Current energy levels");

    }

    /**
     * Draws a downward arrow at a certain position
     *
     * @param posx X position of the arrow
     * @param posy Y position of the arrow
     * @param length length of the arrow
     * @param width width of the arrow
     * @param pointWidth width of the point of the arrow
     */
    public void drawArrow(double posx, double posy, double length, double width, double pointWidth) {
        Line arrowShaft = new Line();
        arrowShaft.setStartY(posy);
        arrowShaft.setStartX(posx);
        arrowShaft.setEndX(posx);
        arrowShaft.setEndY(posy + length);
        arrowShaft.setStrokeWidth(width);

        Line leftPoint = new Line(posx, posy + length, posx + pointWidth, posy + length - pointWidth);
        leftPoint.setStrokeWidth(width);

        Line rightPoint = new Line(posx, posy + length, posx - pointWidth, posy + length - pointWidth);
        rightPoint.setStrokeWidth(width);

        paneAnimation.getChildren().addAll(arrowShaft, leftPoint, rightPoint);

    }

    /**
     * Opens a color picked window and changes the color of the corresponding
     * object, either the ramp or the ball
     *
     * @param objectColor current color of the object
     * @param title title of the opened stage
     * @param choice choice between changing the ramp color or the ball color
     */
    public void getNewColor(Color objectColor, String title, String choice) {
        Stage colorPickerStage = new Stage();
        Pane colorPickerPane = new Pane();

        double colorPickerHeight = 300;
        double colorPickerWidth = 450;
        double squareSides = 50;

        //components
        for (int i = 0; i < (Settings.COLOR_LIST.length); i++) {
            Rectangle colorChoice = new Rectangle();
            colorChoice.setHeight(squareSides);
            colorChoice.setWidth(squareSides);
            if (i < 4) {
                colorChoice.setX(squareSides + 100 * i);
                colorChoice.setY(100);

            } else {
                colorChoice.setX(squareSides + 100 * (i - 4));
                colorChoice.setY(200);
            }

            colorChoice.setFill(Settings.COLOR_LIST[i]);
            colorChoice.setStroke(Color.BLACK);

            if (choice.equals("ramp")) {
                colorChoice.setOnMouseClicked((eventHandler) -> {
                    rampColor = (Color) colorChoice.getFill();
                    colorPickerStage.close();
                });
            } else {
                colorChoice.setOnMouseClicked((eventHandler) -> {
                    ballColor = (Color) colorChoice.getFill();
                    colorPickerStage.close();
                });
            }

            colorPickerPane.getChildren().add(colorChoice);
        }
        ColorPicker extraColorOptions = new ColorPicker();
        extraColorOptions.setLayoutX(colorPickerWidth / 2 - 50);
        extraColorOptions.setLayoutY(50);

        if (choice.equals("ramp")) {
            extraColorOptions.setValue(rampColor);
            extraColorOptions.setOnAction((eventHandler) -> {
                rampColor = extraColorOptions.getValue();
                colorPickerStage.close();
            });
        } else {
            extraColorOptions.setValue(ballColor);
            extraColorOptions.setOnAction((eventHandler) -> {
                ballColor = extraColorOptions.getValue();
                colorPickerStage.close();
            });
        }

        colorPickerPane.getChildren().add(extraColorOptions);

        Scene colorPickerScene = new Scene(colorPickerPane, colorPickerWidth, colorPickerHeight);

        colorPickerStage.setScene(colorPickerScene);
        colorPickerStage.setTitle(title);
        colorPickerStage.setResizable(false);
        colorPickerStage.initModality(Modality.APPLICATION_MODAL);

        colorPickerStage.showAndWait();
    }

    /**
     * Disables the side bar options while the animation is playing.
     *
     * @param status disabled or enabled
     */
    public void disableSidebar(boolean status) {
        sliderHeight.setDisable(status);
        sliderMass.setDisable(status);

        choiceBoxGravitationalAcceleration.setDisable(status);

    }

    /**
     * Opens the help menu and defines the functionality for the return button.
     */
    public void openHelpMenu() {
        Stage helpStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/conservation_help.fxml"));
        ConservationHelpPageController controller = new ConservationHelpPageController();
        loader.setController(controller);

        Scene scene = null;
        try {
            scene = new Scene(loader.load(), Settings.HELP_MENU_WIDTH, Settings.HELP_MENU_HEIGHT);
        } catch (IOException ex) {
            System.out.println("Graaph stage could not be opened");
        }

        helpStage.setScene(scene);
        helpStage.setTitle("Current energy levels");

        helpStage.setTitle("Help Menu");
        helpStage.setResizable(false);
        helpStage.initModality(Modality.APPLICATION_MODAL);
        helpStage.show();

    }
}
