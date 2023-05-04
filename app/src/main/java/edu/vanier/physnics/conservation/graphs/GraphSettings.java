/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation.graphs;

import javafx.scene.paint.Color;

/**
 *
 * @author benja
 */
public class GraphSettings {
    public static final double PANE_WIDTH = 800;
    public static final double PANE_HEIGHT = 400;
    
    public static final double KINETIC_ENERGY_GRAPH_POSITION_X = 600;
    public static final double POTENTIAL_ENERGY_GRAPH_POSITION_X = 400;
    public static final double FRICTION_ENERGY_GRAPH_POSITION_x = 200;
    
    public static final double GRAPH_WIDTH = 50;
    public static final double MAX_GRAPH_HEIGHT = 200;
    public static final double GRAPHS_POSITION_Y = PANE_HEIGHT-300;
    
    public static final Color POTENTIAL_ENERGY_GRAPH_COLOR = Color.BLACK;
    public static final Color KINETIC_ENERGY_GRAPH_COLOR = Color.RED;
    public static final Color FRICTION_ENERGY_GRAPH_COLOR = Color.SILVER;
    
    public static final double VELOCITY_TEXT_POSITION_X = 550;
    public static final double VELOCITY_TEXT_POSITION_Y = 50;
    
    public static final double CURRENT_HEIGHT_TEXT_POSITION_X = 350;
    public static final double CURRENT_HEIGHT_TEXT_POSITION_Y = 50;
    
    public static final double KINETIC_ENERGY_TEXT_POSITION_X = PANE_WIDTH - 250 ; 
    public static final double KINETIC_ENERGY_TEXT_POSITION_Y = PANE_HEIGHT - 50;
    
    public static final double POTENTIAL_ENERGY_TEXT_POSITION_X = PANE_WIDTH/2-100; 
    public static final double POTENTIAL_ENERGY_TEXT_POSITION_Y = PANE_HEIGHT - 50;
    
    public static final double FRICTION_ENERGY_TEXT_POSITION_X = PANE_WIDTH/2-300; 
    public static final double FRICTION_ENERGY_TEXT_POSITION_Y = PANE_HEIGHT - 50;
    
    public static final double TOTAL_ENERGY_TEXT_POSITION_X = 50; 
    public static final double TOTAL_ENERGY_TEXT_POSITION_Y =  50;
    
    public static final double ENERGY_AXIS_POSITION_X = 100; 
    public static final double ENERGY_AXIS_START_POSITION_Y =  GRAPHS_POSITION_Y;
    public static final double ENERGY_AXIS_END_POSITION_Y = PANE_HEIGHT-100;
    public static final double ENERGY_AXIS_STROKE_WIDTH =  5;
    public static final double ENERGY_AXIS_WIDTH = 10;
     
    
}
