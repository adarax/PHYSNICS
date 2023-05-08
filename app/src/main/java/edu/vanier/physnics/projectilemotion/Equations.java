/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

/**
 *
 * @author vires
 */
public class Equations {
    /**
     * Method representing the formula for max height of projectile motion.
     * H(m) = (initialVelocityMPS^2(sin(launchAngleDeg))^2) / (2 * gravityAccelMPSS)
     *
     * @param gravityAccelMPSS - Gravitational Acceleration entered by user
     * @param launchAngleDeg - Launch Angle entered by user
     * @param initialVelocityMPS - Initial Velocity entered by user
     * @return maxHeightM (The maximum height reached by projectile motion (m))
     */
    public static double getMaxHeight(double launchAngleDeg, double initialVelocityMPS, double gravityAccelMPSS) {
       double sinSquaredThetaDeg = Math.pow(Math.sin(Math.toRadians(launchAngleDeg)), 2);
       double velocitySquaredMPS = Math.pow(initialVelocityMPS, 2);
       double maxHeightM = (velocitySquaredMPS * sinSquaredThetaDeg) / (2 * gravityAccelMPSS);
       
       
       return maxHeightM;
    }
    
    public static double getXVelocityMetersPerSecond(double launchAngleDeg, double initialVelocityMetersPerSecond) {
        double velocityXMetersPerSecond = Math.cos(Math.toRadians(launchAngleDeg)) * initialVelocityMetersPerSecond;
        return velocityXMetersPerSecond;
    }
    
    /**
     * Method representing the formula for the max displacement in the x direction of the 
     * projectile motion.
     * D(m) = (initialVelocityMPS^2 * sin(2 * launchAngleDeg)) / gravityAccelMPSS
     * 
     * @param gravityAccelMPSS - Gravitational Acceleration entered by user
     * @param launchAngleDeg - Launch Angle entered by user
     * @param initialVelocityMPS - Initial Velocity entered by user
     * @return xDisplacementM (Max displacement in the X direction (m))
     */
    public static double getXdisplacement(double launchAngleDeg, double initialVelocityMPS, double gravityAccelMPSS) {
        double velocitySquaredMPS = Math.pow(initialVelocityMPS, 2);
        double sin2LaunchAngleDeg = Math.sin(2 * Math.toRadians(launchAngleDeg));
        double xDisplacementM = (velocitySquaredMPS * sin2LaunchAngleDeg) / gravityAccelMPSS;
        
        return xDisplacementM;
    }
    
    /**
     * Method representing the formula for the total time the projectile is in air. 
     * t(s) = (2 * initialVelocityMPS * sin(launchAngleDeg)) / gravityAccelMPSS
     * 
     * @param gravityAccelMPSS - Gravitational Acceleration entered by user
     * @param launchAngleDeg - Launch Angle entered by user
     * @param initialVelocityMPS - Initial Velocity entered by user
     * @return flightTimeS (Time that projectile in in the air (s))
     */
    public static double getFlightTime(double launchAngleDeg, double initialVelocityMPS, double gravityAccelMPSS) {
        
        double sinLaunchAngleDeg = Math.sin(Math.toRadians(launchAngleDeg));
        double flightTimeS = (2 * initialVelocityMPS * sinLaunchAngleDeg) / gravityAccelMPSS;
        
        return flightTimeS;
    }
    
    
}
