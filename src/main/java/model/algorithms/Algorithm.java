package model.algorithms;

import util.Tuple;

import java.util.ArrayList;

/**
 * Created by Rudolf Cicko on 7/11/16.
 * @email: alu0100824780@ull.edu.es
 * @description: Main class of algorithms
 */
public class Algorithm {

    private static ArrayList<Algorithm> algorithms = new ArrayList<>();

    public static ArrayList<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public static void addAlgorithms(Algorithm algorithm) {
        algorithms.add(algorithm);
    }
}
