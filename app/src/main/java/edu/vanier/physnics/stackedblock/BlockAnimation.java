 package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.TranslateTransition;
 import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

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
        ArrayList<Block> blocks = new ArrayList<>(List.of(topBlock, bottomBlock));

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

    public void drawFloor(Pane animationPane, boolean isDarkMode)
    {
        double paneWidth = animationPane.getWidth();
        double paneHeight = animationPane.getHeight();

        Rectangle floorDrawing = new Rectangle(0, paneHeight - this.floorHeight - 1,
                paneWidth - 1, this.floorHeight);
        
        // Make floor a light gray in light mode and a dark gray in dark mode
        floorDrawing.setFill(isDarkMode ? Color.web("ccc1c1") : Color.web("807979"));

        animationPane.getChildren().add(floorDrawing);
    }

    // TODO: implement animation using guidelines commented below
    
    private TranslateTransition animationTopBlock;
    
    public void play(Block topBlock, Block bottomBlock, Pane animationPane)
    {
        // If top and bottom block are no longer touching, top block should fall off
        // at gravitational acceleration minus the opposing vertical forces
        // Watch out for NaN when forces are set to 0 in simulation, this could cause errors!
        BlockFormulas blockFormulas = new BlockFormulas();
            
        Vector topBlockNetForceVector = blockFormulas.calculateNetForceVector(topBlock.getForcesExperienced());
        double topBlockNetForceX = topBlockNetForceVector.asComponents().get(0);
        
        if (Double.isNaN(topBlockNetForceX))
        {
            topBlockNetForceX = 0;
        }
        
        double accelerationTopBlock = topBlockNetForceX / topBlock.getMass();

        Vector bottomBlockNetForceVector = blockFormulas.calculateNetForceVector(bottomBlock.getForcesExperienced());
        double bottomBlockNetForceX = bottomBlockNetForceVector.asComponents().get(0);
        
        if (Double.isNaN(bottomBlockNetForceX))
        {
            bottomBlockNetForceX = 0;
        }
        
        double accelerationBottomBlock = bottomBlockNetForceX / (bottomBlock.getMass() + topBlock.getMass());
        

        double displacementTopBlock = topBlock.getBoundsInParent().getMaxX() - topBlock.getWidth();
        
        System.out.println(displacementTopBlock);
        
        animationTopBlock = new TranslateTransition(Duration.seconds(calculateDuration(accelerationTopBlock, displacementTopBlock)), topBlock);
        
        // Move this much relative to its original position
        animationTopBlock.setByX(displacementTopBlock * (accelerationTopBlock > 0 ? 1 : -1));
        
        double u = animationTopBlock.getByX();
        System.out.println(u);
        
        // This needs to be custom, based on my own acc values
        animationTopBlock.setInterpolator(new AccelerateInterpolator((accelerationTopBlock)));
        
        System.out.println(topBlock.getBoundsInParent());
        
        animationTopBlock.play();
        
        animationTopBlock.setOnFinished(e -> System.out.println("done"));
        // onfinished, buttons need to be reenabled

    }

    private double calculateDuration(double acceleration, double displacement)
    {
        // x = displacement, a = acceleration
        // x = vo*t + 0.5*a*t^2
        // vo = 0
        // x = 0.5*a*t^2
        // => sqrt(2x/a) = t
        
        return Math.sqrt(2 * displacement / Math.abs(acceleration));        
    }
    
    public void pause()
    {
        animationTopBlock.pause();
    }

    
    // FIXME: see projects from last sem on how to use stop properly, and onFinished()?
    public void stop()
    {
        // Go to initial position, clear vectors
        animationTopBlock.stop();
        animationTopBlock.jumpTo(Duration.ZERO);
    }

}
