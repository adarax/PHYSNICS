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
 *
 * @author Admin
 */
public class ChangeColorWindow extends Stage{
    Stage colorChangeStage = new Stage();
    ColorPicker colorPicker = new ColorPicker(Color.DODGERBLUE);
    VBox vBox = new VBox(colorPicker);
    Scene scene = new Scene(vBox, 300, 50);           

    public ChangeColorWindow(Stage mainWindow){
        initModality(Modality.WINDOW_MODAL);
        initOwner(mainWindow);    
    }
    
    public void changeColor(Path path1, Path path2){
        colorChangeStage.setScene(scene);
        colorChangeStage.setResizable(false);
        colorChangeStage.setTitle("Change Path Color");
        colorChangeStage.initModality(Modality.WINDOW_MODAL);
        colorPicker.setValue((Color) path1.getStroke());
        colorChangeStage.showAndWait();    
        path1.setStroke(colorPicker.getValue());
        path2.setStroke(colorPicker.getValue());     
    }
    
    public void stop(MenuItem menuItem){
        menuItem.setDisable(false);
    }
}
