/**
 * IEMLS - AgentsManager.java 3/1/16
 * <p>
 * Copyright 20XX IEMLS team. All rights reserved.
 */

package model;

import javafx.application.Platform;
import model.object.agent.Agent;

import java.util.*;
import java.util.function.Consumer;

/**
 * All thing relate to agents
 */
public class AgentsManager {
    /**
     * Agents
     */
    private ArrayList<Agent> agents = new ArrayList<>();
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
        for (Agent agent : agents) {
            agent.getAlgorithm().start(agent);
        }
        getTimer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (Agent agent : agents) {
                    Platform.runLater(() -> {
                        agent.getAlgorithm().update();
                        tickEv.accept(agent);
                        //System.out.println("Hello evil world");
                    });
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
                agent.getAlgorithm().stop();
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
