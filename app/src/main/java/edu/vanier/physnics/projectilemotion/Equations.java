/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

/**
 * Class containing all the equations involved in a projectile motion. Used to 
 * animate a projectile motion accurately and to obtain data from user-entered 
 * variables.
 * 
 * @author vireshpatel43
 */
public class Equations {
    /**
     * Method representing the formula for max height of projectile motion.
     * {@code maxHeightMeters = (initialVelocityMetersPerSecond^2(sin(launchAngleDegrees))^2) / (2 * gravityMetersPerSecondSquared)}
     *
     * @param gravityMetersPerSecondSquared Gravitational Acceleration entered by user
     * @param launchAngleDegrees Launch Angle entered by user
     * @param initialVelocityMetersPerSecond  Initial Velocity entered by user
     * @return The maximum height reached by projectile motion /// in meters
     */
    public static double getMaxHeightMeters(double launchAngleDegrees, double initialVelocityMetersPerSecond, double gravityMetersPerSecondSquared) {
       double sinSquaredTheta = Math.pow(Math.sin(Math.toRadians(launchAngleDegrees)), 2);
       double velocitySquaredMetersPerSecond = Math.pow(initialVelocityMetersPerSecond, 2);
       double maxHeightMeters = (velocitySquaredMetersPerSecond * sinSquaredTheta) / (2 * gravityMetersPerSecondSquared);   
       
       return maxHeightMeters;
    }
    
    /**
     * Method that gets the x-component of velocity. Cos of launch angle multiplied
     * by initial velocity gives the x-component directly. There is no acceleration.
     * 
     * {@code velocityXMetersPerSecond = cos(launchAngleDegrees) * initialVelocityMetersPerSecond}
     * 
     * @param launchAngleDegrees Launch angle entered by user
     * @param initialVelocityMetersPerSecond Initial velocity entered by user
     * @return x-component of velocity
     */
    public static double getXVelocityMetersPerSecond(double launchAngleDegrees, double initialVelocityMetersPerSecond) {
        double velocityXMetersPerSecond = Math.cos(Math.toRadians(launchAngleDegrees)) * initialVelocityMetersPerSecond;
        return velocityXMetersPerSecond;
    }
    
    /**
     * Method that gets the y-component of velocity as a function of time. Uses 
     * time to incorporate acceleration. Sin of launch angle multiplied by initial
     * velocity gets the y-component. Since there is acceleration, this number needs
     * to be subtracted every second.
     * 
     * /// Typo
     * {@code veloctiyYMetersPerSecond = (sin(launchAngleDegrees) * initialVelocityMetersPerSecond) - gravityMetersPerSecondSquared * currentTimeSeconds}
     * 
     * @param launchAngleDegrees Launch angle entered by user
     * @param initialVelocityMetersPerSecond InitialVelocity entered by user
     * @param gravityMetersPerSecondSquared Gravitational acceleration entered by user
     * @param currentTimeSeconds current time entered as a parameter
     * @return y-component of velocity
     */
    public static double getYVelocityMetersPerSecond(double launchAngleDegrees, double initialVelocityMetersPerSecond, double gravityMetersPerSecondSquared, double currentTimeSeconds) {
        double velocityYMetersPerSecond = (Math.sin(Math.toRadians(launchAngleDegrees)) * initialVelocityMetersPerSecond) - (currentTimeSeconds * gravityMetersPerSecondSquared);
        return velocityYMetersPerSecond;
    }

    /**
     * Method representing the formula for the max displacement in the x direction of the 
     * projectile motion.
     * 
     * {@code DistanceMeters = (initialVelocityMetersPerSecond^2 * sin(2 * launchAngleDegrees)) / gravityMetersPerSecondSquared}
     * 
     * @param gravityMetersPerSecondSquared Gravitational Acceleration entered by user
     * @param launchAngleDegrees Launch Angle entered by user
     * @param initialVelocityMetersPerSecond Initial Velocity entered by user
     * @return Max displacement in the X direction (meters)
     */
    public static double getXdisplacementMeters(double launchAngleDegrees, double initialVelocityMetersPerSecond, double gravityMetersPerSecondSquared) {
        double velocitySquaredMetersPerSecond = Math.pow(initialVelocityMetersPerSecond, 2);
        double sin2TimesLaunchAngleDegrees = Math.sin(2 * Math.toRadians(launchAngleDegrees));
        double xDisplacementMeters = (velocitySquaredMetersPerSecond * sin2TimesLaunchAngleDegrees) / gravityMetersPerSecondSquared;
        
        return xDisplacementMeters;
    }
    
    /**
     * Method representing the formula for the total time the projectile is in air. 
     * {@code flightTimeSeconds = (2 * initialVelocityMetersPerSecond * sin(launchAngleDegrees)) / gravityMetersPerSecondSquared}
     * 
     * @param gravityMetersPerSecondSquared Gravitational Acceleration entered by user
     * @param launchAngleDegrees Launch Angle entered by user
     * @param initialVelocityMetersPerSecond Initial Velocity entered by user
     * @return Time that projectile in in the air (seconds)
     */
    public static double getFlightTimeSeconds(double launchAngleDegrees, double initialVelocityMetersPerSecond, double gravityMetersPerSecondSquared) {
        
        double sinLaunchAngleDegrees = Math.sin(Math.toRadians(launchAngleDegrees));
        double flightTimeSeconds = (2 * initialVelocityMetersPerSecond * sinLaunchAngleDegrees) / gravityMetersPerSecondSquared;
        
        return flightTimeSeconds;
    }
    
    
}
