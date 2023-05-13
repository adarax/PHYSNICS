/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Admin
 */
public class Vector {
    
    private final Image arrowBodyObject;
    private ImageView arrowBody;
    private Label nameTag = new Label();
    private String vectorType;
    private double forceMagnitude;

    public Vector(double xCoordinate, double yCoordinate, double forceMagnitude, String vectorType) {
        this.arrowBodyObject = new Image(getClass().getResourceAsStream("/images/arrow_" + determineColor(vectorType) + ".png"));
        this.arrowBody = new ImageView(arrowBodyObject);
        this.vectorType = vectorType;
        this.forceMagnitude = forceMagnitude;
        this.nameTag.setText(String.valueOf(forceMagnitude));
        arrowBody.setRotate(70);
        this.arrowBody.setLayoutX(xCoordinate);
        this.arrowBody.setLayoutY(yCoordinate);
    }
    
    public Vector(double xCoordinate, double yCoordinate, String vectorType) {
        this.arrowBodyObject = new Image(getClass().getResourceAsStream("/images/arrow_" + determineColor(vectorType) + ".png"));
        this.arrowBody = new ImageView(arrowBodyObject);
        this.vectorType = vectorType;
        this.nameTag.setText(vectorType);
        this.arrowBody.setLayoutX(xCoordinate);
        this.arrowBody.setLayoutY(yCoordinate);
        arrowBody.setRotate(40);
        arrowBody.setFitWidth(50);
        arrowBody.setFitHeight(50);
   }   
    
    private String determineColor(String vectorType){
        if (vectorType.equals("FORCE")) {
            return "black";
        }
        else if (vectorType.equals("FORCEXCOMPONENT")) {
            return "red";
        }
        return "blue";
    }

    public Label getNameTag() {
        return this.nameTag;
    }

    public void setNameTag(Label nameTag) {
        this.nameTag = nameTag;
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
