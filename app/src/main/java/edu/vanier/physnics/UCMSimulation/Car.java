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
public class Car {
    private double speed;
    private double radius;
    private double mass;
    
    private double[] velocity = new double[2];
    private double accelerationcentr;
    private double accelerationtang;

    public Car() {
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

    public double[] getVelocity() {
        return this.velocity;
    }

    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    public double getAccelerationcentr() {
        return this.accelerationcentr;
    }

    public void setAccelerationcentr(double accelerationcentr) {
        this.accelerationcentr = accelerationcentr;
    }

    public double getAccelerationtang() {
        return this.accelerationtang;
    }

    public void setAccelerationtang(double accelerationtang) {
        this.accelerationtang = accelerationtang;
    }

    @Override
    public String toString() {
        return "Object{" + "speed=" + speed + ", radius=" + radius + ", mass=" + mass + ", velocity=" + velocity + ", accelerationcentr=" + accelerationcentr + ", accelerationtang=" + accelerationtang + '}';
    }  
}
