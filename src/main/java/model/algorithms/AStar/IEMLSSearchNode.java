package model.algorithms.AStar;

import model.map.EnvironmentMap;
import model.object.TypeObject;
import util.Tuple;

import java.util.ArrayList;

/**
 * Created by rudy on 7/01/17.
 */
public class IEMLSSearchNode extends ASearchNode {
    private int x;
    private int y;
    private IEMLSSearchNode parent;
    private GoalPosition goal;
    private EnvironmentMap map;

    public IEMLSSearchNode(int x, int y, IEMLSSearchNode parent, GoalPosition goal, EnvironmentMap map){
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.goal = goal;
        this.map = map;
    }
    public IEMLSSearchNode getParent(){
        return this.parent;
    }

    public ArrayList<ISearchNode> getSuccessors() {
        ArrayList<ISearchNode> successors = new ArrayList<ISearchNode>();

        if (map == null) System.out.println("Map is null");

        if (!map.get(x-1, y).isPresent() || map.get(x-1, y).get().getType() != TypeObject.Obstacle)
            successors.add(new IEMLSSearchNode(this.x-1, this.y, this, this.goal, map));
        if (!map.get(x+1, y).isPresent() || map.get(x+1, y).get().getType() != TypeObject.Obstacle)
            successors.add(new IEMLSSearchNode(this.x+1, this.y, this, this.goal, map));
        if (!map.get(x, y+1).isPresent() || map.get(x, y+1).get().getType() != TypeObject.Obstacle)
            successors.add(new IEMLSSearchNode(this.x, this.y+1, this, this.goal, map));
        if (!map.get(x, y-1).isPresent() || map.get(x, y-1).get().getType() != TypeObject.Obstacle)
            successors.add(new IEMLSSearchNode(this.x, this.y-1, this, this.goal, map));

        return successors;
    }
    public double h() {
        return this.dist(goal.getX(), goal.getY());
    }

    public double c(ISearchNode successor) {
        IEMLSSearchNode successorNode = this.castToIEMLSSearchNode(successor);
        return 1;
    }
    public void setParent(ISearchNode parent) {
        this.parent = this.castToIEMLSSearchNode(parent);
    }
    public boolean equals(Object other) {
        if(other instanceof IEMLSSearchNode) {
            IEMLSSearchNode otherNode = (IEMLSSearchNode) other;
            return (this.x == otherNode.getX()) && (this.y == otherNode.getY());
        }
        return false;
    }

    public int hashCode() {
        return (41 * (41 + this.x + this.y));
    }

    public double dist(int otherX, int otherY) {
        return Math.sqrt(Math.pow(this.x-otherX,2) + Math.pow(this.y-otherY,2));
    }
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Tuple<Integer, Integer> getPosition () {
        return new Tuple<>(x, y);
    }

    public String toString(){
        return "(" + Integer.toString(this.x) + ";" + Integer.toString(this.y)
                + ";h:"+ Double.toString(this.h())
                + ";g:" +  Double.toString(this.g()) + ")";
    }

    private IEMLSSearchNode castToIEMLSSearchNode(ISearchNode other) {
        return (IEMLSSearchNode) other;
    }

    public Integer keyCode() {
        return null;
    }
}
