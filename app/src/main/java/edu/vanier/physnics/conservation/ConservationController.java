/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import com.sun.javafx.scene.shape.RectangleHelper;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author benja
 */
public class ConservationController {

    @FXML
    private ImageView btnGraph;

    @FXML
    private MenuItem menuItemProjectile;

    @FXML
    private MenuItem menuItemQuit;

    @FXML
    private MenuItem menuItemStacked;

    @FXML
    private MenuItem menuItemUCM;

    @FXML
    private ImageView btnPause;

    @FXML
    private ImageView btnPlay;

    @FXML
    private ImageView btnReset;

    @FXML
    private ImageView btnHelp;

    @FXML
    private ImageView buttonHome;

    @FXML
    private CheckBox checkBoxFriction;

    @FXML
    private ChoiceBox<String> choiceBoxg;

    @FXML
    private ChoiceBox<String> choiceBoxu;

    @FXML
    private Pane paneAnimation;

    @FXML
    private MFXSlider sliderHeight;

    @FXML
    private MFXSlider sliderMass;

    //color of the ramp and the ball
    private Color rampColor;
    private Color ballColor;

    //ball object
    private Ball ball;

    //physics variables (TODO: add units to variables and rename)
    private double g; //m/s^2
    private double u; //no units
    private double rampHeight; //m
    private double mass; // kg

    //object to generate the animation of the ball
    private AnimationBackend animBackend;

    //text objects for different values
    private Text textHeight;
    private Text textMass;
    private Text textg;

    private boolean friction;

    private Ramp ramp;

    private ConservationGraphsController graphController;

    private AnimationTimer updater;

    private EventHandler changeRampColor;
    private EventHandler changeBallColor;

