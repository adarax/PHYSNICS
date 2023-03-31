/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

/**
 *
 * @author vires
 */
public class ProjectileAnimation {
    
    public static void ballAnimation(Circle ball, double launchAngle, double gravityAccelMPSS, double initialVelocityMPS) {
        
        Path path = new Path();
        
        double maxHeightM = ProjectileEquations.getMaxHeight(launchAngle, initialVelocityMPS, gravityAccelMPSS);
        double xDisplacementM = ProjectileEquations.getXdisplacement(launchAngle, initialVelocityMPS, gravityAccelMPSS);
        double flightTimeS = ProjectileEquations.getFlightTime(launchAngle, initialVelocityMPS, gravityAccelMPSS);
        
        double maxHeightPX = maxHeightM * -10;
        double xDisplacementPX = xDisplacementM * 10;
        

        MoveTo moveTo = new MoveTo();
        // Sets initial position of ball
        moveTo.setX(50);
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
        quadTo.setControlX(xDisplacementPX / 2);
        quadTo.setControlY(maxHeightPX);
        
        //Final point (final displacement)
        quadTo.setX(xDisplacementPX);
        quadTo.setY(800);
        path.getElements().add(moveTo);
        path.getElements().add(quadTo);
        
        PathTransition pathTransition = new PathTransition();
        
        pathTransition.setDuration(Duration.seconds(flightTimeS));
        pathTransition.setPath(path);
        pathTransition.setNode(ball);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
    }
}
