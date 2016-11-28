package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Created by eleazardd on 13/11/16.
 */
public class ResourceView extends ObjectView {

    public ResourceView() {
        Circle circle = new Circle(10);
        circle.setTranslateX(-10);
        circle.setTranslateY(-10);
        Rectangle rectangle = new Rectangle(10,10);
        rectangle.setFill(Color.BLACK);
        circle.setFill(Color.GREEN);
        rectangle.setTranslateX(-15);
        rectangle.setTranslateY(-15);
        getChildren().addAll(circle, rectangle);
    }
}
