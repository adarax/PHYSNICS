/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

/**
 * A class to store all the "magic numbers" and constants used in the simulation.
 * @author Victor-Pen
 */
public class Settings {
    /**
     * The x coordinate of the center where everything which revolve about.
     */
    static final double CENTER_MARKER_X_COORDINATE = 800;
    /**
     * The x coordinate of the center where everything which revolve about.
     */
    static final double CENTER_MARKER_Y_COORDINATE = 400;
    /**
     * The initial radius set for the revolving car.
     */
    static final double CENTER_MARKER_INITIAL_RADIUS_REVOLVING_CAR = 200;
    /**
     * The initial radius set for the vectors.
     */
    static final double CENTER_MARKER_INITIAL_RADIUS_REVOLVING_VECTORS = 180;

    /**
     * The maximum radius of the simulation for the rectangle car.
     */
    static final double MAX_RADIUS_RECTANGLE = 200+8*(40-25);
    
    /**
     * The maximum radius of the simulation for the vectors.
     */
    static final double MAX_RADIUS_VECTORS = 180+180/25*(40-25);
    
    /**
     * The width of the rectangle.
     */
    static final double RECTANGLE_WIDTH = 30;
    
    /**
     * The width of the rectangle.
     */
    static final double RECTANGLE_HEIGHT = 50;
    
    /**
     * The initial radius set for the car.
     */
    static final double CAR_INITIAL_RADIUS_METERS = 20;
    
    /**
     * The initial speed set for the car.
     */
    static final double CAR_INITIAL_SPEED_METERS_PER_SECONDS = 10;  
    
    /**
     * The initial mass set for the car.
     */
    static final double CAR_INITIAL_MASS_KILOGRAMS = 20;
    
    /**
     * The biggest radius that the simulation can visually display
     */
    static final double SIMULATION_MAXIMUM_RADIUS_METERS = 35;
    
    /**
     * The biggest radius that the simulation can visually display
     */
    static final double SIMULATION_MAXIMUM_MASS_KILOGRAMS = 40;

    /**
     * The biggest radius that the simulation can visually display
     */
    static final double SIMULATION_MAXIMUM_SPEED_METERS_PER_SECONDS = 200;

    /**
     * The maximum that one can input in a TextField
     */
    static final double SIMULATION_INPUT_MAXIMUM = 10000;
    /**
     * Contains the two vector types that are present in the simulation.
     */
    static final String[] VECTOR_TYPE = {"FORCE","ACCELERATION"};
    
    /**
     * The String to display if the simulation reaches past the limits of accuracy for the radius.
     */
    static final String RADIUS_LIMIT_MESSAGE = "Simulation can only show radius up to 35 m. Any radius set beyond that reflect the simulation of a radius of 35 m.";   
    
    /**
     * The String to display if the simulation reaches past the limits of accuracy for the speed.
     */
    static final String SPEED_LIMIT_MESSAGE = "Simulation can only show speeds up to 200 m/s. Any speed set beyond that reflect the simulation of a speed of 200 m/s.";
    
    /**
     * The String to display if the simulation reaches past the limits of accuracy for the mass.
     */    
    static final String MASS_LIMIT_MESSAGE = "Simulation can only simulate masses up to 40 kg. Any mass set beyond that reflect the simulation of a mass of 40 kg.";
}
