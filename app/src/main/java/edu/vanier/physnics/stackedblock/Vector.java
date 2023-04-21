package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;

/**
 *
 * @author adarax
 */
public class Vector {

    private double magnitudeInNewtons;

    // Values range from 0 to 360 degrees
    private double directionInDegrees;
    
    // Setting the type of force is useful when animating the various Vectors
    private FORCE_TYPE forceType;

    public Vector(double magnitudeInNewtons, double directionInDegrees, FORCE_TYPE forceType)
    {
        this.magnitudeInNewtons = magnitudeInNewtons;
        this.directionInDegrees = directionInDegrees;
        this.forceType = forceType;
    }
    
    /**
     * Finds the quadrant of the Vector using the directionInDegrees
     * variable that gets instantiated in the constructor.
     * 
     * Quadrants are based on a 2D Cartesian plane, the angle is with respect
     * to the positive x-axis.
     * 
     * @return The quadrant of the Vector instance.
     */
    public int findQuadrant()
    {        
        if (directionInDegrees < 0 || directionInDegrees > 360)
        {
            throw new IllegalArgumentException("Invalid direction value");
        }

        if (directionInDegrees <= 90)
        {
            return 1;
        } else if (directionInDegrees <= 180)
        {
            return 2;
        } else if (directionInDegrees <= 270)
        {
            return 3;
        } else
        {
            return 4;
        }
    }

    public ArrayList<Double> toComponents()
    {
        ArrayList<Double> asComponents = new ArrayList<>();

        double xComponent = Math.cos(directionInDegrees) * magnitudeInNewtons;
        double yComponent = Math.sin(directionInDegrees) * magnitudeInNewtons;

        asComponents.add(xComponent);
        asComponents.add(yComponent);

        return asComponents;
    }
    
    /**
     * Changes the direction of the vector by 180 degrees, and ensures that
     * the vectors direction has not exceeded 360 degrees.
     */
    public void flipDirection()
    {
        double currentDirection = this.directionInDegrees;
        
        currentDirection += 180;
        
        if (currentDirection >= 360)
        {
            currentDirection -= 360;
        }
        
        this.directionInDegrees = currentDirection;
    }

    public double getMagnitudeInNewtons()
    {
        return magnitudeInNewtons;
    }

    public double getDirectionInDegrees()
    {
        return directionInDegrees;
    }
    
    public FORCE_TYPE getForceType()
    {
        return forceType;
    }
    
    public enum FORCE_TYPE
    {
        APPLIED,
        FRICTION,
        NORMAL
    }
}
