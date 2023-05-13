/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

/**
 * Physics formulas used in the program
 * @author Benjamin Pratt
 */
public class ConservationFormulas {
    
    /**
     * Calculates the potential energy of the object at a certain height
     * @param mass mass of the object
     * @param gravitationalAcceleration applied gravitational acceleration
     * @param height current height of the ball
     * @return potential energy
     */
    public static double potentialEnergy(double mass,double gravitationalAcceleration, double height){
        return mass*gravitationalAcceleration*height;
    }
    
    /**
     * Calculates the kinetic energy of the object at a certain speed
     * @param mass mass of the ball
     * @param velocity current velocity of the ball
     * @return kinetic energy 
     */
    public static double kineticEnergy(double mass, double velocity){
        return 0.5*mass*velocity*velocity;
    }
    
    /**
     * Calculates the current velocity of an object based on the total mechanical energy, potential energy and mass of the ball
     * @param totalMechanicalEnergy total mechanical of the system
     * @param potentialEnergy current potential energy of the object
     * @param mass mass of the object
     * @return velocity
     */
    public static double getCurrentVelocity(double totalMechanicalEnergy, double potentialEnergy, double mass){
        return Math.sqrt(((totalMechanicalEnergy-potentialEnergy)*2)/mass);
    }
    
    /**
     * Time it takes for the object to from one side of the ramp to the other
     * @param height starting height of the ramp
     * @param gravitationalAcceleration applied gravitational acceleration
     * @return time
     */
    public static double getArcTime(double height, double gravitationalAcceleration){
        return 2.0*Math.sqrt((2.0*height)/gravitationalAcceleration);
    }
    
    
    /**
     * gets the circumference of a semicircle
     * @param radius radius of the circle
     * @return circumference of a semicircle
     */
    public static double getHalfCircleCircumference(double radius){
        return Math.PI*radius;
    }
     
    /**
     * Returns the height of an object from a potential energy
     * @param gravitationalAcceleration applied gravitational acceleration
     * @param mass mass of the object
     * @param potentialEnergy current potential energy of the object
     * @return height
     */
    public static double getHeightFromPotentialEnergy(double gravitationalAcceleration, double mass, double potentialEnergy){
        return potentialEnergy/(mass*gravitationalAcceleration);
    }

}
