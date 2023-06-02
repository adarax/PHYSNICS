package edu.vanier.physnics.stackedblock;

import edu.vanier.physnics.stackedblock.BlockFrontEndController.POSITION;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A custom object built on top of StackPane that handles both the
 * front and back end of the Block in the simulation.
 * 
 * @author adarax
 */
public class Block extends StackPane {

    private double mass, drawingHeight, drawingWidth; /// missing units
    private POSITION blockId;
    private ArrayList<Vector> forcesExperienced;
    private Color blockColor;
    private Rectangle blockDrawing;
    private String name;
    private Label nameTag;

    /**
     * Constructor for the Block class.
     *
     * @param blockId The enum of the Block's position in the simulation
     */
    public Block(POSITION blockId)
    {
        this.mass = 1.0;

        determineAndSetDrawingHeight();
        determineAndSetDrawingWidth();

        this.blockId = blockId;
        this.blockColor = determineColor();
        this.name = determineName();
    }

    /**
     * Returns the mass of the Block.
     * 
     * @return the mass of the Block in kg
     */
    public double getMass()
    {
        return mass;
    }

    /**
     * Sets the mass of the Block.
     * 
     * @param mass the mass of the Block in kg
     */
    public void setMass(double mass)
    {
        this.mass = mass;
    }

    /**
     * Returns the enum of the Block's position in the simulation,
     * which is either TOP or BOTTOM.
     * 
     * @return enum of the Block's position in the simulation
     */
    public POSITION getBlockId()
    {
        return blockId;
    }

    /**
     * Sets the enum of the Block's position in the simulation,
     * which is either TOP or BOTTOM.
     * 
     * @param blockId enum of the Block's position in the simulation
     */
    public void setBlockId(POSITION blockId)
    {
        this.blockId = blockId;
    }

    /**
     * Returns a list of all the forces experienced by the Block. The list
     * is made up of Vector objects.
     * 
     * @return an ArrayList of Vector objects acting on the Block
     */
    public ArrayList<Vector> getForcesExperienced()
    {
        return forcesExperienced;
    }

    /**
     * Sets the list of all the forces experienced by the Block. The list
     * is made up of Vector objects.
     * 
     * @param forcesExperienced an ArrayList of Vector objects acting on the Block
     */
    public void setForcesExperienced(ArrayList<Vector> forcesExperienced)
    {
        this.forcesExperienced = forcesExperienced;
    }

    /**
     * Returns the color of the Block as a Color object.
     * 
     * @return the color of the Block
     */
    public Color getBlockColor()
    {
        return blockColor;
    }

    /**
     * Sets the color of the Block as a Color object.
     * 
     * @param blockColor the color of the Block
     */
    public void setBlockColor(Color blockColor)
    {
        this.blockColor = blockColor;
    }

    /**
     * Returns the Rectangle object that represents the Block in the simulation.
     * 
     * @return the Rectangle object of the Block
     */
    public Rectangle getBlockDrawing()
    {
        return blockDrawing;
    }

    /**
     * Sets the Rectangle object that represents the Block in the simulation.
     * 
     * @param blockDrawing the Rectangle object of the Block
     */
    public void setBlockDrawing(Rectangle blockDrawing)
    {
        this.blockDrawing = blockDrawing;
    }

    /**
     * Returns the Label object that represents the Block's name tag in the
     * simulation.
     * 
     * @return the Label object of the Block's name tag
     */
    public Label getNameTag()
    {
        return nameTag;
    }

    /**
     * Sets the Label object that represents the Block's name tag in the
     * simulation. The name tag's text is set to the Block's name.
     * 
     * @param nameTag the Label object of the Block's name tag
     */
    public void setNameTag(Label nameTag)
    {
        this.nameTag = nameTag;
    }

    /**
     * Returns the name of the Block, which is either M1 or M2.
     * 
     * @return the name of the Block
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the Block, which is either M1 or M2.
     * 
     * @param name the name of the Block as a String
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Assembles the Block's drawing and name tag into a StackPane for ease
     * of manipulation.
     */
    protected void assemble()
    {
        this.getChildren().clear();
        this.getChildren().addAll(blockDrawing, nameTag);
    }

    /**
     * Determines the width of the Block's drawing in the simulation based on
     * its height. For aesthetic purposes, the width is 1.5 times the height.
     * 
     * Then, it sets the width of the Rectangle object using setDrawingWidth().
     */
    protected final void determineAndSetDrawingWidth()
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
    protected final void determineAndSetDrawingHeight()
    {
        double heightValue = 100 * Math.log(mass / 2) + 40; /// if 40 is the default height, wrap that in a variable
        this.setDrawingHeight(heightValue);
    }

    /**
     * Returns the width of the Rectangle object that represents the Block in
     * the simulation.
     * 
     * @return the width of the Block's drawing in pixels
     */
    public double getDrawingWidth()
    {
        return drawingWidth;
    }

    /**
     * Sets the width of the Rectangle object that represents the Block in the
     * simulation.
     * 
     * @param drawingWidth the width of the Block's drawing in pixels
     */
    public void setDrawingWidth(double drawingWidth)
    {
        this.drawingWidth = drawingWidth;
    }

    /**
     * Returns the height of the Rectangle object that represents the Block in
     * the simulation.
     * 
     * @return the height of the Block's drawing in pixels
     */
    public double getDrawingHeight()
    {
        return drawingHeight;
    }

    /**
     * Sets the height of the Rectangle object that represents the Block in the
     * simulation.
     * 
     * @param drawingHeight the height of the Block's drawing in pixels
     */
    public void setDrawingHeight(double drawingHeight)
    {
        this.drawingHeight = drawingHeight;
    }

    /**
     * Determines the name of the Block based on its position in the simulation.
     * Name is either M1 or M2.
     * 
     * @return the name of the Block as a String
     */
    private String determineName()
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

    /**
     * Determines the color of the Block based on its position in the simulation.
     * 
     * @return the color of the Block as a Color object
     */
    protected final Color determineColor()
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
    protected final int determineLabelFontSize()
    {
        return (int) (7 * Math.log(this.drawingHeight)); /// if 7 is a default size, wrap this in a variable
    }

    /**
     * Using Arrow objects, assemble the vectors affecting the block 
     * and draw them in the simulation.
     *
     * @param animationPane the Pane that holds the simulation
     */
    protected void drawFreeBodyDiagram(Pane animationPane)
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
