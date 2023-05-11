/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import edu.vanier.physnics.conservation.graphs.GraphSettings;
import java.util.ArrayList;
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

    private boolean playing;

    public AnimationBackend() {
        ballPath = new Path();
        playing = false;
        mainAnimation = new ParallelTransition();
    }

    public void createBallAnimation(Ball ball, double height, double g) {

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
    
    public void createFrictionBallAnimation(Ball ball, double height, double g, double TME, double FE){
        FE = FE/2;
        cycleTime = ConservationFormulas.getArcTime(height, g);
        ArrayList<Double> stopHeights = new ArrayList<Double>();
        stopHeights.add(0, height);
        for(double i = TME; i >= 0; i=-FE){
            TME = TME - FE;
            double newHeight = ConservationFormulas.getHeightFromPotentialEnergy(g, ball.getMass(), TME);
            stopHeights.add(newHeight);
            
        }
        
        for(double d : stopHeights){
            System.out.println(d);
        }
        
        
    }
        

    public void createGraphAnimation(Rectangle KE, Rectangle PE) {
        ScaleTransition stKe = new ScaleTransition(Duration.seconds(cycleTime / 2), KE);
        stKe.setNode(KE);

        stKe.setFromY(0);
        stKe.setToY(1);

        stKe.setCycleCount(Timeline.INDEFINITE);
        stKe.setAutoReverse(true);
        stKe.setInterpolator(Interpolator.EASE_BOTH);

        TranslateTransition kineticEnergyGraphTranslation
                = new TranslateTransition(Duration.seconds(cycleTime / 2), KE);
        kineticEnergyGraphTranslation.setFromY(GraphSettings.GRAPHS_POSITION_Y);
        kineticEnergyGraphTranslation.setToY(GraphSettings.GRAPHS_POSITION_Y - (GraphSettings.MAX_GRAPH_HEIGHT / 2));
        kineticEnergyGraphTranslation.setCycleCount(Timeline.INDEFINITE);
        kineticEnergyGraphTranslation.setAutoReverse(true);
        kineticEnergyGraphTranslation.setInterpolator(Interpolator.EASE_BOTH);

        ScaleTransition stPe = new ScaleTransition(Duration.seconds(cycleTime / 2), PE);
        stPe.setNode(PE);

        stPe.setFromY(1);
        stPe.setToY(0);

        stPe.setCycleCount(Timeline.INDEFINITE);
        stPe.setAutoReverse(true);
        stPe.setInterpolator(Interpolator.EASE_BOTH);

        TranslateTransition potentialEnergyGraphTranslation
                = new TranslateTransition(Duration.seconds(cycleTime / 2), PE);
        potentialEnergyGraphTranslation.setToY(GraphSettings.GRAPHS_POSITION_Y);
        potentialEnergyGraphTranslation.setFromY(GraphSettings.GRAPHS_POSITION_Y - (GraphSettings.MAX_GRAPH_HEIGHT / 2));
        potentialEnergyGraphTranslation.setCycleCount(Timeline.INDEFINITE);
        potentialEnergyGraphTranslation.setAutoReverse(true);
        potentialEnergyGraphTranslation.setInterpolator(Interpolator.EASE_BOTH);

        mainAnimation.getChildren().addAll(stKe, kineticEnergyGraphTranslation, stPe, potentialEnergyGraphTranslation);
    }

    public void createFrictionGraphAnimation(double frictionOverOneCyle, double TME, Rectangle FE) {
        double timeToOvertakeTME = (cycleTime)*(TME/frictionOverOneCyle);
        System.out.println(timeToOvertakeTME);
        ScaleTransition stFe = new ScaleTransition(Duration.seconds(timeToOvertakeTME));
        stFe.setNode(FE);

        stFe.setToY(1);
        stFe.setFromY(0);

        stFe.setCycleCount(1);
        stFe.setAutoReverse(false);
        stFe.setInterpolator(Interpolator.EASE_BOTH);

       TranslateTransition frictionEnergyGraphTranslation
                = new TranslateTransition(Duration.seconds(timeToOvertakeTME), FE);
        frictionEnergyGraphTranslation.setFromY(GraphSettings.GRAPHS_POSITION_Y);
        frictionEnergyGraphTranslation.setToY(GraphSettings.GRAPHS_POSITION_Y - (GraphSettings.MAX_GRAPH_HEIGHT/2));
        frictionEnergyGraphTranslation.setCycleCount(1);
        frictionEnergyGraphTranslation.setAutoReverse(false);
        frictionEnergyGraphTranslation.setInterpolator(Interpolator.EASE_BOTH);
        
        stFe.setOnFinished((eventHandler) -> {
            mainAnimation.stop();
        });
        
        mainAnimation.getChildren().addAll(stFe, frictionEnergyGraphTranslation);
    }


public void playBallAnimation(Ball ball, double height, double g, Rectangle KE, Rectangle PE, Rectangle FE, boolean friction, double TME, double frictionOverOneCycle){
        if(playing){
            mainAnimation.play();
        }
        else{
            
            if(friction){
                createFrictionGraphAnimation(frictionOverOneCycle, TME, FE);
                createFrictionBallAnimation(ball, height, g, TME, frictionOverOneCycle);
            }
            else{
                createBallAnimation(ball,height,g);
                createGraphAnimation(KE, PE);
            }
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
        return time.toSeconds();  
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public Path getBallPath() {
        return ballPath;
    }

    public void setBallPath(Path ballPath) {
        this.ballPath = ballPath;
    }

    public ParallelTransition getMainAnimation() {
        return mainAnimation;
    }

    public void setMainAnimation(ParallelTransition mainAnimation) {
        this.mainAnimation = mainAnimation;
    }

    public double getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(double cycleTime) {
        this.cycleTime = cycleTime;
    }
    
    
    
}
