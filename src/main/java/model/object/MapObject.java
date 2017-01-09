/**
 * IEMLS - MapObject.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object;

import model.object.agent.CollectorAgent;
import model.object.agent.ExplorerAgent;
import model.object.agent.PerceptionActionAgent;
import model.object.agent.ScriptAgent;
import util.Tuple;
import view.ObjectView.ObjectView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * TODO: Define more interactions between objects
 *
 */
public abstract class MapObject implements Cloneable {

    /**
     * Internal use to have a "manual" list of all object available
     */
    private static ArrayList<MapObject> mapObjects = new ArrayList<>();

    private Tuple<Integer, Integer> objectPosition = new Tuple<>(0, 0);


    static {
        mapObjects = new ArrayList<>(Arrays.asList(
                new Block(),
                new Resource(10, "Food"),
                new ExplorerAgent(),
                new PerceptionActionAgent(),
                new CollectorAgent()));
    }

    /**
     * Basic representation of what is this object
     * @return enum
     */
    public abstract TypeObject getType();

    /**
     * Representation of object in 2D dimension
     * @return
     */
    public abstract ObjectView getVisualObject();

    /**
     * Name of object to user display identification
     * @return
     */
    public abstract String getName();

    /**
     * Is possible configure this object?
     * @return boolean to question
     */
    public abstract boolean hasOptions();

    /**
     * Each object define how is itself configured or not
     */
    public void showOptions() {};

    /**
     * Retrieve list of objects available to use
     */
    public static ArrayList<MapObject> getMapObjects() {
        return mapObjects;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        System.out.println("Called MapObject Clone");
        return super.clone();
    }

    public Tuple<Integer, Integer> getPosition() {
        return objectPosition;
    }

    public void setPosition(Tuple<Integer, Integer> objectPosition) {

        this.objectPosition = objectPosition;
    }

    public void setPosition (int x, int y) {

        objectPosition = new Tuple<>(x, y);
    }
}
