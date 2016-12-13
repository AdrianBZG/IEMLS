/**
 * IEMLS - AgentView.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object.agent;

import model.object.MapObject;
import model.object.TypeObject;
import util.Tuple;
import view.AgentView;
import view.ObjectView;

/**
 *
 *
 */
public class Agent extends MapObject {
    /**
     * Position Agent
     */
    private Tuple<Integer, Integer> position;

    @Override
    public TypeObject getType() {
        return TypeObject.Agent;
    }

    @Override
    public ObjectView getVisualObject() {
        return new AgentView();
    }

    @Override
    public String getName() {
        return "Agent";
    }

    @Override
    public boolean hasOptions() {
        return true;
    }

    @Override
    public void options() {
        // TODO: Define options to agent
        // This options should let choose type of algorithm in background, and what objectives
        // have the agent.
        // A search resource blocks(unknown position of resource blocks), but it knows base block to save
        // resources, limit load, and ticks to get resource...

    }

    public Tuple<Integer, Integer> getPosition() {
        return position;
    }

    public void setPosition(Tuple<Integer, Integer> position) {
        this.position = position;
    }
}
