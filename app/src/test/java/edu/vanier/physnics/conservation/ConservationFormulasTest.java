/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package edu.vanier.physnics.conservation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author benja
 */
public class ConservationFormulasTest {
    
    public ConservationFormulasTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of potentialEnergy method, of class ConservationFormulas.
     */
    @Test
    public void testPotentialEnergy() {
        System.out.println("potentialEnergy");
        double mass = 20.0;
        double g = 9.8;
        double height = 100;
        double expResult = 19600.0;
        double result = ConservationFormulas.potentialEnergy(mass, g, height);
        assertEquals(expResult, result, 0.1);
        System.out.println("Expected: " + expResult);
        System.out.println("Result: " + result);
    }

    /**
     * Test of kineticEnergy method, of class ConservationFormulas.
     */
    @Test
    public void testKineticEnergy() {
        System.out.println("kineticEnergy");
        double mass = 5;
        double velocity = 10.0;
        double expResult = 250;
        double result = ConservationFormulas.kineticEnergy(mass, velocity);
        assertEquals(expResult, result, 0.1);
        
    }

    /**
     * Test of getCurrentVelocity method, of class ConservationFormulas.
     */
    @Test
    public void testGetCurrentVelocity() {
        System.out.println("getCurrentVelocity");
        double TME = 20;
        double PE = 5;
        double mass = 2.0;
        double expResult = 3.87298;
        double result = ConservationFormulas.getCurrentVelocity(TME, PE, mass);
        System.out.println("Expected: " + expResult);
        System.out.println("Result: " + result);
        assertEquals(expResult, result, 0.1);
        
    }

    /**
     * Test of getArcTime method, of class ConservationFormulas.
     */
    @Test
    public void testGetArcTime() {
        System.out.println("getArcTime");
        double height = 25;
        double g = 1.6;
        double expResult = 11.18;
        double result = ConservationFormulas.getArcTime(height, g);
        assertEquals(expResult, result, 0.1);
       
    }
    
}
