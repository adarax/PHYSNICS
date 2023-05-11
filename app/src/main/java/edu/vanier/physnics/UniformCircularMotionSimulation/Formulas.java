/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

/**
 * A class used to store formulas and any mathematical equations
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
    
    /**
     * Returns the angle of the car with respect to the circle.
     * @param coordinateX the length that is adjacent to the angle that is being computed for 
     * @param coordinateY the length that is opposite to the angle that is being computed for 
     * @return the angle made from the car with respect to the center's horizontal axis
     */
    public static double getAngle(double coordinateX, double coordinateY){
        return Math.atan(coordinateY/coordinateX);
    }   
    
    /**
     * Returns the angle in its correct quadrant, given its x and y coordinate.
     * @param angle
     * @param coordinateX
     * @param coordinateY
     * @return 
     */
    public static double determineQuadrant(double angle, double coordinateX, double coordinateY){
            if (coordinateY > 0) {
                if (angle > 0) {
                    //4th quadrant
                    angle = 360-Math.abs(angle);
                }
                else{
                    //3rd quadrant
                    angle = 180+Math.abs(angle);
                }
            }
            else{
                if (angle < 0) {
                    //1st quadrant
                    angle = Math.abs(angle);
                }
                else{
                    //2nd quadrant
                    angle = 180-Math.abs(angle);
                }
            }
        return angle;
    }
    
    /**
     * Returns the x component of a vector that is in polar coordinates.
     * @param magnitude the magnitude of the vector's length
     * @param angle the angle that the vector makes, in degrees, with respect to a positive x-axis.
     * @return 
     */
    public static double returnMagnitudeXComponent(double magnitude, double angle){
        return Math.cos(Math.toRadians(angle))*magnitude;
    }
    
    /**
     * Returns the y component of a vector that is in polar coordinates.
     * @param magnitude the magnitude of the vector's length
     * @param angle the angle that the vector makes, in degrees, with respect to a positive x-axis.
     * @return 
     */
    public static double returnMagnitudeYComponent(double magnitude, double angle){
        return Math.sin(Math.toRadians(angle))*magnitude;
    }    
    
    /**
     * Rounds a double value to 2 decimal places.
     * @param value the number to roundTwoDecimals
     * @return the value, rounded to 2 decimal places
     */
    public static double roundTwoDecimals(double value){
        //https://stackoverflow.com/questions/5710394/how-do-i-roundTwoDecimals-a-double-to-two-decimal-places-in-java
        double valueToRound = Math.round(value*100.00);
        valueToRound = valueToRound/100.00;
        return valueToRound;
    }    
}
