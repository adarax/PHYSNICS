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
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Creates and manipulates all animations for the conservation of energy
 * simulation. Works in tandem with the conservation controller to create and
 * run the corresponding animations.
 *
 * @author Benjamin Pratt
 */
public class AnimationBackend {

    private ParallelTransition mainAnimation;

    private double timeToCompleteOneCycleSeconds;

    private boolean playing;

    /**
     * Constructor for the animation backend object Initializes the
     * mainAnimation and sets the playing boolean to be false by default, as the
     * animation has not yet been started.
     */
    public AnimationBackend() {
        playing = false;
        mainAnimation = new ParallelTransition();

    }

    /**
     * Creates the basic ball animation for a simulation without any friction
     * involved. Calculates the time to complete one cycle from the starting
     * height and the gravitational acceleration.
     *
     * @param ball ball to animate
     * @param height initial height of the ball
     * @param gravitationalAcceleration gravitational acceleration
     */
    public void createBallAnimation(Ball ball, double height, double gravitationalAcceleration) {

        timeToCompleteOneCycleSeconds = ConservationFormulas.getArcTime(height, gravitationalAcceleration);
        PathTransition ballCurve = new PathTransition();

        ballCurve.setDuration(Duration.seconds(timeToCompleteOneCycleSeconds));
        ballCurve.setNode(ball);
        ballCurve.setPath(ball.getBallPath());
        ballCurve.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

        ballCurve.setCycleCount(Timeline.INDEFINITE);
        ballCurve.setAutoReverse(true);
        ballCurve.setInterpolator(Interpolator.EASE_BOTH);

        mainAnimation.getChildren().add(ballCurve);
    }

    /**
     * Creates the graph animation for the basic simulation without friction.
     * Divides the total time to complete one cycle by 2, as the graphs will
     * reverse during the second half of a cycle.
     *
     * @param kineticEnergyRectangle kinetic energy rectangle graph 
     * @param potentialEnergyRectangle potential energy rectangle graph
     */
    public void createGraphAnimation(Rectangle kineticEnergyRectangle, Rectangle potentialEnergyRectangle) {
        Duration graphAnimationDuration = Duration.seconds(timeToCompleteOneCycleSeconds / 2);
        ScaleTransition scaleTransitionKineticEnergy = new ScaleTransition(graphAnimationDuration, kineticEnergyRectangle);
        scaleTransitionKineticEnergy.setNode(kineticEnergyRectangle);

        scaleTransitionKineticEnergy.setFromY(0);
        scaleTransitionKineticEnergy.setToY(1);

        scaleTransitionKineticEnergy.setCycleCount(Timeline.INDEFINITE);
        scaleTransitionKineticEnergy.setAutoReverse(true);
        scaleTransitionKineticEnergy.setInterpolator(Interpolator.EASE_BOTH);

        TranslateTransition kineticEnergyGraphTranslation
                = new TranslateTransition(Duration.seconds(timeToCompleteOneCycleSeconds / 2), kineticEnergyRectangle);
        kineticEnergyGraphTranslation.setFromY(GraphSettings.GRAPHS_POSITION_Y);
        kineticEnergyGraphTranslation.setToY(GraphSettings.GRAPHS_POSITION_Y - (GraphSettings.MAX_GRAPH_HEIGHT / 2));
        kineticEnergyGraphTranslation.setCycleCount(Timeline.INDEFINITE);
        kineticEnergyGraphTranslation.setAutoReverse(true);
        kineticEnergyGraphTranslation.setInterpolator(Interpolator.EASE_BOTH);

        ScaleTransition scaleTransitionPotentialEnergy = new ScaleTransition(graphAnimationDuration, potentialEnergyRectangle);
        scaleTransitionPotentialEnergy.setNode(potentialEnergyRectangle);

        scaleTransitionPotentialEnergy.setFromY(1);
        scaleTransitionPotentialEnergy.setToY(0);

        scaleTransitionPotentialEnergy.setCycleCount(Timeline.INDEFINITE);
        scaleTransitionPotentialEnergy.setAutoReverse(true);
        scaleTransitionPotentialEnergy.setInterpolator(Interpolator.EASE_BOTH);

        TranslateTransition potentialEnergyGraphTranslation
                = new TranslateTransition(Duration.seconds(timeToCompleteOneCycleSeconds / 2), potentialEnergyRectangle);
        potentialEnergyGraphTranslation.setToY(GraphSettings.GRAPHS_POSITION_Y);
        potentialEnergyGraphTranslation.setFromY(GraphSettings.GRAPHS_POSITION_Y - (GraphSettings.MAX_GRAPH_HEIGHT / 2));
        potentialEnergyGraphTranslation.setCycleCount(Timeline.INDEFINITE);
        potentialEnergyGraphTranslation.setAutoReverse(true);
        potentialEnergyGraphTranslation.setInterpolator(Interpolator.EASE_BOTH);

        mainAnimation.getChildren().addAll(scaleTransitionKineticEnergy, kineticEnergyGraphTranslation, scaleTransitionPotentialEnergy, potentialEnergyGraphTranslation);
    }

    /**
     * Creates and then plays the animation of the ball without friction
     * involved.If the simulation is paused, it will resume it.
     *
     * @param ball animated ball
     * @param ramp ramp where the ball will roll on
     * @param height initial height of the ball
     * @param gravitationalAcceleration applied gravitational acceleration
     * @param kineticEnergyRectangle rectangle of the kinetic energy graph
     * @param potentialEnergyRectangle rectangle of the potential energy graph
     */
    public void playBallAnimation(Ball ball, Ramp ramp, double height,
            double gravitationalAcceleration, Rectangle kineticEnergyRectangle, Rectangle potentialEnergyRectangle) {
        if (playing) {
            mainAnimation.play();
        } else {
            createBallAnimation(ball, height, gravitationalAcceleration);
            createGraphAnimation(kineticEnergyRectangle, potentialEnergyRectangle);

            mainAnimation.play();

            playing = true;
        }

    }
   
    /**
     * Returns the time that the animation has been running for in seconds
     * @return current time of the animation
     */
    public double getCurrentTime() {
        return (mainAnimation.getCurrentTime().toSeconds());
    }

    /**
     * Resets the main animation to its default state.
     */
    public void reset() {
        playing = false;
        mainAnimation.stop();
    }

    /**
     * Pause the animation
     */
    public void pause() {
        if (playing) {
            mainAnimation.pause();
        }
    }

    /**
     * Getter for playing
     * @return playing boolean
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Setter for playing
     * @param playing new boolean value for playing
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    /**
     * Getter for mainAnimation
     * @return main animation
     */
    public ParallelTransition getMainAnimation() {
        return mainAnimation;
    }

    /**
     * setter for mainAnimation
     * @param mainAnimation new mainAnimation
     */
    public void setMainAnimation(ParallelTransition mainAnimation) {
        this.mainAnimation = mainAnimation;
    }

    /**
     * Getter for timeToCompleteOneCycleSeconds
     *
     * @return returns the time to complete one cycle
     */
    public double getTimeToCompleteOneCycleSeconds() {
        return timeToCompleteOneCycleSeconds;
    }

    /**
     * setter for timeToCompleteOneCycleSeconds
     *
     * @param timeToCompleteOneCycleSeconds sets the time to complete one animation cycle
     */
    public void setTimeToCompleteOneCycleSeconds(double timeToCompleteOneCycleSeconds) {
        this.timeToCompleteOneCycleSeconds = timeToCompleteOneCycleSeconds;
    }

}
