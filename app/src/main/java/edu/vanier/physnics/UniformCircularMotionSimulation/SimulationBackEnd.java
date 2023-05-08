/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import edu.vanier.physnics.conservation.ConservationController;
import edu.vanier.physnics.mainmenu.MainMenuController;
import edu.vanier.physnics.projectilemotion.MainAppController;
import edu.vanier.physnics.stackedblock.BlockFrontEndController;
import java.io.IOException;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Admin
 */
public class SimulationBackEnd {

    /**
     * Constructs an instance of SimulationBackEnd to be used.
     */
    public SimulationBackEnd() {
    }  
    
    /**
     * Draws an elliptical/circular path.
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
     * @param path The path to set to the PathTransition
     * @param node The node to set to the PathTransition
     * @param car The car that is revolving in the simulation
     * @return a PathTransition that has the path and the node set, making them move at the same speed as the car in the simulation.
     */
    public PathTransition createPathTransitionCircle(Path path, Node node, Car car){
        PathTransition pathTransitionCircle = new PathTransition();
        pathTransitionCircle.setDuration(Duration.seconds(50/car.getSpeed()));
        pathTransitionCircle.setPath(path);
        pathTransitionCircle.setNode(node);
        pathTransitionCircle.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransitionCircle.setCycleCount(Timeline.INDEFINITE);
        pathTransitionCircle.setAutoReverse(false);
        pathTransitionCircle.setInterpolator(Interpolator.LINEAR);       
        return pathTransitionCircle;
    }

    /**
     * A method used to switch the present simulation to another simulation
     * @param simulationName the name of the simulation that is being switched to
     */
    public void switchSimulation(String simulationName, BorderPane borderPane){
        
            Stage currentStage = (Stage) borderPane.getScene().getWindow();

            String destination = "/fxml/" + simulationName + ".fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));

            if (simulationName.equals("quit")) {
                currentStage.close();
            }
            else{
                switch (simulationName)
                {
                    case "stackedblock" ->
                    {
                        BlockFrontEndController blockcontroller = new BlockFrontEndController();
                        loader.setController(blockcontroller);
                    }
                    case "projectile" ->
                    {
                        MainAppController projectileController = new MainAppController();
                        loader.setController(projectileController);
                    }
                    case "ucm-scene-graph" ->
                    {
                        UniformCircularMotionController ucmController = new UniformCircularMotionController();
                        loader.setController(ucmController);
                    }
                    case "conservation" ->
                    {
                        ConservationController controller = new ConservationController();
                        loader.setController(controller);
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
        }            
    }
}
