package model.algorithms;

import model.map.EnvironmentMap;
import model.object.TypeObject;
import model.object.agent.Agent;
import util.Directions;
import util.Position;
import util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rudy on 31/12/16.
 */
public class Explorer extends Algorithm {

    public Directions execStep (Agent agent) {
        /*
        boolean firstStep = agent.getLastAction() == null;
        boolean mustChangeAction = false;

        Directions nextAction = null;  // Its possible to don't have any allowed action, so the agent will be still.
        EnvironmentMap map = agent.getMap();

        ArrayList<Directions> allowedActions = agent.getAllowedActions();
        ArrayList<Directions> resources = new ArrayList<>();


        for (Directions dir : Directions.values())
            // Check if the agent is next to a resource in the given direction.
            if (map.resourceAtPos(Position.getInDirection(agent.getPosition(), dir)))
                resources.add(dir);


        if (resources.size() > 0) {// If there are resources near.
            nextAction = resources.get((int) (Math.random() * resources.size()));
        }
        else {
            Integer action = (int) (Math.random() * allowedActions.size());

            if (!firstStep && action < ((1 - 0.8) * allowedActions.size())) {  // 90 % to take the same action as previous step.
                if (agent.checkAllowedPos(Position.getInDirection(agent.getPosition(), agent.getLastAction()))) {
                    System.out.println("Same action like before " + agent.getLastAction());
                    nextAction = agent.getLastAction();

                }
                else
                    mustChangeAction = true;
            }
            else {
                mustChangeAction = true;
            }
        }
        if (mustChangeAction)
            nextAction = allowedActions.get((int) (Math.random() * allowedActions.size()));


        return nextAction;
        */
        return null;
    }

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Explorer();
    }
}
