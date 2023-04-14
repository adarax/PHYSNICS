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
        return ((TME-PE)*2)/mass;
    }
    
    public static double getArcTime(double height, double g){
        return 2.0*Math.sqrt((2.0*height)/g);
    }

}
