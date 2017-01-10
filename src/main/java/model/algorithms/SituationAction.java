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

    private Agent agent;

    public SituationAction() {
    }

    public SituationAction(SituationAction situationAction) {
        rules = (ArrayList<Tuple<IEval,Directions>>) situationAction.rules.clone();
        visited = (ArrayList<Tuple<Integer, Integer>>) visited.clone();
    }

    /**
     * Initialize algorithm, it could run in background
     *
     * @param agent
     */
    @Override
    public void start(Agent agent) {
        this.agent = agent;
    }

    /**
     * Get an update from algorithm, the environment uses ticks to update its "world" each unit of time its called this
     * function by all agents.
     *
     * @param agent
     */
    @Override
    public void update(Agent agent) {

        boolean find = false;
        int i = 0;

        while (!find && i < rules.size()) {
            Tuple<IEval, Directions> rule = rules.get(i);
            if (rule.getFst().satisfy(agent)) {
                find = true;
                if (agent.getAllowedActions().contains(rule.getSnd())) {
                    visited.add(agent.getPosition());
                    agent.move(rule.getSnd());
                    agent.getMap().removeAt(agent.getPosition().getX(), agent.getPosition().getY());
                }
                else {
                    //new ErrorView("Table Perception-Action is incoherent check it");
                }
            }
            i++;
        }

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
        Tuple<Integer, Integer> lastPosition = agent.getPosition();
        agent.move(directions);
        boolean result = visited.contains(agent.getPosition());
        agent.setPosition(lastPosition);
        return result;
    }


    public void addRule(IEval eval, Directions directions) {
        rules.add(new Tuple<>(eval, directions));
    }
}
