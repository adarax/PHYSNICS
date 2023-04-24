package edu.vanier.physnics.stackedblock;

import edu.vanier.physnics.stackedblock.BlockFrontEndController.POSITION;
import edu.vanier.physnics.stackedblock.Vector.FORCE_TYPE;
import java.util.ArrayList;
import java.util.List;

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

    
    /**
     * Calculates the magnitude and direction of the friction vector.
     * 
     * The friction vector is always parallel to the surface. Therefore,
     * the vector's direction can only be 0 or 180 degrees.
     * 
     * The friction vector is always less than the x component of the corresponding
     * force vector.
     * 
     * @param coefficientOfFriction
     * @param normalForceMagnitude
     * @param correspondingForceVector
     * @return The friction vector with the correct magnitude and direction based on
     *         the coefficient of friction, the normal force and corresponding force vector.
     */
    public Vector calculateFrictionVector(double coefficientOfFriction, double normalForceMagnitude, Vector correspondingForceVector)
    {
        double frictionVectorMagnitude = coefficientOfFriction * normalForceMagnitude;
        double correspondingForceVectorXComponent = correspondingForceVector.toComponents().get(0);
        
        if (frictionVectorMagnitude > correspondingForceVectorXComponent)
        {
            frictionVectorMagnitude = correspondingForceVectorXComponent;
        }
        
        double frictionVectorDirection = 180;
        double correspondingVectorDirection = correspondingForceVector.getDirectionInDegrees();
        
        if (correspondingVectorDirection > 90 && correspondingVectorDirection < 270)
        {
            frictionVectorDirection = 0;
        }
        
        return new Vector(frictionVectorMagnitude, frictionVectorDirection, FORCE_TYPE.FRICTION);
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

        return new Vector(magnitudeOfResultant, directionOfResultant, FORCE_TYPE.NORMAL);
    }

    public ArrayList<Vector> determineForcesExperienced(Block topBlock,
            Block bottomBlock,
            double forceOnBottomBlock,
            double forceOnTopBlock,
            double angleOfForceOnBottomBlock,
            double angleOfForceOnTopBlock,
            double frictionCoeffFloor,
            double frictionCoeffBottomBlock,
            POSITION blockId)
    {
        ArrayList<Vector> allForcesExperienced = new ArrayList<>();

        double normalForceTopBlock = calculateNormalForceMagnitude(topBlock.getMass());
        Vector forceVectorOnTopBlock = new Vector(forceOnTopBlock, angleOfForceOnTopBlock, FORCE_TYPE.APPLIED);
        Vector frictionVectorOnTopBlock = calculateFrictionVector(frictionCoeffBottomBlock, normalForceTopBlock, forceVectorOnTopBlock);
      
        // TODO: Create normal for vectors for each block.
        
        if (blockId == POSITION.TOP)
        {
            allForcesExperienced.addAll(List.of(forceVectorOnTopBlock, frictionVectorOnTopBlock));
        }
        else if (blockId == POSITION.BOTTOM)
        {
            double normalForceOnBottomBlock = calculateNormalForceMagnitude(topBlock.getMass(), bottomBlock.getMass());
            Vector forceVectorOnBottomBlock = new Vector(forceOnBottomBlock, angleOfForceOnBottomBlock, FORCE_TYPE.APPLIED);
            Vector frictionVectorDueToFloor = calculateFrictionVector(frictionCoeffFloor, normalForceOnBottomBlock, forceVectorOnBottomBlock);
            frictionVectorOnTopBlock.flipDirection();

            allForcesExperienced.addAll(List.of(frictionVectorDueToFloor, forceVectorOnBottomBlock, frictionVectorOnTopBlock));
        }

        return allForcesExperienced;
    }
    
}
