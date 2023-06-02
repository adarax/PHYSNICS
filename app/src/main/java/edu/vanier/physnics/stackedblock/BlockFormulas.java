package edu.vanier.physnics.stackedblock;

import edu.vanier.physnics.stackedblock.BlockFrontEndController.POSITION;
import edu.vanier.physnics.stackedblock.Vector.FORCE_TYPE;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that offers helper functions to calculate various Vectors, forces,
 * and angles required for the stacked block simulation.
 * 
 * @author adarax
 */
public class BlockFormulas {

    // Value in m/s^2
    protected static final double GRAVITATIONAL_ACCELERATION = 9.81;

    /**
     * Calculates the total normal force on a block.
     *
     * @param contributingMassInKg mass of Block plus any masses on top of it /// Type out kilograms
     * @param opposingForces other vertical forces that may change the net normal force /// Missing units
     * @return magnitude of the total normal force on the block in Newtons
     */
    protected double calculateNormalForceMagnitude(double opposingForces, double... contributingMassInKg)
    {
        double sumOfMasses = 0;

        for (double mass : contributingMassInKg)
        {
            sumOfMasses += mass;
        }

        double magnitudeOfForce = (sumOfMasses * GRAVITATIONAL_ACCELERATION) - opposingForces;

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
     * @param coefficientOfFriction the coefficient of friction between the two surfaces
     * @param normalForceMagnitude the magnitude of the normal force in Newtons
     * @param correspondingForceVector the force vector that causes the friction
     * @return the friction vector with the correct magnitude and direction based on
     *         the coefficient of friction, the normal force and corresponding force vector
     */
    protected Vector calculateFrictionVector(double coefficientOfFriction, double normalForceMagnitude, Vector correspondingForceVector)
    {
        double frictionVectorMagnitude = coefficientOfFriction * normalForceMagnitude;
        double correspondingForceVectorXComponent = correspondingForceVector.asComponents().get(0);
        
        if (frictionVectorMagnitude > Math.abs(correspondingForceVectorXComponent))
        {
            frictionVectorMagnitude = Math.abs(correspondingForceVectorXComponent);
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
     * the components of all forces experienced by the Block.
     *
     * @param forcesExperienced Vector objects representing all forces experienced by the Block
     * @return the resultant vector of all forces from forcesExperienced
     */
    protected Vector calculateNetForceVector(ArrayList<Vector> forcesExperienced)
    {
        double sumXComponents = 0, sumYComponents = 0;

        for (Vector forceVector : forcesExperienced)
        {
            sumXComponents += Math.cos(Math.toRadians(forceVector.getDirectionInDegrees())) * forceVector.getMagnitudeInNewtons();
            sumYComponents += Math.sin(Math.toRadians(forceVector.getDirectionInDegrees())) * forceVector.getMagnitudeInNewtons();
        }

        double magnitudeOfResultant = Math.sqrt(Math.pow(sumXComponents, 2) + Math.pow(sumYComponents, 2));
        double directionOfResultantDegrees = getProperDirection(sumXComponents, sumYComponents);

        return new Vector(magnitudeOfResultant, directionOfResultantDegrees, FORCE_TYPE.APPLIED);
    }
    
    /**
     * Takes an x and y component of a vector as arguments and returns the 
     * direction of the vector in degrees with respect to the positive x-axis.
     * 
     * The returned value ranges between 0 and 360 degrees.
     * 
     * @param xComponent the horizontal component of the vector
     * @param yComponent the vertical component of the vector
     * @return direction in degrees with respect to the +x axis
     */
    protected double getProperDirection(double xComponent, double yComponent)
    {
        double properDirection = Math.abs(Math.toDegrees(Math.atan(yComponent / xComponent)));
        
        if (xComponent < 0 && yComponent >= 0)
        {
            properDirection = 180 - properDirection;
        }
        else if (xComponent < 0 && yComponent < 0)
        {
            properDirection += 180;
        }
        else if (xComponent >= 0 && yComponent < 0)
        {
            properDirection = 360 - properDirection;
        }
        
        return properDirection;
    }

    
    /**
     * Determine all forces on the block based on the slider values and with
     * respect to the laws of physics.
     *
     * @param topBlock the top block in the simulation
     * @param bottomBlock the bottom block in the simulation
     * @param forceOnBottomBlock the magnitude of the applied force on the bottom block in Newtons
     * @param forceOnTopBlock the magnitude of the applied force on the top block in Newtons
     * @param angleOfForceOnBottomBlock the angle of the applied force on the bottom block in degrees
     * @param angleOfForceOnTopBlock the angle of the applied force on the top block in degrees
     * @param frictionCoeffFloor the coefficient of friction between the floor and the bottom block
     * @param frictionCoeffBottomBlock the coefficient of friction between the bottom block and the top block
     * @param blockId the POSITION of the block in the simulation (TOP or BOTTOM)
     * @return an ArrayList consisting of all vectors doing work on the system
     *         other than the normal force vectors.
     */
    protected ArrayList<Vector> determineForcesExperienced(Block topBlock,
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
        
        Vector forceVectorOnTopBlock = new Vector(forceOnTopBlock, angleOfForceOnTopBlock, FORCE_TYPE.APPLIED),
                forceVectorOnBottomBlock = new Vector(forceOnBottomBlock, angleOfForceOnBottomBlock, FORCE_TYPE.APPLIED);

        double forceVectorTopBlockY = forceVectorOnTopBlock.asComponents().get(1),
                forceVectorBottomBlockY = forceVectorOnBottomBlock.asComponents().get(1);
        
        double totalAppliedVerticalForce = forceVectorTopBlockY + forceVectorBottomBlockY;
        
        double normalForceTopBlock = calculateNormalForceMagnitude(forceVectorTopBlockY, topBlock.getMass()),
                normalForceBottomBlock = calculateNormalForceMagnitude(totalAppliedVerticalForce, topBlock.getMass(), bottomBlock.getMass());
        
        Vector frictionVectorOnTopBlock = calculateFrictionVector(frictionCoeffBottomBlock, normalForceTopBlock, forceVectorOnTopBlock);
        
        if (blockId == POSITION.TOP)
        {   
            allForcesExperienced.addAll(List.of(forceVectorOnTopBlock, frictionVectorOnTopBlock));
        }
        else if (blockId == POSITION.BOTTOM)
        {
            Vector frictionVectorDueToFloor = calculateFrictionVector(frictionCoeffFloor, normalForceBottomBlock, forceVectorOnBottomBlock);
            frictionVectorOnTopBlock.flipDirection();

            allForcesExperienced.addAll(List.of(frictionVectorDueToFloor, forceVectorOnBottomBlock, frictionVectorOnTopBlock));
        }

        return allForcesExperienced;
    }
    
}
