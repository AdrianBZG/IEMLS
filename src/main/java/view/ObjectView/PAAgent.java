package view.ObjectView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Perception-Action view
 * Created by eleazardd on 9/01/17.
 */
public class PAAgent extends ObjectView {

    public PAAgent() {
        Circle right = new Circle(10);
        right.setTranslateX(-10);
        right.setTranslateY(-10);
        right.setFill(Color.LIGHTBLUE);

        FontIcon icon = new FontIcon(FontAwesome.EYE);
        icon.setIconSize(16);
        icon.setTranslateX(-16.5);
        icon.setTranslateY(-6);
        getChildren().addAll(right, icon);
    }
}
