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
 * @author Victor-Pen
 */
public class Vector {
    /**
     * The image of the arrow.
     */
    private final Image arrowBodyObject;
    /**
     * The image of the arrow as an ImageView.
     */
    private ImageView arrowBody;
    /**
     * The type of vector that is being displayed (either Force or Acceleration vector).
     */
    private String vectorType;
    /**
     * The magnitude of the vector.
     */
    private double magnitude;
    
    /**
     * A Constructor for the Vector class to be instantiated.
     * @param xCoordinate The initial x-Coordinate of the vector
     * @param yCoordinate The initial y-Coordinate of the vector
     * @param fitWidth The initial width of the vector
     * @param fitHeight The initial height of the vector
     * @param vectorType The type of vector (whether it be a force or acceleration vector)
     */
    public Vector(double xCoordinate, double yCoordinate, double fitWidth, double fitHeight, String vectorType) {
        this.arrowBodyObject = new Image(getClass().getResourceAsStream("/images/uniform_circular_motion_vector_" + determineType(vectorType) + ".png"));
        this.arrowBody = new ImageView(arrowBodyObject);
        this.vectorType = vectorType;
        this.arrowBody.setLayoutX(xCoordinate);
        this.arrowBody.setLayoutY(yCoordinate);
        arrowBody.setFitWidth(fitWidth);
        arrowBody.setFitHeight(fitHeight);
        if (vectorType.equals("FORCE")) {
            arrowBody.setOpacity(0.5);
        }
   }   
    
    /**
     * Determines the type of vector that is being instantiated, between an acceleration or a force vector. 
     * @param vectorType 
     * @return The String that indicates what type of vector it is
     */
    private String determineType(String vectorType){
        if (vectorType.equals("FORCE")) {
            return "force_magnitude";
        }
        
        return "acceleration_magnitude";
    }
    
    /**
     * Sets the opacity of the vector arrow.
     * @param opacity the value of the opacity, which is a double from 0 to 1.
     */
    public void setOpacity(double opacity){
        arrowBody.setOpacity(opacity);
    }

    /**
     * Returns the vector type of the vector, in a String.
     * @return the vector type of the vector, in a String.
     */
    public String getVectorType() {
        return this.vectorType;
    }

    /**
     * Setter for the vector type.
     * @param vectorType the type of vector as a String
     */
    public void setVectorType(String vectorType) {
        this.vectorType = vectorType;
    }

    /**
     * Getter for the magnitude.
     * @return the magnitude of the vector
     */
    public double getMagnitude() {
        return this.magnitude;
    }

    /**
     * Setter for the force magnitude.
     * @param magnitude the magnitude of the force to be set
     */
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }  

    /**
     * Getter for the arrowBody.
     * @return the ImageView of the arrowBody
     */
    public ImageView getArrowBody() {
        return this.arrowBody;
    }

    /**
     * Setter for the arrow body ImageView.
     * @param arrowBody the ImageView to set the arrowBody
     */
    public void setArrowBody(ImageView arrowBody) {
        this.arrowBody = arrowBody;
    }   
}
