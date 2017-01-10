/**
 * IEMLS - AgentView.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object.agent;

import controller.ExplorerConfigurationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import model.object.MapObject;
import model.object.Resource;
import model.species.Specie;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import util.Tuple;
import view.ErrorView;
import view.ObjectView.CollectorAgentView;
import view.ObjectView.ObjectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


/**
 *
 *
 */
public class CollectorAgent extends Agent {

    private ArrayList<Resource> resources = new ArrayList<>();

    private ArrayList<Tuple<Integer, Integer>> visitedPoints = new ArrayList<>();

    /**
     * The agent specie
     */
    private Specie specie;

    /**
     * A simple constructor
     */
    public CollectorAgent() {}

    /**
     * Copy constructor
     * @param agent
     */
    public CollectorAgent(CollectorAgent agent) {
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
                getMap().get(getPosition().getX() + i, getPosition().getY() + j)
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

    @Override
    public ObjectView getVisualObject() {
        return new CollectorAgentView();
    }

    @Override
    public String getName() {
        return "Collector Agent";
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
            FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("CollectionAgentConfiguration.fxml"));
            Parent root = fxmlLoader.load();
            dialog.getDialogPane().setContent(root);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == applyChanges) {
                    ExplorerConfigurationController explorerConfigurationController = fxmlLoader.getController();
                    setAlgorithm(explorerConfigurationController.getAlgorithm());
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new CollectorAgent(this);
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
