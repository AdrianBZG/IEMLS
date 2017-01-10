package view.ObjectView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import view.ObjectView.ObjectView;

/**
 * Created by eleazardd on 2/01/17.
 */
public class ScriptAgentView extends ObjectView {

    public ScriptAgentView() {
        Circle right = new Circle(10);
        right.setTranslateX(-10);
        right.setTranslateY(-10);
        right.setFill(Color.BLUE);

        FontIcon icon = new FontIcon(FontAwesome.CODE);
        icon.setFill(Color.WHITE);
        icon.setIconSize(16);
        icon.setTranslateX(-16.5);
        icon.setTranslateY(-6);
        getChildren().addAll(right, icon);

    }
}
