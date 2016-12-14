/**
 * IEMLS - Resource.java 6/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object;

import view.ObjectView;
import view.ResourceView;

/**
 * Represent a unit visual of a resource, but it can be consumed partiality
 */
public class Resource extends MapObject {
    /**
     * Represent a available quantity of resource
     */
    private int quantity;
    /**
     * A identifier of resource, that let to agents determine if useful for them
     */
    private String tag; // TODO: When refactor transform to enum? or it is necessary a explicit name

    /**
     * Create a Resource
     */
    public Resource(int quantity, String name) {
        tag = name;
        this.quantity = quantity;
    }

    /**
     *
     */
    public int getQuantity() {
        return quantity;
    }

    public Resource setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     *
     */
    public String getTag() {
        return tag;
    }

    public Resource setTag(String tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public TypeObject getType() {
        return TypeObject.Resource;
    }

    @Override
    public ObjectView getVisualObject() {
        return new ResourceView();
    }

    @Override
    public String getName() {
        return "Resource";
    }

    @Override
    public boolean hasOptions() {
        return true;
    }

    /**
     * Display a dialog with capacity of modified the resource settings
     */
    @Override
    public void showOptions() {
        // TODO: Define it
    }
}
