package model.algorithms;

import model.object.MapObject;
import model.object.Resource;
import model.object.TypeObject;
import model.object.agent.Agent;
import util.Directions;
import view.ErrorView;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * TODO: Document it
 * Created by eleazardd on 10/01/17.
 */
public class PerceptionAction extends Algorithm {

    private class Rule {
        public Predicate<Agent> predicate;
        public Directions direction;

        private Rule(Predicate<Agent> predicate, Directions directions) {
            this.direction = directions;
            this.predicate = predicate;
        }

        public Predicate<Agent> getPredicate() {
            return predicate;
        }

        public Directions getDirection() {
            return direction;
        }
    }

    private ArrayList<Rule> rules = new ArrayList<>();


    public PerceptionAction() {

    }

    public PerceptionAction(PerceptionAction perceptionAction) {
        this.rules = (ArrayList<Rule>) perceptionAction.rules.clone();
    }


    public void addRule(String left, String right, String up, String down, Directions directions) {
        Predicate<Agent> predicate = agent ->
                ((left.equals("Any") || TypeObject.valueOf(left).equals(agent.left().map(MapObject::getType).orElse(TypeObject.Empty)))
                    && (right.equals("Any") || TypeObject.valueOf(right).equals(agent.right().map(MapObject::getType).orElse(TypeObject.Empty)))
                    && (up.equals("Any") || TypeObject.valueOf(up).equals(agent.up().map(MapObject::getType).orElse(TypeObject.Empty)))
                    && (down.equals("Any") || TypeObject.valueOf(down).equals(agent.down().map(MapObject::getType).orElse(TypeObject.Empty))));

        rules.add(new Rule(predicate, directions));
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
        boolean find = false;
        int i = 0;

        while (!find && i < rules.size()) {
            Rule rule = rules.get(i);
            if (rule.predicate.test(agent)) {
                find = true;
                if (agent.getAllowedActions().contains(rule.direction)) {
                    agent.move(rule.direction);
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
        return new PerceptionAction(this);
    }

    @Override
    public int getAlgorithmType() {
        return 0;
    }
}
