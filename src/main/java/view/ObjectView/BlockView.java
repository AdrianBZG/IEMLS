package view.ObjectView;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by eleazardd on 13/11/16.
 */
public class BlockView extends ObjectView {

    public BlockView() {
        Rectangle rectangle = new Rectangle(20,20);
        rectangle.setTranslateX(-20);
        rectangle.setTranslateY(-20);
        rectangle.setFill(Color.BLACK);
        getChildren().addAll(rectangle);
    }
}
