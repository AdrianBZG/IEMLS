package view.ObjectView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Created by eleazardd on 13/11/16.
 */
public class PheromoneView extends ObjectView {

    public PheromoneView() {
        Circle circle = new Circle(5);
        circle.setTranslateX(-5);
        circle.setTranslateY(-5);
        circle.setFill(Color.RED);
        getChildren().addAll(circle);
    }
}
