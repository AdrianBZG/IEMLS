package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Created by eleazardd on 2/01/17.
 */
public class ScriptAgentView extends ObjectView {

    public ScriptAgentView() {
        Circle right = new Circle(10);
        right.setTranslateX(-10);
        right.setTranslateY(-10);
        right.setFill(Color.RED);

        FontIcon icon = new FontIcon(FontAwesome.CODE);
        icon.setIconSize(16);
        icon.setTranslateX(-16.5);
        icon.setTranslateY(-6);
        getChildren().addAll(right, icon);

    }
}
