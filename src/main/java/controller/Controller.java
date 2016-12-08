/**
 * IEMLS - Controller.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import model.object.Block;
import model.object.MapObject;
import model.object.Resource;
import model.object.agent.Agent;
import rx.observables.JavaFxObservable;
import view.CellObjectView;
import view.EnvironmentView;

import java.net.URL;
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
    public ChoiceBox noiseChoose;

    @FXML
    public ListView<MapObject> listObjects;

    @FXML
    public TextField xDim;

    @FXML
    public TextField yDim;

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
    }

    public void generateMap() {
        if (xDim.getText() == "" || yDim.getText() == "") {
            environmentView = new EnvironmentView();
            centralPane.setCenter(environmentView);
        }
        else {
            try {
                int width = Integer.parseInt(xDim.getText());
                int height = Integer.parseInt(yDim.getText());
                environmentView = new EnvironmentView(width, height);
                centralPane.setCenter(environmentView);
            } catch (Exception e) {
                // TODO: LAUNCH ERROR
            }
        }
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
