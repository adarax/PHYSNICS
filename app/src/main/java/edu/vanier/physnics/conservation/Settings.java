/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physnics.conservation;

/**
 *
 * @author benja
 */
public class Settings {
    
     //values obtained from https://space.nss.org/settlement/nasa/teacher/lessons/bryan/microgravity/gravback.html
    public static final String[] GRAVITATIONAL_CONSTANTS = {
        "Earth: 9.8", "Moon: 1.6", "Mars: 3.7", "Venus: 8.87", "Jupiter: 24.5", "Sun: 275"};
    
    /*
    values for u obtained from https://www.engineersedge.com/coeffients_of_friction.htm
    Ball is assumed to be made of steel (TODO: find better values)
    */
    public static final String[] FRICTION_COEFFICIENTS = 
    {"Aluminium: 0.61", "Brass: 0.5", "Cast Iron: 0.4", "Copper: 0.53", "Steel: 0.8"};
    
    
    
    public static final double HEIGHT_ANIMATION_PANE = 790;
    public static final double WIDTH_ANIMATION_PANE = 1480;
    
    public static final double BALL_RADIUS = 20;
    
    public static final double RAMP_RADIUS = 500;
    public static final double RAMP_POSITION_X = WIDTH_ANIMATION_PANE/2;
    public static final double RAMP_POISTION_Y = HEIGHT_ANIMATION_PANE/2+300;
    public static final double RAMP_THICKNESS = 40;
    
    
    public static final double HEIGHT_TEXT_POSITION_X = 50;
    public static final double HEIGHT_TEXT_POSITION_Y = HEIGHT_ANIMATION_PANE/2;
    
    public static final double MASS_TEXT_POSITION_X = WIDTH_ANIMATION_PANE/2-150;
    public static final double MASS_TEXT_POSITION_Y = 100;
    
    public static final double ACCELERATION_TEXT_POSITION_X = WIDTH_ANIMATION_PANE-170;
    public static final double ACCELERATION_TEXT_POSITION_Y = HEIGHT_ANIMATION_PANE/4;
    
    public static final double FONT_SIZE = 30;
    public static final String TEXT_FONT = "Times new roman";
    
    
    public static final double ARROW_POSITION_X = WIDTH_ANIMATION_PANE-50;
    public static final double ARROW_POSITION_Y = 300;
    public static final double ARROW_LENGTH = 300;
    public static final double ARROW_THICKNESS = 5;
    public static final double ARROW_POINT_WIDTH = 50;
    
    
}