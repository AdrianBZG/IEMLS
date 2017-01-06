package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import model.species.Specie;
import view.SpecieItemView;

import java.net.URL;
import java.util.ResourceBundle;

public class SpeciesController implements Initializable {

    @FXML
    public ChoiceBox choiceSpecie;

    @FXML
    public ListView<Specie> listSpecies;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceSpecie.setItems(FXCollections.observableArrayList(Specie.getSpecies()));
        choiceSpecie.getSelectionModel().selectFirst();

        listSpecies.setItems(FXCollections.observableArrayList(Specie.getSpecies()));
        listSpecies.setCellFactory(value -> new SpecieItemView());
        listSpecies.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, mapObject, newMapObject) -> {
                    System.out.println("wtf");
                });

        Specie.getSpecies().add(new Specie("Default specie"));
    }

    public Specie getSpecie() {
        return (Specie) choiceSpecie.getSelectionModel().getSelectedItem();
    }
}
