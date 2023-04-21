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
        mediaButtonText.setText("The green “Play” button will start the animation\n"
                + "The yellow “Pause” button will stop the animation\n"
                + "The red “Restart” button will return the ball to its original position");
    }
    
}
