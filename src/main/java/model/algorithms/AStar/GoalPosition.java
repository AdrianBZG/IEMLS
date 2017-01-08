package model.algorithms.AStar;

/**
 * Created by rudy on 7/01/17.
 */
public class GoalPosition implements IGoalNode {
    private int x;
    private int y;

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
