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
import model.object.Block;
import model.object.MapObject;
import model.object.Resource;
import model.object.agent.Agent;
import view.CellObjectView;
import view.EnvironmentView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO: Commenta algo
 *
 */
public class Controller implements Initializable {

    @FXML
    public BorderPane centralPane;

    @FXML
    public ChoiceBox noiseChoose;

    @FXML
    public ListView<MapObject> listObjects;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<MapObject> list = FXCollections.observableArrayList(
                new Agent(), new Resource(10, "Food"), new Block());

        listObjects.setItems(list);
        listObjects.setCellFactory(value -> new CellObjectView());
    }

    public void generateMap() {
        centralPane.setCenter(new EnvironmentView());
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
