/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author benja
 */
public class Ball extends Circle{
    private double currentVelocityJoules;
    private double currentHeightMeters;
    private double massKilograms;
    
    private double animationCenterX;
    private double animationCenterY;
    
    
    public Ball(Color color, double x, double y){
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(20);
        this.setFill(color);
        
        animationCenterX = x;
        animationCenterY = y;
    }

    public double getCurrentVelocity() {
        return currentVelocityJoules;
    }

    public void setCurrentVelocity(double currentVelocity) {
        this.currentVelocityJoules = currentVelocity;
    }

    public double getCurrentHeight() {
        return currentHeightMeters;
    }

    public void setCurrentHeight(double currentHeight) {
        this.currentHeightMeters = currentHeight;
    }

    public double getMass() {
        return massKilograms;
    }

    public void setMass(double mass) {
        this.massKilograms = mass;
    }

    public double getCurrentVelocityJoules() {
        return currentVelocityJoules;
    }

    public void setCurrentVelocityJoules(double currentVelocityJoules) {
        this.currentVelocityJoules = currentVelocityJoules;
    }

    public double getCurrentHeightMeters() {
        return currentHeightMeters;
    }

    public void setCurrentHeightMeters(double currentHeightMeters) {
        this.currentHeightMeters = currentHeightMeters;
    }

    public double getMassKilograms() {
        return massKilograms;
    }

    public void setMassKilograms(double massKilograms) {
        this.massKilograms = massKilograms;
    }

    public double getAnimationCenterX() {
        return animationCenterX;
    }

    public void setAnimationCenterX(double animationCenterX) {
        this.animationCenterX = animationCenterX;
    }

    public double getAnimationCenterY() {
        return animationCenterY;
    }

    public void setAnimationCenterY(double animationCenterY) {
        this.animationCenterY = animationCenterY;
    }
    
    
    
    
}
