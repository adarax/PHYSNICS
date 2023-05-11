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
    
    /**
     * Test of getAngle method, of class Formulas.
     */
    @org.junit.jupiter.api.Test    
    public void testGetAngle(){
        System.out.println("getAngle");
        double result = Formulas.getAngle(10, 10);
        double expResult = Math.PI/4;
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result, 0.1);
        // TODO review the generated test code and remove the default call to fail.
    } 
    
    /**
     * Test of determineQuadrant method, of class Formulas.
     */
    @org.junit.jupiter.api.Test 
    public void testDetermineQuadrant(){
        System.out.println("determineQuadrant");
        double result = Formulas.determineQuadrant(-45, 10, 10);
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
