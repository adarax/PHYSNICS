package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;

/**
 *
 * @author adarax
 */
public class Vector {

    private final double magnitudeInNewtons;

    // Values range from 0 to 360 degrees
    private double directionInDegrees;

    public Vector(double magnitudeInNewtons, double directionInDegrees)
    {
        this.magnitudeInNewtons = magnitudeInNewtons;
        this.directionInDegrees = directionInDegrees;
    }
    
    // This method may be useful for drawing the vectors onto the screen
    public int findQuadrant(double directionInDegrees)
    {
        // Handle any illegal direction values
        if (directionInDegrees < 0 || directionInDegrees > 360)
        {
            return 0;
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

    public ArrayList<Double> vectorToComponents(double magnitudeInNewtons, double directionInDegrees)
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
}
