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

    public void or(IEval e1, IEval e2) {
        predicate = e1.predicate.or(e2.predicate);
    }

    public void and(IEval e1, IEval e2) {
        predicate = e1.predicate.and(e2.predicate);
    }

    public void not(IEval e1) {
        predicate = e1.predicate.negate();
    }

    /**
     * Check if the direction is allowed to transit
     */
    public void free(Directions directions) {
        predicate = agent ->
            agent.getAllowedActions().contains(directions);
    }

    public void visited(Directions directions) {
        predicate = agent ->
            ((SituationAction) agent.getAlgorithm()).isVisited(directions);
    }
}
