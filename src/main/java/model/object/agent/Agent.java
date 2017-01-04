/**
 * IEMLS - AgentView.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object.agent;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import model.algorithms.Algorithm;
import model.map.EnvironmentMap;
import model.object.MapObject;
import model.object.TypeObject;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import util.Tuple;
import view.ObjectView.AgentView;
import view.ObjectView.ObjectView;

import java.io.IOException;
import java.util.ArrayList;
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
    private Algorithm algorithm;

    /**
     * A simple constructor
     */
    public Agent() {}

    /**
     * Copy constructor
     * @param agent
     */
    public Agent(Agent agent) {
        setAlgorithm(agent.getAlgorithm());
        setMap(agent.getMap());
        setPosition(agent.getPosition().getX(), agent.getPosition().getY());
    }

    public ArrayList<MapObject> getArounds() {
        ArrayList<MapObject> list = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 1 && j == 1)) {
                    map.get(getPosition().getX() + i, getPosition().getY() + j)
                        .ifPresent(list::add);
                }
            }
        }
        return list;
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
        dialog.getDialogPane().setPrefSize(500, 600);

        FontIcon icon = new FontIcon(FontAwesome.COGS);
        icon.setIconSize(64);
        dialog.setGraphic(icon);
        ButtonType applyChanges = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyChanges, ButtonType.CANCEL);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("AgentConfiguration.fxml"));

            Parent root = fxmlLoader.load();

            dialog.getDialogPane().setContent(root);

        } catch (IOException e) {
            e.printStackTrace();
        }


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyChanges) {
                return null;
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
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

    /**
     * Set position of agent
     * @param x
     * @param y
     */
    public void setPosition(Integer x, Integer y) {
        position.setFst(x);
        position.setSnd(y);
    }

    /**
     *
     * @param pos
     */
    public void setPosition(Tuple<Integer, Integer> pos) {
        position = pos;
    }

    public Tuple<Integer, Integer> getPosition () { return position; }

    public void setMap(EnvironmentMap map) {
        this.map = map;
    }

    public EnvironmentMap getMap () { return map; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Agent(this);
    }
}
