/**
 * IEMLS - Controller.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package controller;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import model.map.generator.IGenerator;
import model.map.generator.SimplexNoise;
import model.object.Block;
import model.object.MapObject;
import model.object.Resource;
import model.object.agent.Agent;
import rx.observables.JavaFxObservable;
import view.CellObjectView;
import view.EnvironmentView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * TODO: Commenta algo
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<MapObject> list = FXCollections.observableArrayList(
                new Agent(), new Resource(10, "Food"), new Block());

        listObjects.setItems(list);
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
        noiseChoose.setItems(FXCollections.observableArrayList(new SimplexNoise()));  // TODO: Add more methods
        noiseChoose.getSelectionModel().selectFirst();
    }


    /**
     * Generate a map
     * TODO: Reset change seed in noiseChoose to don't repeat map
     */
    public void generateMap() {
        if (xDim.getText().equals("") && yDim.getText().equals("")) {
            environmentView = new EnvironmentView(noiseChoose.getSelectionModel().getSelectedItem());
            centralPane.setCenter(environmentView);
        }
        else {
            try {
                int width = Math.abs(Integer.parseInt(xDim.getText()));
                int height = Math.abs(Integer.parseInt(yDim.getText()));
                environmentView = new EnvironmentView(width, height, noiseChoose.getSelectionModel().getSelectedItem());
                centralPane.setCenter(environmentView);
            } catch (Exception e) {
                errorDialog("Fail To build a map with " + xDim.getText() + " x " + yDim.getText());
            }
        }
    }

    public void clearMap() {
        centralPane.setCenter(initialMessage);
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
