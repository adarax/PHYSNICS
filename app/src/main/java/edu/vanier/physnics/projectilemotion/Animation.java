/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import java.awt.Point;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author vires
 */
public class Animation {
    
    PathTransition pathTransition = new PathTransition();
    
    public void playAnimation(Circle ball, double launchAngle, double gravityAccelMPSS, double initialVelocityMPS) {
        
        Path path = new Path();
        
        double maxHeightM = Equations.getMaxHeight(launchAngle, initialVelocityMPS, gravityAccelMPSS);
        double xDisplacementM = Equations.getXdisplacement(launchAngle, initialVelocityMPS, gravityAccelMPSS);
        double flightTimeS = Equations.getFlightTime(launchAngle, initialVelocityMPS, gravityAccelMPSS);
        
        // Set the scaling from meters to pixels 
        // TODO: Scale the y-axis 
        double xDisplacementPX = xDisplacementM * 50;
        double maxHeightPX =  - maxHeightM;
        

        MoveTo moveTo = new MoveTo();
        // Sets initial position of ball
        moveTo.setX(maxHeightM);
        moveTo.setY(800);

        // Projectile motion can be reresented by a quadratic curve
        QuadCurveTo quadTo = new QuadCurveTo();
        
        
        
        //TODO: Add exception handling so that the animation doesn't exceed the pixel boundaries
        
        /**
         * Max right is 1560px
         * Max left is 0px
         * Ceiling is -760px
         * Floor is at 29px
         * 
         * Use a scale of 10 (1560px = 156 meters)
         * So grab the values and X10
         */
        
        
        // Sets a point on the arc (in this case the max height or middle of the arc)
        quadTo.setControlX((xDisplacementPX - 50) / 2);
        quadTo.setControlY(maxHeightPX);
        
        // Final point (final displacement)
        quadTo.setX(xDisplacementPX);
        quadTo.setY(800);
        path.getElements().add(moveTo);
        path.getElements().add(quadTo);

        pathTransition.setDuration(Duration.seconds(flightTimeS));
        pathTransition.setPath(path);
        pathTransition.setNode(ball);
        pathTransition.play();

    }

    public void pauseAnimation() {
        pathTransition.pause();
    }
    
    public void rotateCannon(ImageView cannonBarrel, double launchAngle) {
        RotateTransition rotate = new RotateTransition();
        rotate.setToAngle(-launchAngle);
        rotate.setNode(cannonBarrel);
        rotate.play();
    }
    
    
}
