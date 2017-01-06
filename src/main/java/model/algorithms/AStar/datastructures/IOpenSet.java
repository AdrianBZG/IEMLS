package model.algorithms.AStar.datastructures;

import model.algorithms.AStar.ISearchNode;

/**
 * Created by rudy on 6/01/17.
 */
public interface IOpenSet {

    public void add(ISearchNode node);
    public void remove(ISearchNode node);
    public ISearchNode poll();
    //returns node if present otherwise null
    public ISearchNode getNode(ISearchNode node);
    public int size();

}
