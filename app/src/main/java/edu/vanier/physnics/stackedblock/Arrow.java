package edu.vanier.physnics.stackedblock;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author adarax
 */
public class Arrow extends StackPane {

    private final Image arrowBodyObject = new Image(getClass().getResourceAsStream("/images/arrow.png"));
    private final ImageView arrowBody;
    private final Label nameTag;

    public Arrow(Vector forceVector, Block targetBlock)
    {
        this.arrowBody = new ImageView(arrowBodyObject);
        this.nameTag = new Label(Double.toString(forceVector.getMagnitudeInNewtons()) + " N");
        this.setLayoutX(targetBlock.getLayoutX() - (arrowBody.getFitWidth() / 2));
        this.setLayoutY(targetBlock.getLayoutY() - (arrowBody.getFitHeight() / 2));
        arrowBody.setRotate(forceVector.getDirectionInDegrees());
    }

    public void draw()
    {
        this.getChildren().clear();
        this.getChildren().addAll(arrowBody, nameTag);
    }

    public void sizeToBlock()
    {

        // For name tag: get dimensions of arrow drawing, size writing accordingly
    }

}
