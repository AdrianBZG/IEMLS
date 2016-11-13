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
import view.Environment;

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
    public ListView<String> listObjects;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> list = FXCollections.observableArrayList(
                "Item 1", "Item 2", "Item 3", "Item 4");
        listObjects.setItems(list);
    }

    public void generateMap() {
        centralPane.setCenter(new Environment());
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
