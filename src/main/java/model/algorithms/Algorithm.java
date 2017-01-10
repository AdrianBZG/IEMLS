package model.algorithms;

import javafx.scene.Node;
import model.algorithms.AStar.AStar;
import model.algorithms.AStar.LRTAStar;
import model.algorithms.AStar.RTAStar;
import model.algorithms.geneticalgorithm.ExplorerGeneticAlgorithm;
import model.algorithms.swarm_aco.AntColonyOptimization;
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
    private static ArrayList<Algorithm> explorationAlgorithms = new ArrayList<>();
    private static ArrayList<Algorithm> collectionAlgorithms = new ArrayList<>();
    private static ArrayList<Algorithm> swarmAlgorithms = new ArrayList<>();

    static {
        explorationAlgorithms.add(new CustomExplorer());

        /* Genetic algorithm for explorer using the follow default parameters:
            - 100 Population size
            - 0.05 Mutation rate
            - 0.9 Crossover Rate
            - 2 ElitismCount
            - 10 Tournament size
         */
        explorationAlgorithms.add(new ExplorerGeneticAlgorithm(100, 0.05, 0.9, 2, 10));

        collectionAlgorithms.add(new AStar());
        collectionAlgorithms.add(new RTAStar());
        collectionAlgorithms.add(new LRTAStar());

        swarmAlgorithms.add(new AntColonyOptimization());
    }

    public static ArrayList<Algorithm> getExplorationAlgorithms() {
        return explorationAlgorithms;
    }

    public static ArrayList<Algorithm> getCollectionAlgorithms() {
        return collectionAlgorithms;
    }

    public static ArrayList<Algorithm> getSwarmAlgorithms() {
        return swarmAlgorithms;
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
