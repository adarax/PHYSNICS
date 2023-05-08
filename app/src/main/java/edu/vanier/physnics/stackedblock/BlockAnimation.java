package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
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

    
    /************************************************************************/
    /*                        Animation of simulation                       */
    /************************************************************************/
    
    
    private TranslateTransition animationTopBlock, animationBottomBlock;
    private AnimationTimer topBlockAnimationTimer, bottomBlockAnimationTimer;
    
    protected void createBlockAnimation(Block topBlock, Block bottomBlock, Pane animationPane)
    {
                                /* Top block */
        
        BlockFormulas blockFormulas = new BlockFormulas();
        
        Vector topBlockNetForceVector = blockFormulas.calculateNetForceVector(topBlock.getForcesExperienced());
        double topBlockNetForceX = topBlockNetForceVector.asComponents().get(0);

        if (Double.isNaN(topBlockNetForceX))
        {
            topBlockNetForceX = 0;
        }

        double accelerationTopBlock = topBlockNetForceX / topBlock.getMass(),
                displacementTopBlock = animationPane.getWidth() - topBlock.getLayoutX() - topBlock.getDrawingWidth(),
                durationOfAnimationTopBlock = calculateDuration(accelerationTopBlock, displacementTopBlock);

        animationTopBlock = new TranslateTransition(Duration.seconds(durationOfAnimationTopBlock), topBlock);
        animationTopBlock.setToX(displacementTopBlock);
        animationTopBlock.setInterpolator(new AccelerateInterpolator((accelerationTopBlock)));

        topBlockAnimationTimer = new AnimationTimer() {
            @Override
            public void handle(long l)
            {
                reactToConditions(topBlock, displacementTopBlock,accelerationTopBlock);
            }
        };

        topBlockAnimationTimer.start();
        animationTopBlock.play();
        
        
                                /* Bottom block */
        
        Vector bottomBlockNetForceVector = blockFormulas.calculateNetForceVector(bottomBlock.getForcesExperienced());
        double bottomBlockNetForceX = bottomBlockNetForceVector.asComponents().get(0);

        if (Double.isNaN(bottomBlockNetForceX))
        {
            bottomBlockNetForceX = 0;
        }

        double accelerationBottomBlock = bottomBlockNetForceX / (bottomBlock.getMass() + topBlock.getMass()),
                displacementBottomBlock = bottomBlock.getLayoutX(),
                durationOfAnimationBottomBlock = calculateDuration(accelerationBottomBlock, displacementBottomBlock);

        animationBottomBlock = new TranslateTransition(Duration.seconds(durationOfAnimationBottomBlock), bottomBlock);
        animationBottomBlock.setToX(displacementBottomBlock);
        animationTopBlock.setInterpolator(new AccelerateInterpolator((accelerationBottomBlock)));
        
        bottomBlockAnimationTimer = new AnimationTimer() {
            @Override
            public void handle(long l)
            {
                reactToConditions(bottomBlock, displacementBottomBlock ,accelerationBottomBlock);
            }
        };

        bottomBlockAnimationTimer.start();
        animationBottomBlock.play();
    }

    private void reactToConditions(Block block, double maxDisplacement, double acceleration)
    {
        double translated = block.getTranslateX() * Math.signum(acceleration);

        if (translated >= maxDisplacement)
        {
            if (block.getBlockId() == BlockFrontEndController.POSITION.TOP)
            {
                topBlockAnimationTimer.stop();
                animationTopBlock.stop();
            } else
            {
                bottomBlockAnimationTimer.stop();
                animationBottomBlock.stop();
            }
        }
        
        // Gravity (for top block)
    }

    private void animateFall()
    {
        // IMPLEMENT ME
    }

    /**
     * Finds duration of animation using the Block's acceleration and
     * displacement.
     * 
     * Derivation: x = vo * t + 1/2 * a * t^2
     *          => x = 1/2 * a * t^2 (since initial velocity is 0)
     *          => t = sqrt(2x/a)
     * 
     * x is displacement (m)
     * a is acceleration (m/s^2)
     * t is time (seconds)
     * 
     * @param acceleration
     * @param displacement
     * @return duration of animation based on the displacement
     */
    private double calculateDuration(double acceleration, double displacement)
    {
        return Math.sqrt(2 * Math.abs(displacement) / Math.abs(acceleration));
    }
    
    public void play()
    {
        topBlockAnimationTimer.start();
        animationTopBlock.play();
        
        bottomBlockAnimationTimer.start();
        animationBottomBlock.play();
    }

    public void pause()
    {
        animationTopBlock.pause();
    }

    // FIXME: onFinished()? maybe say something or change color, some type of indication
    public void stop()
    {
        animationTopBlock.jumpTo(Duration.ZERO);
        animationTopBlock.stop();
        topBlockAnimationTimer.stop();
        
        animationBottomBlock.jumpTo(Duration.ZERO);
        animationBottomBlock.stop();
        bottomBlockAnimationTimer.stop();
    }
}
