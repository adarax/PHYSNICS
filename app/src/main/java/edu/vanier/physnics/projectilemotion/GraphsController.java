/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;


/**
 *
 * @author vires
 */
public class GraphsController {
    
    @FXML
    private LineChart<?, ?> velocityXvsTime;
    
    @FXML
    private LineChart<?, ?> velocityYvsTime;
    
    @FXML 
    private LineChart<?, ?> accelerationYvsTime;
    

    @FXML
    public void initalize() {
        
    }

}
