/**
 * IEMLS - AgentsManager.java 3/1/16
 * <p>
 * Copyright 20XX IEMLS team. All rights reserved.
 */

package model;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.object.agent.Agent;
import model.object.agent.ExplorerAgent;

import java.util.*;
import java.util.function.Consumer;

/**
 * All thing relate to agents
 */
public class AgentsManager {
    /**
     * Agents
     */
    private static ObservableList<Agent> agents = FXCollections.observableArrayList(new ArrayList<>());
    /**
     * Launch an event each tick
     */
    public Consumer<Agent> tickEv = (a) -> {};

    /**
     * Delay between updates
     */
    private int delay = 100;

    private static Timer timer = null;

    public AgentsManager() {
        // TODO: The agent can call to controller to remove them from scene, use events!
        timer = new Timer();
    }

    /**
     * Initialize all agents from environment. Each one in a independent thread
     */
    public void play() {
        stop(); // Little fix for start button
        if (timer != null) { // Avoid play several times
            return;
        }

        for (Agent agent : agents) {
            if (agent.getAlgorithm() != null) {
                agent.getAlgorithm().start(agent);
            }
        }
        getTimer().schedule(new TimerTask() {
            @Override
            public void run() {

                for (Agent agent : agents) {
                    if (agent.getAlgorithm() != null) {
                        Platform.runLater(() -> {
                            agent.getAlgorithm().update(agent);
                            tickEv.accept(agent);
                        });
                    }
                }
            }
        },0, delay);
    }

    /**
     *
     */
    public void stop() {
        if (timer != null) {
            for (Agent agent : agents) {
                if(agent.getAlgorithm() != null) agent.getAlgorithm().stop();
            }
            timer.cancel();
            timer = null;
        }
    }

    public Timer getTimer () {
        if (timer == null)  {
            timer = new Timer ();
        }
        return timer;
    };

    public ObservableList<Agent> getAgents() {
        return agents;
    }
    public void setAgents(ObservableList<Agent> agents) {
        this.agents = agents;
    }
    public int getDelay() {
        return delay;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * This method returns a random agent explorer that picked up at least one resource.
     * @return an explorer that picked at least one resource.
     */
    public static Agent getRandomExplorer () {
        ArrayList<Agent> explorers = new ArrayList<>();

        for (Agent agent : agents) {
            if (agent.getAlgorithm().toString() == "Explorer" && agent.getResources().size() > 0) {
                explorers.add(agent);
            }
        }

        if (!explorers.isEmpty())
            return explorers.get((int)(Math.random() * explorers.size()));
        else {
            return null;
        }
    }

    public static boolean existsExplorers () {
        System.out.println ("Number of agents: " + agents.size());
        for (Agent agent : agents) {
            System.out.println ("Agent: " + agent.getAlgorithm().toString() + " with resources size: " + agent.getResources().size());
            if (agent.getAlgorithm().toString() == "Explorer" && agent.getResources().size() > 0) {
                System.out.println ("Now exists explorers");
                return true;
            }
        }
        return false;
    }
}
