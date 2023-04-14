/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UCMSimulation;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Admin
 */
public class Vector {
    private double startX;
    private double startY;
    private double angle;
    private Line vectorBody = new Line();
    private Line vectorHeadLeft = new Line();
    private Line vectorHeadRight = new Line();

    public Vector(double startX, double startY, double angle) {
        this.startX = startX;
        this.startY = startY;
        this.angle = angle;
        
        vectorBody = setLine(vectorBody, startX, startY, angle, 40);
        vectorHeadLeft = setLine(vectorHeadLeft, vectorBody.getEndX(), vectorBody.getEndY(),
        (angle+157), 7);
        vectorHeadRight = setLine(vectorHeadRight, vectorBody.getEndX(), vectorBody.getEndY(),
        (angle+203), 7);
        //root.getChildren().add(line1);
    }
  
    public void addVector(Pane root){
        root.getChildren().addAll(vectorBody, vectorHeadLeft, vectorHeadRight);
    }
    
    private Line setLine(Line line, double startX, double startY, double angle, double length){
        line.setStartX(startX);
        line.setStartY(startY);    
        rotateVectorPart(line, startX, startY, angle, length);
        line.setStrokeWidth(3);
        return line;
    }
    
    public void rotateVector(double angle){
        rotateVectorPart(vectorBody, startX, startY, angle, 20);
        vectorHeadLeft = setLine(vectorHeadLeft, vectorBody.getEndX(), vectorBody.getEndY(),
        (angle+157), 15);
        vectorHeadRight = setLine(vectorHeadRight, vectorBody.getEndX(), vectorBody.getEndY(),
        (angle+203), 15);
    }
    
    public void rotateVectorPart(Line line, double startX, double startY, double angle, double length){
        line.setEndX(startX+length*Math.cos(Math.toRadians(angle)));
        line.setEndY(startY-length*Math.sin(Math.toRadians(angle)));        
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

    public Line getVectorBody() {
        return this.vectorBody;
    }

    public void setVectorBody(Line vectorBody) {
        this.vectorBody = vectorBody;
    }

    public Line getVectorHeadLeft() {
        return this.vectorHeadLeft;
    }

    public void setVectorHeadLeft(Line vectorHeadLeft) {
        this.vectorHeadLeft = vectorHeadLeft;
    }

    public Line getVectorHeadRight() {
        return this.vectorHeadRight;
    }

    public void setVectorHeadRight(Line vectorHeadRight) {
        this.vectorHeadRight = vectorHeadRight;
    }
}
