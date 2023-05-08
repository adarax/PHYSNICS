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
    protected double curve(double time)
    {
        // 0.5 * a * t^2 = x when initial velocity is 0
        return 0.5 * factor * Math.pow(time, 2);
    }
}
