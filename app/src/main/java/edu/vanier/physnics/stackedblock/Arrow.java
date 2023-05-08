package edu.vanier.physnics.stackedblock;

import edu.vanier.physnics.stackedblock.Vector.FORCE_TYPE;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author adarax
 */
public class Arrow extends StackPane {

    private Block correspondingBlock;
    private Vector forceVector;
    private Image arrowBodyObject;
    private ImageView arrowBody;
    private Label nameTag;

    public Arrow(Vector forceVector, Block correspondingBlock)
    {
        this.correspondingBlock = correspondingBlock;
        this.forceVector = forceVector;

        this.arrowBodyObject = new Image(getClass().getResourceAsStream("/images/arrow_" + determineColor() + ".png"));
        this.arrowBody = new ImageView(arrowBodyObject);

        String magnitudeAsString = String.format("%.2f", forceVector.getMagnitudeInNewtons()) + " N";
        this.nameTag = new Label(magnitudeAsString);

        sizeAndPositionToBlock();
    }

    /**
     * Add the arrowBody and nameTag into a single easy-to-manipulate StackPane.
     */
    protected void assemble()
    {
        this.getChildren().clear();
        this.getChildren().addAll(arrowBody, nameTag);
    }

    
    protected final void sizeAndPositionToBlock()
    {
        double blockWidth = correspondingBlock.getDrawingWidth();
        double blockHeight = correspondingBlock.getDrawingHeight();

        /*
         * The following "magic" scalars are used to adjust the size of the
         * arrow so that it appears proportional to its corresponding block.
         * These scalars were determined experimentally to ensure that the
         * arrows are easily readable. 
         * 
         * By making the arrow size proportional to its corresponding block,
         * we can help users understand the relative magnitudes of the forces
         * being depicted, which is critical for accurate physics simulations.
         */
        arrowBody.setFitWidth(blockWidth * 0.5 + (forceVector.getMagnitudeInNewtons() * 0.75));
        arrowBody.setFitHeight(blockHeight * 0.4 + (forceVector.getMagnitudeInNewtons() * 0.25));
        nameTag.setFont(new Font("SansSerif", blockHeight * 0.1));
        nameTag.setTextFill(Color.color(1, 1, 1));
        nameTag.setTextAlignment(TextAlignment.CENTER);

        orientNameTagAndArrow();

        int quadrant = 0;

        try
        {
            quadrant = forceVector.findQuadrant();
        } catch (IllegalArgumentException ex)
        {
            System.out.println(ex.getMessage());
        }

        if (quadrant == 2 || quadrant == 3)
        {
            placeVectorOnSide(SIDE.LEFT);
        } else
        {
            placeVectorOnSide(SIDE.RIGHT);
        }
    }

    private void placeVectorOnSide(SIDE side)
    {
        double blockPositionX = correspondingBlock.getLayoutX(),
                blockPositionY = correspondingBlock.getLayoutY(),
                blockWidth = correspondingBlock.getDrawingWidth(),
                blockHeight = correspondingBlock.getDrawingHeight();

        switch (side)
        {
            case LEFT ->
            {
                /*
                 * If it's a friction force (which otherwise should not rotate)
                 * but the arrow needs to be on the left, flip the orientation
                 */
                if (forceVector.getForceType() == FORCE_TYPE.FRICTION)
                {
                    arrowBody.setRotate(180);
                }

                this.setLayoutX(blockPositionX - arrowBody.getFitWidth());
            }

            case RIGHT ->
            {
                this.setLayoutX(blockPositionX + blockWidth);
            }
        }

        double yValueOffset = 0;

        switch (forceVector.getForceType())
        {
            case APPLIED ->
            {
                yValueOffset = blockHeight * (2 / 3);
            }
            case FRICTION ->
            {
                yValueOffset = blockHeight / 3;
            }
        }

        this.setLayoutY(blockPositionY + yValueOffset);

    }

    private void orientNameTagAndArrow()
    {
        /*
         * The (* -1) is to get the Arrow to rotate CCW as the angle slider
         * value increases, which standarizes the angle to a Cartesian plane
         */
        double rotationInDegrees = forceVector.getDirectionInDegrees() * -1;

        /*
         * Only applied forces can rotate, friction vectors are always 0 or 180
         * degrees and normal force is always 90 degrees (to the floor)
         */
        if (forceVector.getForceType() == FORCE_TYPE.APPLIED)
        {
            arrowBody.setRotate(rotationInDegrees);

            // Keep text upright for readability
            if (Math.abs(rotationInDegrees) > 90 && Math.abs(rotationInDegrees) < 270)
            {
                nameTag.setRotate(rotationInDegrees + 180);
            } else
            {
                nameTag.setRotate(rotationInDegrees);
            }
        }
    }

    /**
     * Determines the color of the Arrow based on its FORCE_TYPE.
     *
     * @return the color of the arrow.
     */
    private String determineColor()
    {
        switch (forceVector.getForceType())
        {
            case APPLIED ->
            {
                return "red";
            }
            case FRICTION ->
            {
                return "blue";
            }
            default ->
                throw new IllegalArgumentException("Invalid force type");
        }
    }

    /**
     * Adjusts the layout of this Arrow object and any other Arrow objects in
     * the given ArrayList that overlap with it and have the same corresponding
     * block, to avoid visual overlap.
     *
     * @param arrows an ArrayList of Arrow objects that may overlap
     */
    protected void avoidOverlaps(ArrayList<Arrow> arrows)
    {
        double yCoordinateBlock = correspondingBlock.getLayoutY();

        double[] yValueBoundsOfBlock =
        {
            yCoordinateBlock, yCoordinateBlock + correspondingBlock.getDrawingHeight()
        };

        for (Arrow arrow : arrows)
        {
            if (!arrow.equals(this) && this.correspondingBlock.equals(arrow.getCorrespondingBlock()) && this.intersects(arrow.getBoundsInLocal()))
            {
                arrow.setLayoutY(yValueBoundsOfBlock[0]);
                this.setLayoutY(yValueBoundsOfBlock[1] - (this.arrowBody.getFitHeight()));
            }
        }
    }

    public Vector getForceVector()
    {
        return forceVector;
    }

    public ImageView getArrowBody()
    {
        return arrowBody;
    }

    public Block getCorrespondingBlock()
    {
        return correspondingBlock;
    }

    private enum SIDE {
        RIGHT,
        LEFT
    }
}
