package edu.vanier.physnics.stackedblock;

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
    
}
