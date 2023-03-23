package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author adarax
 */
public class BlockAnimation {

    private final int floorHeight = 50;

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

        // Drawn from top to bottom, so anything the block must be above must
        // be subtracted from the paneHeight
        // The -1 is to prevent overlap with the border of the gridpane
        double bottomBlockYCoordinate = paneHeight - this.floorHeight
                - bottomBlock.getDrawingHeight() - 1;

        double topBlockYCoordinate = paneHeight - this.floorHeight
                - bottomBlock.getDrawingHeight()
                - topBlock.getDrawingHeight() - 1;

        // Position the blocks in the center
        bottomBlock.setLayoutX(paneWidth / 2 - bottomBlock.getDrawingWidth() / 2);
        bottomBlock.setLayoutY(bottomBlockYCoordinate);

        topBlock.setLayoutX(paneWidth / 2 - topBlock.getDrawingWidth() / 2);
        topBlock.setLayoutY(topBlockYCoordinate);

        // Add to animation Pane
        animationPane.getChildren().addAll(bottomBlock, topBlock);
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

    public void drawFreeBodyDiagram(ArrayList<Vector> forcesExperienced)
    {
        // Animate vectors (arrows) onto block
    }
}
