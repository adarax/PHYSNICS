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
        double mass = 0.0;
        double g = 0.0;
        double height = 0.0;
        double expResult = 0.0;
        double result = ConservationFormulas.potentialEnergy(mass, g, height);
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of kineticEnergy method, of class ConservationFormulas.
     */
    @Test
    public void testKineticEnergy() {
        System.out.println("kineticEnergy");
        double mass = 0.0;
        double velocity = 0.0;
        double expResult = 0.0;
        double result = ConservationFormulas.kineticEnergy(mass, velocity);
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentVelocity method, of class ConservationFormulas.
     */
    @Test
    public void testGetCurrentVelocity() {
        System.out.println("getCurrentVelocity");
        double TME = 0.0;
        double PE = 0.0;
        double mass = 0.0;
        double expResult = 0.0;
        double result = ConservationFormulas.getCurrentVelocity(TME, PE, mass);
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArcTime method, of class ConservationFormulas.
     */
    @Test
    public void testGetArcTime() {
        System.out.println("getArcTime");
        double height = 0.0;
        double g = 0.0;
        double expResult = 0.0;
        double result = ConservationFormulas.getArcTime(height, g);
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
