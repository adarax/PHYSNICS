/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import edu.vanier.physnics.conservation.Ball;
import edu.vanier.physnics.conservation.Settings;
import java.awt.Point;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author vires
 */
public class Animation {
    
    PathTransition pathTransition = new PathTransition();
    
    double maxHeightM;
    double xDisplacementM;
    double flightTimeS;
    
    public void playAnimation(Circle ball, ImageView cannon, QuadCurve quadCurve, double launchAngleDeg, double gravityAccelMPSS, double initialVelocityMPS) {
        
        Path path = new Path();
        
        maxHeightM = Equations.getMaxHeight(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS);
        xDisplacementM = Equations.getXdisplacement(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS);
        flightTimeS = Equations.getFlightTime(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS);
        
        // Set the scaling from meters to pixels 
        // TODO: Scale the y-axis 
        double xDisplacementPX = xDisplacementM * 50;
        
        double cannonEdgeX = 135 + ((Math.cos(Math.toRadians(launchAngleDeg))) * 67.27);
        double cannonEdgeY = 790 - ((Math.sin(Math.toRadians(launchAngleDeg))) * 67.27);
        
        MoveTo moveTo = new MoveTo();
        // Sets initial position of ball
        moveTo.setX(cannonEdgeX);
        moveTo.setY(cannonEdgeY);;
        
        QuadCurveTo quadTo = new QuadCurveTo();
        
        quadTo.setControlX((cannonEdgeX + xDisplacementPX) / 2);
        quadTo.setControlY(835 - (scaleHeightToPixels(launchAngleDeg, xDisplacementPX)));
        
        // Final point (final displacement)
        quadTo.setX(70 + xDisplacementPX);
        quadTo.setY(835);
        
        
        path.getElements().add(moveTo);
        path.getElements().add(quadTo);
        
        path.setVisible(true);
        path.setStrokeWidth(5);
        
        pathTransition.setDuration(Duration.seconds(flightTimeS));
        pathTransition.setPath(path);
        pathTransition.setNode(ball);
        pathTransition.play();
       
    }

    public void pauseAnimation() {
        pathTransition.pause();
    }
    
    public void resetBall(Circle projectileBall) {
        projectileBall.setCenterX(135);
        projectileBall.setCenterY(790);
    }
    

    public void rotateCannon(ImageView cannonBarrel, double launchAngle) {
        RotateTransition rotate = new RotateTransition();
        rotate.setToAngle(-launchAngle);
        rotate.setNode(cannonBarrel);
        rotate.play();
    }
    
    
    /**
     * Method that takes parameters for calculations in meters and converts them 
     * to pixels to be used in the animation.
     * @param launchAngleDeg
     * @param xDisplacementMeters 
     */
    public double scaleHeightToPixels(double launchAngleDeg, double xDisplacementPixels) {
        double controlY = Math.tan(Math.toRadians(launchAngleDeg)) * ((70 + xDisplacementPixels) / 2);
        return controlY;
    }
    
    
    
    
}
