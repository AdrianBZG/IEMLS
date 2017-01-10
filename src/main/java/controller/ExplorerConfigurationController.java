package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.algorithms.Algorithm;
import model.species.Specie;
import org.fxmisc.flowless.VirtualizedScrollPane;
import view.ScriptEditorView;

import java.net.URL;
import java.util.ResourceBundle;

public class ExplorerConfigurationController implements Initializable {

    @FXML
    public ChoiceBox choiceExplorationAlgorithm;

    @FXML
    public ChoiceBox choiceSpecie;

   // @FXML
   // public VBox vBoxScript;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceExplorationAlgorithm.setItems(FXCollections.observableArrayList(Algorithm.getExplorationAlgorithms()));
        choiceExplorationAlgorithm.getSelectionModel().selectFirst();

        choiceSpecie.setItems(FXCollections.observableArrayList(Specie.getSpecies()));
        choiceSpecie.getSelectionModel().selectFirst();

        VirtualizedScrollPane virtualizedScrollPane = new VirtualizedScrollPane<>(new ScriptEditorView());
        VBox.setVgrow(virtualizedScrollPane, Priority.ALWAYS);
     //   vBoxScript.getChildren().add(virtualizedScrollPane);
    }

    public Algorithm getAlgorithm() {
        return (Algorithm) choiceExplorationAlgorithm.getSelectionModel().getSelectedItem();
    }
}
