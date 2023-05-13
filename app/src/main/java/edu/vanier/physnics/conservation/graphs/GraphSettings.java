/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation.graphs;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Settings for the graph window 
 * @author Benjamin Pratt
 */
public class GraphSettings {

    /**
     * Width of the graph window
     */
    public static final double PANE_WIDTH = 800;

    /**
     * Height of the graph window
     */
    public static final double PANE_HEIGHT = 400;
    
    /**
     * X position of the kinetic energy graph
     */
    public static final double KINETIC_ENERGY_GRAPH_POSITION_X = 600;

    /**
     * X position of the potential energy graph
     */
    public static final double POTENTIAL_ENERGY_GRAPH_POSITION_X = 400;

    
    /**
     * Width of the graphs
     */
    public static final double GRAPH_WIDTH = 50;

    /**
     * Maximum height of the graphs
     */
    public static final double MAX_GRAPH_HEIGHT = 200;

    /**
     * Y position of the graphs
     */
    public static final double GRAPHS_POSITION_Y = PANE_HEIGHT-300;
    
    /**
     * Color of the potential energy graph
     */
    public static final Color POTENTIAL_ENERGY_GRAPH_COLOR = Color.BLACK;

    /**
     * Color of the kinetic energy graph
     */
    public static final Color KINETIC_ENERGY_GRAPH_COLOR = Color.RED;

    
    
    /**
     * X position of the current velocity text
     */
    public static final double VELOCITY_TEXT_POSITION_X = 550;

    /**
     * Y position of the current velocity text
     */
    public static final double VELOCITY_TEXT_POSITION_Y = 50;
    
    /**
     * X position of the current height text
     */
    public static final double CURRENT_HEIGHT_TEXT_POSITION_X = 350;

    /**
     * Y position of the current height text
     */
    public static final double CURRENT_HEIGHT_TEXT_POSITION_Y = 50;
    
    /**
     * X position of the kinetic energy text
     */
    public static final double KINETIC_ENERGY_TEXT_POSITION_X = PANE_WIDTH - 250 ; 

    /**
     * Y position of the kinetic energy text
     */
    public static final double KINETIC_ENERGY_TEXT_POSITION_Y = PANE_HEIGHT - 50;
    
    /**
     * X position of the potential energy text
     */
    public static final double POTENTIAL_ENERGY_TEXT_POSITION_X = PANE_WIDTH/2-100; 

    /**
     * Y position of the potential energy text
     */
    public static final double POTENTIAL_ENERGY_TEXT_POSITION_Y = PANE_HEIGHT - 50;
    
    /**
     * X position of the total energy text
     */
    public static final double TOTAL_ENERGY_TEXT_POSITION_X = 50; 

    /**
     *Y position of the total energy text
     */
    public static final double TOTAL_ENERGY_TEXT_POSITION_Y =  50;
    
    /**
     * X position of the energy axis
     */
    public static final double ENERGY_AXIS_POSITION_X = 100; 

    /**
     * Y position of the energy axis
     */
    public static final double ENERGY_AXIS_START_POSITION_Y =  GRAPHS_POSITION_Y;

    /**
     * End position of the energy axis
     */
    public static final double ENERGY_AXIS_END_POSITION_Y = PANE_HEIGHT-100;

    /**
     * Stroke width of the energy axis
     */
    public static final double ENERGY_AXIS_STROKE_WIDTH =  5;

    /**
     * width of the energy axis
     */
    public static final double ENERGY_AXIS_WIDTH = 10;
    
    /**
     * Font used in the graph window
     */
    public static final Font GRAPH_TEXT_FONT = new Font("Times new roman", 20);
     
    
}
