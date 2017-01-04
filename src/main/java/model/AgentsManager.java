/**
 * IEMLS - AgentsManager.java 3/1/16
 * <p>
 * Copyright 20XX IEMLS team. All rights reserved.
 */

package model;

import javafx.application.Platform;
import javafx.concurrent.Task;
import model.object.agent.Agent;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * All thing relate to agents
 */
public class AgentsManager {

    /**
     * Agents
     */
    private ArrayList<Agent> agents = new ArrayList<>();

    /**
     * Delay between updates
     */
    private int delay = 1000;

    public AgentsManager() {
        // TODO: The agent can call to controller to remove them from scene, use events!

    }

    /**
     * Initialize all agents from environment. Each one in a independent thread
     */
    public void startAgents() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Agent agent : agents) {
                    Task task = new Task() {
                        @Override
                        protected Object call() throws Exception {
                            // agent.execStep();
                            return null;
                        }
                    };
                    Platform.runLater(task);
                }
            }
        },0, delay);
    }

    /**
     *
     */
    public void play() {

    }

    /**
     *
     */
    public void stop() {

    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<Agent> agents) {
        this.agents = agents;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
