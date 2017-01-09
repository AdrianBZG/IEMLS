package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.object.MapObject;
import model.object.agent.Agent;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Render a cell into list view in this case specific for agents.
 * Created by eleazardd on 14/11/16.
 *
 */
public class CellAgentView extends ListCell<Agent> {
    HBox box = new HBox();
    Button options = new Button();
    Pane spacer = new Pane();
    Label tag = new Label();

    public CellAgentView() {
        super();
        box.getChildren().addAll(tag, spacer, options);
        box.setHgrow(spacer, Priority.ALWAYS);
        box.setSpacing(5.0);
        options.setGraphic(new FontIcon(FontAwesome.GEARS));
    }

    @Override
    protected void updateItem(Agent item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            if (item != null) {
                options.setOnMouseClicked(mouseEvent -> item.showOptions());
                tag.setText(item.getName());
                box.getChildren().add(item.getVisualObject());
            }
            setGraphic(box);
        }
    }


}
