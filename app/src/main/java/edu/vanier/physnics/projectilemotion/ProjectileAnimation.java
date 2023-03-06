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
    
    public static void ballAnimation(Circle ball) {
        
        Path path = new Path();

        MoveTo moveTo = new MoveTo();
        moveTo.setX(50);
        moveTo.setY(800);

        QuadCurveTo quadTo = new QuadCurveTo();
        quadTo.setControlX(725);
        quadTo.setControlY(300);
        quadTo.setX(1500);
        quadTo.setY(800);
        path.getElements().add(moveTo);
        path.getElements().add(quadTo);
        
        PathTransition pathTransition = new PathTransition();
        
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(ball);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
    }
}
