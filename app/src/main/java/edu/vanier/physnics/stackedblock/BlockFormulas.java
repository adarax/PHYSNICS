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
        double frictionVectorDirection = correspondingForceVector.getDirectionInDegrees();
        
        Vector resultant = new Vector(frictionVectorMagnitude, frictionVectorDirection);

        resultant.flipDirection();
        
        return resultant;
    }

    /**
     * Calculates the magnitude and direction of a resultant vector by adding
     * the components of all forces experienced by the block.
     *
     * @param forcesExperienced
     * @return The resultant vector of all forces from forcesExperienced.
     */
    public Vector calculateNetForceVector(ArrayList<Vector> forcesExperienced)
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

    public ArrayList<Vector> determineForcesExperienced(Block topBlock,
            Block bottomBlock,
            double forceOnM1,
            double forceOnM2,
            double angleOfForceOnM1,
            double angleOfForceOnM2,
            double frictionCoeffFloor,
            double frictionCoeffM1,
            int blockNumber)
    {
        ArrayList<Vector> allForcesExperienced = new ArrayList<>();

        double normalForceM2 = calculateNormalForceMagnitude(topBlock.getMass());
        Vector forceVectorOnM2 = new Vector(forceOnM2, angleOfForceOnM2);
        Vector frictionVectorOnM2 = calculateFrictionVector(frictionCoeffM1, normalForceM2, forceVectorOnM2);

        // For top block (M2)        
        if (blockNumber == 1)
        {
            allForcesExperienced.add(forceVectorOnM2);
            allForcesExperienced.add(frictionVectorOnM2);
        } // For bottom block (M1)
        else if (blockNumber == 0)
        {
            double normalForceM1 = calculateNormalForceMagnitude(topBlock.getMass(), bottomBlock.getMass());
            Vector forceVectorOnM1 = new Vector(forceOnM1, angleOfForceOnM1);
            Vector frictionVectorDueToFloor = calculateFrictionVector(frictionCoeffFloor, normalForceM1, forceVectorOnM1);
            frictionVectorOnM2.flipDirection();

            allForcesExperienced.add(frictionVectorDueToFloor);
            allForcesExperienced.add(forceVectorOnM1);
            allForcesExperienced.add(frictionVectorOnM2);
        }

        // TODO: turn into actual tests, ensure values are correct
//        System.out.println(calculateNetForceVector(allForcesExperienced).getMagnitudeInNewtons());
        return allForcesExperienced;
    }
    
}
