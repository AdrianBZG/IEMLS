package model.algorithms.swarm_aco;

import model.PheromonesManager;
import model.algorithms.Algorithm;
import model.object.agent.Agent;
import util.Tuple;
import view.EnvironmentView;

import java.util.ArrayList;

/**
 * Created by adrian on 10/01/17.
 */
public class AntColony extends Algorithm {
    private int numberOfColonyAnts = 4;         // Default is 4
    private Agent agent = null;
    private ArrayList<Ant> colonyAnts = new ArrayList<>();
    private EnvironmentView environmentView = null;

    public Tuple<Integer,Integer> getHomePosition() {
        if(agent != null) {
            return agent.getPosition();
        } else {
            return new Tuple<Integer,Integer>(0,0);
        }
    }

    private void initializeColonyAnts() {
        Ant newAnt1 = new Ant();
        newAnt1.setAlgorithm(this);
        newAnt1.setPosition(agent.getPosition().getX() + 1, agent.getPosition().getY());
        colonyAnts.add(newAnt1);

        Ant newAnt2 = new Ant();
        newAnt2.setAlgorithm(this);
        newAnt2.setPosition(agent.getPosition().getX() - 1, agent.getPosition().getY());
        colonyAnts.add(newAnt2);

        Ant newAnt3 = new Ant();
        newAnt3.setAlgorithm(this);
        newAnt3.setPosition(agent.getPosition().getX(), agent.getPosition().getY() + 1);
        colonyAnts.add(newAnt3);

        Ant newAnt4 = new Ant();
        newAnt4.setAlgorithm(this);
        newAnt4.setPosition(agent.getPosition().getX(), agent.getPosition().getY() - 1);
        colonyAnts.add(newAnt4);
    }

    private void initializePheromones() {
        for(int i = 0; i < agent.getMap().getDimensions().get().getX(); i ++){
            for(int j = 0; j < agent.getMap().getDimensions().get().getY(); j ++){
                PheromonesManager.getPheromones().put(new Tuple<Integer,Integer>(i,j), 0.0);
            }
        }
    }

    /**
     * Initialize algorithm, it could run in background
     */
    @Override
    public void start(Agent agent) {
        this.agent = agent;
        initializeColonyAnts();
        initializePheromones();
    }

    /**
     * Get an update from algorithm, the environment uses ticks to update its "world" each unit of time its called this
     * function by all agents.
     */
    @Override
    public void update(Agent agent) {
        if (this.agent != null) {
            for(Ant ant : colonyAnts) {
                ant.step();
                environmentView.paintAgent(ant);
            }

        }
        else {
            start(agent);
        }
    }

    /**
     * Stop algorithm, especially when its used threads with ourselves resources.
     */
    @Override
    public void stop() {
    }

    @Override
    public String toString() {
        return "Ant Colony Optimization";
    }

    @Override
    public Algorithm clone() {
        return new AntColony();
    }

    @Override
    public int getAlgorithmType() {
        return 2;
    }

    public int getNumberOfColonyAnts() {
        return numberOfColonyAnts;
    }

    public void setNumberOfColonyAnts(int numberOfColonyAnts) {
        this.numberOfColonyAnts = numberOfColonyAnts;
    }

    public EnvironmentView getEnvironmentView() {
        return environmentView;
    }

    public void setEnvironmentView(EnvironmentView environmentView) {
        this.environmentView = environmentView;
    }
}
