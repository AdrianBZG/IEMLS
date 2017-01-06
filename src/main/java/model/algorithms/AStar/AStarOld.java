package model.algorithms.AStar;

import model.algorithms.Algorithm;
import model.map.EnvironmentMap;
import model.object.TypeObject;
import model.object.agent.Agent;
import util.Tuple;


;import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by rudy on 06/01/17.
 *  This method is offline. First it calculate the solution and then it have the full solution path to be traveled.
 */
public class AStarOld extends Algorithm {
    private Agent agent;
    private EnvironmentMap map;
    private Tuple<Integer, Integer> objective;
    private boolean ready = false;


    class Path {
        public ArrayList<Tuple<Integer, Integer>> path = new ArrayList<>();
        public double hCost;  // Heuristic cost
        public double gCost;  // Real cost
    }


    LinkedList<Path> openedList = new LinkedList<>();
    LinkedList<Path> closedList = new LinkedList<>();

    @Override
    public void start(Agent agent) {
        this.agent = agent;
        this.map = agent.getMap();
        openedList.add
    }

    public void setObjective (Tuple<Integer, Integer> objective) {
        this.objective = objective;
    }

    public Tuple<Integer, Integer> getObjective () { return objective; }


    /**
     * This method returns an heuristic cost from the given pos as parameter to the objective.
     * @param pos
     * @return the heuristic cost
     */
    private double getHeuristicCost (Tuple<Integer, Integer> pos) {
        return Math.sqrt(Math.pow(Math.abs(pos.getSnd() - objective.getSnd()), 2 ) +
                         Math.pow(Math.abs(pos.getFst() - objective.getFst()), 2 ));
    }

    public boolean checkObjectiveExists () {
        return map.get(objective).isPresent() && map.get(objective).equals(TypeObject.Resource);
    }

    @Override
    public void update() {

        if (ready && checkObjectiveExists()) {

        }
    }

    @Override
    public void stop() {

    }

    @Override
    public Algorithm clone() {
        return new AStarOld();
    }

    @Override
    public String toString() {
        return "A*";
    }
}
