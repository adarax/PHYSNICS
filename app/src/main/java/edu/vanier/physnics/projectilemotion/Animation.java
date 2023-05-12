/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

/**
 * Class that handles the animation of the projectile and its trail. Uses calculated
 * values from the Equations class to animate accurately according to kinematics.
 * 
 * @author vireshpatel43
 */
public class Animation {
    // Tansition animation that uses a provided path and node to animate.
    PathTransition pathTransition = new PathTransition();
    private double xDisplacementMeters;
    private double flightTimeSeconds;
    // The quadratic curve representing the projectile motion
    private QuadCurveTo projectileMotion;
    // Constant value used to scale distance from meters to pixels for the animation
    private final double SCALE_METERS_TO_PIXELS = 50;
    // Initial x position of the ball in the scene
    private final double INITIAL_BALL_POSITION_X_PIXELS = 135;
    // Initial y position of the ball in the scene
    private final double INITIAL_BALL_POSITION_Y_PIXELS = 790;

    
    /**
     * Calls the Equations class to get the flight time, and X-displacement based
     * on the parameters provided. Also calls scaleHeightToPixels() which is used to 
     * find the height of the parabola based on the launch Angle. The parabola and
     * initial position are passed into a Path object which is later returned.
     * 
     * @param launchAngleDegrees Launch angle of ball
     * @param gravityMetersPerSecondSquared The gravitational acceleration
     * @param initialVelocityMetersPerSecond Initial velocity of ball
     * @return The path representing the projectile motion based on the parameters provided
     */
    public Path setPath(double launchAngleDegrees, double gravityMetersPerSecondSquared, double initialVelocityMetersPerSecond) {
        // Holds different paths such as a quadratic curve or a movement. 
        Path path = new Path();
        // Calles Equations class to calculate values
        xDisplacementMeters = Equations.getXdisplacementMeters(launchAngleDegrees, initialVelocityMetersPerSecond, gravityMetersPerSecondSquared);
        flightTimeSeconds = Equations.getFlightTimeSeconds(launchAngleDegrees, initialVelocityMetersPerSecond, gravityMetersPerSecondSquared);
        // Scales values from meters into pixels
        double xDisplacementPixels = xDisplacementMeters * SCALE_METERS_TO_PIXELS;
        // Position of the floor in pixels
        final double POSITION_FLOOR_PIXELS = 835;
        
        // length of cannon used to move ball from inital position to edge of cannon.
        final double CANNON_LENGTH = 68;
        // Using the cannon length and launch angle, calculates the position of the cannon Edge using trigonometry.
        double cannonEdgeXPixels = INITIAL_BALL_POSITION_X_PIXELS + ((Math.cos(Math.toRadians(launchAngleDegrees))) * CANNON_LENGTH);
        double cannonEdgeYPixels = INITIAL_BALL_POSITION_Y_PIXELS - ((Math.sin(Math.toRadians(launchAngleDegrees))) * CANNON_LENGTH);
        
        // Sets initial position of ball to the edge of the cannon
        MoveTo initialPositionPixels = new MoveTo();
        initialPositionPixels.setX(cannonEdgeXPixels);
        initialPositionPixels.setY(cannonEdgeYPixels);
          
        // Sets the maximum height of the quadratic curve using the xDisplacment and the height obtained from getHeightPixels()
        projectileMotion = new QuadCurveTo();
        projectileMotion.setControlX((cannonEdgeXPixels + xDisplacementPixels) / 2);
        projectileMotion.setControlY(cannonEdgeYPixels - (getHeightPixels(launchAngleDegrees, xDisplacementPixels)));
        
        // Sets the final point of the quadratic curve. 
        projectileMotion.setX(cannonEdgeXPixels + xDisplacementPixels);
        projectileMotion.setY(POSITION_FLOOR_PIXELS);
        
        // Adds elements to complete the path
        path.getElements().add(initialPositionPixels);
        path.getElements().add(projectileMotion);
        
        return path;
    }
    
    
    /**
     * Method that handles the playing of animation. Gets the path from setPath(),
     * sets the ball as node, and sets the animation time to the flight time. Interpolator
     * set to better represent projectile. 
     * 
     * @param ball Node for the animation
     * @param launchAngleDegrees gets launch angle to set animation path
     * @param gravityMetersPerSecondSquared gets gravitational acceleration for animation path
     * @param initialVelocityMetersPerSecond gets initial velocity for animation path
     */
    public void playAnimation(Circle ball, double launchAngleDegrees, double gravityMetersPerSecondSquared, double initialVelocityMetersPerSecond) {
        Path animationPath = setPath(launchAngleDegrees, gravityMetersPerSecondSquared, initialVelocityMetersPerSecond);
        pathTransition.setDuration(Duration.seconds(flightTimeSeconds));
        pathTransition.setPath(animationPath);
        pathTransition.setNode(ball);
        // Sets a custom interpolator
        pathTransition.setInterpolator(quadraticInterpolator);
        pathTransition.play();
    }
    
