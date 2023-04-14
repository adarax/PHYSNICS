/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UCMSimulation;

/**
 *
 * @author Admin
 */
public class Formulas {
    
    public static double calculateAccelerationCentr(Car car){
        double accel = car.getSpeed()*car.getSpeed()/car.getRadius();
        car.setAccelerationcentr(accel);
        return accel;
    }    

    public static double calculateForce(Car car){
        return car.getAccelerationcentr()*car.getMass();
    }
    
    
}
