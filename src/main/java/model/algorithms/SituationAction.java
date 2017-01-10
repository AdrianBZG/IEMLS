package model.algorithms;

import model.dsl.IEval;
import model.object.agent.Agent;
import util.Directions;
import util.Tuple;

import java.util.ArrayList;

/**
 *
 * Created by eleazardd on 10/01/17.
 */
public class SituationAction extends Algorithm {

    private ArrayList<Tuple<IEval, Directions>> rules = new ArrayList<>();

    private ArrayList<Tuple<Integer, Integer>> visited = new ArrayList<>();

    public SituationAction() {
    }

    public SituationAction(SituationAction situationAction) {
        rules = (ArrayList<Tuple<IEval,Directions>>) situationAction.rules.clone();
    }

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
        return new SituationAction(this);
    }

    @Override
    public int getAlgorithmType() {
        return 0;
    }

    public boolean isVisited(Directions directions) {
        // TODO:
        return false;
    }


    public void addRule(IEval eval, Directions directions) {

    }
}
