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
    Button options = new Button();
    Pane spacer = new Pane();
    Label tag = new Label();

    public SpecieItemView() {
        super();
        box.getChildren().addAll(tag, spacer);
        box.setHgrow(spacer, Priority.ALWAYS);
        box.setSpacing(5.0);
        options.setGraphic(new FontIcon(FontAwesome.GEARS));
    }

    @Override
    protected void updateItem(Specie item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            if (item != null) {
                options.setOnMouseClicked(mouseEvent -> Specie.deleteSpecie(item));
                tag.setText(item.getSpecieName());
            }
            setGraphic(box);
        }
    }


}
