package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.object.MapObject;

/**
 * Created by eleazardd on 14/11/16.
 */
public class CellObjectView extends ListCell<MapObject> {
    HBox box = new HBox();
    Button options = new Button("Options");
    Pane spacer = new Pane();
    Label tag = new Label();

    public CellObjectView() {
        super();
        box.getChildren().addAll(tag, spacer);
        box.setHgrow(spacer, Priority.ALWAYS);
        box.setSpacing(5.0);
    }

    @Override
    protected void updateItem(MapObject item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            if (item != null) {
                ObjectView objectView = item.getVisualObject();
                if (objectView.hasOptions()) {
                    options.setOnMouseClicked(mouseEvent -> objectView.showOptions());
                    box.getChildren().add(options);
                }
                tag.setText(item.getName());
                box.getChildren().add(item.getVisualObject());
            }
            setGraphic(box);
        }
    }


}
