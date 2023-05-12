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
import javafx.animation.SequentialTransition;
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
    
    public void createFrictionBallAnimation(Ball ball, Ramp ramp, double height, double g, double u){
       SequentialTransition ballTransition = new SequentialTransition();
       double x = ramp.getInitialBallPositionX();
       double y = ramp.getInitialBallPositionY();
       double arcRadius = ramp.getRadius()-ball.getRadius();
       double initialTME = ConservationFormulas.potentialEnergy(ball.getMass(), g, height);
       double time;
       double TME = initialTME;
       
       while(TME > 0){
        //drop
        Path dropPath = new Path();
        MoveTo initialBallPos = new MoveTo();
        initialBallPos.setX(x);
        initialBallPos.setY(y);
        dropPath.getElements().add(initialBallPos);
        
        //x = ;
        //y = y+ramp.getRadius()+ball.getRadius()-ramp.getThickness();
        
        ArcTo ballArc = new ArcTo();
        ballArc.setX((x+ramp.getRadius()-ball.getRadius()));
        ballArc.setY(y+ramp.getRadius()+ball.getRadius()-ramp.getThickness());
        ballArc.setRadiusX(arcRadius);
        ballArc.setRadiusY(arcRadius);
        
        dropPath.getElements().add(ballArc);
        ball.setBallPath(dropPath);
        
        PathTransition firstDrop = new PathTransition();
        
        time = ConservationFormulas.getArcTime(height, g)/2;
        
        firstDrop.setDuration(Duration.seconds(time));
        firstDrop.setNode(ball);
        firstDrop.setPath(dropPath);
        firstDrop.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

        firstDrop.setCycleCount(1);
        firstDrop.setAutoReverse(false);
        firstDrop.setInterpolator(Interpolator.EASE_BOTH);

        mainAnimation.getChildren().add(firstDrop);
        TME -= ConservationFormulas.getFrictionEnergyOverCircleSection(height, ball.getMass(), g, u, 0, 90);
        
        System.out.println(TME);
        //climb
        
        Path climbPath = new Path();
        MoveTo start = new MoveTo();
        start.setX(x+ramp.getRadius()-ball.getRadius());
        start.setY(y+ramp.getRadius()+ball.getRadius()-ramp.getThickness());
        climbPath.getElements().add(start);
        
        //x = ;
        //y = y+ramp.getRadius()+ball.getRadius()-ramp.getThickness();
        
        ArcTo climbArc = new ArcTo();
        climbArc.setX((x+ramp.getRadius()-ball.getRadius()));
        climbArc.setY(y);
        climbArc.setRadiusX(arcRadius);
        climbArc.setRadiusY(arcRadius);
        
        climbPath.getElements().add(climbArc);
        ball.setBallPath(climbPath);
        
        PathTransition climb = new PathTransition();
        
        time = ConservationFormulas.getArcTime(height, g)/2;
        
        climb.setDuration(Duration.seconds(time));
        climb.setNode(ball);
        climb.setPath(climbPath);
        climb.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

        climb.setCycleCount(1);
        climb.setAutoReverse(false);
        climb.setInterpolator(Interpolator.EASE_BOTH);

        mainAnimation.getChildren().add(climb);
        TME -= ConservationFormulas.getFrictionEnergyOverCircleSection(height, ball.getMass(), g, u, 0, 90);
        
        System.out.println(TME);
        
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


public void playBallAnimation(Ball ball, Ramp ramp, double height, double g, Rectangle KE, Rectangle PE, Rectangle FE, boolean friction){
        if(playing){
            mainAnimation.play();
        }
        else{
            
            if(friction){
                //createFrictionGraphAnimation(frictionOverOneCycle, TME, FE);
                createFrictionBallAnimation(ball, ramp, height, g, 0.6);
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
