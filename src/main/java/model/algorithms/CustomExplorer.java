package model.algorithms;

import model.map.EnvironmentMap;
import model.object.TypeObject;
import model.object.agent.Agent;
import model.object.agent.ExplorerAgent;
import util.Directions;
import util.Position;
import util.Tuple;

import java.util.ArrayList;

/**
 * Created by rudy on 31/12/16.
 */
public class CustomExplorer extends Algorithm {

    Directions lastDirection = null;

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
            boolean firstStep = lastDirection == null;
            boolean mustChangeAction = false;

            Directions nextAction = null;  // Its possible to don't have any allowed action, so the agent will be still.
            EnvironmentMap map = agent.getMap();

            ArrayList<Directions> allowedActions = agent.getAllowedActions();
            ArrayList<Directions> resources = new ArrayList<>();


            for (Directions dir : Directions.values())
                // Check if the agent is next to a resource in the given direction.
                map.get(Position.getInDirection(agent.getPosition(), dir)).ifPresent(mapObject -> {
                            if (mapObject.getType().equals(TypeObject.Resource) && !agent.containsVisitedPoint(Position.getInDirection(agent.getPosition(), dir))) {
                                resources.add(dir);
                            }
                        }
                );

            if (resources.size() > 0) { // If there are resources near.
                //System.out.println("Next to resource");
                nextAction = resources.get((int) (Math.random() * resources.size()));
            } else {
                Integer action = (int) (Math.random() * allowedActions.size());
                if (!firstStep && action < ((0.6) * allowedActions.size())) {  // 75 % to take the same action as previous step.
                    if (agent.checkAllowedPos(Position.getInDirection(agent.getPosition(), lastDirection))) {
                        //System.out.println("Same action like before " + lastDirection);
                        nextAction = lastDirection;
                    } else {
                        mustChangeAction = true;
                    }
                } else {
                    mustChangeAction = true;
                }
            }
            if (mustChangeAction) {
                nextAction = allowedActions.get((int) (Math.random() * allowedActions.size()));
            }

            lastDirection = nextAction;
            agent.move(nextAction);
            map.removeAt(agent);
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
        return "CustomExplorer";
    }

    @Override
    public Algorithm clone() {
        return new CustomExplorer();
    }

    @Override
    public int getAlgorithmType() {
        return 0;
    }
}
