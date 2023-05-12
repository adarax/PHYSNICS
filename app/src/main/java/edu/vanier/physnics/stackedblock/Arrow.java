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
import javafx.scene.transform.Rotate;

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

    /**
     * Constructor for the Arrow class.
     * 
     * @param forceVector the force vector to be represented by the Arrow
     * @param correspondingBlock the Block the Arrow is attached to
     */
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

    /**
     * Sizes and positions the Arrow to match the size and position of its
     * corresponding Block. This method is called whenever the corresponding Block is resized or
     * repositioned.
     * 
     * The size of the Arrow is proportional to the size of the Block it is
     * attached to. The position of the Arrow is determined by the position of
     * the Block it is attached to.
     * 
     * The Arrow is positioned on the left or right side of the Block depending
     * on the quadrant of the vector it represents.
     */
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
                blockWidth = correspondingBlock.getDrawingWidth();
        
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
                    this.setLayoutX(blockPositionX - arrowBody.getFitWidth());
                }
                else
                {
                    this.setLayoutX(blockPositionX);
                }
            }

            case RIGHT ->
            {
                this.setLayoutX(blockPositionX + blockWidth);
            }
        }

        // This will get modified later in avoidOverlaps()
        this.setLayoutY(blockPositionY);
    }

    
    /**
     * Rotates the Arrow and its nameTag to match the direction of the
     * forceVector.
     * 
     * Ensures the text on the nameTag is always readable by keeping it upright.
     */
    protected void orientNameTagAndArrow()
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
            if (Math.abs(rotationInDegrees) > 90 && Math.abs(rotationInDegrees) < 270)
            {
                Rotate rotate = new Rotate(rotationInDegrees, arrowBody.getLayoutX(), arrowBody.getLayoutY() + arrowBody.getFitHeight());
                this.getTransforms().add(rotate);
                nameTag.setRotate(180);
            } else
            {
                Rotate rotate = new Rotate(rotationInDegrees, arrowBody.getLayoutX(), arrowBody.getLayoutY() + arrowBody.getFitHeight());
                this.getTransforms().add(rotate);
            }
        }
    }

    /**
     * Determines the color of the Arrow based on its FORCE_TYPE.
     *
     * @return the color of the arrow as a String
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
                this.setLayoutY(yValueBoundsOfBlock[1] - this.arrowBody.getFitHeight());
            }
        }
    }

    /**
     * Returns the Vector object that this Arrow represents.
     * 
     * @return the Vector object represented by this Arrow
     */
    public Vector getForceVector()
    {
        return forceVector;
    }

    /**
     * Returns the manipulable ImageView object that represents the Arrow's
     * body. This is the object that is rotated to match the direction of the
     * forceVector.
     * 
     * @return the ImageView object that represents the Arrow's body
     */
    public ImageView getArrowBody()
    {
        return arrowBody;
    }

    /**
     * Returns the Block object that this Arrow is attached to. This is the
     * Block that the Arrow's forceVector is acting on.
     * 
     * @return the Block object that this Arrow is attached to
     */
    public Block getCorrespondingBlock()
    {
        return correspondingBlock;
    }

    private enum SIDE {
        RIGHT,
        LEFT
    }
}
