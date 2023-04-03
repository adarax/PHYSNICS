package edu.vanier.physnics.stackedblock;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author kn
 * 
 * Source: https://gist.github.com/kn0412/2086581e98a32c8dfa1f69772f14bca4
 * 
 * Modified version by adarax
 */
public class Arrow extends Path {
    
    /*
    TODO: convert this class to extend stackPane, so that text can be put on
          top of the arrow. Also, figure out how to make the body of the arrow
          wider.
     */
    public Arrow(double startX, double startY, double endX, double endY, double arrowHeadSize)
    {
        super();
        strokeProperty().bind(fillProperty());
        setFill(Color.BLACK);

        // Line
        getElements().add(new MoveTo(startX, startY));
        getElements().add(new LineTo(endX, endY));

        // ArrowHead
        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        
        // Point 1
        double x1 = (-1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y1 = (-1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        
        // Point 2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;

        getElements().add(new LineTo(x1, y1));
        getElements().add(new LineTo(x2, y2));
        getElements().add(new LineTo(endX, endY));
    }
}
