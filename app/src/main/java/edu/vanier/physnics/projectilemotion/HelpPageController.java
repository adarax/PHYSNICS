/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 *
 * @author vires
 */
public class HelpPageController {
    @FXML
    private Text mediaButtonText;
    
    @FXML
    private Text sliderText;
    
    @FXML
    private Text graphButtonText;
    
    @FXML
    private Text clearValuesText;
    
    
    @FXML
    public void initialize() {
        mediaButtonText.setText("The green “Play” button will start the animation.\n"
                + "The yellow “Pause” button will stop the animation.\n"
                + "The red “Restart” button will return the ball to its original position.");
        
        sliderText.setText("Click & Drag the sliders to change the values. \n"
                + "     Initial velocity will change the velocity of the ball the moment it is shot from the cannon. \n"
                + "     Launch angle changes the angle at which the ball is shot. \n"
                + "     Gravitational acceleration is the downwards force applied to the ball. ");
        
        graphButtonText.setText("The “Graph” button will open a new page with graphs that represent the simulation. ");
        
        clearValuesText.setText("The “Clear values” button will reset the sliders to their default position. ");
    }
   
}
