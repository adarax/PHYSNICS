/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

/**
 *
 * @author benja
 */
public class ConservationFormulas {
    
    public static double potentialEnergy(double mass,double g, double height){
        return mass*g*height;
    }
    
    public static double kineticEnergy(double mass, double velocity){
        return 0.5*mass*velocity*velocity;
    }
    
    public static double getCurrentVelocity(double TME, double PE, double mass){
        return Math.sqrt(((TME-PE)*2)/mass);
    }
    
    public static double getArcTime(double height, double g){
        return 2.0*Math.sqrt((2.0*height)/g);
    }
    
    public static double getFrictionEnergy(double u, double mass, double g, double d){
        return u*mass*g*d;
    }
    
     public static double getAngle(double currentHeight, Double radius){
        double angleRadians = Math.asin(currentHeight/radius);
        return Math.toDegrees(angleRadians);
    }
     
    public static double getHalfCircleCircumference(double radius){
        return Math.PI*radius;
    }
     
    public static double getFrictionEnergyOverCircleSection(double radius, double m, double g, double u, double initialAngle, double finalAngle){
        return Math.abs(radius*m*g*u*(Math.sin(0) - Math.sin(finalAngle)));
    }
    
    public static double getHeightFromPotentialEnergy(double g, double mass, double PE){
        return PE/(mass*g);
    }

}