    /**
     * Custom interpolator provided by easings.net
     */
    Interpolator quadraticInterpolator = new Interpolator() {
        @Override
        protected double curve(double x) {
            // parabola with zeros at t=0 and t=1 and a maximum of 1 at t=0.5
            return 1 - (1 - x) * (1 - x);        }  //Creates method that dynamically scales the path based on slider
    };
    
    /**
     * Pauses the animation
     */
    public void pauseAnimation() {
        pathTransition.pause();
    }
    
    /**
     * Resets the animation by stopping the animation and resetting the position
     * of the ball back to its initial position. 
     * @param projectileBall ball to be brought back to original position
     */
    public void resetBall(Circle projectileBall) {
        pathTransition.stop();
        projectileBall.setTranslateX(INITIAL_BALL_POSITION_X_PIXELS);
        projectileBall.setTranslateY(INITIAL_BALL_POSITION_Y_PIXELS);
    }
    
    /**
     * Method that rotates the cannon based on the angle provided. 
     * Uses a RotateTransition to animate the rotation of the cannon. 
     * @param cannonBarrel image to be rotated
     * @param launchAngleDegrees angle to rotate to
     */
    public void rotateCannon(ImageView cannonBarrel, double launchAngleDegrees) {
        RotateTransition rotate = new RotateTransition();
        rotate.setToAngle(-launchAngleDegrees);
        rotate.setNode(cannonBarrel);
        rotate.play();
    }
    
    /**
     * Method that uses the setPath() method and draws an arc based on the path.
     * Adds this path to the pane and removes any existing ones. 
     * 
     * @param paneAnimation Layout container with all of the elements
     * @param launchAngleDegrees Launch angle to set path
     * @param gravityMetersPerSecondSquared Gravity to set path
     * @param initialVelocityMetersPerSecond Initial velocity to set path
     */
    public void drawTrail(Pane paneAnimation, double launchAngleDegrees, double gravityMetersPerSecondSquared, double initialVelocityMetersPerSecond) {
        Path trailPath = setPath(launchAngleDegrees, gravityMetersPerSecondSquared, initialVelocityMetersPerSecond);
        // removes any existing trails in Pane
        paneAnimation.getChildren().removeIf(trail -> trail instanceof Path);
        trailPath.setStroke(Color.BLACK);
        // Width of dash in pixels
        final double DASH_WIDTH = 10;
        // Gap between dashes in pixels
        final double GAP_WIDTH = 15;
        // Creates a dashed line
        trailPath.getStrokeDashArray().addAll(DASH_WIDTH, GAP_WIDTH);
        trailPath.setStrokeWidth(2);
        // Adds trail to Pane 
        paneAnimation.getChildren().add(trailPath);
    }
    
    
    /**
     * Method that takes the x-displacement and calculates the respective height
     * based on the launch angle. Uses trigonometry to calculate height. Height 
     * is essential to determine the maximum height of the parabola for the path. 
     * @param launchAngleDegrees launch angle to calculate height
     * @param xDisplacementPixels x-displacement to calculate height
     * @return controlY the max height of parabola
     */
    public double getHeightPixels(double launchAngleDegrees, double xDisplacementPixels) {
        double controlY = Math.tan(Math.toRadians(launchAngleDegrees)) * ((70 + xDisplacementPixels) / 2);
        return controlY;
    }
    
    /**
     * Shows a label telling user that the ball is out of bounds. Animation can be
     * reset or replayed by user.
     *
     * @param animationOffScreenLabel label passed in as parameter to set text
     */
    public void handleBallOutOfBounds(Label animationOffScreenLabel) {
        // Maximum width of pane 
        final double MAXIMUM_WIDTH = 1650;
        if (projectileMotion.getX() > MAXIMUM_WIDTH) {
            animationOffScreenLabel.setText("Ball is off Screen ->");
        }
    }
}
