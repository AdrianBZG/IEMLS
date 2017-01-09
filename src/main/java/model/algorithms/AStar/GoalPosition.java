package model.algorithms.AStar;

import util.Tuple;

/**
 * Created by rudy on 7/01/17.
 */
public class GoalPosition implements IGoalNode {
    private Integer x = null;
    private Integer y = null;


    public GoalPosition (Tuple<Integer, Integer> pos) {
        if (pos != null) {
            x = pos.getX();
            y = pos.getY();
        }
    }

    public boolean isValidPos () {
        return x != null && y != null;
    }

    public GoalPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean inGoal(ISearchNode other) {
        if(other instanceof IEMLSSearchNode) {
            IEMLSSearchNode otherNode = (IEMLSSearchNode) other;
            return (this.x == otherNode.getX()) && (this.y == otherNode.getY());
        }
        return false;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
}
