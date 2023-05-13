package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author adarax
 */
public class BlockFormulasTest {

    /**
     * Test of calculateNormalForceMagnitude method, of class BlockFormulas.
     */
    @Test
    public void testCalculateNormalForceMagnitude()
    {
        System.out.println("calculateNormalForceMagnitude");
        double opposingForces = 25.3;
        double[] contributingMassInKg = {10, 15};
        BlockFormulas instance = new BlockFormulas();
        double expResult = 219.95;
        double result = instance.calculateNormalForceMagnitude(opposingForces, contributingMassInKg);
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of calculateFrictionVector method, of class BlockFormulas.
     */
    @Test
    public void testCalculateFrictionVector()
    {
        System.out.println("calculateFrictionVector");
        double coefficientOfFriction = 0.7;
        double normalForceMagnitude = 219.95;
        double magnitudeCorrespondingVector = 10.0;
        double directionCorrespondingVector = 45.0;
        Vector correspondingForceVector = new Vector(magnitudeCorrespondingVector, directionCorrespondingVector, Vector.FORCE_TYPE.APPLIED);
        BlockFormulas instance = new BlockFormulas();
        Vector expResult = new Vector(7.07, 180, Vector.FORCE_TYPE.FRICTION);
        Vector result = instance.calculateFrictionVector(coefficientOfFriction, normalForceMagnitude, correspondingForceVector);

        /*
         * To check equality between Vector objects, comparing them directly will always
         * yield false as they are different instances, even if their values are the same.
         * Using toString() method resolves this issue.
         */
        assertEquals(expResult.toString(), result.toString());
    }

    /**
     * Test of calculateNetForceVector method, of class BlockFormulas.
     */
    @Test
    public void testCalculateNetForceVector()
    {
        System.out.println("calculateNetForceVector");
        Vector vectorAppliedForce = new Vector(15, 120, Vector.FORCE_TYPE.APPLIED);
        Vector vectorFrictionForce = new Vector(2.54, 0, Vector.FORCE_TYPE.FRICTION);
        ArrayList<Vector> forcesExperienced = new ArrayList<>(List.of(vectorAppliedForce, vectorFrictionForce));
        BlockFormulas instance = new BlockFormulas();
        Vector expResult = new Vector(13.91,110.90, Vector.FORCE_TYPE.APPLIED);
        Vector result = instance.calculateNetForceVector(forcesExperienced);
        assertEquals(expResult.toString(), result.toString());
    }
    

    /**
     * Test of getProperDirection method, of class BlockFormulas.
     */
    @Test
    public void testGetProperDirection()
    {
        System.out.println("getProperDirection");
        double xComponent = 22.7;
        double yComponent = -9;
        BlockFormulas instance = new BlockFormulas();
        double expResult = 338.37;
        double result = Double.parseDouble(String.format("%.2f", instance.getProperDirection(xComponent, yComponent)));
        assertEquals(expResult, result);
    }
}
