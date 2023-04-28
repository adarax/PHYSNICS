/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import edu.vanier.physnics.conservation.graphs.GraphSettings;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
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
    
    private double currentCycle;
    private double currentTime;
    
    private boolean playing;
    
    public AnimationBackend(){
        ballPath = new Path();
        playing = false;
        mainAnimation = new ParallelTransition();
        currentTime = 0;
        currentCycle = 0;
    }
    
    public void createBallAnimation(Ball ball, double height, double g){
        
        cycleTime = ConservationFormulas.getArcTime(height, g);
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
    
    public void createGraphAnimation(Rectangle KE, Rectangle PE){
        ScaleTransition stKe = new ScaleTransition(Duration.seconds(cycleTime/2), KE);
        stKe.setNode(KE);
        
        stKe.setFromY(0);
        stKe.setToY(1);
        
        stKe.setCycleCount(Timeline.INDEFINITE);
        stKe.setAutoReverse(true);
        stKe.setInterpolator(Interpolator.EASE_BOTH);
        
        TranslateTransition kineticEnergyGraphTranslation = 
                new TranslateTransition(Duration.seconds(cycleTime/2), KE);
        kineticEnergyGraphTranslation.setFromY(GraphSettings.KEGraphPositionY);
        kineticEnergyGraphTranslation.setToY(GraphSettings.KEGraphPositionY - 100);
        kineticEnergyGraphTranslation.setCycleCount(Timeline.INDEFINITE);
        kineticEnergyGraphTranslation.setAutoReverse(true);
        kineticEnergyGraphTranslation.setInterpolator(Interpolator.EASE_BOTH);
        
        ScaleTransition stPe = new ScaleTransition(Duration.seconds(cycleTime/2), PE);
        stPe.setNode(PE);
        
        stPe.setFromY(1);
        stPe.setToY(0);
       
        stPe.setCycleCount(Timeline.INDEFINITE);
        stPe.setAutoReverse(true);
        stPe.setInterpolator(Interpolator.EASE_BOTH);
        
         TranslateTransition potentialEnergyGraphTranslation = 
                new TranslateTransition(Duration.seconds(cycleTime/2), PE);
        potentialEnergyGraphTranslation.setToY(GraphSettings.PEGraphPositionY);
        potentialEnergyGraphTranslation.setFromY(GraphSettings.PEGraphPositionY - 100);
        potentialEnergyGraphTranslation.setCycleCount(Timeline.INDEFINITE);
        potentialEnergyGraphTranslation.setAutoReverse(true);
        potentialEnergyGraphTranslation.setInterpolator(Interpolator.EASE_BOTH);
        
        mainAnimation.getChildren().addAll(stKe, kineticEnergyGraphTranslation, stPe, potentialEnergyGraphTranslation);
    }
    
    public void playBallAnimation(Ball ball, double height, double g, Rectangle KE, Rectangle PE){
        if(playing){
            mainAnimation.play();
        }
        else{
            createBallAnimation(ball,height,g);
            createGraphAnimation(KE, PE);
            mainAnimation.play();
        }
        playing = true;
        
    }
    
    
    
    public void reset(){       
        playing = false;
        mainAnimation.stop();
    }
    
    public void pause(){
        if(playing){
            mainAnimation.pause();
        }
    }
    
    public double getCurrentTime(){
        Duration time = mainAnimation.getCurrentTime();
        
        if(cycleTime< time.toSeconds() - cycleTime*currentCycle){
            currentCycle++;
        }
        currentTime = time.toSeconds()-cycleTime*currentCycle;
        
       
        System.out.println("Current time: " + currentTime);
        System.out.println("CurrentCycle: " + currentCycle);
        
       
       return currentTime;
        
    }
}
