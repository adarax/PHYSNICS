/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;

/**
 * Ball object which is a child of circle. Adds the mass variable and the path the ball will take on the ramp.
 * @author Benjamin Pratt
 */
public class Ball extends Circle{
    private double massKg;
    
    private Path ballPath;
    
    /**
     *  Constructor for the ball object. Sets the radius and the color of the ball.
     * @param radius Radius of the ball
     * @param ballColor Color of the ball
     */
    public Ball(double radius, Color ballColor){
        this.setRadius(radius);
        this.setFill(ballColor);
    }
    
    /**
     *  Constructor for the ball object. 
     * Sets color, radius and the x and y position of the ball.
     * @param color Color of the ball
     * @param centerX Center X of the ball
     * @param centerY CenterY of the ball
     * @param radius Radius of the ball
     */
    public Ball(Color color, double centerX, double centerY, double radius){
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setFill(color);
        this.setRadius(radius);
        
    }
   

    /**
     * Getter for massKg
     * @return current mass of the ball
     */
    public double getMassKg() {
        return massKg;
    }

    /**
     * Setter for massKg
     * @param massKg new mass value
     */
    public void setMassKg(double massKg) {
        this.massKg = massKg;
    }

    /**
     * Getter for ballPath
     * @return path of the ball during the animation
     */
    public Path getBallPath() {
        return ballPath;
    }

    /**
     * Setter for ballPath
     * @param ballPath sets a new ball path for the ball animation
     */
    public void setBallPath(Path ballPath) {
        this.ballPath = ballPath;
    }
}
