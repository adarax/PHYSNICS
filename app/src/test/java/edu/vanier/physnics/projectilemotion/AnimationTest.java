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
public class AnimationTest {
    /**
     * Test of getHeightPixels method, of class Animation.
     */
    @Test
    public void testScaleHeightToPixels() {
        System.out.println("scaleHeightToPixels");
        double launchAngleDeg = 30;
        double xDisplacementPixels = 100;
        Animation instance = new Animation();
        double expResult = 49.07;
        double result = instance.getHeightPixels(launchAngleDeg, xDisplacementPixels);
        assertEquals(expResult, result, 0.1);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println(result);
    }
    
}
