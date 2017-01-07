package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.species.Specie;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import view.SpecieItemView;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SpeciesController implements Initializable {

    @FXML
    public TextArea inputText;

    @FXML
    public ListView<Specie> listSpecies;

    @FXML
    public Button createSpecieButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listSpecies.setItems(FXCollections.observableArrayList(Specie.getSpecies()));
        System.out.println(Specie.getSpecies().size());
        listSpecies.setCellFactory(value -> new SpecieItemView());
        listSpecies.getSelectionModel()
                .selectedItemProperty();

        createSpecieButton.setGraphic(new FontIcon(FontAwesome.PLUS_CIRCLE));
    }

    @FXML
    public void onClickCreateSpecie () throws IOException, ClassNotFoundException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Create a new specie");
        dialog.setHeaderText("");
        dialog.setContentText("Write a name for the new specie:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            System.out.println("Your name: " + name);
            new Specie(name);
        });
    }

    public Specie getSpecie() {
        return listSpecies.getSelectionModel().getSelectedItem();
    }
}
