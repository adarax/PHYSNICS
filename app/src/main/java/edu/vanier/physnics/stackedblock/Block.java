package edu.vanier.physnics.stackedblock;

import edu.vanier.physnics.stackedblock.BlockFrontEndController.POSITION;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author adarax
 */
public class Block extends StackPane {

    private double mass;
    private POSITION blockId;
    private double drawingHeight;
    private double drawingWidth;
    private ArrayList<Vector> forcesExperienced;
    private Color blockColor;
    private Rectangle blockDrawing;
    private String name;
    private Label nameTag;

    public Block(POSITION blockId)
    {
        this.mass = 1.0;

        determineAndSetDrawingHeight();
        determineAndSetDrawingWidth();

        this.blockId = blockId;
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

    public POSITION getBlockId()
    {
        return blockId;
    }

    public void setBlockId(POSITION blockId)
    {
        this.blockId = blockId;
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

    public Label getNameTag()
    {
        return nameTag;
    }

    public void setNameTag(Label nameTag)
    {
        this.nameTag = nameTag;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void draw()
    {
        this.getChildren().clear();
        this.getChildren().addAll(blockDrawing, nameTag);
    }

    public final void determineAndSetDrawingWidth()
    {
        this.setDrawingWidth(drawingHeight * 1.5);
    }

    /**
     * Sets the width and height of the Block's drawing in the simulation
     * based on its mass.
     * 
     * Logarithmic growth of width/height is best, since this way the block 
     * starts off at a reasonable size and yet doesn't get so big that it goes 
     * out of the view. This also allows the simulation to handle a larger 
     * spectrum of mass values.
     */
    public final void determineAndSetDrawingHeight()
    {
        double heightValue = 100 * Math.log(mass / 2) + 40;
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

    public final String determineName()
    {
        String label = "";

        switch (this.blockId)
        {
            case BOTTOM ->
                label = "M1";
            case TOP ->
                label = "M2";
            default ->
                throw new IllegalArgumentException();
        }

        return label;
    }

    public final Color determineColor()
    {
        Color correspondingColor = null;

        switch (this.blockId)
        {
            case BOTTOM ->
                // Light green
                correspondingColor = Color.web("46B198");
            case TOP ->
                // Light red
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
     * @return The font size of the block's label
     */
    public final int determineLabelFontSize()
    {
        return (int) (7 * Math.log(this.drawingHeight));
    }

    /**
     * Using Arrow objects, assemble the vectors affecting the block.
     *
     * @param animationPane the Pane that holds the simulation
     */
    public void drawFreeBodyDiagram(Pane animationPane)
    {
        ArrayList<Arrow> vectorDrawings = new ArrayList<>();
        
        for (Vector forceVector : forcesExperienced)
        {
            if (forceVector.getMagnitudeInNewtons() > 0)
            {
                Arrow vectorDrawing = new Arrow(forceVector, this);
                vectorDrawings.add(vectorDrawing);
            }
        }

        for (Arrow vectorDrawing : vectorDrawings)
        {
            vectorDrawing.sizeAndPositionToBlock();
            vectorDrawing.assemble();
            vectorDrawing.orientNameTagAndArrow();
            vectorDrawing.avoidOverlaps(vectorDrawings);
        }
        
        animationPane.getChildren().addAll(vectorDrawings);
    }
}
