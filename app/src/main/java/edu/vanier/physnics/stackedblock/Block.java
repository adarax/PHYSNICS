package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;

/**
 *
 * @author adarax
 */
public class Block {
    
    private double mass;
    
    // Either 0, which is the bottom block
    // or 1, which is the top block
    private int blockNumber;

    private ArrayList<Vector> forcesExperienced;

    public Block(double mass, int blockNumber, ArrayList<Vector> forcesExperienced)
    {
        this.mass = mass;
        this.blockNumber = blockNumber;
        this.forcesExperienced = forcesExperienced;
    }
    
    public Vector calculateNetForceAndDirection(ArrayList<Vector> forcesExperienced)
    {
        // top block treated differently from bottom (friction mainly)
        // sum up x and y components, figure out magnitude and direction
        
        return null;
    }
    
    public void drawFreeBodyDiagram(ArrayList<Vector> forcesExperienced)
    {
        // Animate vectors (arrows) onto block
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public ArrayList<Vector> getForcesExperienced() {
        return forcesExperienced;
    }

    public void setForcesExperienced(ArrayList<Vector> forcesExperienced) {
        this.forcesExperienced = forcesExperienced;
    }
}
