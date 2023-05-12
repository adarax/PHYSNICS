/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

/**
 * A class to store all the "magic numbers" and constants used in the simulation.
 * @author Admin
 */
public class Settings {
    /**
     * The x coordinate of the center where everything which revolve about.
     */
    static final double CENTER_MARKER_X_COORDINATE = 507;
    /**
     * The x coordinate of the center where everything which revolve about.
     */
    static final double CENTER_MARKER_Y_COORDINATE = 346.5;
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
    static final double MAX_RADIUS_RECTANGLE = 200+8*(41-25);
    
    /**
     * The maximum radius of the simulation for the vectors.
     */
    static final double MAX_RADIUS_VECTORS = 180+180/25*(41-25);
    
    /**
     * The width of the rectangle.
     */
    static final double RECTANGLE_WIDTH = 30;
    
    /**
     * The width of the rectangle.
     */
    static final double RECTANGLE_HEIGHT = 50;
    
    /**
     * Contains the two vector types that are present in the simulation.
     */
    static final String[] VECTOR_TYPE = {"FORCE","ACCELERATION"};
    
    static final String RADIUS_LIMIT_MESSAGE = "Simulation can only show radius up to 41 m. Any radius set beyond that reflect the simulation of a radius of 41 m.";    
    static final String SPEED_LIMIT_MESSAGE = "Simulation can only show speeds up to 200 m/s. Any speed set beyond that reflect the simulation of a speed of 200 m/s.";
    static final String MASS_LIMIT_MESSAGE = "Simulation can only simulate masses up to 40 kg. Any mass set beyond that reflect the simulation of a mass of 40 kg.";
}
