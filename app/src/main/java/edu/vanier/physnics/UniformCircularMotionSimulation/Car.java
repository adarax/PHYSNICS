/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

/**
 *
 * @author Admin
 */
public class Car {
    
    private double speed;
    private double radius;
    private double mass;
    
    private double accelerationCentripetal;
    private double accelerationtang;
    
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

    public double getAccelerationcentr() {
        return this.accelerationCentripetal;
    }

    public void setAccelerationcentr(double accelerationcentr) {
        this.accelerationCentripetal = accelerationcentr;
    }

    public double getAccelerationtang() {
        return this.accelerationtang;
    }

    public void setAccelerationtang(double accelerationtang) {
        this.accelerationtang = accelerationtang;
    }

    @Override
    public String toString() {
        return "Object{" + "speed=" + speed + ", radius=" + radius + ", mass=" + mass +  ", accelerationcentr=" + accelerationCentripetal + ", accelerationtang=" + accelerationtang + '}';
    }  
}
