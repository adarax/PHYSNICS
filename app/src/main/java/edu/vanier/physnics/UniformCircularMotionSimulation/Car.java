/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

/**
 * The car that is going to be revolving in the simulation.
 * @author Admin
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
    
    public Car(double speed, double radius, double mass) {
        this.speed = speed;
        this.radius = radius;
        this.mass = mass;
    }
    
    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getMass() {
        return this.mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getAccelerationCentripetal() {
        return accelerationCentripetal;
    }

    public void setAccelerationCentripetal(double accelerationCentripetal) {
        this.accelerationCentripetal = accelerationCentripetal;
    }

    public double getForce() {
        return force;
    }

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
