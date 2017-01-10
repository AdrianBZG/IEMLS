/**
 * IEMLS - Controller.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import model.AgentsManager;
import model.map.generator.*;
import model.object.Block;
import model.object.MapObject;
import model.object.Resource;
import model.object.agent.Agent;
import model.species.Specie;
import view.CellAgentView;
import view.CellObjectView;
import view.EnvironmentView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main controller of GUI
 *
 */
public class Controller implements Initializable {
    EnvironmentView environmentView;

    @FXML
    public BorderPane centralPane;

    @FXML
    public ChoiceBox<IGenerator> noiseChoose;

    @FXML
    public ListView<MapObject> listObjects;

    @FXML
    public TextField xDim;

    @FXML
    public TextField yDim;

    @FXML
    public TitledPane mapTitledPane;

    @FXML
    public Accordion accordion;

    @FXML
    public Label initialMessage;

    @FXML
    public ListView<Agent> agentList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listObjects.setItems(FXCollections.observableArrayList(MapObject.getMapObjects()));
        listObjects.setCellFactory(value -> new CellObjectView());
        listObjects.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, mapObject, newMapObject) -> {
                    if (environmentView != null) {
                        environmentView.setPencil(newMapObject);
                }
            });
        accordion.setExpandedPane(mapTitledPane);

        // Noise methods available
        noiseChoose.setItems(FXCollections.observableArrayList(new NullNoise(), new SimplexNoise(), new DisplacementFractalNoise(), new VoronoiNoise()));
        noiseChoose.getSelectionModel().selectFirst();

        new Specie("Default specie");
    }


    @FXML
    public void onSaveMap () throws IOException {
        environmentView.getEnvironmentMap().saveMap();
    }

    @FXML
    public void onLoadMap () throws IOException, ClassNotFoundException {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("./maps"));
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null)
        {
            environmentView = new EnvironmentView(selectedFile.getAbsolutePath());
            centralPane.setCenter(environmentView);
            agentList.setItems(environmentView.getAgentsManager().getAgents());
            agentList.setCellFactory(value -> new CellAgentView());
        }
    }

    @FXML
    public void onOpenSpeciesManager () throws IOException, ClassNotFoundException {
        Specie.showSpeciesManager();
    }


    @FXML
    public void onPlayButton () {
        environmentView.getAgentsManager().play();
    }

    @FXML
    public void onStopButton () {
        environmentView.getAgentsManager().stop();
    }


    /**
     * Generate a map
     * TODO: Reset change seed in noiseChoose to don't repeat map
     */
    public void generateMap() {
        IGenerator selectedItem = noiseChoose.getSelectionModel().getSelectedItem();
        selectedItem.newSeed();
        if (xDim.getText().equals("") && yDim.getText().equals("")) {
            environmentView = new EnvironmentView(selectedItem);
            centralPane.setCenter(environmentView);
        }
        else {
            try {
                int width = Math.abs(Integer.parseInt(xDim.getText()));
                int height = Math.abs(Integer.parseInt(yDim.getText()));
                environmentView = new EnvironmentView(width, height, noiseChoose.getSelectionModel().getSelectedItem());
                centralPane.setCenter(environmentView);
                agentList.setItems(environmentView.getAgentsManager().getAgents());
                agentList.setCellFactory(value -> new CellAgentView());
            } catch (Exception e) {
                errorDialog("Fail To build a map with " + xDim.getText() + " x " + yDim.getText());
            }
        }
    }

    public void clearMap() {
        centralPane.setCenter(initialMessage);
        environmentView.getAgentsManager().getAgents().clear();
        environmentView = null;
    }

    public void errorDialog(String error) {
        Dialog dialog = new Dialog();
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Got it!", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setTitle("Error");
        dialog.setHeaderText("An error has occurred");
        dialog.setContentText(error);
        dialog.showAndWait();
    }

    public void aboutDialog() {
        Dialog dialog = new Dialog();
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Got it!", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setTitle("About");
        dialog.setHeaderText("IEMLS");
        dialog.setContentText("Information about project ...");
        dialog.showAndWait();
    }
}
