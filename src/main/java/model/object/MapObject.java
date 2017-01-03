/**
 * IEMLS - MapObject.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object;

import model.object.agent.Agent;
import model.object.agent.ScriptAgent;
import util.Tuple;
import view.ObjectView;

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

    static {
        mapObjects = new ArrayList<>(Arrays.asList(
                new Block(),
                new Resource(10, "Food"),
                new ScriptAgent(),
                new Agent()));
    }

    private Tuple<Integer, Integer> position = new Tuple<>(0,0);

    /**
     * Set position of object.
     * TODO: Warning this update don't have a really effect.
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        position = new Tuple<>(x, y);
    }

    /**
     * Current position of object
     */
    public Tuple<Integer, Integer> getPosition() {
        return position;
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
}
