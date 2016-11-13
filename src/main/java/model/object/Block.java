/**
 * IEMLS - Block.java 6/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object;

import javafx.scene.Node;
import view.BlockView;

/**
 * Represent a no passing tile, a wall
 */
public class Block extends MapObject {

    @Override
    public TypeObject getType() {
        return TypeObject.Obstacle;
    }

    @Override
    public Node getVisualObject() {
        return new BlockView();
    }
}
