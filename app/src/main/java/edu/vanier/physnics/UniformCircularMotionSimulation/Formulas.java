/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;
/**
 * A class used to store formulas
 * @author Admin
 */
class Formulas {
    /**
     * Method representing the formula for the magnitude of the centripetal acceleration of the revolving car
     * Represented by a=v*v/r
     * 
     * @param car the car that is revolving in the simulation
     * @return the magnitude of centripetal acceleration, in m/s^2
     */
    public static double calculateAccelerationCentripetal(Car car){
        double accel = car.getSpeed()*car.getSpeed()/car.getRadius();
        car.setAccelerationcentr(accel);
        return accel;
    }    

    /**
     * Method representing the formula for the magnitude of the centripetal force of the revolving car
     * Represented by F=m*a
     * 
     * @param car the car that is revolving in the simulation
     * @return the magnitude of centripetal force, in N
     */
    public static double calculateForce(Car car){
        return car.getAccelerationcentr()*car.getMass();
    }    
}
