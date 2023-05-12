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
     * H(m) = (initialVelocityMPS^2(sin(launchAngleDeg))^2) / (2 * gravityAccelMPSS)
     *
     * @param gravityMetersPerSecondSquared - Gravitational Acceleration entered by user
     * @param launchAngleDegrees - Launch Angle entered by user
     * @param initialVelocityMetersPerSecond - Initial Velocity entered by user
     * @return maxHeightM (The maximum height reached by projectile motion (m))
     */
    public static double getMaxHeight(double launchAngleDegrees, double initialVelocityMetersPerSecond, double gravityMetersPerSecondSquared) {
       double sinSquaredThetaDeg = Math.pow(Math.sin(Math.toRadians(launchAngleDegrees)), 2);
       double velocitySquaredMPS = Math.pow(initialVelocityMetersPerSecond, 2);
       double maxHeightM = (velocitySquaredMPS * sinSquaredThetaDeg) / (2 * gravityMetersPerSecondSquared);   
       
       return maxHeightM;
    }
    
    /**
     * Method that gets the x-component of velocity.
     * 
     * @param launchAngleDeg Launch angle entered by user
     * @param initialVelocityMetersPerSecond Initial velocity entered by user
     * @return x-component of velocity
     */
    public static double getXVelocityMetersPerSecond(double launchAngleDeg, double initialVelocityMetersPerSecond) {
        double velocityXMetersPerSecond = Math.cos(Math.toRadians(launchAngleDeg)) * initialVelocityMetersPerSecond;
        return velocityXMetersPerSecond;
    }
    
    /**
     * Method that gets the y-component of velocity as a function of time. Uses 
     * time to incorporate acceleration.
     * 
     * @param launchAngleDeg Launch angle entered by user
     * @param initialVelocityMetersPerSecond InitialVelocity entered by user
     * @param gravityMetersPerSecondSquared Gravitational acceleration entered by user
     * @param currentTimeSeconds current time entered as a parameter
     * @return y-component of velocity
     */
    public static double getYVelocityMetersPerSecond(double launchAngleDeg, double initialVelocityMetersPerSecond, double gravityMetersPerSecondSquared, double currentTimeSeconds) {
        double velocityYMetersPerSecond = (Math.sin(Math.toRadians(launchAngleDeg)) * initialVelocityMetersPerSecond) - (currentTimeSeconds * gravityMetersPerSecondSquared);
        return velocityYMetersPerSecond;
    }

    /**
     * Method representing the formula for the max displacement in the x direction of the 
     * projectile motion.
     * D(m) = (initialVelocityMPS^2 * sin(2 * launchAngleDeg)) / gravityAccelMPSS
     * 
     * @param gravityAccelMetersPerSecondSquared - Gravitational Acceleration entered by user
     * @param launchAngleDegrees - Launch Angle entered by user
     * @param initialVelocityMetersPerSecond - Initial Velocity entered by user
     * @return xDisplacementM (Max displacement in the X direction (m))
     */
    public static double getXdisplacement(double launchAngleDegrees, double initialVelocityMetersPerSecond, double gravityAccelMetersPerSecondSquared) {
        double velocitySquaredMPS = Math.pow(initialVelocityMetersPerSecond, 2);
        double sin2LaunchAngleDeg = Math.sin(2 * Math.toRadians(launchAngleDegrees));
        double xDisplacementM = (velocitySquaredMPS * sin2LaunchAngleDeg) / gravityAccelMetersPerSecondSquared;
        
        return xDisplacementM;
    }
    
    /**
     * Method representing the formula for the total time the projectile is in air. 
     * t(s) = (2 * initialVelocityMPS * sin(launchAngleDeg)) / gravityAccelMPSS
     * 
     * @param gravityMetersPerSecondSquared - Gravitational Acceleration entered by user
     * @param launchAngleDegrees - Launch Angle entered by user
     * @param initialVelocityMetersPerSecond - Initial Velocity entered by user
     * @return flightTimeS (Time that projectile in in the air (s))
     */
    public static double getFlightTime(double launchAngleDegrees, double initialVelocityMetersPerSecond, double gravityMetersPerSecondSquared) {
        
        double sinLaunchAngleDeg = Math.sin(Math.toRadians(launchAngleDegrees));
        double flightTimeS = (2 * initialVelocityMetersPerSecond * sinLaunchAngleDeg) / gravityMetersPerSecondSquared;
        
        return flightTimeS;
    }
    
    
}
