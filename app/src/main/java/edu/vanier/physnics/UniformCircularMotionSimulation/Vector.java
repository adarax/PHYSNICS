/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A class used to represent and display the acceleration and force vectors.
 * @author Admin
 */
public class Vector {
    
    private final Image arrowBodyObject;
    private ImageView arrowBody;
    private String vectorType;
    private double forceMagnitude;

    public Vector(double xCoordinate, double yCoordinate, double forceMagnitude, String vectorType) {
        this.arrowBodyObject = new Image(getClass().getResourceAsStream("/images/uniform_circular_motion_vector_" + determineType(vectorType) + ".png"));
        this.arrowBody = new ImageView(arrowBodyObject);
        this.vectorType = vectorType;
        this.forceMagnitude = forceMagnitude;
        this.arrowBody.setRotate(90);
        this.arrowBody.setLayoutX(xCoordinate);
        this.arrowBody.setLayoutY(yCoordinate);
    }
    
    public Vector(double xCoordinate, double yCoordinate, double fitWidth, double fitHeight, String vectorType) {
        this.arrowBodyObject = new Image(getClass().getResourceAsStream("/images/uniform_circular_motion_vector_" + determineType(vectorType) + ".png"));
        this.arrowBody = new ImageView(arrowBodyObject);
        this.vectorType = vectorType;
        this.arrowBody.setLayoutX(xCoordinate);
        this.arrowBody.setLayoutY(yCoordinate);
        arrowBody.setRotate(40);
        arrowBody.setFitWidth(fitWidth);
        arrowBody.setFitHeight(fitHeight);
   }   
    
    private String determineType(String vectorType){
        if (vectorType.equals("FORCE")) {
            return "force_magnitude";
        }
        
        return "acceleration_magnitude";
    }
    
    public void setOpacity(double opacity){
        arrowBody.setOpacity(opacity);
    }

    public String getVectorType() {
        return this.vectorType;
    }

    public void setVectorType(String vectorType) {
        this.vectorType = vectorType;
    }

    public double getForceMagnitude() {
        return this.forceMagnitude;
    }

    public void setForceMagnitude(double forceMagnitude) {
        this.forceMagnitude = forceMagnitude;
    }  

    public ImageView getArrowBody() {
        return this.arrowBody;
    }

    public void setArrowBody(ImageView arrowBody) {
        this.arrowBody = arrowBody;
    }   
}
