/**
 * IEMLS - AgentView.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object.agent;

import javafx.scene.Node;
import model.object.MapObject;
import model.object.TypeObject;
import view.AgentView;

/**
 *
 *
 */
public class Agent extends MapObject {

    @Override
    public TypeObject getType() {
        return TypeObject.Agent;
    }

    @Override
    public Node getVisualObject() {
        return new AgentView();
    }

    @Override
    public String getName() {
        return "Agent";
    }
}
