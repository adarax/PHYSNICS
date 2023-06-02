/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

/**
 * The car that is going to be revolving in the simulation.
 * @author Victor-Pen
 */
public class Car {
    
    /**
     * The speedMetersPerSeconds of the car.
     */
    private double speedMetersPerSeconds;
    /**
     * The radiusMeters that the car makes when revolving.
     */
    private double radiusMeters;
    /**
     * The massKilograms of the car.
     */
    private double massKilograms;
    
    /**
     * The centripetal acceleration of the car.
     */
    private double accelerationCentripetalMetersPerSecondsSquared;
    /**
     * The centripetal forceNewtons experienced by the car.
     */
    private double forceNewtons;
    
    /**
     * Constructor for the Car class, containing no parameters.
     */
    public Car(){
    }
    
    /**
     * Constructor for the car class, given specific parameters. 
     * @param speed The initial speedMetersPerSeconds of the car
     * @param radius the initial radiusMeters of the car
     * @param mass the initial massKilograms of the car
     */
    public Car(double speed, double radius, double mass) {
        this.speedMetersPerSeconds = speed;
        this.radiusMeters = radius;
        this.massKilograms = mass;
    }
    
    /// Consider making these fields public and removing the following methods,
    /// if having these methods makes no difference.
    /**
     * Getter for the car's speedMetersPerSeconds
     * @return the speedMetersPerSeconds of the car
     */
    public double getSpeedMetersPerSeconds() {
        return this.speedMetersPerSeconds;
    }

    /**
     * Sets the car's speedMetersPerSeconds
     * @param speed speedMetersPerSeconds of the car to set
     */
    public void setSpeedMetersPerSeconds(double speed) {
        this.speedMetersPerSeconds = speed;
    }

    /**
     * Getter for the car's radiusMeters
     * @return the radiusMeters of the car
     */
    public double getRadiusMeters() {
        return this.radiusMeters;
    }

    /**
     * Sets the car's radiusMeters
     * @param radiusMeters radiusMeters of the car to set
     */
    public void setRadiusMeters(double radiusMeters) {
        this.radiusMeters = radiusMeters;
    }

    /**
     * Getter for the car's massKilograms
     * @return the massKilograms of the car
     */
    public double getMassKilograms() {
        return this.massKilograms;
    }

    /**
     * Sets the car's massKilograms
     * @param massKilograms the massKilograms of the car to set
     */
    public void setMassKilograms(double massKilograms) {
        this.massKilograms = massKilograms;
    }

    /**
     * Getter for the car's centripetal acceleration
     * @return the centripetal acceleration of the car
     */
    public double getAccelerationCentripetalMetersPerSecondsSquared() {
        return accelerationCentripetalMetersPerSecondsSquared;
    }

    /**
     * Sets the car's centripetal acceleration
     * @param accelerationCentripetalMetersPerSecondsSquared the centripetal acceleration of the car to set
     */
    public void setAccelerationCentripetalMetersPerSecondsSquared(double accelerationCentripetalMetersPerSecondsSquared) {
        this.accelerationCentripetalMetersPerSecondsSquared = accelerationCentripetalMetersPerSecondsSquared;
    }

    /**
     * Getter for the car's forceNewtons
     * @return the forceNewtons of the car
     */
    public double getForceNewtons() {
        return forceNewtons;
    }

    /**
     * Sets the car's forceNewtons
     * @param forceNewtons the forceNewtons of the car to set
     */
    public void setForceNewtons(double forceNewtons) {
        this.forceNewtons = forceNewtons;
    }
    
    /**
     * Returns a string listing all variables of the Car and their values.
     * @return the string listing all variables of the Car and their values
     */
    @Override
    public String toString() {
        return "Object{" + "speed=" + speedMetersPerSeconds + ", radius=" + radiusMeters + ", mass=" + massKilograms +  ", accelerationcentr=" + accelerationCentripetalMetersPerSecondsSquared +'}';
    }  
}
