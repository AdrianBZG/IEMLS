package model.algorithms;

import model.object.agent.Agent;

/**
 * Created by rudy on 31/12/16.
 */
public class Explorer extends Algorithm {

    /**
     * Initialize algorithm, it could run in background
     */
    @Override
    public void start() {

    }

    /**
     * Get an update from algorithm, the environment uses ticks to update its "world" each unit of time its called this
     * function by all agents.
     */
    @Override
    public void update() {

    }

    /**
     * Stop algorithm, especially when its used threads with ourselves resources.
     */
    @Override
    public void stop() {

    }

    @Override
    public String toString() {
        return "Explorer";
    }
}
