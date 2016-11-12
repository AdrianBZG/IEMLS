/**
 * IEMLS - Controller.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void generateMap() {
        centralPane.setCenter(new Environment());
    }

    public void aboutDialog() {
        Dialog dialog = new Dialog();
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Got it!", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setTitle("About");
        dialog.setHeaderText("IEMLS");
        dialog.setContentText("Information about project ....");
        dialog.showAndWait();
    }
}
