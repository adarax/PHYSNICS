/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UCMSimulation;

import javafx.scene.shape.Line;

/**
 *
 * @author Admin
 */
public class Vector {
    private double startX;
    private double startY;
    private double angle;
    private Line vectorBody;
    private Line vectorHeadLeft;
    private Line vectorHeadRight;

    public Vector(double startX, double startY, double angle) {
        vectorBody.setStartX(startX);
        vectorBody.setStartX(startY);
        
        
        = new Line(startX, startY, startX+20*Math.cos(angle), startY+20*Math.sin(angle));
    }

    public void setLine(Line line, double startX, double startY, double angle, double length){
        line.setStartX(startX);
        line.setStartX(startY);    
        line.setEndX(startX+length*Math.cos(angle));
        line.setEndY(startY+length*Math.sin(angle));
    }
    
    public double getStartX() {
        return this.startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return this.startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }  
}
