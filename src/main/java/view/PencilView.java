package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

import java.io.IOException;

/**
 * Created by eleazardd on 12/11/16.
 */
public class PencilView extends TitledPane {

    @FXML //  fx:id="first"
    public Button first;


    public PencilView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Pencil.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        first.setOnMouseClicked(mouseEvent -> {
            first.setText("Clicked");
        });
    }

}
