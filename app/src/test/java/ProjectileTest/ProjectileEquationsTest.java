package ProjectileTest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import edu.vanier.physnics.projectilemotion.ProjectileEquations;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author vires
 */
public class ProjectileEquationsTest {
    
    public ProjectileEquationsTest() {
    }
    
    /**
     * Test of getMaxHeight method, of class ProjectileEquations.
     */
    @Test
    public void testGetMaxHeight() {
        System.out.println("getMaxHeight");
        double launchAngleDeg = 0.0;
        double initialVelocityMPS = 0.0;
        double gravityAccelMPSS = 0.0;
        ProjectileEquations instance = new ProjectileEquations();
        double expResult = 0.0;
        double result = instance.getMaxHeight(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getXdisplacement method, of class ProjectileEquations.
     */
    @Test
    public void testGetXdisplacement() {
        System.out.println("getXdisplacement");
        double launchAngleDeg = 0.0;
        double initialVelocityMPS = 0.0;
        double gravityAccelMPSS = 0.0;
        double expResult = 0.0;
        double result = ProjectileEquations.getXdisplacement(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFlightTime method, of class ProjectileEquations.
     */
    @Test
    public void testGetFlightTime() {
        System.out.println("getFlightTime");
        double launchAngleDeg = 0.0;
        double initialVelocityMPS = 0.0;
        double gravityAccelMPSS = 0.0;
        ProjectileEquations instance = new ProjectileEquations();
        double expResult = 0.0;
        double result = instance.getFlightTime(launchAngleDeg, initialVelocityMPS, gravityAccelMPSS);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
