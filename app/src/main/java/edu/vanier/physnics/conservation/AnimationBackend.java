/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author benja
 */
public class AnimationBackend {
    private Path ballPath;
    private ParallelTransition mainAnimation;
    private double cycleTime;
    
    private boolean playing;
    
    public AnimationBackend(){
        ballPath = new Path();
        playing = false;
        mainAnimation = new ParallelTransition();
    }
    
    public void createBallAnimation(Ball ball, double height, double g){
        
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
        
        mainAnimation.getChildren().add(ballCurve);
    }
    
    public void createGraphAnimation(Rectangle PE, Rectangle KE){
        ScaleTransition stKe = new ScaleTransition(Duration.seconds(cycleTime), KE);
        
        stKe.setFromY(0);
        stKe.setToY(1);
        
        stKe.setCycleCount(Timeline.INDEFINITE);
        stKe.setAutoReverse(true);
        stKe.setInterpolator(Interpolator.EASE_BOTH);
        
        mainAnimation.getChildren().add(stKe);
    }
    
    public void playBallAnimation(Ball ball, double height, double g){
        if(playing){
            mainAnimation.play();
        }
        else{
            createBallAnimation(ball,height,g);
            
            mainAnimation.play();
        }
        playing = true;
        
    }
    
    
    
    public void reset(){
        if(playing){
            playing = false;
            mainAnimation.stop();
            mainAnimation = null;
        }
        
       
    }
    
    public void pause(){
        if(playing){
            mainAnimation.pause();
        }
    }
}
