package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
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
    private Block topBlock, bottomBlock;
    
    protected void createBlockAnimations(ArrayList<Block> blocks, Pane animationPane)
    {
        BlockFormulas blockFormulas = new BlockFormulas();
        
        for (Block block : blocks)
        {
            Vector netForceVector = blockFormulas.calculateNetForceVector(block.getForcesExperienced());
            double netForceX = netForceVector.asComponents().get(0), acceleration;

            if (Double.isNaN(netForceX))
            {
                acceleration = 0;
            } else if (block.getBlockId() == BlockFrontEndController.POSITION.TOP)
            {
                acceleration = netForceX / block.getMass();
            } else
            {
                acceleration = netForceX / (blocks.get(0).getMass() + blocks.get(1).getMass());
            }

            double displacement;

            if (acceleration > 0)
            {
                displacement = animationPane.getWidth() - block.getLayoutX() - block.getDrawingWidth();
            } else
            {
                displacement = block.getLayoutX() * -1;
            }

            AnimationTimer blockAnimationTimer = new AnimationTimer() {
                @Override
                public void handle(long l)
                {
                    reactToConditions(block, displacement, acceleration);
                }
            };

            double durationOfAnimation = calculateDuration(acceleration, displacement);
            TranslateTransition blockAnimation = new TranslateTransition(Duration.seconds(durationOfAnimation), block);
            blockAnimation.setByX(displacement);
            blockAnimation.setInterpolator(new AccelerateInterpolator(acceleration));

            if (block.getBlockId() == BlockFrontEndController.POSITION.TOP)
            {
                this.topBlock = block;
                animationTopBlock = blockAnimation;
                topBlockAnimationTimer = blockAnimationTimer;
            } else if (block.getBlockId() == BlockFrontEndController.POSITION.BOTTOM)
            {
                this.bottomBlock = block;
                animationBottomBlock = blockAnimation;
                bottomBlockAnimationTimer = blockAnimationTimer;
            }
        }
    }

    private void reactToConditions(Block block, double maxDisplacement, double acceleration)
    {
        double translated = block.getTranslateX() * Math.signum(acceleration);
        
        if (translated >= Math.abs(maxDisplacement))
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
        
        if (acceleration > 0)
        {
            if (topBlock.getLayoutX() + topBlock.getTranslateX() + (topBlock.getDrawingWidth() / 2) > bottomBlock.getLayoutX() + bottomBlock.getTranslateX() + bottomBlock.getDrawingWidth())
            {
                System.out.println("Time to fall");
            }
        } else
        {
            if (topBlock.getLayoutX() + topBlock.getTranslateX() + (topBlock.getDrawingWidth() / 2) < bottomBlock.getLayoutX() + bottomBlock.getTranslateX())
            {
                System.out.println("Time to fall (left)");
            }
        }
        // TODO: Gravity (for top block)
        
        // if location of middle point of top block 
        // is greater than rightmost side of bottom block --> fall
        
        // OR
        
        // if location of middle point of top block is less than leftmost side
        // of bottom block --> fall
        
        // ** Update: conditions for when to trigger fall work!
        
        // if top block has fallen, stop simulation
        
        //** a few notes **
        // fall direction has to be the same as movement direction of (top) block
        // has to also tilt in that direction
        
        // should be fun...
        
    }

    // Implement slower fall if there are vectors facing upward (partially or entirely)
    private void animateFall(double acceleration)
    {
        if (acceleration > 0)
        {
            
        } else
        {
            
        }
        
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
        animationBottomBlock.pause();
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
