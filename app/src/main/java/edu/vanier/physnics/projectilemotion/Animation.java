/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

/**
 *
 * @author vires
 */
public class Animation {
    
    PathTransition pathTransition = new PathTransition();
    double xDisplacementM;
    double flightTimeS;

    public Path setPath(double launchAngleDeg, double gravityAccelMPSS, double initialVelocityMPS) {
        Path path = new Path();
        xDisplacementM = Equations.getXdisplacement(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS);
        flightTimeS = Equations.getFlightTime(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS);
        
        // Set the scaling from meters to pixels 
        // TODO: Scale the y-axis 
        double xDisplacementPX = xDisplacementM * 50;
        
        double cannonEdgeX = 135 + ((Math.cos(Math.toRadians(launchAngleDeg))) * 67.27);
        double cannonEdgeY = 790 - ((Math.sin(Math.toRadians(launchAngleDeg))) * 67.27);
        
        // Sets initial position of ball
        MoveTo moveTo = new MoveTo();
        moveTo.setX(cannonEdgeX);
        moveTo.setY(cannonEdgeY);
          
        QuadCurveTo quadTo = new QuadCurveTo();
        quadTo.setControlX((cannonEdgeX + xDisplacementPX) / 2);
        quadTo.setControlY(835 - (scaleHeightToPixels(launchAngleDeg, xDisplacementPX)));
        
        // Final point (final displacement)
        quadTo.setX(70 + xDisplacementPX);
        quadTo.setY(835);
        
        path.getElements().add(moveTo);
        path.getElements().add(quadTo);
        
        return path;
    }
    
    public void playAnimation(Circle ball, double launchAngleDeg, double gravityAccelMPSS, double initialVelocityMPS) {
        Path animationPath = setPath(launchAngleDeg, gravityAccelMPSS, initialVelocityMPS);
        pathTransition.setDuration(Duration.seconds(flightTimeS));
        pathTransition.setPath(animationPath);
        pathTransition.setNode(ball);
        pathTransition.play();
        
    }
    //Creates method that dynamically scales the path based on slider

    public void pauseAnimation() {
        pathTransition.pause();
    }
    
    public void resetBall(Circle projectileBall) {
        pathTransition.stop();
        projectileBall.setTranslateX(135);
        projectileBall.setTranslateY(790);
    }
    

    public void rotateCannon(ImageView cannonBarrel, double launchAngle) {
        RotateTransition rotate = new RotateTransition();
        rotate.setToAngle(-launchAngle);
        rotate.setNode(cannonBarrel);
        rotate.play();
    }
    
    public void drawTrail(Pane paneAnimation, double launchAngleDeg, double gravityAccelMPSS, double initialVelocityMPS) {
        Path trailPath = setPath(launchAngleDeg, gravityAccelMPSS, initialVelocityMPS);
        trailPath.setStroke(Color.RED);
        trailPath.setStrokeWidth(2);
        paneAnimation.getChildren().add(trailPath);
    }
    
    
    /**
     * Method that takes parameters for calculations in meters and converts them 
     * to pixels to be used in the animation.
     * @param launchAngleDeg
     * @param xDisplacementPixels
     * @return controlY
     */
    public double scaleHeightToPixels(double launchAngleDeg, double xDisplacementPixels) {
        double controlY = Math.tan(Math.toRadians(launchAngleDeg)) * ((70 + xDisplacementPixels) / 2);
        return controlY;
    }
    
    
    
    
}
