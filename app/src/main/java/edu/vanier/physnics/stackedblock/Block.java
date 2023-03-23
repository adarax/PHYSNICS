package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

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
    private final Rectangle blockDrawing;
    private final Label nametag;

    public Block(double mass, int blockNumber, ArrayList<Vector> forcesExperienced)
    {
        this.mass = mass;
        this.blockNumber = blockNumber;
        this.forcesExperienced = forcesExperienced;

        /*
         * Logarithmic growth is best, since this way the block starts off at a
         * resonable size and yet doesn't get so big that it goes out of the
         * screen. This allows the simulation to be able to handle a larger
         * range of mass values.
         */
        this.drawingHeight = 80 * Math.log(mass) + 80;
        this.drawingWidth = this.drawingHeight * 1.5;

        this.blockColor = determineColor();
        this.blockDrawing = new Rectangle(this.drawingWidth, this.drawingHeight);
        this.blockDrawing.setFill(this.blockColor);

        this.nametag = new Label(determineLabel());
        this.nametag.setFont(new Font("SansSerif Bold", determineLabelFontSize()));

        this.getChildren().addAll(blockDrawing, nametag);
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

    public double getDrawingHeight()
    {
        return drawingHeight;
    }

    public void setDrawingHeight(double drawingHeight)
    {
        this.drawingHeight = drawingHeight;
    }

    public double getDrawingWidth()
    {
        return drawingWidth;
    }

    public void setDrawingWidth(double drawingWidth)
    {
        this.drawingWidth = drawingWidth;
    }

    // The bottom block is blockNumber 0, the top is blockNumber 1
    public final String determineLabel()
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
}