    @FXML
    public void initialize() {

        //setup the ramp and the ball, and the values
        setup();

        btnPlay.setOnMouseClicked((e) -> {
            animBackend.playBallAnimation(ball, rampHeight, g, graphController.getKEGraph(),
                    graphController.getPEGraph());
            graphController.setTotalEnergyText(ConservationFormulas.potentialEnergy(mass, g, rampHeight));
            updater.start();
            disableSidebar(true);
        });

        btnPause.setOnMouseClicked((e) -> {
            animBackend.pause();
        });

        btnReset.setOnMouseClicked((e) -> {
            resetBall();
            disableSidebar(false);
            animBackend.setPlaying(false);
        });

        btnGraph.setOnMouseClicked((e) -> {
            graphController.show();
        });

        btnHelp.setOnMouseClicked((e) -> {

        });

        sliderMass.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            mass = sliderMass.getValue();
            textMass.setText("Mass of the ball: " + mass + " kg");
        });

        sliderHeight.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            paneAnimation.getChildren().remove(ramp);
            ramp = null;
            rampHeight = sliderHeight.getValue();
            createRamp();
            textHeight.setText("Height: " + rampHeight + " m");
        });

        choiceBoxg.setOnAction((e) -> {
            g = getNumber(choiceBoxg.getValue());
            textg.setText("Gravitational\n acceleration:\n" + g + " m/s^2");
        });

        choiceBoxu.setOnAction((e) -> {
            u = getNumber(choiceBoxu.getValue());
        });

        checkBoxFriction.setOnAction((e) -> {
            friction = !friction;

        });

        menuItemQuit.setOnAction((e) -> {
            Platform.exit();
        });

        buttonHome.setOnMouseClicked((e) -> {
            switchSimulation("mainmenu");
        });

        menuItemProjectile.setOnAction((e) -> {
            switchSimulation("projectile");
        });

        menuItemStacked.setOnAction((e) -> {
            switchSimulation("stackedblock");
        });

        menuItemUCM.setOnAction((e) -> {
            switchSimulation("ucm-scene-graph");
        });

        changeRampColor = (EventHandler) (Event event) -> {
            if (!animBackend.isPlaying()) {
                getNewColor(rampColor, "Change ramp color", "ramp");
                ramp = null;
                paneAnimation.getChildren().remove(ramp);
                createRamp();
            }
        };

        ramp.setOnMouseClicked(changeRampColor);

        changeBallColor = (EventHandler) (Event event) -> {
            if (!animBackend.isPlaying()) {
                getNewColor((Color) ball.getFill(), "Change ball color", "ball");
                ball.setFill(ballColor);
            }
        };

        ball.setOnMouseClicked(changeBallColor);

    }

    ;
    
    
    public void setup() {
         //initializes the variables
        mass = 10;
        rampHeight = 10;
        g = 9.8;
        
        openGraphWindow();
        //initialize the animation backend
        animBackend = new AnimationBackend();

        //setup color of the ramp and the ball
        rampColor = Settings.INITTIAL_RAMP_COLOR;
        ballColor = Settings.INITTIAL_BALL_COLOR;

        //initialize the ball and ramp
        ball = new Ball(Settings.BALL_RADIUS, ballColor);

        //no friction on initialize
        friction = false;

        paneAnimation.getChildren().add(ball);

        //draw the ramp
        createRamp();

        //add the options to the choiceboxes
        for (int i = 0; i < Settings.GRAVITATIONAL_CONSTANTS.length; i++) {
            choiceBoxg.getItems().add(Settings.GRAVITATIONAL_CONSTANTS[i]);
        }
        choiceBoxg.setValue(Settings.GRAVITATIONAL_CONSTANTS[0]);

        //add the options to the choiceboxes
        for (int i = 0; i < Settings.FRICTION_COEFFICIENTS.length; i++) {
            choiceBoxu.getItems().add(Settings.FRICTION_COEFFICIENTS[i]);
        }
        choiceBoxu.setValue(Settings.FRICTION_COEFFICIENTS[0]);

       

        sliderMass.setValue(mass);
        sliderHeight.setValue(rampHeight);

        ball.setMass(mass);

        setValueIndicators();

        updater = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateValues();
            }
        };
    }

    public void createRamp() {
        //draw the ramp
        ramp = new Ramp(rampHeight, Settings.RAMP_THICKNESS,
                Settings.RAMP_POSITION_X, Settings.RAMP_POISTION_Y, rampColor);

        //set the path of the ball
        ramp.createBallPath(ball);
        ramp.setOnMouseClicked(changeRampColor);

        paneAnimation.getChildren().add(ramp);
    }

    public void updateValues() {
        double currentHeight = rampHeight - ((ball.getTranslateY()) / (ramp.getRadius() + ball.getRadius()) * rampHeight);
        double TME = ConservationFormulas.potentialEnergy(mass, g, rampHeight);
        double PE = ConservationFormulas.potentialEnergy(mass, g, currentHeight);
        double currentVelocity = ConservationFormulas.getCurrentVelocity(TME, PE, mass);
        double KE = ConservationFormulas.kineticEnergy(mass, currentVelocity);

        graphController.setCurrentHeightText(currentHeight);
        graphController.setKeText(KE);
        graphController.setVelocityText(currentVelocity);
        graphController.setPeText(PE);
    }

    public void setValueIndicators() {
        //height text placed to the left of the ramp
        textHeight = new Text("Height: " + rampHeight + " m");
        textHeight.setFont(Settings.TEXT_FONT);
        paneAnimation.getChildren().add(textHeight);
        textHeight.setX(Settings.HEIGHT_TEXT_POSITION_X);
        textHeight.setY(Settings.HEIGHT_TEXT_POSITION_Y);

        //mass text placed on top of the ramp
        textMass = new Text("Mass of the ball: " + mass + " kg");
        textMass.setFont(Settings.TEXT_FONT);
        paneAnimation.getChildren().add(textMass);
        textMass.setX(Settings.MASS_TEXT_POSITION_X);
        textMass.setY(Settings.MASS_TEXT_POSITION_Y);

        //acceleration text placed to the right of the ramp
        textg = new Text("Gravitational\n acceleration: \n" + g + " m/s^2");
        textg.setFont(Settings.TEXT_FONT);
        paneAnimation.getChildren().add(textg);
        textg.setX(Settings.ACCELERATION_TEXT_POSITION_X);
        textg.setY(Settings.ACCELERATION_TEXT_POSITION_Y);

        drawArrow(Settings.ARROW_POSITION_X,
                Settings.ARROW_POSITION_Y, Settings.ARROW_LENGTH,
                Settings.ARROW_THICKNESS, Settings.ARROW_POINT_WIDTH);

    }

    public double getNumber(String option) {
        String value = "";
        for (int i = 0; i < option.length(); i++) {
            if (Character.isDigit(option.charAt(i)) || option.charAt(i) == '.') {
                value += option.charAt(i);
            }
        }

        return Double.parseDouble(value);
    }

    private void resetBall() {
        animBackend.reset();
        paneAnimation.getChildren().remove(ball);
        ball = null;
        ball = new Ball(Settings.BALL_RADIUS, ballColor);
        ramp.createBallPath(ball);
        ball.setMass(mass);
        ball.setOnMouseClicked(changeBallColor);
        paneAnimation.getChildren().add(ball);

    }

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

    public void openGraphWindow() {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/conservation_graphs.fxml"));
        graphController = new ConservationGraphsController(ball);
        loader.setController(graphController);

        Scene scene = null;
        try {
            scene = new Scene(loader.load(), GraphSettings.PANE_WIDTH, GraphSettings.PANE_HEIGHT);
        } catch (IOException ex) {
            System.out.println("Graaph stage could not be opened");
        }

        stage.setScene(scene);
        stage.setTitle("Current energy levels");

    }

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

    public void getNewColor(Color objectColor, String title, String choice) {
        Color chosenColor = objectColor;
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
        extraColorOptions.setLayoutX(colorPickerWidth/2-50);
        extraColorOptions.setLayoutY(50);
        
        
        if(choice.equals("ramp")){
            extraColorOptions.setValue(rampColor);
            extraColorOptions.setOnAction((eventHandler) ->{
                rampColor = extraColorOptions.getValue();
                colorPickerStage.close();
            });
        }
        else{
            extraColorOptions.setValue(ballColor);
            extraColorOptions.setOnAction((eventHandler) ->{
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

    public void disableSidebar(boolean status) {
        sliderHeight.setDisable(status);
        sliderMass.setDisable(status);
        checkBoxFriction.setDisable(status);
        choiceBoxg.setDisable(status);
        choiceBoxu.setDisable(status);
    }
}
