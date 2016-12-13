/**
 * IEMLS - Block.java 6/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object;

import view.BlockView;
import view.ObjectView;

/**
 * Represent a no passing tile, a wall
 */
public class Block extends MapObject {

    @Override
    public TypeObject getType() {
        return TypeObject.Obstacle;
    }

    @Override
    public ObjectView getVisualObject() {
        return new BlockView();
    }

    @Override
    public String getName() {
        return "Block";
    }

    @Override
    public boolean hasOptions() {
        return false;
    }
}
