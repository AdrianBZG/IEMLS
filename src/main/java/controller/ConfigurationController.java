package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import model.algorithms.Algorithm;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by eleazardd on 3/01/17.
 */
public class ConfigurationController implements Initializable {

    @FXML
    public ChoiceBox choiceAlgorithm;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceAlgorithm.setItems(FXCollections.observableArrayList(Algorithm.getAlgorithms()));
        choiceAlgorithm.getSelectionModel().selectFirst();
    }

    public Algorithm getAlgorithm() {
        return (Algorithm) choiceAlgorithm.getSelectionModel().getSelectedItem();
    }
}
