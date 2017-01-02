package model.algorithms;

import model.object.agent.Agent;
import util.Directions;
import util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rudolf Cicko on 7/11/16.
 * @email: alu0100824780@ull.edu.es
 * @description: Main class of algorithms
 */
public abstract class Algorithm {

    private static ArrayList<Algorithm> algorithms = new ArrayList<>();

    public static ArrayList<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public static void addAlgorithms(Algorithm algorithm) {
        algorithms.add(algorithm);
    }


    public abstract Directions execStep (Agent agent);

}
