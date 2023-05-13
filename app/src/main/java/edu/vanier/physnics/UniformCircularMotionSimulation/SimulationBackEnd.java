/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import edu.vanier.physnics.mainmenu.MainMenuController;
import edu.vanier.physnics.projectilemotion.MainAppController;
import edu.vanier.physnics.stackedblock.BlockFrontEndController;
import io.github.palexdev.materialfx.controls.MFXSlider;
import java.io.IOException;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class that houses methods related to the controller, but that aren't
 * necessary to be in that class for them to work.
 *
 * @author Victor-Pen
 */
public class SimulationBackEnd {

    /**
     * Constructs an instance of SimulationBackEnd to be used.
     */
    public SimulationBackEnd()
    {
    }

    /**
     * Draws an elliptical/circular path.
     *
     * @param centerX The X coordinate of the center of the circular path
     * @param centerY The Y coordinate of the center of the circular path
     * @param radiusX The radius of the circular path, in terms of X coordinates
     * @param radiusY The radius of the circular path, in terms of X coordinates
     * @return a path with the set center coordinates and radii.
     */
    public Path createEllipsePath(double centerX, double centerY, double radiusX, double radiusY)
    {
        //https://stackoverflow.com/questions/14171856/javafx-2-circle-path-for-animation
        ArcTo arcTo = new ArcTo();
        arcTo.setX(centerX - radiusX + 1); // to simulate a full 360 degree celcius circle.
        arcTo.setY(centerY - radiusY);
        arcTo.setSweepFlag(false);
        arcTo.setLargeArcFlag(true);
        arcTo.setRadiusX(radiusX);
        arcTo.setRadiusY(radiusY);

        //making the pth
        Path path = new Path();
        path.getElements().addAll(
                new MoveTo(centerX - radiusX, centerY - radiusY),
                arcTo,
                new ClosePath()); // close 1 px gap.
        path.setStroke(Color.DODGERBLUE);
        path.getStrokeDashArray().setAll(5d, 5d);
        return path;
    }

    /**
     * Returns a PathTransition with the proper node and car set
     *
     * @param path The path to set to the PathTransition
     * @param node The node to set to the PathTransition
     * @param car The car that is revolving in the simulation
     * @return a PathTransition that has the path and the node set, making them
     * move at the same speed as the car in the simulation.
     */
    public PathTransition createPathTransitionCircle(Path path, Node node, Car car)
    {
        PathTransition pathTransitionCircle = new PathTransition();
        pathTransitionCircle.setDuration(Duration.seconds(50 / car.getSpeedMetersPerSeconds()));
        pathTransitionCircle.setPath(path);
        pathTransitionCircle.setNode(node);
        pathTransitionCircle.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransitionCircle.setCycleCount(Timeline.INDEFINITE);
        pathTransitionCircle.setAutoReverse(false);
        pathTransitionCircle.setInterpolator(Interpolator.LINEAR);
        return pathTransitionCircle;
    }

    /**
     * Sets the minimum and maximum of the sliders.
     *
     * @param slider the slider to set
     * @param min the minimum value
     * @param max the maximum value
     */
    public void setSliderRange(MFXSlider slider, double min, double max)
    {
        slider.setMin(min);
        slider.setMax(max);
        //make the slider show ticks and display the min/max values
        slider.setShowMajorTicks(true);
        slider.setShowTicksAtEdges(true);
    }

    /**
     * Retrieves the numerical value of a TextField
     *
     * @param textfield the TextField to retrieve a value from
     * @return the numerical value in the TextField
     */
    @FXML
    public double retrieveTextField(TextField textfield)
    {
        double valueInTextField = 0;
        try
        {
            valueInTextField = Double.valueOf(textfield.getText());
        } catch (NumberFormatException e)
        {
            //in case the textfield detects a non-number, it will push an error and return 0.
            System.out.println("Error");
        }
        return valueInTextField;
    }

    /**
     * Shows an alert error pop-up, and resets the value of the TextField and
     * the Slider to the default value.
     *
     * @param textField the TextField to reset
     * @param slider the Slider to reset
     * @param valueToSet the value to set for both the slider and the TextField
     * @param string The message that will appear in the pop-up
     */
    public void showErrorAlertAndReset(TextField textField, MFXSlider slider, double valueToSet, String string)
    {
        System.out.println("Error");
        //setting the slider and textfield back to default value
        textField.setText(String.valueOf(valueToSet));
        slider.setValue(valueToSet);
        //showing the alert
        popAlert(string);
    }

    /**
     * Makes an alert pop-up
     *
     * @param string the String to display in the pop-up message
     */
    public void popAlert(String string)
    {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(string);
        a.show();
    }

    /**
     * A method used to switch the present simulation to another simulation
     *
     * @param simulationName the name of the simulation that is being switched
     * to
     * @param borderPane
     */
    public void switchSimulation(String simulationName, BorderPane borderPane)
    {

        Stage currentStage = (Stage) borderPane.getScene().getWindow();

        String destination = "/fxml/" + simulationName + ".fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));

        switch (simulationName)
        {
            //switch to the stacked block simulation
            case "stackedblock" ->
            {
                BlockFrontEndController blockcontroller = new BlockFrontEndController();
                loader.setController(blockcontroller);
            }
            //switch to the projectile simulation
            case "projectile" ->
            {
                MainAppController projectileController = new MainAppController();
                loader.setController(projectileController);
            }
            //switch to the stacked block simulation
            case "uniform-circular-motion" ->
            {
                UniformCircularMotionController ucmController = new UniformCircularMotionController();
                loader.setController(ucmController);
            }
            //switch ot the main menu
            case "mainmenu" ->
            {
                MainMenuController menuController = new MainMenuController(currentStage);
                loader.setController(menuController);
            }
            //in case of an error
            default ->
                System.out.println("Invalid simulation name");
        }
        //loads the scene graph that is being switched into
        try
        {
            Parent root = loader.load();
            Scene scene = new Scene(root, 1920, 1080);
            currentStage.setScene(scene);
        } catch (IOException ex)
        {
            System.out.println("Something went wrong changing scenes.");
        }

        currentStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        currentStage.setFullScreenExitHint("");
        currentStage.setFullScreen(true);
    }
}
