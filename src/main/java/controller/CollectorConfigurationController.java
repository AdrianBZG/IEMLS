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

public class CollectorConfigurationController implements Initializable {

    @FXML
    public ChoiceBox choiceCollectionAlgorithm;

    @FXML
    public ChoiceBox choiceSpecie;

    //@FXML
    //public VBox vBoxScript;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceCollectionAlgorithm.setItems(FXCollections.observableArrayList(Algorithm.getCollectionAlgorithms()));
        choiceCollectionAlgorithm.getSelectionModel().selectFirst();

        choiceSpecie.setItems(FXCollections.observableArrayList(Specie.getSpecies()));
        choiceSpecie.getSelectionModel().selectFirst();

        VirtualizedScrollPane virtualizedScrollPane = new VirtualizedScrollPane<>(new ScriptEditorView());
        VBox.setVgrow(virtualizedScrollPane, Priority.ALWAYS);
       // vBoxScript.getChildren().add(virtualizedScrollPane);
    }

    public Algorithm getAlgorithm() {
        return (Algorithm) choiceCollectionAlgorithm.getSelectionModel().getSelectedItem();
    }
}
