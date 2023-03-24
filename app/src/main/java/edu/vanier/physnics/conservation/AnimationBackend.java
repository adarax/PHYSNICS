/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 *
 * @author benja
 */
public class AnimationBackend {
    private Path ballPath;
    private Transition mainAnimation;
    
    public AnimationBackend(){
        ballPath = new Path();
    }
    
    public void createAnimation(Ball ball){
        PathTransition ballCurve = new PathTransition();
        ballPath = ball.getBallPath();
                
        ballCurve.setDuration(Duration.millis(4000));
        ballCurve.setNode(ball);
        ballCurve.setPath(ballPath);
        ballCurve.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        ballCurve.setCycleCount(1);
        ballCurve.setAutoReverse(true);
        ballCurve.setInterpolator(Interpolator.EASE_BOTH);
        
        mainAnimation = ballCurve;
    }
    
    public void play(){
        mainAnimation.play();
    }
}
