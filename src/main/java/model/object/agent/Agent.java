/**
 * IEMLS - AgentView.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object.agent;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import model.algorithms.Algorithm;
import model.algorithms.Explorer;
import model.map.Chunk;
import model.map.EnvironmentMap;
import model.map.Sector;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import model.object.MapObject;
import model.object.TypeObject;
import util.Directions;
import util.Position;
import util.Tuple;
import view.AgentView;
import view.ObjectView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
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

    private Tuple<Integer, Integer> lastPosition = null;

    private Directions lastAction = null;

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

    public void setPosition (Tuple<Integer, Integer> pos) {
        if (getLastPosition() == null) {
            setLastPosition(new Tuple<>(0, 0));
        }
        else {
            setLastAction(Position.getDirectionFromPositions(getLastPosition(), position));
            setLastPosition(position);

        }
        position = pos;
    }

    public void setLastPosition (Tuple<Integer, Integer> pos) {
        lastPosition = pos;
    }

    public void setLastAction (Directions action) { lastAction = action; }

    public Directions getLastAction () { return lastAction; }

    public Tuple<Integer, Integer> getPosition () { return position; }
    public Tuple<Integer, Integer> getLastPosition () { return lastPosition; }

    public void setMap(EnvironmentMap map) {
        this.map = map;
    }
    public EnvironmentMap getMap () { return map; }

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
                setAlgorithm( algorithmChoiceBox.getSelectionModel().getSelectedItem());
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
     * Get all allowed actions for the actual position of the agent.
     * @return an array with all allowed actions.
     */
    public ArrayList<Directions> getAllowedActions () {
        ArrayList<Directions> allowedDirections = new ArrayList<>();
        for (Directions dir : Directions.values())
            if (checkAllowedPos(Position.getInDirection(getPosition(), dir)))
                allowedDirections.add(dir);

        return allowedDirections;
    }

    public boolean checkAllowedPos (Tuple<Integer, Integer> nextPos) {
        return ((!map.get(nextPos).isPresent() ||
                (map.get(nextPos).get().getType() != TypeObject.Obstacle &&
                map.get(nextPos).get().getType() != TypeObject.Agent)));
    }

    /**
     *
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Directions execStep () {
        if (algorithm != null) {
            return algorithm.execStep(this);

        }
        else {
            System.out.println("Without algorithm");
            return null;
        }
    }
}
