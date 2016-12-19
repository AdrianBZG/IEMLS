/**
 * IEMLS - MapObject.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object;

import util.Tuple;
import view.ObjectView;

/**
 * TODO: Define more interactions between objects
 *
 */
public abstract class MapObject {

    private Tuple<Integer, Integer> position = new Tuple<>(0,0);

    /**
     *
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        position = new Tuple<>(x, y);
    }

    /**
     *
     */
    public Tuple<Integer, Integer> getPosition() {
        return position;
    }

    public abstract TypeObject getType();

    public abstract ObjectView getVisualObject();

    public abstract String getName();

    public abstract boolean hasOptions();

    /**
     * TODO: Define how interact with this
     */
    public void showOptions() {};
}
