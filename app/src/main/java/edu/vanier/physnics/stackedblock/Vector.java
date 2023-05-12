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
    
    private final FORCE_TYPE forceType;

    /**
     * Constructor for the Vector class.
     * 
     * @param magnitudeInNewtons the magnitude of force of the Vector in Newtons
     * @param directionInDegrees the direction of the Vector in degrees
     * @param forceType the type of force that the Vector is (APPLIED or FRICTION)
     */
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

    /**
     * Returns the x and y components of the Vector by using the magnitudeInNewtons
     * and directionInDegrees variables that get instantiated in the constructor and
     * simple trigonometric principles.
     * 
     * @return the x and y components of the Vector in an ArrayList
     */
    public ArrayList<Double> asComponents()
    {
        ArrayList<Double> asComponents = new ArrayList<>();

        double xComponent = Math.cos(Math.toRadians(directionInDegrees)) * magnitudeInNewtons;
        double yComponent = Math.sin(Math.toRadians(directionInDegrees)) * magnitudeInNewtons;

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
        currentDirection = currentDirection % 360;
        
        this.directionInDegrees = currentDirection;
    }
    
    /**
     * Returns the magnitude of force of the Vector in Newtons.
     * 
     * @return the magnitude of force of the Vector
     */
    public double getMagnitudeInNewtons()
    {
        return magnitudeInNewtons;
    }

    /**
     * Returns the direction of the Vector in degrees.
     * 
     * @return the direction of the Vector
     */
    public double getDirectionInDegrees()
    {
        return directionInDegrees;
    }
    
    /**
     * Returns the type of force that the Vector is.
     * 
     * @return the type of force that the Vector is (APPLIED or FRICTION)
     */
    public FORCE_TYPE getForceType()
    {
        return forceType;
    }
    
    /**
     * The type of force that the Vector is.
     */
    public enum FORCE_TYPE
    {
        APPLIED,
        FRICTION
    }

    /**
     * Simple toString() method to easily view the properties of a Vector
     * instance. This method is used for the tests in the BlockFormulasTest class.
     *
     * Numerical values are rounded to two decimal places to simplify test
     * methods.
     *
     * @return properties of the Vector in the form of a String
     */
    @Override
    public String toString()
    {
        return "Vector{" + "magnitudeInNewtons=" + String.format("%.2f", magnitudeInNewtons) + ", directionInDegrees=" + String.format("%.2f", directionInDegrees) + ", forceType=" + forceType + '}';
    }
}
