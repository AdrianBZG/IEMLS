package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TitledPane;
import model.algorithms.Algorithm;
import model.object.agent.NeuralAgent;
import model.species.Specie;

import java.net.URL;
import java.util.ResourceBundle;

public class MachineLearningAgentConfigurationController implements Initializable {

    @FXML
    public ChoiceBox choiceMachineLearningAlgorithm;

    @FXML
    public ChoiceBox choiceSpecie;

    @FXML
    public TitledPane titledPane1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceMachineLearningAlgorithm.setItems(FXCollections.observableArrayList(Algorithm.getMachineLearningAlgorithms()));
        choiceMachineLearningAlgorithm.getSelectionModel().selectFirst();

        choiceSpecie.setItems(FXCollections.observableArrayList(Specie.getSpecies()));
        choiceSpecie.getSelectionModel().selectFirst();

    }

    public Algorithm getAlgorithm() {
        return (Algorithm) choiceMachineLearningAlgorithm.getSelectionModel().getSelectedItem();
    }
}
