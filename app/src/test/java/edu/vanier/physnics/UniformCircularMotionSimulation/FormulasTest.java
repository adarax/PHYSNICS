/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
        Car car = new Car(25, 10, 10);
        double expResult = 62.5;
        double result = Formulas.calculateAccelerationCentripetal(car);
        assertEquals(expResult, result, 0.1);
        System.out.println(expResult);
        System.out.println(result);
    }

    /**
     * Test of calculateForce method, of class Formulas.
     */
    @org.junit.jupiter.api.Test
    public void testCalculateForce() {
        System.out.println("calculateForce");
        Car car = new Car(25, 10, 10);
        double expResult = 625;
        car.setAccelerationcentr(Formulas.calculateAccelerationCentripetal(car));
        System.out.println(car.getAccelerationcentr());
        double result = Formulas.calculateForce(car);
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result, 0.1);
        // TODO review the generated test code and remove the default call to fail.
    }    
}
