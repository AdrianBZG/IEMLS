package view.ObjectView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * TODO: Document it
 * Created by eleazardd on 10/01/17.
 */
public class SituationActionView extends ObjectView {

    public SituationActionView() {
        Circle right = new Circle(10);
        right.setTranslateX(-10);
        right.setTranslateY(-10);
        right.setFill(Color.BLUE);

        FontIcon icon = new FontIcon(FontAwesome.HISTORY);
        icon.setFill(Color.WHITE);
        icon.setIconSize(16);
        icon.setTranslateX(-16.5);
        icon.setTranslateY(-6);
        getChildren().addAll(right, icon);
    }
}
