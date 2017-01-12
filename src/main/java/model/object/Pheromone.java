/**
 * IEMLS - Resource.java 6/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object;

import view.ObjectView.ObjectView;
import view.ObjectView.PheromoneView;

/**
 * Represent a unit visual of a pheromone, but it can be consumed partiality
 */
public class Pheromone extends MapObject {

    public Pheromone() {
    }

    @Override
    public TypeObject getType() {
        return TypeObject.Pheromone;
    }

    @Override
    public ObjectView getVisualObject() {
        return new PheromoneView();
    }

    @Override
    public String getName() {
        return "Pheromone";
    }

    @Override
    public boolean hasOptions() {
        return false;
    }

    /**
     * Display a dialog with capacity of modified the resource settings
     */
    @Override
    public void showOptions() {
    }
}
