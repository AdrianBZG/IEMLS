package model.algorithms.AStar.datastructures;

import model.algorithms.AStar.ISearchNode;

/**
 * Created by rudy on 6/01/17.
 */
public interface IClosedSet {

    public boolean contains(ISearchNode node);
    public void add(ISearchNode node);
    public ISearchNode min();
}
