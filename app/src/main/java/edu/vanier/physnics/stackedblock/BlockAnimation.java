package edu.vanier.physnics.stackedblock;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
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
     * @param topBlock the top block in the simulation
     * @param bottomBlock the bottom block in the simulation
     * @param animationPane the Pane in which the animation takes place
     */
    public void situateBlocks(Block topBlock, Block bottomBlock, Pane animationPane)
    {
        double paneWidth = animationPane.getWidth();
        double paneHeight = animationPane.getHeight();

        assembleBlocks(topBlock, bottomBlock);

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

    /**
     * Assembles the blocks by determining their drawing height and width,
     * label, creating the Rectangle object, and setting the color of the block.
     * 
     * @param topBlock the top block in the simulation
     * @param bottomBlock the bottom block in the simulation
     */
    public void assembleBlocks(Block topBlock, Block bottomBlock)
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
            block.assemble();
        }
    }

    private Rectangle floorDrawing;
    
    /**
     * Draws the floor of the simulation. The floor is a Rectangle object that
     * is drawn at the bottom of the Pane.
     * 
     * @param animationPane the Pane in which the animation takes place
     * @param isDarkMode whether the simulation is in dark mode or not
     */
    public void drawFloor(Pane animationPane, boolean isDarkMode)
    {
        double paneWidth = animationPane.getWidth();
        double paneHeight = animationPane.getHeight();

        floorDrawing = new Rectangle(0, paneHeight - this.floorHeight - 1,
                paneWidth - 1, this.floorHeight);

        // #ccc1c1 is light gray, #807979 is gray
        floorDrawing.setFill(isDarkMode ? Color.web("ccc1c1") : Color.web("807979"));

        animationPane.getChildren().add(floorDrawing);
    }

    
    /************************************************************************/
    /*                        Animation of simulation                       */
    /************************************************************************/
    
    private ParallelTransition allTransitionsTopBlock;
    private TranslateTransition userDefinedAnimationTopBlock, userDefinedAnimationBottomBlock;
    private AnimationTimer topBlockAnimationTimer, bottomBlockAnimationTimer;
    private Block topBlock, bottomBlock;
    
    /**
     * Creates the animations for the blocks based on the forces experienced by
     * the blocks. The blocks are animated by translating them horizontally
     * across the screen. The blocks are animated in parallel.
     * 
     * @param blocks the blocks in the simulation
     * @param animationPane the Pane in which the animation takes place
     */
    protected void createBlockAnimations(ArrayList<Block> blocks, Pane animationPane)
    {
        BlockFormulas blockFormulas = new BlockFormulas();
        allTransitionsTopBlock = new ParallelTransition();
        
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

            AnimationTimer blockAnimationTimer = new AnimationTimer()
            {
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
                userDefinedAnimationTopBlock = blockAnimation;
                topBlockAnimationTimer = blockAnimationTimer;
                allTransitionsTopBlock.getChildren().add(userDefinedAnimationTopBlock);
            } else if (block.getBlockId() == BlockFrontEndController.POSITION.BOTTOM)
            {
                this.bottomBlock = block;
                userDefinedAnimationBottomBlock = blockAnimation;
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
                allTransitionsTopBlock.stop();
            } else
            {
                bottomBlockAnimationTimer.stop();
                userDefinedAnimationBottomBlock.stop();
            }
        }
        
        if (acceleration > 0)
        {
            if (topBlock.getLayoutX() + topBlock.getTranslateX() + (topBlock.getDrawingWidth() / 2) > bottomBlock.getLayoutX() + bottomBlock.getTranslateX() + bottomBlock.getDrawingWidth())
            {
                allTransitionsTopBlock.stop();
                allTransitionsTopBlock.getChildren().remove(0);
                topBlockAnimationTimer.stop();
                bottomBlockAnimationTimer.stop();
                userDefinedAnimationBottomBlock.stop();
                animateFall(acceleration);
            }
        } else
        {
            if (topBlock.getLayoutX() + topBlock.getTranslateX() + (topBlock.getDrawingWidth() / 2) < bottomBlock.getLayoutX() + bottomBlock.getTranslateX())
            {
                allTransitionsTopBlock.stop();
                allTransitionsTopBlock.getChildren().remove(0);
                topBlockAnimationTimer.stop();
                bottomBlockAnimationTimer.stop();
                userDefinedAnimationBottomBlock.stop();
                animateFall(acceleration);
            }
        }       
    }

    private AnimationTimer fallAnimationTimer;
    
    private void animateFall(double acceleration)
    {
        double fallTime = calculateDuration(BlockFormulas.GRAVITATIONAL_ACCELERATION, bottomBlock.getDrawingHeight());
        
        if (acceleration > 0)
        {
            RotateTransition rotate = new RotateTransition(Duration.seconds(fallTime), topBlock);
            rotate.setByAngle(90);
            
            TranslateTransition moveInAcceleratedDirection = new TranslateTransition(Duration.seconds(fallTime), topBlock);
            moveInAcceleratedDirection.setByX(150);
            
            TranslateTransition fallToFloor = new TranslateTransition(Duration.seconds(fallTime), topBlock);
            fallToFloor.setByY(bottomBlock.getDrawingHeight());
            fallToFloor.setInterpolator(new AccelerateInterpolator(BlockFormulas.GRAVITATIONAL_ACCELERATION));
            
            allTransitionsTopBlock.getChildren().addAll(rotate, moveInAcceleratedDirection, fallToFloor);
            allTransitionsTopBlock.play();
        } else
        {
            RotateTransition rotate = new RotateTransition(Duration.seconds(fallTime), topBlock);
            rotate.setByAngle(-90);
            
            TranslateTransition moveInAcceleratedDirection = new TranslateTransition(Duration.seconds(fallTime), topBlock);
            moveInAcceleratedDirection.setByX(-150);
            
            TranslateTransition fallToFloor = new TranslateTransition(Duration.seconds(fallTime), topBlock);
            fallToFloor.setByY(bottomBlock.getDrawingHeight());
            fallToFloor.setInterpolator(new AccelerateInterpolator(BlockFormulas.GRAVITATIONAL_ACCELERATION));
            
            allTransitionsTopBlock.getChildren().addAll(rotate, moveInAcceleratedDirection, fallToFloor);
            allTransitionsTopBlock.play();
        }
        
        fallAnimationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                double verticalTranslation = topBlock.getTranslateY();
                
                // TODO: using an intersects() method would be better
                if (verticalTranslation > bottomBlock.getDrawingHeight() - (floorDrawing.getHeight() * 0.8))
                {
                    allTransitionsTopBlock.stop();
                    fallAnimationTimer.stop();
                }
            }
        };
        
        fallAnimationTimer.start();
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
     * y-values can also work in the place of x for situations such as
     * free fall.
     * 
     * @param acceleration (m/s^2)
     * @param displacement (m)
     * @return duration of animation based on the displacement
     */
    private double calculateDuration(double acceleration, double displacement)
    {
        return Math.sqrt(2 * Math.abs(displacement) / Math.abs(acceleration));
    }
    
    /**
     * Plays the animation by starting the animation timers and the
     * transitions.
     */
    public void play()
    {
        topBlockAnimationTimer.start();
        allTransitionsTopBlock.play();
        
        bottomBlockAnimationTimer.start();
        userDefinedAnimationBottomBlock.play();
        
        if (fallAnimationTimer != null)
        {
            fallAnimationTimer.start();
        }
    }

    /**
     * Pauses the animation by pausing the transitions.
     */
    public void pause()
    {
        allTransitionsTopBlock.pause();
        userDefinedAnimationBottomBlock.pause();
    }

    /**
     * Resets the animation by stopping the animation timers and the
     * transitions.
     */
    public void reset()
    {        
        userDefinedAnimationBottomBlock.stop();
        bottomBlockAnimationTimer.stop();
        
        allTransitionsTopBlock.stop();
        
        if (fallAnimationTimer != null)
        {
            fallAnimationTimer.stop();
        }
    }
}
