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
     * The speed of the car.
     */
    private double speed;
    /**
     * The radius that the car makes when revolving.
     */
    private double radius;
    /**
     * The mass of the car.
     */
    private double mass;
    
    /**
     * The centripetal acceleration of the car.
     */
    private double accelerationCentripetal;
    /**
     * The centripetal force experienced by the car.
     */
    private double force;
    
    /**
     * Constructor for the Car class, containing no parameters.
     */
    public Car(){
    }
    
    /**
     * Constructor for the car class, given specific parameters. 
     * @param speed The initial speed of the car
     * @param radius the initial radius of the car
     * @param mass the initial mass of the car
     */
    public Car(double speed, double radius, double mass) {
        this.speed = speed;
        this.radius = radius;
        this.mass = mass;
    }
    
    /**
     * Getter for the car's speed
     * @return the speed of the car
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Sets the car's speed
     * @param speed speed of the car to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Getter for the car's radius
     * @return the radius of the car
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * Sets the car's radius
     * @param radius radius of the car to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Getter for the car's mass
     * @return the mass of the car
     */
    public double getMass() {
        return this.mass;
    }

    /**
     * Sets the car's mass
     * @param mass the mass of the car to set
     */
    public void setMass(double mass) {
        this.mass = mass;
    }

    /**
     * Getter for the car's centripetal acceleration
     * @return the centripetal acceleration of the car
     */
    public double getAccelerationCentripetal() {
        return accelerationCentripetal;
    }

    /**
     * Sets the car's centripetal acceleration
     * @param accelerationCentripetal the centripetal acceleration of the car to set
     */
    public void setAccelerationCentripetal(double accelerationCentripetal) {
        this.accelerationCentripetal = accelerationCentripetal;
    }

    /**
     * Getter for the car's force
     * @return the force of the car
     */
    public double getForce() {
        return force;
    }

    /**
     * Sets the car's force
     * @param force the force of the car to set
     */
    public void setForce(double force) {
        this.force = force;
    }
    
    /**
     * Returns a string listing all variables of the Car and their values.
     * @return the string listing all variables of the Car and their values
     */
    @Override
    public String toString() {
        return "Object{" + "speed=" + speed + ", radius=" + radius + ", mass=" + mass +  ", accelerationcentr=" + accelerationCentripetal +'}';
    }  
}
