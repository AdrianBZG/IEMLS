/**
 * IEMLS - AgentView.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object.agent;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.scene.control.Dialog;
import model.object.MapObject;
import model.object.TypeObject;
import util.Directions;
import util.Tuple;
import view.AgentView;
import view.ObjectView;

import java.util.Optional;






/**
 *
 *
 */
public class Agent extends MapObject {
    /**
     * Position Agent
     */
    private Tuple<Integer, Integer> position = new Tuple<>(0,0);
    private Tuple<Integer, Integer> lastPos = new Tuple<>(0,0);
    private boolean firstPos = true;

    public Agent () {}

    @Override
    public TypeObject getType() {
        return TypeObject.Agent;
    }

    @Override
    public ObjectView getVisualObject() {
        return new AgentView();
    }

    @Override
    public String getName() {
        return "Agent";
    }

    @Override
    public boolean hasOptions() {
        return true;
    }

    @Override
    public void showOptions() {
        // TODO: Define showOptions to agent
        // This showOptions should let choose type of algorithm in background, and what objectives
        // have the agent.
        // A search resource blocks(unknown position of resource blocks), but it knows base block to save
        // resources, limit load, and ticks to get resource...
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Agent Configuration");
        dialog.setHeaderText("You can edit: \n - appearance, \n - internal algorithm, \n - objectives, \n - and export and import agents");

        FontIcon icon = new FontIcon(FontAwesome.COGS);
        icon.setIconSize(64);
        dialog.setGraphic(icon);

        ButtonType applyChanges = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyChanges, ButtonType.CANCEL);

        TextArea textArea = new TextArea();
        dialog.getDialogPane().setContent(textArea);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyChanges) {
                return null; // new changes
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
    }

    public Tuple<Integer, Integer> getPosition() {
        return position;
    }

    public Tuple<Integer, Integer> getLastPos () { return lastPos; }

    public void setPosition(Tuple<Integer, Integer> position) {
        if (firstPos) {
            firstPos = false;
        }
        else {
            lastPos = this.position;
        }
        this.position = position;
    }

    public void move (Directions dir) {
        switch (dir) {
            case UP:
                position.setSnd(position.getSnd() + 1);
                break;
            case RIGHT:
                position.setFst(position.getFst() + 1);
                break;
        }

    }
}
