/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

/**
 * A class to store all the "magic numbers" and constants used in the simulation
 * @author Admin
 */
public class Settings {
    static final double CENTER_MARKER_X_COORDINATE = 503;
    static final double CENTER_MARKER_Y_COORDINATE = 346.5;
    static final double CENTER_MARKER_INITIAL_RADIUS = 200;
    
    static final String RADIUS_LIMIT_MESSAGE = "Simulation can only show radius up to 41 m. Any radius set beyond that reflect the simulation of a radius of 41 m.";    
    static final String SPEED_LIMIT_MESSAGE = "Simulation can only show speeds up to 200 m/s. Any speed set beyond that reflect the simulation of a speed of 200 m/s.";
    static final String MASS_LIMIT_MESSAGE = "Simulation can only simulate masses up to 40 kg. Any mass set beyond that reflect the simulation of a mass of 40 kg.";
}
