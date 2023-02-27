/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

/**
 *
 * @author benja
 */
public class ConservationController {
    @FXML
    private Button btnFile;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnPause;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnReset;

    @FXML
    private CheckBox checkBoxFriction;

    @FXML
    private ChoiceBox<?> choiceBoxg;

    @FXML
    private ChoiceBox<?> choiceBoxu;

    @FXML
    private Slider sliderHeight;

    @FXML
    private Slider sliderMass;
    
    @FXML
    public void initialize(){
        
    }
}
