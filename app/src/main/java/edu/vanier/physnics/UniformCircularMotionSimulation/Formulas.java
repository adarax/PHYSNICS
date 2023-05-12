/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

/**
 * A class used to store formulas and any mathematical equations
 * @author Victor-Pen
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
        double acceleration = car.getSpeedMetersPerSeconds()*car.getSpeedMetersPerSeconds()/car.getRadiusMeters();
        car.setAccelerationCentripetalMetersPerSecondsSquared(acceleration);
        return acceleration;
    }    

    /**
     * Method representing the formula for the magnitude of the centripetal force of the revolving car
     * Represented by F=m*a
     * 
     * @param car the car that is revolving in the simulation
     * @return the magnitude of centripetal force, in N
     */
    public static double calculateForce(Car car){
        double force = car.getAccelerationCentripetalMetersPerSecondsSquared()*car.getMassKilograms();
        car.setForceNewtons(force);
        return force;
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
     * Given an angle theta between 0 and 90,
     * if it is in the 1st quadrant, then the angle equals theta
     * if it is in the 2nd quadrant, then the angle has to be 180-theta
     * if it is in the 3rd quadrant, then the angle has to be theta+180
     * if it is in the 4th quadrant, then the angle has to be 360-theta
     * @param angle
     * @param coordinateX
     * @param coordinateY
     * @return 
     */
    public static double determineQuadrantDegrees(double angle, double coordinateX, double coordinateY){
            /*checks the y coordinate. due to how javaFX works, a positive y coordinate 
            indicates that it is in the 3rd or 4th quadrant, whilst a negative indicates
            it is in the 1st or 2nd quadrant.*/
            if (coordinateY > 0) {
                /*if the angle is positive, then due to the coordinate system of JavaFX,
                it is in the 2nd or 3rd quadrant. Else, it is in the 1st or 4th quadrant.*/
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
     * Represented by magnitude*cos(angle), where angle is in radians
     * @param magnitude the magnitude of the vector's length
     * @param angle the angle that the vector makes, in degrees, with respect to a positive x-axis.
     * @return 
     */
    public static double returnMagnitudeXComponent(double magnitude, double angle){
        return Math.cos(Math.toRadians(angle))*magnitude;
    }
    
    /**
     * Returns the y component of a vector that is in polar coordinates.
     * Represented by magnitude*sin(angle), where angle is in radians
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
