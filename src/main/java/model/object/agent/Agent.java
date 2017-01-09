package model.object.agent;

import model.algorithms.Algorithm;
import model.map.EnvironmentMap;
import model.object.MapObject;
import model.object.Resource;
import model.object.TypeObject;
import util.Directions;
import util.Position;
import util.Tuple;

import java.util.ArrayList;

/**
 * Created by adrian on 8/01/17.
 */
public abstract class Agent extends MapObject {
    /**
     * Position Agent
     */
    //private Tuple<Integer, Integer> position = new Tuple<>(0,0);

    private ArrayList<Resource> resources = new ArrayList<>();



    private ArrayList<Tuple<Integer, Integer>> visitedPoints = new ArrayList<>();

    /**
     * Reference to map
     */
    private EnvironmentMap map;

    /**
     * Algorithm into Agent
     */
    private Algorithm algorithm;

    /**
     * Get all allowed actions for the actual position of the agent.
     * @return an array with all allowed actions.
     */
    public ArrayList<Directions> getAllowedActions () {
        ArrayList<Directions> allowedDirections = new ArrayList<>();
        for (Directions dir : Directions.values())
            if (checkAllowedPos(Position.getInDirection(getPosition(), dir)))
                allowedDirections.add(dir);

        return allowedDirections;
    }

    public boolean checkAllowedPos (Tuple<Integer, Integer> nextPos) {
        return ((!getMap().get(nextPos).isPresent() ||
                (getMap().get(nextPos).get().getType() != TypeObject.Obstacle &&
                        getMap().get(nextPos).get().getType() != TypeObject.Agent)));
    }

    public ArrayList<Tuple<Integer, Integer>> getVisitedPoints() {
        return visitedPoints;
    }

    public void setVisitedPoints(ArrayList<Tuple<Integer, Integer>> visitedPoints) {
        this.visitedPoints = visitedPoints;
    }

    public void showVisitedPoints () {
        System.out.println("Visited POINTS: ");
        for (Tuple<Integer, Integer> p : visitedPoints) {
            System.out.println(p);
        }

        System.out.println("End VISITED POINTS");
    }

    public boolean containsVisitedPoint (Tuple<Integer, Integer> pos) {
        for (Tuple<Integer, Integer> p : visitedPoints) {
            if (pos.getX() == p.getX() && pos.getY() == p.getY())
                return true;
        }
        return false;
    }

    /**
     * Add resource to the picked resources.
     * @param res is the picked resource
     */
    public void addResource (Resource res) {
        resources.add(res);
    }

    /**
     * Get the array of resources
     * @return the resources.
     */
    public ArrayList<Resource> getResources () {
        return resources;
    }

    /**
     * Get the last picked resource.
     * @return the last picked resource.
     */
    public Resource getLastResource () {
        return resources.get(resources.size() - 1);
    }


    public void move(Directions directions) {
        if (directions == null) return;
        switch (directions) {
            case DOWN:
                this.getPosition().chgSnd((y) -> y + 1);
                break;
            case UP:
                this.getPosition().chgSnd((y) -> y - 1);
                break;
            case LEFT:
                this.getPosition().chgFst((x) -> x - 1);
                break;
            case RIGHT:
                this.getPosition().chgFst((x) -> x + 1);
                break;
        }
        if (getMap().get(getPosition()).isPresent() && getMap().get(getPosition()).get().getType().equals(TypeObject.Resource)) {
            visitedPoints.add(new Tuple<>(getPosition().getX(), getPosition().getY()));
            System.out.println ("Added point: " + visitedPoints.get(visitedPoints.size() - 1));
        }
    }

    /**
     * Get left element of agent
     * @return
     */
    public MapObject left() {
        return map.get(getPosition().getX() - 1, getPosition().getY()).get();
    }

    /**
     * Get right element of agent
     * @return
     */
    public MapObject right() {
        return map.get(getPosition().getX() + 1, getPosition().getY()).get();
    }

    /**
     * Get down element of agent
     * @return
     */
    public MapObject down() {
        return map.get(getPosition().getX(), getPosition().getY() + 1).get();
    }

    /**
     * Get up element of agent
     * @return
     */
    public MapObject up() {
        return map.get(getPosition().getX() - 1, getPosition().getY() - 1).get();
    }

    /**
     * Get up left corner element of agent
     * @return
     */
    public MapObject upLeft() {
        return map.get(getPosition().getX() - 1, getPosition().getY() - 1).get();
    }

    /*
     * Get up right corner element of agent
     * @return
     */
    public MapObject upRight() {
        return map.get(getPosition().getX() + 1, getPosition().getY() - 1).get();
    }

    /**
     * Get up down right corner element of agent
     * @return
     */
    public MapObject downRight() {
        return map.get(getPosition().getX() + 1, getPosition().getY() + 1).get();
    }

    /**
     * Get down left corner element of agent
     * @return
     */
    public MapObject downLeft() {
        return map.get(getPosition().getX() - 1, getPosition().getY() + 1).get();
    }

    @Override
    public TypeObject getType() {
        return TypeObject.Agent;
    }



    public EnvironmentMap getMap() {
        return map;
    }

    public void setMap(EnvironmentMap map) {
        this.map = map;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
