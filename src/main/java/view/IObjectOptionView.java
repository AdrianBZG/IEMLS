/**
 * IEMLS - IObjectOptionView.java 14/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package view;

import model.object.MapObject;

import java.util.Optional;

/**
 * TODO: Commenta algo
 *
 */
public interface IObjectOptionView {
    boolean hasOptions();

    default Optional<MapObject> showOptions() {
        return Optional.empty();
    }
}
