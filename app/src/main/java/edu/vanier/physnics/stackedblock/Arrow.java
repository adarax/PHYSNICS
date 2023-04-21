package edu.vanier.physnics.stackedblock;

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

    private final Block correspondingBlock;
    private final Vector forceVector;
    private final Image arrowBodyObject;
    private final ImageView arrowBody;
    private final Label nameTag;

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

    public void draw()
    {
        this.getChildren().clear();
        this.getChildren().addAll(arrowBody, nameTag);
    }

    public final void sizeAndPositionToBlock()
    {
        double blockWidth = correspondingBlock.getDrawingWidth();
        double blockHeight = correspondingBlock.getDrawingHeight();

        /*
         * The following scalars are used to make the arrow size look
         * proportional to its corresponding block.
         *
         * The same goes for the font size.
         */
        arrowBody.setFitWidth(blockWidth * 0.5 + (forceVector.getMagnitudeInNewtons() * 0.75));
        arrowBody.setFitHeight(blockHeight * 0.4 + (forceVector.getMagnitudeInNewtons() * 0.25));
        nameTag.setFont(new Font("SansSerif", blockHeight * 0.1));
        nameTag.setTextFill(Color.color(1, 1, 1));
        nameTag.setTextAlignment(TextAlignment.CENTER);
        
        orientNameTagAndArrow();
        
        // Position on block
        
        // TODO: Get quadrant and figure out how to place and angle arrows based on that
        int quadrant = 0;
        
        try
        {
            quadrant = forceVector.findQuadrant();
        }
        catch (IllegalArgumentException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        
        if (quadrant == 2 || quadrant == 3)
        {
            placeVectorOnSide(SIDE.LEFT);
        }
        else
        {
            placeVectorOnSide(SIDE.RIGHT);
        }
    }
    
    private void placeVectorOnSide(SIDE side)
    {
        double blockPositionX = correspondingBlock.getLayoutX();
        double blockPositionY = correspondingBlock.getLayoutY();
        
        double blockWidth = correspondingBlock.getDrawingWidth();
        double blockHeight = correspondingBlock.getDrawingHeight();
        
        
        // Nodes draw from the top-left corner
        switch (side)
        {
            case LEFT ->
            {
                /*
                 TODO: Use the type to prevent overlap of arrows.
                       Do this by having the lower half of the block reserved
                       for friction forces, top half for applied forces (using its type)
                
                 TODO: Friction is always less than or equal to the force applied
                       and so fricion vectors should not appear without a corresponding force
                       vector, nor should the magnitude ever get bigger than the force no matter the friction coeff.
                */
                
                this.setLayoutX(blockPositionX - arrowBody.getFitWidth());
                
                this.setLayoutY(blockPositionY + arrowBody.getFitHeight());
            }
            
            case RIGHT ->
            {
                this.setLayoutX(blockPositionX + blockWidth);
                this.setLayoutY(blockPositionY + arrowBody.getFitHeight());
            }
        }
    }
    
    private void orientNameTagAndArrow()
    {
        /*
         * The (* -1) is to get the Arrow to rotate CCW as the angle slider value 
         * increases, which standarizes the angle to a Cartesian plane
         */
        double rotationInDegrees = forceVector.getDirectionInDegrees() * -1;
        
        arrowBody.setRotate(rotationInDegrees);
        
        // Keep text upright (readable)
        if (Math.abs(rotationInDegrees) > 90 && Math.abs(rotationInDegrees) < 270)
        {
            nameTag.setRotate(rotationInDegrees + 180);
        }
        else
        {
            nameTag.setRotate(rotationInDegrees);
        }
    }
    
    /**
     * Determines the color of the Arrow based on its FORCE_TYPE.
     * 
     * @return the color of the arrow.
     */
    private String determineColor()
    {        
        switch(forceVector.getForceType())
        {
            case APPLIED ->
            {
                return "red";
            }
            case NORMAL ->
            {
                return "black";
            }
            case FRICTION ->
            {
                return "blue";
            }
            default ->
                throw new IllegalArgumentException("Invalid force type");
        }
    }
    
    private enum SIDE
    {
        RIGHT,
        LEFT
    }
}
