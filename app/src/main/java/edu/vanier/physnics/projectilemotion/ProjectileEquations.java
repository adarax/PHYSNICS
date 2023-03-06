/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

/**
 *
 * @author vires
 */
public class ProjectileEquations {
    double initialVelocity;
    double launchAngle;
    double gravityAccel;
    double sinAngle = Math.toDegrees(Math.sin(launchAngle));
    
    public double getMaxHeight() {
        
       double maxHeight = Math.pow(initialVelocity, 2);
        
       return 0.0;
    }
    
    public static double getXdisplacement() {
        return 0.0;
    }
    
    public double getFlightTime() {
        
        double flightTime = (2.0 * initialVelocity * sinAngle) / gravityAccel;
        return flightTime;
    }
    
    
}
