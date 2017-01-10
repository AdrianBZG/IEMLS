package model.algorithms.swarm_aco;

import model.algorithms.Algorithm;
import model.object.agent.Agent;

/**
 * Created by adrian on 10/01/17.
 */
public class AntColonyOptimization extends Algorithm {
    private Agent agent = null;



    /**
     * Initialize algorithm, it could run in background
     */
    @Override
    public void start(Agent agent) {
        this.agent = agent;
    }

    /**
     * Get an update from algorithm, the environment uses ticks to update its "world" each unit of time its called this
     * function by all agents.
     */
    @Override
    public void update(Agent agent) {
        if (this.agent != null) {
        }
        else {
            start(agent);
        }
    }

    /**
     * Stop algorithm, especially when its used threads with ourselves resources.
     */
    @Override
    public void stop() {
    }

    @Override
    public String toString() {
        return "Ant Colony Optimization";
    }

    @Override
    public Algorithm clone() {
        return new AntColonyOptimization();
    }

    @Override
    public int getAlgorithmType() {
        return 2;
    }
}
