package model.dsl;

import model.algorithms.SituationAction;
import model.object.agent.Agent;
import util.Directions;

import java.util.function.Predicate;

/**
 * TODO: Document it
 * Created by eleazardd on 10/01/17.
 */
public class IEval {

    /**
     * Predicate to satisfy to apply action
     */
    private Predicate<Agent> predicate = agent -> true; // over empty we can af

    public IEval() {
    }

    public boolean satisfy(Agent agent) {
        return predicate.test(agent);
    }

    public IEval or(IEval e2) {
        predicate = predicate.or(e2.predicate);
        return this;
    }

    public IEval and(IEval e2) {
        predicate = predicate.and(e2.predicate);
        return this;
    }

    public IEval not(IEval e1) {
        predicate = e1.predicate.negate();
        return this;
    }

    /**
     * Check if the direction is allowed to transit
     */
    public IEval free(Directions directions) {
        predicate = agent ->
            agent.getAllowedActions().contains(directions);
        return this;
    }

    public IEval visited(Directions directions) {
        predicate = agent ->
            ((SituationAction) agent.getAlgorithm()).isVisited(directions);
        return this;
    }
}
