/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author benja
 */
public class Ramp extends Path{
    private double radius;
    private double thickness;
    private double positionX;
    private double positionY;
    private Color rampColor;
    
    private double initialBallPositionX;
    private double initialBallPositionY;
    
    private double centerX;
    private double centerY;

    public Ramp(double radius, double thickness, double positionX, double positionY, Color rampColor) {
        this.radius = radius;
        this.thickness = thickness;
        this.positionX = positionX;
        this.positionY = positionY;
        this.rampColor = rampColor;
        
        this.centerX = positionX;
        this.centerY = positionY-radius-thickness;
        
        drawRamp();
    }
    
    /**
     * position x and y refer to the bottom middle of the outer semicircle. 
     * Radius is for the inner semicircle. The thickness refers to the radius between the outer and
     * inner semicircle.
     */
    public void drawRamp(){
        determineAndSetRampRadius();
        this.setFill(rampColor);
        this.setStroke(rampColor);
        this.setFillRule(FillRule.EVEN_ODD);
        
        MoveTo initialMoveTo = new MoveTo();
        initialMoveTo.setX(positionX-radius); // x starting position of inner semicircle
        initialMoveTo.setY(positionY-radius-thickness); // initial y position for both outer and inner semicircle
        
        ArcTo arcToInner = new ArcTo();
        arcToInner.setX(positionX + radius); // draws the inner arc to the final x postion
        arcToInner.setY(positionY-radius-thickness); // same height as initial
        arcToInner.setRadiusX(radius);
        arcToInner.setRadiusY(radius);
        
        // return to original x and y position
        MoveTo moveTo2 = new MoveTo();
        moveTo2.setX(positionX-radius); 
        moveTo2.setY(positionY-radius-thickness);
        
        //draw a line to the outer semicircle
        HLineTo hLineToRightLeg = new HLineTo();
        hLineToRightLeg.setX(positionX-radius-thickness);
        
        //draw outer semicircle
        ArcTo arcTo = new ArcTo();
        arcTo.setX(positionX + radius + thickness);// outer x postion, equals the inner semicircle + thickness
        arcTo.setY(positionY - radius - thickness); // same as inner semicircle
        arcTo.setRadiusX(radius+thickness);
        arcTo.setRadiusY(radius+thickness);
        
        HLineTo hLineToLeftLeg = new HLineTo();
        hLineToLeftLeg.setX(positionX+radius); // draw line back to inner semircle, to complete the arc

        this.getElements().add(initialMoveTo);
        this.getElements().add(arcToInner);
        this.getElements().add(moveTo2);
        this.getElements().add(hLineToRightLeg);
        this.getElements().add(arcTo);
        this.getElements().add(hLineToLeftLeg);

    } 
    
    public void createBallPath(Ball ball){
        ball.setCenterX(positionX-radius+ball.getRadius());
        ball.setCenterY(positionY-radius-thickness);
        
        initialBallPositionX = positionX-radius+ball.getRadius();
        initialBallPositionY = positionY-radius-thickness;
        
        
        //create path for the ball
        
        //starting position
        MoveTo initialBallPos = new MoveTo();
        initialBallPos.setX(positionX-radius+ball.getRadius());
        initialBallPos.setY(positionY-radius-thickness);
        
        ArcTo ballArc = new ArcTo();
        ballArc.setX(positionX+radius-ball.getRadius());
        ballArc.setY(positionY-radius-thickness);
        ballArc.setRadiusX(radius-ball.getRadius());
        ballArc.setRadiusY(radius-ball.getRadius());
       
        Path ballPath = new Path();
        ballPath.getElements().add(initialBallPos);
        ballPath.getElements().add(ballArc);
        ball.setBallPath(ballPath);
        
    }
    
    /*
     * Logarithmic growth is best, since this way the block starts off at a
     * resonable size and yet doesn't get so big that it goes out of the screen.
     * This allows the simulation to be able to handle a larger range of mass
     * values.
     */
  
    public final void determineAndSetRampRadius()
    {
        radius = 100 * Math.log( radius/ 2) + 200;
       
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }
    
    

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public Color getRampColor() {
        return rampColor;
    }

    public void setRampColor(Color rampColor) {
        this.rampColor = rampColor;
    }

    public double getInitialBallPositionX() {
        return initialBallPositionX;
    }

    public void setInitialBallPositionX(double initialBallPositionX) {
        this.initialBallPositionX = initialBallPositionX;
    }

    public double getInitialBallPositionY() {
        return initialBallPositionY;
    }

    public void setInitialBallPositionY(double initialBallPositionY) {
        this.initialBallPositionY = initialBallPositionY;
    }
    
    
    
}
