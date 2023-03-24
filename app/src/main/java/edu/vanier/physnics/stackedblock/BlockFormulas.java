package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;

/**
 *
 * @author adarax
 */
public class BlockFormulas {
    
    // Value in m/s/s
    private final double GRAVITATIONAL_ACCELERATION = 9.81;
    
    // Var-args treats the inputs as an array
    // @return Value in Newtons
    public Vector calculateNormalForceVector(double... contributingMassInKg)
    {
        double sumOfMasses = 0;
        
        for (double mass : contributingMassInKg)
        {
            sumOfMasses += mass;
        }
        
        double magnitudeOfForce = sumOfMasses * this.GRAVITATIONAL_ACCELERATION;
        
        // Normal force always points opposite to direction of gravity.
        // In the case of this simulation, gravity always points 270
        // degrees from the +x-axis since the floor is flat
        double directionOfForce = 90;
        
        return new Vector(magnitudeOfForce, directionOfForce);
    }
    
    
    public double calculateForceOfFriction(double coefficientOfFriction, double normalForce)
    {
        return coefficientOfFriction * normalForce;
    }
    
    
    /**
     * Calculates the magnitude and direction of a resultant vector by adding
     * the components of all forces experienced by the block.
     * 
     * @param forcesExperienced
     * @return The resultant vector of all forces from forcesExperienced.
     */
    public Vector calculateNetForceAndDirection(ArrayList<Vector> forcesExperienced)
    {
        double sumXComponents = 0, sumYComponents = 0;
        
        for (Vector forceVector : forcesExperienced)
        {
            sumXComponents += Math.cos(forceVector.getDirectionInDegrees()) * forceVector.getMagnitudeInNewtons();
            sumYComponents += Math.sin(forceVector.getDirectionInDegrees()) * forceVector.getMagnitudeInNewtons();
        }
        
        // Calculate magnitude and direction of resultant
        double magnitudeOfResultant = Math.sqrt(Math.pow(sumXComponents, 2) + Math.pow(sumYComponents, 2));
        double directionOfResultant = Math.atan(sumYComponents / sumXComponents);
        
        return new Vector(magnitudeOfResultant, directionOfResultant);
    }
}
