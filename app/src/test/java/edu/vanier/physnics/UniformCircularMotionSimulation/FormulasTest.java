/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

/**
 *
 * @author Admin
 */
public class FormulasTest {
    
    public FormulasTest() {
    }
    
    /**
     * Test of calculateAccelerationCentripetal method, of class Formulas.
     */
    @org.junit.jupiter.api.Test
    public void testCalculateAccelerationCentripetal() {
        System.out.println("calculateAccelerationCentripetal");
        Car car = null;
        double expResult = 0.0;
        double result = Formulas.calculateAccelerationCentripetal(car);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateForce method, of class Formulas.
     */
    @org.junit.jupiter.api.Test
    public void testCalculateForce() {
        System.out.println("calculateForce");
        Car car = null;
        double expResult = 0.0;
        double result = Formulas.calculateForce(car);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
