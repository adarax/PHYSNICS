
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.physnics.UniformCircularMotionSimulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Admin
 */
public class FormulasTest {
    
    public FormulasTest() {
    }
    
    /**
     * Test of calculateAccelerationCentripetalMetersPerSecondsSquared method, of class Formulas.
     */
    @org.junit.jupiter.api.Test
    public void testCalculateAccelerationCentripetalMetersPerSecondsSquared() {
        System.out.println("calculateAccelerationCentripetal");
        Car car = new Car(25, 10, 10);
        double expResult = 62.5;
        double result = Formulas.calculateAccelerationCentripetalMetersPerSecondsSquared(car);
        assertEquals(expResult, result, 0.1);
        System.out.println(expResult);
        System.out.println(result);
    }

    /**
     * Test of calculateForceNewtons method, of class Formulas.
     */
    @org.junit.jupiter.api.Test
    public void testCalculateForceNewtons() {
        System.out.println("calculateForce");
        Car car = new Car(25, 10, 10);
        double expResult = 625;
        car.setAccelerationCentripetalMetersPerSecondsSquared(Formulas.calculateAccelerationCentripetalMetersPerSecondsSquared(car));
        System.out.println(car.getAccelerationCentripetalMetersPerSecondsSquared());
        double result = Formulas.calculateForceNewtons(car);
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result, 0.1);
        // TODO review the generated test code and remove the default call to fail.
    } 
    
    /**
     * Test of getAngleDegrees method, of class Formulas.
     */
    @org.junit.jupiter.api.Test    
    public void testGetAngleDegrees(){
        System.out.println("getAngle");
        double result = Formulas.getAngleDegrees(10, 10);
        double expResult = Math.PI/4;
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result, 0.1);
        // TODO review the generated test code and remove the default call to fail.
    } 
    
    /**
     * Test of determineQuadrantDegrees method, of class Formulas.
     */
    @org.junit.jupiter.api.Test 
    public void testDetermineQuadrant(){
        System.out.println("determineQuadrant");
        double result = Formulas.determineQuadrantDegrees(-45, 10, 10);
        double expResult = 225;
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result, 0.1);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of returnMagnitudeXComponent method, of class Formulas.
     */
    @org.junit.jupiter.api.Test 
    public void testReturnMagnitudeXComponent(){
        System.out.println("returnMagnitudeXComponent");
        double result = Formulas.returnMagnitudeXComponent(30, 60);
        double expResult = 15;
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result, 0.1);
        // TODO review the generated test code and remove the default call to fail.    
    }
    
    /**
     * Test of returnMagnitudeYComponent method, of class Formulas.
     */
    @org.junit.jupiter.api.Test 
    public void testReturnMagnitudeYComponent(){
        System.out.println("returnMagnitudeYComponent");
        double result = Formulas.returnMagnitudeYComponent(30, 30);
        double expResult = 15;
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result, 0.1);
        // TODO review the generated test code and remove the default call to fail.     
    }      
    
    /**
    * Test of roundTwoDecimals method, of class Formulas.
    */   
    @org.junit.jupiter.api.Test 
    public void testRoundTwoDecimals(){
        System.out.println("roundTwoDecimals");
        double result = Formulas.roundTwoDecimals(30.2317824621487164728164121);
        double expResult = 30.23;
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result, 0.1);
        // TODO review the generated test code and remove the default call to fail.     
    }      
}
