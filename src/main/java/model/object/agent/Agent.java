/**
 * IEMLS - AgentView.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object.agent;

import controller.ConfigurationController;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import model.algorithms.Algorithm;
import model.map.EnvironmentMap;
import model.object.MapObject;
import model.object.Resource;
import model.object.TypeObject;
import model.species.Specie;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import util.Directions;
import util.Position;
import util.Tuple;
import view.ErrorView;
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

    private ArrayList<Resource> resources = new ArrayList<>();

    private ArrayList<Tuple<Integer, Integer>> visitedPoints = new ArrayList<>();

    /**
     * Reference to map
     */
    private EnvironmentMap map;

    /**
     * Algorithm into Agent
     */
    private Algorithm algorithm;

    /**
     * The agent specie
     */
    private Specie specie;

    /**
     * A simple constructor
     */
    public Agent() {}

    /**
     * Copy constructor
     * @param agent
     */
    public Agent(Agent agent) {
        setAlgorithm(agent.getAlgorithm().clone());
        setMap(agent.getMap());
        setPosition(agent.getPosition().getX(), agent.getPosition().getY());
    }

    /**
     * Get all elements in around of agent. Also get under agent object.
     * The around space is define with a matrix of 3x3 with current agent in center.
     * @return list with all objects found
     */
    public ArrayList<MapObject> getAround() {
        ArrayList<MapObject> list = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                map.get(getPosition().getX() + i, getPosition().getY() + j)
                        .ifPresent(list::add);

            }
        }
        return list;
    }


    /**
     * Add resource to the picked resources.
     * @param res is the picked resource
     */
    public void addResource (Resource res) {
        resources.add(res);
        System.out.println("Adding new resource. Size: " + resources.size());
    }

    /**
     * Get the array of resources
     * @return the resources.
     */
    public ArrayList<Resource> getResources () {
        return resources;
    }

    /**
     * Get the last picked resource.
     * @return the last picked resource.
     */
    public Resource getLastResource () {
        return resources.get(resources.size() - 1);
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


    public void move(Directions directions) {
        switch (directions) {
            case DOWN:
                this.getPosition().chgSnd((y) -> y + 1);
                break;
            case UP:
                this.getPosition().chgSnd((y) -> y - 1);
                break;
            case LEFT:
                this.getPosition().chgFst((x) -> x - 1);
                break;
            case RIGHT:
                this.getPosition().chgFst((x) -> x + 1);
                break;
        }
    }

    /**
     * Get left element of agent
     * @return
     */
    public MapObject left() {
        return map.get(getPosition().getX() - 1, getPosition().getY()).get();
    }

    /**
     * Get right element of agent
     * @return
     */
    public MapObject right() {
        return map.get(getPosition().getX() + 1, getPosition().getY()).get();
    }

    /**
     * Get down element of agent
     * @return
     */
    public MapObject down() {
        return map.get(getPosition().getX(), getPosition().getY() + 1).get();
    }

    /**
     * Get up element of agent
     * @return
     */
    public MapObject up() {
        return map.get(getPosition().getX() - 1, getPosition().getY() - 1).get();
    }

    /**
     * Get up left corner element of agent
     * @return
     */
    public MapObject upLeft() {
        return map.get(getPosition().getX() - 1, getPosition().getY() - 1).get();
    }

    /*
     * Get up right corner element of agent
     * @return
     */
    public MapObject upRight() {
        return map.get(getPosition().getX() + 1, getPosition().getY() - 1).get();
    }

    /**
     * Get up down right corner element of agent
     * @return
     */
    public MapObject downRight() {
        return map.get(getPosition().getX() + 1, getPosition().getY() + 1).get();
    }

    /**
     * Get down left corner element of agent
     * @return
     */
    public MapObject downLeft() {
        return map.get(getPosition().getX() - 1, getPosition().getY() + 1).get();
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

    /**
     *
     */
    @Override
    public void showOptions() {
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
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == applyChanges) {
                    ConfigurationController configurationController = fxmlLoader.getController();
                    setAlgorithm(configurationController.getAlgorithm());
                    return null;
                }
                return null;
            });

        } catch (IOException e) {
            new ErrorView("No possible load agent configuration");
            e.printStackTrace();
        }


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

    public Specie getSpecie() {
        return specie;
    }

    public void setSpecie(Specie specie) {
        this.specie = specie;
    }

    public ArrayList<Tuple<Integer, Integer>> getVisitedPoints() {
        return visitedPoints;
    }

    public void setVisitedPoints(ArrayList<Tuple<Integer, Integer>> visitedPoints) {
        this.visitedPoints = visitedPoints;
    }
}
