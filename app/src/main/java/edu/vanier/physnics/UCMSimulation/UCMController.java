/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UCMSimulation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 *
 * @author Admin
 */
public class UCMController extends Stage{
        
    @FXML
    Button pauseButton;
    @FXML
    Button playButton;
    @FXML
    Button resetButton;  
    @FXML
    TextField radiusTextField;
    @FXML
    Slider radiusSlider;
    @FXML
    TextField speedTextField;
    @FXML
    Slider speedSlider;
    @FXML
    TextField massTextField;
    @FXML
    Slider massSlider;
    @FXML
    Button submitButton;
    @FXML
    Text centrAccelText;
    @FXML
    Text forceText;
    
    @FXML
    void initialize(){
        System.out.println("Booting up simulation...");
        pauseButton.setDisable(true);
        resetButton.setDisable(true);
        playButton.setDisable(true);
        pause();
        reset();
        play();
        submitSimulation();
    }
    
    @FXML
    public double retrieveRadiusTextField(){
        double d = 0;
        try {
            d = Double.valueOf(radiusTextField.getText());
        } catch (Exception e) {
            System.out.println("Error");
        }
        return d;
    }
    
    @FXML
    public double retrieveSpeedTextField(){
        double d = 0;
        try {
            d = Double.valueOf(speedTextField.getText());
        } catch (Exception e) {
            System.out.println("Error");
        }
        return d;
    }

    @FXML
    public double retrieveMassTextField(){
        double d = 0;
        try {
            d = Double.valueOf(massTextField.getText());
        } catch (Exception e) {
            System.out.println("Error");
        }
        return d;
    }
    
    @FXML
    void pause(){
        pauseButton.setOnAction((event) -> {
            System.out.println("pausing...");
            pauseButton.setDisable(true);
            playButton.setDisable(false);
            resetButton.setDisable(false);
        });
    }
    
    @FXML
    void play(){
        playButton.setOnAction((event) -> {
            System.out.println("playing...");
            playButton.setDisable(true);
            pauseButton.setDisable(false);
            resetButton.setDisable(false);
        });
    }

    @FXML
    void reset(){
        resetButton.setOnAction((event) -> {
            System.out.println("resetting...");
            resetButton.setDisable(true);
            pauseButton.setDisable(false);
            playButton.setDisable(false);
        });
    }    
    
    @FXML
    void submitSimulation(){
        submitButton.setOnAction((event) -> {

            double radius = retrieveRadiusTextField();
            double speed = retrieveSpeedTextField();
            double mass = retrieveMassTextField();
            System.out.println("Printing: " 
                                + "\nRadius: " + radius
                                + "\nSpeed: " + speed
                                + "\nMass: " +mass);
            
            Car car = new Car(speed, radius, mass);

            centrAccelText.setText(String.valueOf(Formulas.calculateAccelerationCentr(car)));
            forceText.setText(String.valueOf(Formulas.calculateForce(car)));
        });
    }
}
