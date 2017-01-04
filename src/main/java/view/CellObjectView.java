package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.object.MapObject;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * TODO: Document it
 * Created by eleazardd on 14/11/16.
 *
 */
public class CellObjectView extends ListCell<MapObject> {
    HBox box = new HBox();
    Button options = new Button();
    Pane spacer = new Pane();
    Label tag = new Label();

    public CellObjectView() {
        super();
        box.getChildren().addAll(tag, spacer);
        box.setHgrow(spacer, Priority.ALWAYS);
        box.setSpacing(5.0);
        options.setGraphic(new FontIcon(FontAwesome.GEARS));
    }

    @Override
    protected void updateItem(MapObject item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            if (item != null) {
                if (item.hasOptions()) {
                    options.setOnMouseClicked(mouseEvent -> item.showOptions());
                    box.getChildren().add(options);
                }
                tag.setText(item.getName());
                box.getChildren().add(item.getVisualObject());
            }
            setGraphic(box);
        }
    }


}
