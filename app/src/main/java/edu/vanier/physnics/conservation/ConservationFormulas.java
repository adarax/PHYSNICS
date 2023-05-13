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
     * 
     * @param mass mass of the object
     * @param gravitationalAcceleration applied gravitational acceleration
     * @param height current height of the ball
     * @return Calculates the potential energy of the object at a certain height
     */
    public static double potentialEnergy(double mass,double gravitationalAcceleration, double height){
        return mass*gravitationalAcceleration*height;
    }
    
    /**
     *
     * @param mass mass of the ball
     * @param velocity current velocity of the ball
     * @return  Calculates the kinetic energy of the object at a certain speed
     */
    public static double kineticEnergy(double mass, double velocity){
        return 0.5*mass*velocity*velocity;
    }
    
    /**
     * 
     * @param totalMechanicalEnergy total mechanical of the system
     * @param potentialEnergy current potential energy of the object
     * @param mass mass of the object
     * @return Calculates the current velocity of an object based on the total mechanical energy, potential energy and mass of the ball
     */
    public static double getCurrentVelocity(double totalMechanicalEnergy, double potentialEnergy, double mass){
        return Math.sqrt(((totalMechanicalEnergy-potentialEnergy)*2)/mass);
    }
    
    /**
     * 
     * @param height starting height of the ramp
     * @param gravitationalAcceleration applied gravitational acceleration
     * @return Time it takes for the object to from one side of the ramp to the other
     */
    public static double getArcTime(double height, double gravitationalAcceleration){
        return 2.0*Math.sqrt((2.0*height)/gravitationalAcceleration);
    }
    
    
    /**
     * @param radius radius of the circle
     * @return  gets the circumference of a semicircle
     */
    public static double getHalfCircleCircumference(double radius){
        return Math.PI*radius;
    }
     
    /**
     * 
     * @param gravitationalAcceleration applied gravitational acceleration
     * @param mass mass of the object
     * @param potentialEnergy current potential energy of the object
     * @return Returns the height of an object from a potential energy
     */
    public static double getHeightFromPotentialEnergy(double gravitationalAcceleration, double mass, double potentialEnergy){
        return potentialEnergy/(mass*gravitationalAcceleration);
    }

}
