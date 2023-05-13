/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Class that sets the text for the Help Page
 * 
 * @author vireshpatel43
 */
public class HelpPageController {
    
    //Text fields that are populated in the initialize() method.
    @FXML
    private Text mediaButtonText,
            settingsText,
            menuText,
            homeText,
            trailToggleText,
            sliderText,
            graphButtonText,
            clearValuesText;
    
    
    /**
     * Sets the text for each text field in the Help Page
     */
    @FXML
    public void initialize() {
        mediaButtonText.setText("The green \"Play\" button will start the animation.\n"
                + "The yellow \"Pause\" button will stop the animation.\n"
                + "The red \"Restart\" button will return the ball to its original position.");
        
        sliderText.setText("Click & Drag the sliders to change the values. \n"
                + "     Initial velocity will change the velocity of the ball the moment it is shot from the cannon. \n"
                + "     Launch angle changes the angle at which the ball is shot. \n"
                + "     Gravitational acceleration is the downwards force applied to the ball. ");
        
        graphButtonText.setText("The \"Graph\" button will open a new page with graphs that represent the simulation. ");
        
        clearValuesText.setText("The \"Clear values\" button will reset the sliders to their default position. ");
        
        settingsText.setText("The settings button will open a new page where you can modify cosmetic features\n"
                + "like trail colour, ball colour, and even the background.");
        
        trailToggleText.setText("The trail toggle will toggle a trail representing the path of the ball");
        
        homeText.setText("The home button will bring you back to the main menu.");
        
        menuText.setText("The menu tab will allow you to navigate between all of the simulations,\n"
                + "exit the application, or even go to the main menu.");
        
        
    }
}
   

