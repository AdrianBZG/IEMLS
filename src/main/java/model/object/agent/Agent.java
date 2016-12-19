/**
 * IEMLS - AgentView.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object.agent;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import model.algorithms.Algorithm;
import model.map.EnvironmentMap;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import model.object.MapObject;
import model.object.TypeObject;
import util.Directions;
import util.Tuple;
import view.AgentView;
import view.ObjectView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
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

    /**
     * Reference to map
     */
    private EnvironmentMap map;

    /**
     * Algorithm into Agent
     */
    Algorithm algorithm;

    public Agent() {
    }

    public void setMap(EnvironmentMap map) {
        this.map = map;
    }

    public ArrayList<MapObject> getArounds() {
        ArrayList<MapObject> ret = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 1 && j == 1)) {
                    map.get(getPosition().getX() + i, getPosition().getY() + j)
                        .ifPresent((mapObject -> ret.add(mapObject)));
                }
            }
        }
        return ret;
    }

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

        ChoiceBox<Algorithm> algorithmChoiceBox = new ChoiceBox<>();
        algorithmChoiceBox.setItems(FXCollections.observableArrayList(Algorithm.getAlgorithms()));
        algorithmChoiceBox.getSelectionModel().selectFirst();

        dialog.getDialogPane().setContent(algorithmChoiceBox);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyChanges) {
                setAlgorithm(algorithmChoiceBox.getSelectionModel().getSelectedItem());
                return null;
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
    }

    public void move (Directions dir) {
        switch (dir) {
            case UP:
                map.removeAt(position.getX(), position.getY());
                position.setSnd(position.getSnd() + 1);
                map.set(position.getX(), position.getY(), this);
                break;
            case RIGHT:
                map.removeAt(position.getX(), position.getY());
                position.setFst(position.getFst() + 1);
                map.set(position.getX(), position.getY(), this);
                break;
            case LEFT:
                map.removeAt(position.getX(), position.getY());
                position.setFst(position.getFst() - 1);
                map.set(position.getX(), position.getY(), this);
                break;
            case DOWN:
                map.removeAt(position.getX(), position.getY());
                position.setFst(position.getSnd() - 1);
                map.set(position.getX(), position.getY(), this);
                break;
        }

    }

    /**
     *
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Agent setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }
}
