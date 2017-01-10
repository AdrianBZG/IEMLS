package model.algorithms;

import model.object.agent.Agent;
import util.Directions;

/**
 * TODO: Document it
 * Created by eleazardd on 10/01/17.
 */
public class SituationAction extends Algorithm {

    /**
     * Initialize algorithm, it could run in background
     *
     * @param agent
     */
    @Override
    public void start(Agent agent) {

    }

    /**
     * Get an update from algorithm, the environment uses ticks to update its "world" each unit of time its called this
     * function by all agents.
     *
     * @param agent
     */
    @Override
    public void update(Agent agent) {

    }

    /**
     * Stop algorithm, especially when its used threads with ourselves resources.
     */
    @Override
    public void stop() {

    }

    @Override
    public Algorithm clone() {
        return null;
    }

    @Override
    public int getAlgorithmType() {
        return 0;
    }

    public boolean isVisited(Directions directions) {
        // TODO:
        return false;
    }
}
