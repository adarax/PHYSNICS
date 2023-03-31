/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;

/**
 *
 * @author benja
 */
public class Ball extends Circle{
    private double currentVelocityJoules;
    private double currentHeightMeters;
    private double massKilograms;
    
    private double initialPositionX;
    private double initialPositionY;
    
    private Path ballPath;
    
    public Ball(double radius, Color ballColor){
        this.setRadius(radius);
        this.setFill(ballColor);
    }
    
    public Ball(Color color, double x, double y, double radius){
        this.setCenterX(x);
        this.setCenterY(y);
        
        initialPositionX = x;
        initialPositionY = y;
        
        this.setRadius(radius);
        this.setFill(color);
    }
    
    public void reset(){
        this.setCenterX(initialPositionX);
        this.setCenterY(initialPositionY);
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

    public Path getBallPath() {
        return ballPath;
    }

    public void setBallPath(Path ballPath) {
        this.ballPath = ballPath;
    }

    public double getInitialPositionX() {
        return initialPositionX;
    }

    public void setInitialPositionX(double initialPositionX) {
        this.initialPositionX = initialPositionX;
    }

    public double getInitialPositionY() {
        return initialPositionY;
    }

    public void setInitialPositionY(double initialPositionY) {
        this.initialPositionY = initialPositionY;
    }
    
    
}
