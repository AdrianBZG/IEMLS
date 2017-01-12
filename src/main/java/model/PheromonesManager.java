/**
 * IEMLS - AgentsManager.java 3/1/16
 * <p>
 * Copyright 20XX IEMLS team. All rights reserved.
 */

package model;

import model.object.Resource;
import util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The known resources manager
 */
public class PheromonesManager {
    /**
     * Pheromones for Swarm algorithms
     */
    public static HashMap<Tuple<Integer,Integer>,Double> pheromones;
    public static HashMap<Tuple<Integer,Integer>,Double> pheromonesTimer;

    public static HashMap<Tuple<Integer,Integer>,Double> getPheromones () {
        return pheromones;
    }
    public static HashMap<Tuple<Integer,Integer>,Double> getPheromonesTimer () {
        return pheromonesTimer;
    }
}
