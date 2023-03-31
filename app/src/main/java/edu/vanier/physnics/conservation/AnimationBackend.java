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
    private double cycleTime;
    
    public AnimationBackend(){
        ballPath = new Path();
       
    }
    
    public void createAnimation(Ball ball, double height, double g){
        
        cycleTime = ConservationFormulas.getArcTime(height, g);
        System.out.println(ConservationFormulas.getArcTime(height, g));
        PathTransition ballCurve = new PathTransition();
        ballPath = ball.getBallPath();
                
        ballCurve.setDuration(Duration.seconds(cycleTime));
        ballCurve.setNode(ball);
        ballCurve.setPath(ballPath);
        ballCurve.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        
        ballCurve.setCycleCount(Timeline.INDEFINITE);
        ballCurve.setAutoReverse(true);
        ballCurve.setInterpolator(Interpolator.EASE_BOTH);
        
        mainAnimation = ballCurve;
    }
    
    public void play(){
        mainAnimation.play();
    }
    
    public void reset(){
        mainAnimation.stop();
        mainAnimation.jumpTo(Duration.ZERO);
    }
    
    public void pause(){
        mainAnimation.pause();
    }
}
