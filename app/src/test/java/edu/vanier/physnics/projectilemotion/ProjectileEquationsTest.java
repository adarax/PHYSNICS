/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing for Equations class
 * 
 * @author vireshpatel43
 */
public class ProjectileEquationsTest {
    
    /**
     * Test of getMaxHeightMeters method, of class ProjectileEquations.
     */
    @Test
    public void testGetMaxHeightMeters() {
        System.out.println("getMaxHeight");
        double launchAngleDegrees = 30;
        double initialVelocityMetersPerSecond = 20;
        double gravityMetersPerSecondSquared = 15;
        double expResult = 3.33;
        double result = Equations.getMaxHeightMeters(launchAngleDegrees, initialVelocityMetersPerSecond, gravityMetersPerSecondSquared);
        // Compares calculated result with result from the method.
        assertEquals(expResult, result, 0.1);
    }

    /**
     * Test of getXdisplacementMeters method, of class ProjectileEquations.
     */
    @Test
    public void testGetXdisplacementMeters() {
        System.out.println("getXdisplacement");
        double launchAngleDegrees = 30;
        double initialVelocityMetersPerSecond = 20;
        double gravityMetersPerSecondSquared = 15;
        double expResult = 23.09;
        double result = Equations.getXdisplacementMeters(launchAngleDegrees, initialVelocityMetersPerSecond, gravityMetersPerSecondSquared);
        // Compares calculated result with result from the method.
        assertEquals(expResult, result, 0.1);
    }

    /**
     * Test of getFlightTimeSeconds method, of class ProjectileEquations.
     */
    @Test
    public void testGetFlightTimeSeconds() {
        System.out.println("getFlightTime");
        double launchAngleDegrees = 30;
        double initialVelocityMetersPerSecond = 20;
        double gravityMetersPerSecondSquared = 15;
        double expResult = 1.33;
        double result = Equations.getFlightTimeSeconds(launchAngleDegrees, initialVelocityMetersPerSecond, gravityMetersPerSecondSquared);
        // Compares calculated result with result from the method.
        assertEquals(expResult, result, 0.1);
    }
    
    /**
     * Test of getXVelocityMetersPerSecond method, of class ProjectileEquations.
     */
    @Test
    public void testGetXVelocityMetersPerSecond() {
        System.out.println("getXVelocityMetersPerSecond");
        double launchAngleDegrees = 30;
        double initialVelocityMetersPerSecond = 20;
        double expResult = 17.32;
        double result = Equations.getXVelocityMetersPerSecond(launchAngleDegrees, initialVelocityMetersPerSecond);
        // Compares the calculated result with the result from the method
        assertEquals(expResult, result, 0.1);
    }
    
        /**
     * Test of getYVelocityMetersPerSecond method, of class ProjectileEquations.
     */
    @Test
    public void testGetYVelocityMetersPerSecond() {
        System.out.println("getYVelocityMetersPerSecond");
        double launchAngleDegrees = 30;
        double initialVelocityMetersPerSecond = 20;
        double gravityMetersPerSecondSquared = 15;
        double currentTimeSeconds = 0.5;
        double expResult = 2.5;
        double result = Equations.getYVelocityMetersPerSecond(launchAngleDegrees, initialVelocityMetersPerSecond, gravityMetersPerSecondSquared, currentTimeSeconds);
        // Compares the calculated result with the result from the method
        assertEquals(expResult, result, 0.1);
    }
}
