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
    
    public void createGraphAnimation(Rectangle KE, Rectangle PE, Rectangle FE, boolean friction){
        ScaleTransition stKe = new ScaleTransition(Duration.seconds(cycleTime/2), KE);
        stKe.setNode(KE);
        
        stKe.setFromY(0);
        stKe.setToY(1);
        
        stKe.setCycleCount(Timeline.INDEFINITE);
        stKe.setAutoReverse(true);
        stKe.setInterpolator(Interpolator.EASE_BOTH);
        
        TranslateTransition kineticEnergyGraphTranslation = 
                new TranslateTransition(Duration.seconds(cycleTime/2), KE);
        kineticEnergyGraphTranslation.setFromY(GraphSettings.GRAPHS_POSITION_Y);
        kineticEnergyGraphTranslation.setToY(GraphSettings.GRAPHS_POSITION_Y - (GraphSettings.MAX_GRAPH_HEIGHT/2));
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
        potentialEnergyGraphTranslation.setToY(GraphSettings.GRAPHS_POSITION_Y);
        potentialEnergyGraphTranslation.setFromY(GraphSettings.GRAPHS_POSITION_Y - (GraphSettings.MAX_GRAPH_HEIGHT/2));
        potentialEnergyGraphTranslation.setCycleCount(Timeline.INDEFINITE);
        potentialEnergyGraphTranslation.setAutoReverse(true);
        potentialEnergyGraphTranslation.setInterpolator(Interpolator.EASE_BOTH);
        
        ScaleTransition stFe = new ScaleTransition(Duration.seconds(cycleTime/2), PE);
        stFe.setNode(FE);
        
       TranslateTransition frictionEnergyGraphTranslation = null;
        
        if(friction){
            
        }
        else{
            stFe.setFromY(0);
            stFe.setToY(0);
       
            stFe.setCycleCount(Timeline.INDEFINITE);
            stFe.setAutoReverse(true);
            stFe.setInterpolator(Interpolator.EASE_BOTH);
        
            frictionEnergyGraphTranslation = 
                new TranslateTransition(Duration.seconds(0), PE);
            frictionEnergyGraphTranslation.setToY(GraphSettings.GRAPHS_POSITION_Y);
            //frictionEnergyGraphTranslation.setFromY(GraphSettings.GRAPHS_POSITION_Y - (GraphSettings.MAX_GRAPH_HEIGHT/2));
            frictionEnergyGraphTranslation.setCycleCount(Timeline.INDEFINITE);
            frictionEnergyGraphTranslation.setAutoReverse(true);
            frictionEnergyGraphTranslation.setInterpolator(Interpolator.EASE_BOTH);
        }
        
        
        mainAnimation.getChildren().addAll(stKe, kineticEnergyGraphTranslation, stPe, potentialEnergyGraphTranslation, stFe, frictionEnergyGraphTranslation);
    }
    
    public void playBallAnimation(Ball ball, double height, double g, Rectangle KE, Rectangle PE, Rectangle FE, boolean friction){
        if(playing){
            mainAnimation.play();
        }
        else{
            createBallAnimation(ball,height,g);
            createGraphAnimation(KE, PE, FE, friction);
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

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
    
    
}
