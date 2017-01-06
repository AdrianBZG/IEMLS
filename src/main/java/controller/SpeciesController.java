package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import model.species.Specie;

import java.net.URL;
import java.util.ResourceBundle;

public class SpeciesController implements Initializable {

    @FXML
    public ChoiceBox choiceSpecie;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceSpecie.setItems(FXCollections.observableArrayList(Specie.getSpecies()));
        choiceSpecie.getSelectionModel().selectFirst();
    }

    public Specie getSpecie() {
        return (Specie) choiceSpecie.getSelectionModel().getSelectedItem();
    }
}
