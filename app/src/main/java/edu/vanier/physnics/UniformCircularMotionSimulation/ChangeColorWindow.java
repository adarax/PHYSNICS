/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A class used to display the window where the user can change the color of the paths.
 * @author Admin
 */
public class ChangeColorWindow extends Stage{
    /**
     * The stage to set the color picker.
     */
    Stage colorChangeStage = new Stage();
    /**
     * The color picker that will be used to change the color of the path.
     */
    ColorPicker colorPicker = new ColorPicker(Color.DODGERBLUE);
    /**
     * A VBox to contain the color picker inside in the window.
     */
    VBox vBox = new VBox(colorPicker);
    /**
     * A scene to set the color picker and the VBox.
     */
    Scene scene = new Scene(vBox, 300, 50);           

    /**
     * Constructor that creates an instance of the class.
     * @param mainWindow the main window that will pop up the color picker window/
     */
    public ChangeColorWindow(Stage mainWindow){
        initModality(Modality.WINDOW_MODAL);
        initOwner(mainWindow);    
    }
    
    /**
     * Changes the color of the path
     * @param path the path whose color will change
     */
    public void changeColor(Path path){
        colorChangeStage.setScene(scene);
        colorChangeStage.setResizable(false);
        colorChangeStage.setTitle("Change Path Color");
        colorPicker.setValue((Color) path.getStroke());
        colorChangeStage.showAndWait();    
        path.setStroke(colorPicker.getValue());
    }
    
    /**
     * Disables the button used in the main stage to display the color changing 
     * window, preventing said window from being opened multiple times.
     * @param menuItem the menuItem to disable.
     */
    public void setDisableColorWindowButton(MenuItem menuItem){
        menuItem.setDisable(false);
    }
}
