package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import model.algorithms.Algorithm;
import model.species.Specie;

import java.net.URL;
import java.util.ResourceBundle;

public class NeuralNetworkAgentConfigurationController implements Initializable {

    @FXML
    public ChoiceBox choiceNeuralNetworkAlgorithm;

    @FXML
    public ChoiceBox choiceSpecie;

    @FXML
    public TitledPane titledPane1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceNeuralNetworkAlgorithm.setItems(FXCollections.observableArrayList(Algorithm.getNeuralAlgorithms()));
        choiceNeuralNetworkAlgorithm.getSelectionModel().selectFirst();

        choiceSpecie.setItems(FXCollections.observableArrayList(Specie.getSpecies()));
        choiceSpecie.getSelectionModel().selectFirst();

    }

    public Algorithm getAlgorithm() {
        return (Algorithm) choiceNeuralNetworkAlgorithm.getSelectionModel().getSelectedItem();
    }
}
