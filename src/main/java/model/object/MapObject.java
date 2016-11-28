/**
 * IEMLS - MapObject.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object;

import view.ObjectView;

/**
 * TODO: Define more interactions between objects
 *
 */
public abstract class MapObject {

    public abstract TypeObject getType();

    public abstract ObjectView getVisualObject();

    public abstract String getName();
}
