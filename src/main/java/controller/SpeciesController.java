package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.species.Specie;
import view.SpecieItemView;

import java.net.URL;
import java.util.ResourceBundle;

public class SpeciesController implements Initializable {

    @FXML
    public TextArea inputText;

    @FXML
    public ListView<Specie> listSpecies;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listSpecies.setItems(FXCollections.observableArrayList(Specie.getSpecies()));
        System.out.println(Specie.getSpecies().size());
        listSpecies.setCellFactory(value -> new SpecieItemView());
        listSpecies.getSelectionModel()
                .selectedItemProperty();
    }

    public Specie getSpecie() {
        return listSpecies.getSelectionModel().getSelectedItem();
    }
}
