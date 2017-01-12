package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.algorithms.Algorithm;
import model.species.Specie;

import java.net.URL;
import java.util.ResourceBundle;

public class SwarmNestConfigurationController implements Initializable {

    @FXML
    public ChoiceBox choiceSwarmAlgorithm;

    @FXML
    public ChoiceBox choiceSpecie;

    @FXML
    public TitledPane titledPane1;

    public Spinner<Integer> agentsNumberSpinner = new Spinner<>(1,10,4);

    public Spinner<Integer> spawnRadiusSpinner = new Spinner<>(1,5,1);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceSwarmAlgorithm.setItems(FXCollections.observableArrayList(Algorithm.getSwarmAlgorithms()));
        choiceSwarmAlgorithm.getSelectionModel().selectFirst();

        choiceSpecie.setItems(FXCollections.observableArrayList(Specie.getSpecies()));
        choiceSpecie.getSelectionModel().selectFirst();

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5,5,5,5));
        grid.add(new Label("Number of swarm agents for this nest: "),0,0);
        grid.add(agentsNumberSpinner,1,0);
        grid.add(new Label("Spawn Radius: "),0,1);
        grid.add(spawnRadiusSpinner,1,1);

        titledPane1.setContent(grid);

    }

    public Algorithm getAlgorithm() {
        return (Algorithm) choiceSwarmAlgorithm.getSelectionModel().getSelectedItem();
    }
}
