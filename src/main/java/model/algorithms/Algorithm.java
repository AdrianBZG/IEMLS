package model.algorithms;

import javafx.scene.Node;
import model.algorithms.AStar.AStar;
import model.algorithms.geneticalgorithm.ExplorerGeneticAlgorithm;
import model.object.agent.Agent;

import java.util.ArrayList;

/**
 * Created by Rudolf Cicko on 7/11/16.
 * @email: alu0100824780@ull.edu.es
 * @description: Main class of algorithms
 */
public abstract class Algorithm implements Cloneable {

    /**
     * All available algorithms
     */
    private static ArrayList<Algorithm> algorithms = new ArrayList<>();

    static {
        algorithms.add(new CustomExplorer());

        /* Genetic algorithm for explorer using the follow default parameters:
            - 100 Population size
            - 0.05 Mutation rate
            - 0.9 Crossover Rate
            - 2 ElitismCount
            - 10 Tournament size
         */
        algorithms.add(new ExplorerGeneticAlgorithm(100, 0.05, 0.9, 2, 10));
        algorithms.add(new AStar());
    }

    public static ArrayList<Algorithm> getAlgorithms() {
        return algorithms;
    }

    /**
     * Initialize algorithm, it could run in background
     */
    public abstract void start(Agent agent);

    /**
     * Get an update from algorithm, the environment uses ticks to update its "world" each unit of time its called this
     * function by all agents.
     */
    public abstract void update(Agent agent);

    /**
     * Stop algorithm, especially when its used threads with ourselves resources.
     */
    public abstract void stop();

    public abstract Algorithm clone();

    // 0 = Explorer, 1 = Collector
    public abstract int getAlgorithmType();
}
