package edu.vanier.physnics.stackedblock;

import javafx.animation.Interpolator;

/**
 *
 * @author adarax
 */
public class AccelerateInterpolator extends Interpolator {
    
    private final double factor;
    
    public AccelerateInterpolator(double factor)
    {
        this.factor = factor;
    }
    
    @Override
    /**
     * Calculates what the x position of the animated object should be as time
     * progresses using the kinematic motion equation x = 1/2 * a * t^2.
     * 
     * x is the distance from the initial position of the animated object
     * a is the acceleration
     * t is the time elapsed
     * 
     * @param time the time elapsed
     */
    protected double curve(double time)
    {
        return 0.5 * Math.abs(factor) * Math.pow(time, 2);
    }
}
