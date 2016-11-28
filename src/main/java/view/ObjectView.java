/**
 * IEMLS - ObjectView.java 14/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package view;

import javafx.scene.Group;
import model.object.MapObject;

import java.util.Optional;

/**
 * TODO: Commenta algo
 *
 */
public class ObjectView extends Group implements IObjectOptionView{
    @Override
    public boolean hasOptions() {
        return true;
    }

    @Override
    public Optional<MapObject> showOptions() {
        return null;
    }
}
