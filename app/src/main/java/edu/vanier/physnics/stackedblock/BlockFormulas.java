package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;

/**
 *
 * @author adarax
 */
public class BlockFormulas {

    // Value in m/s^2
    private final double GRAVITATIONAL_ACCELERATION = 9.81;

    /**
     * Calculates the total normal force on a block.
     *
     * @param contributingMassInKg
     * @return Magnitude of the total normal force on the block.
     */
    public double calculateNormalForceMagnitude(double... contributingMassInKg)
    {
        double sumOfMasses = 0;

        for (double mass : contributingMassInKg)
        {
            sumOfMasses += mass;
        }

        double magnitudeOfForce = sumOfMasses * GRAVITATIONAL_ACCELERATION;

        return magnitudeOfForce;
    }

    public Vector calculateFrictionVector(double coefficientOfFriction, double normalForceVector, Vector correspondingForceVector)
    {
        double frictionVectorMagnitude = coefficientOfFriction * normalForceVector;
        double frictionVectorDirection = correspondingForceVector.getDirectionInDegrees() + 180;

        if (frictionVectorDirection >= 360)
        {
            frictionVectorDirection -= 360;
        }

        return new Vector(frictionVectorMagnitude, frictionVectorDirection);
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

        double magnitudeOfResultant = Math.sqrt(Math.pow(sumXComponents, 2) + Math.pow(sumYComponents, 2));
        double directionOfResultant = Math.atan(sumYComponents / sumXComponents);

        return new Vector(magnitudeOfResultant, directionOfResultant);
    }
}
