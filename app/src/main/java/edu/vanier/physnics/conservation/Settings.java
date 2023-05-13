/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Settings for the controller of the main conservation of energy simulation
 * @author Benjamin Pratt
 */
public class Settings {
    /**
     * Array of different gravitational accelerations from different planets
     *values obtained from https://space.nss.org/settlement/nasa/teacher/lessons/bryan/microgravity/gravback.html
     */
    public static final String[] GRAVITATIONAL_ACCELERATION_CONSTANTS = {
        "Earth: 9.8", "Moon: 1.6", "Mars: 3.7", "Venus: 8.87", "Jupiter: 24.5", "Sun: 275"};
    
    /**
     * Basic array of colors for the custom color picker window
     */
    public static final Color[] COLOR_LIST = {Color.RED, Color.YELLOW, Color.BLUE, 
        Color.GREEN, Color.PURPLE, Color.GREY, Color.PINK, Color.ORANGE};
    
    /**
     * Default mass of the ball in KG
     */
    public static double DEFAULT_MASS = 10;

    /**
     * Default mass of the ramp in meters
     */
    public static double DEFAULT_HEIGHT = 10;


    /**
     * default gravitational acceleration. Corresponds to the one on earth.
     */
    public static double DEFAULT_GRAVITATIONAL_ACCELERATION = 9.8;
    
    /**
     * Default ball color
     */
    public static final Color INITTIAL_BALL_COLOR = Color.RED; 

    /**
     * default ramp color
     */
    public static final Color INITTIAL_RAMP_COLOR = Color.BLACK;
    
    /**
     * height of the animation pane
     */
    public static final double HEIGHT_ANIMATION_PANE = 790;

    /**
     *width of the animation pane
     */
    public static final double WIDTH_ANIMATION_PANE = 1480;
    
    /**
     * radius of the ball
     */
    public static final double BALL_RADIUS = 20;
    
    /**
     * default radius of the ramp
     */
    public static final double INITIAL_RAMP_RADIUS = 500;

    /**
     * x position of the ramp
     */
    public static final double RAMP_POSITION_X = WIDTH_ANIMATION_PANE/2;

    /**
     * y position of the ramp
     */
    public static final double RAMP_POISTION_Y = HEIGHT_ANIMATION_PANE/2+300;

    /**
     * thickness of the ramp
     */
    public static final double RAMP_THICKNESS = 40;
    
    /**
     * X position of the ramp height text 
     */
    public static final double RAMP_HEIGHT_TEXT_POSITION_X = 50;

    /**
     * Y position of the ramp height text
     */
    public static final double RAMP_HEIGHT_TEXT_POSITION_Y = HEIGHT_ANIMATION_PANE/2;
    
    /**
     * x position of the mass text
     */
    public static final double MASS_TEXT_POSITION_X = WIDTH_ANIMATION_PANE/2-150;

    /**
     * Y position of the mass text
     */
    public static final double MASS_TEXT_POSITION_Y = 100;
    
    /**
     * X position of the acceleration text
     */
    public static final double ACCELERATION_TEXT_POSITION_X = WIDTH_ANIMATION_PANE-170;

    /**
     * Y position of the acceleration text
     */
    public static final double ACCELERATION_TEXT_POSITION_Y = HEIGHT_ANIMATION_PANE/4;
    
    /**
     * Default text font
     */
    public static final Font TEXT_FONT = new Font("Times new roman", 30);

    
    /**
     * X position of the gravitational acceleration arrow
     */
    public static final double ARROW_POSITION_X = WIDTH_ANIMATION_PANE-50;

    /**
     * Y position of the gravitational acceleration arrow
     */
    public static final double ARROW_POSITION_Y = 300;

    /**
     * Length of the gravitational acceleration arrow
     */
    public static final double ARROW_LENGTH = 300;

    /**
     *Thickness of the gravitational acceleration arrow
     */
    public static final double ARROW_THICKNESS = 5;

    /**
     * Point width of the gravitational acceleration arrow
     */
    public static final double ARROW_POINT_WIDTH = 50;
    
    /**
     * Width of the help menu window
     */
    public static final double HELP_MENU_WIDTH = 1920;

    /**
     * Height of the help menu window
     */
    public static final double HELP_MENU_HEIGHT = 1080;
    
    
}
