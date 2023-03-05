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
        submitSimulation();
        setUp();
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
            pauseButton.setDisable(false);
            resetButton.setDisable(false);
        });
    }
    
    @FXML
    public void setUp(){
        pauseButton.setDisable(true);
        resetButton.setDisable(true);
        playButton.setDisable(true);
        radiusTextField.setText("5");
        massTextField.setText("10");
        speedTextField.setText("10");
        setSliders();
    }
    
    public void setSliders(){
        setSliderRange(radiusSlider, 1, 25);
        setSliderRange(massSlider, 0, 25);
        setSliderRange(speedSlider, 0, 30);
        
        linkSliderToTextField(radiusSlider, radiusTextField);
        linkSliderToTextField(massSlider, massTextField);
        linkSliderToTextField(speedSlider, speedTextField);
        
        linkTextFieldToSlider(massSlider, massTextField);
        linkTextFieldToSlider(radiusSlider, radiusTextField);
        linkTextFieldToSlider(speedSlider, speedTextField);
    }
    
    public void linkSliderToTextField(Slider slider, TextField textfield){
        slider.setOnMouseDragged((event) -> {
            textfield.setText(String.valueOf(slider.getValue()));
        });
    }
    
    public void linkTextFieldToSlider(Slider slider, TextField textfield){
        textfield.setOnAction((event) -> {
            slider.setValue(Double.valueOf(textfield.getText()));
        });
    }
    
    public void setSliderRange(Slider slider, double min, double max){
        slider.setMin(min);
        slider.setMax(max);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
    }
}
