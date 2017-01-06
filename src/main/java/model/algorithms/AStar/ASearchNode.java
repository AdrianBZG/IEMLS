package model.algorithms.AStar;

/**
 * Created by rudy on 6/01/17.
 */
public abstract class ASearchNode implements ISearchNode {
    private Double g = 0.0;
    // total estimated cost of the node
    public double f() {
        return this.g() + this.h();
    }
    //"tentative" g, cost from the start node
    public double g() {
        return this.g;
    }
    //set "tentative" g
    public void setG(double g) {
        this.g = g;
    }

}
