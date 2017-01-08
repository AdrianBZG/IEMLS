package model.algorithms.AStar;

import java.util.ArrayList;

/**
 * Created by rudy on 7/01/17.
 */
public class IEMLSSearchNode extends ASearchNode {
    private int x;
    private int y;
    private IEMLSSearchNode parent;
    private GoalPosition goal;

    public IEMLSSearchNode(int x, int y, IEMLSSearchNode parent, GoalPosition goal){
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.goal = goal;

    }
    public IEMLSSearchNode getParent(){
        return this.parent;
    }
    public ArrayList<ISearchNode> getSuccessors() {
        ArrayList<ISearchNode> successors = new ArrayList<ISearchNode>();
        successors.add(new IEMLSSearchNode(this.x-1, this.y, this, this.goal));
        successors.add(new IEMLSSearchNode(this.x+1, this.y, this, this.goal));
        successors.add(new IEMLSSearchNode(this.x, this.y+1, this, this.goal));
        successors.add(new IEMLSSearchNode(this.x, this.y-1, this, this.goal));
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
