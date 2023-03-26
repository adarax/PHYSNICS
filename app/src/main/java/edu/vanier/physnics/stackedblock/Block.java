package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author adarax
 */
public class Block extends StackPane {

    private double mass;
    private int blockNumber;
    private double drawingHeight;
    private double drawingWidth;
    private ArrayList<Vector> forcesExperienced;
    private Color blockColor;
    private Rectangle blockDrawing;
    private String name;
    private Label nametag;

    public Block(double mass, int blockNumber)
    {
        this.mass = mass;
        
        determineAndSetDrawingHeight();
        determineAndSetDrawingWidth();
        
        this.blockNumber = blockNumber;
        this.blockColor = determineColor();
        this.name = determineName();
    }

    public double getMass()
    {
        return mass;
    }

    public void setMass(double mass)
    {
        this.mass = mass;
    }

    public int getBlockNumber()
    {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber)
    {
        this.blockNumber = blockNumber;
    }

    public ArrayList<Vector> getForcesExperienced()
    {
        return forcesExperienced;
    }

    public void setForcesExperienced(ArrayList<Vector> forcesExperienced)
    {
        this.forcesExperienced = forcesExperienced;
    }

    public Color getBlockColor()
    {
        return blockColor;
    }

    public void setBlockColor(Color blockColor)
    {
        this.blockColor = blockColor;
    }

    public Rectangle getBlockDrawing()
    {
        return blockDrawing;
    }

    public void setBlockDrawing(Rectangle blockDrawing)
    {
        this.blockDrawing = blockDrawing;
    }

    public Label getNametag()
    {
        return nametag;
    }

    public void setNametag(Label nametag)
    {
        this.nametag = nametag;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public void drawBlock()
    {
        this.getChildren().clear();
        this.getChildren().addAll(blockDrawing, nametag);
    }

    /*
     * Logarithmic growth is best, since this way the block starts off at a
     * resonable size and yet doesn't get so big that it goes out of the screen.
     * This allows the simulation to be able to handle a larger range of mass
     * values.
     */
    public final void determineAndSetDrawingWidth()
    {
        this.setDrawingWidth(drawingHeight * 1.5);
    }

    public final void determineAndSetDrawingHeight()
    {
        double heightValue = 80 * Math.log(mass) + 80;
        this.setDrawingHeight(heightValue);
    }

    public double getDrawingWidth()
    {
        return drawingWidth;
    }

    public void setDrawingWidth(double drawingWidth)
    {
        this.drawingWidth = drawingWidth;
    }

    public double getDrawingHeight()
    {
        return drawingHeight;
    }

    public void setDrawingHeight(double drawingHeight)
    {
        this.drawingHeight = drawingHeight;
    }

    // The bottom block is blockNumber 0, the top is blockNumber 1
    public final String determineName()
    {
        String label = "";

        switch (this.blockNumber)
        {
            case 0 ->
                label = "M1";
            case 1 ->
                label = "M2";
            default ->
                throw new IllegalArgumentException();
        }

        return label;
    }

    public final Color determineColor()
    {
        Color correspondingColor = null;

        switch (this.blockNumber)
        {
            case 0 ->
                correspondingColor = Color.web("46B198");
            case 1 ->
                correspondingColor = Color.web("D45D5D");
            default ->
                throw new IllegalArgumentException();
        }

        return correspondingColor;
    }

    /**
     * Using logarithmic scaling to determine the appropriate font size of the
     * block's name tag.
     *
     * @return The font size of the block's label.
     */
    public final int determineLabelFontSize()
    {
        return (int) (7 * Math.log(this.drawingHeight));
    }

    
    // TODO: no need for this in release, it's just for testing
    @Override
    public String toString()
    {
        return "Block{" + "mass=" + mass + ", blockNumber=" + blockNumber + ", drawingHeight=" + drawingHeight + ", drawingWidth=" + drawingWidth + ", forcesExperienced=" + forcesExperienced + '}';
    }
    
    
    
    
    
    
}
