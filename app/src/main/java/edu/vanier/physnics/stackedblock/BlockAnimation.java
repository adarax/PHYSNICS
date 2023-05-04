package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author adarax
 */
public class BlockAnimation {

    private final int floorHeight = 50;

    /**
     * Sets up the blocks in their initial positions, and is used when the
     * simulation is reset.
     *
     * Block parameters are used to determine the size of the blocks. A greater
     * mass of the block will make the block appear bigger.
     *
     * The animationPane parameter is the Pane in which the animation takes
     * place in the GUI.
     *
     * In this library, Rectangles are drawn from top to bottom, so anything the
     * block must be above must be subtracted from the paneHeight.
     *
     * When calculating the Y coordinates, the -1 is to prevent overlap with the
     * border of the Gridpane.
     *
     * @param topBlock
     * @param bottomBlock
     * @param animationPane
     */
    public void situateBlocks(Block topBlock, Block bottomBlock, Pane animationPane)
    {
        double paneWidth = animationPane.getWidth();
        double paneHeight = animationPane.getHeight();

        drawBlocks(topBlock, bottomBlock);

        double bottomBlockYCoordinate = paneHeight - this.floorHeight
                - bottomBlock.getDrawingHeight() - 1;

        double topBlockYCoordinate = paneHeight - this.floorHeight
                - bottomBlock.getDrawingHeight()
                - topBlock.getDrawingHeight() - 1;

        // Center the blocks
        bottomBlock.setLayoutX(paneWidth / 2 - bottomBlock.getDrawingWidth() / 2);
        bottomBlock.setLayoutY(bottomBlockYCoordinate);

        topBlock.setLayoutX(paneWidth / 2 - topBlock.getDrawingWidth() / 2);
        topBlock.setLayoutY(topBlockYCoordinate);

        animationPane.getChildren().addAll(topBlock, bottomBlock);
    }

    public void drawBlocks(Block topBlock, Block bottomBlock)
    {
        ArrayList<Block> blocks = new ArrayList<Block>() {
            {
                add(topBlock);
                add(bottomBlock);
            }
        };

        for (Block block : blocks)
        {
            block.determineAndSetDrawingHeight();
            block.determineAndSetDrawingWidth();
            block.setBlockDrawing(new Rectangle(block.getDrawingWidth(), block.getDrawingHeight()));
            block.getBlockDrawing().setFill(block.determineColor());
            block.setNameTag(new Label(block.getName()));
            block.getNameTag().setFont(new Font("SansSerif Bold", block.determineLabelFontSize()));
            block.draw();
        }
    }

    public void drawFloor(Pane animationPane)
    {
        double paneWidth = animationPane.getWidth();
        double paneHeight = animationPane.getHeight();

        Rectangle floorDrawing = new Rectangle(0, paneHeight - this.floorHeight - 1,
                paneWidth - 1, this.floorHeight);
        floorDrawing.setFill(Color.web("807979"));

        animationPane.getChildren().add(floorDrawing);
    }
    
    
    // TODO: implement animation using guidelines commented below
    
    public void play(Vector netForceVectorTopBlock, Vector netForceVectorBottomBlock, Block topBlock, Block bottomBlock)
    {
        // Resolve the direction of the blocks
        // From net force, get acceleration of block since mass is known
        
        // If top and bottom block are no longer touching, top block should fall off
        // at gravitational acceleration minus the opposing vertical forces
    }
    
    public void pause()
    {
    }
    
    public void stop()
    {
        // Go to initial position, clear vectors
    }
    
}
