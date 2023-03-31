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

    public Ramp(double radius, double thickness, double positionX, double positionY, Color rampColor) {
        this.radius = radius;
        this.thickness = thickness;
        this.positionX = positionX;
        this.positionY = positionY;
        this.rampColor = rampColor;
        
        drawRamp();
    }
    
    /**
     * position x and y refer to the bottom middle of the outer semicircle. 
     * Radius is for the inner semicircle. The thickness refers to the radius between the outer and
     * inner semicircle.
     */
    public void drawRamp(){
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
        //ball.setFill(ballColor);
        
        //create path for the ball
        
        //starting position
        MoveTo initialBallPos = new MoveTo();
        initialBallPos.setX(positionX-radius+ball.getRadius());
        initialBallPos.setY(positionY-radius-thickness);
        
        ArcTo ballArc = new ArcTo();
        ballArc.setX(positionX+radius-ball.getRadius());
        ballArc.setY(positionY-radius-thickness);
        //I dont understand how this works
        ballArc.setRadiusX(radius-ball.getRadius());
        ballArc.setRadiusY(radius-ball.getRadius());
        //ballArc.setXAxisRotation(180);
        
        Path ballPath = new Path();
        ballPath.getElements().add(initialBallPos);
        ballPath.getElements().add(ballArc);
        ball.setBallPath(ballPath);
        
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
    
    
    
}