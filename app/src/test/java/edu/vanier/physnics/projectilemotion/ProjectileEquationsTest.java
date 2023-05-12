/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.vanier.physnics.projectilemotion;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author vires
 */
public class ProjectileEquationsTest {
    
    /**
     * Test of getMaxHeight method, of class ProjectileEquations.
     */
    @Test
    public void testGetMaxHeight() {
        System.out.println("getMaxHeight");
        double launchAngleDeg = 30;
        double initialVelocityMPS = 20;
        double gravityAccelMPSS = 15;
        double expResult = 3.33;
        double result = Equations.getMaxHeight(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS);
        // Compares calculated result with result from the method.
        assertEquals(expResult, result, 0.1);
    }

    /**
     * Test of getXdisplacement method, of class ProjectileEquations.
     */
    @Test
    public void testGetXdisplacement() {
        System.out.println("getXdisplacement");
        double launchAngleDeg = 30;
        double initialVelocityMPS = 20;
        double gravityAccelMPSS = 15;
        double expResult = 23.09;
        double result = Equations.getXdisplacement(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS);
        // Compares calculated result with result from the method.
        assertEquals(expResult, result, 0.1);
    }

    /**
     * Test of getFlightTime method, of class ProjectileEquations.
     */
    @Test
    public void testGetFlightTime() {
        System.out.println("getFlightTime");
        double launchAngleDeg = 30;
        double initialVelocityMPS = 20;
        double gravityAccelMPSS = 15;
        double expResult = 1.33;
        double result = Equations.getFlightTime(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS);
        // Compares calculated result with result from the method.
        assertEquals(expResult, result, 0.1);
    }
    
}
