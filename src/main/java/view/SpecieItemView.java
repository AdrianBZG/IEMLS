package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.species.Specie;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

public class SpecieItemView extends ListCell<Specie> {
    HBox box = new HBox();
    Button delete = new Button();
    Pane spacer = new Pane();
    Label tag = new Label();

    public SpecieItemView() {
        super();
        box.getChildren().addAll(tag, spacer);
        box.setHgrow(spacer, Priority.ALWAYS);
        box.setSpacing(5.0);
        delete.setGraphic(new FontIcon(FontAwesome.TIMES));
    }

    @Override
    protected void updateItem(Specie item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            if (item != null) {
                delete.setOnMouseClicked(mouseEvent -> Specie.deleteSpecie(item));
                tag.setText(item.getSpecieName());
                box.getChildren().add(delete);
            }
            setGraphic(box);
        }
    }
}
