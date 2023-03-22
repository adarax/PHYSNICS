package edu.vanier.physnics.stackedblock;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author adarax
 */
public class BlockAnimation {

    /**
     * @param topBlock
     * @param bottomBlock
     * @param animationPane
     *
     * Sets up the blocks in their initial positions, and is used when the
     * simulation is reset.
     *
     * Block parameters are used to determine the size of the blocks. A greater
     * mass of the block will make the block appear bigger.
     *
     * animationPane parameter is the Pane in which the animation takes place in
     * the GUI.
     */
    public void situateBlocks(Block topBlock, Block bottomBlock, Pane animationPane)
    {
        double paneWidth = animationPane.getWidth();
        double paneHeight = animationPane.getHeight();
        int floorHeight = 50;

        // The -1 is to prevent overlap with the border of the gridpane
        Rectangle paneFloor = new Rectangle(0, paneHeight - floorHeight - 1,
                paneWidth - 1, floorHeight);
        
        // TODO: find a darker brown
        paneFloor.setFill(Color.BROWN);

        // Determine height and width of blocks based on the mass
        // (1kg = 12.5px of height)
        
        // TODO: Maybe make the scale logarithmic, it gets big too fast, and 
        // this way there can be are bigger range of mass on the sliders
        double heightTopBlock = topBlock.getMass() * 12.5;
        double heightBottomBlock = bottomBlock.getMass() * 12.5;

        // Width is 1.75 times height of the block (for aesthetics)
        double widthTopBlock = heightTopBlock * 1.75;
        double widthBottomBlock = heightBottomBlock * 1.75;

        // Drawn from top to bottom, so anything the block must be above must
        // be subtracted from the paneHeight
        double bottomBlockYCoordinate = paneHeight - floorHeight
                - heightBottomBlock;
        double topBlockYCoordinate = paneHeight - floorHeight
                - heightBottomBlock - heightTopBlock;

        // Create Rectangle objects for the blocks
        Rectangle drawingBottomBlock = new Rectangle(paneWidth / 2
                - widthBottomBlock / 2, bottomBlockYCoordinate,
                widthBottomBlock, heightBottomBlock);

        Rectangle drawingTopBlock = new Rectangle(paneWidth / 2
                - widthTopBlock / 2, topBlockYCoordinate,
                widthTopBlock, heightTopBlock);

        // Give the blocks colour
        drawingTopBlock.setFill(Color.GREEN);
        drawingBottomBlock.setFill(Color.RED);

        // Add to animation Pane
        animationPane.getChildren().addAll(paneFloor,
                drawingBottomBlock, drawingTopBlock);
    }
}
