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

    private final Image arrowBodyObject = new Image(getClass().getResourceAsStream("/images/arrow.png"));
    private final ImageView arrowBody;
    private final Label nameTag;
    private final Block correspondingBlock;
    private final Vector forceVector;

    public Arrow(Vector forceVector, Block correspondingBlock)
    {
        this.correspondingBlock = correspondingBlock;
        this.forceVector = forceVector;
        this.arrowBody = new ImageView(arrowBodyObject);

        String magnitudeAsString = String.format("%.2f", forceVector.getMagnitudeInNewtons()) + " N";
        this.nameTag = new Label(magnitudeAsString);

        this.setLayoutX(correspondingBlock.getLayoutX() - (arrowBody.getFitWidth() / 2));
        this.setLayoutY(correspondingBlock.getLayoutY() - (arrowBody.getFitHeight() / 2));
        arrowBody.setRotate(forceVector.getDirectionInDegrees());
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
        
        // Position on block
        
        // TODO: Get quadrant and figure out how to place and angle arrows based on that
    }
}
