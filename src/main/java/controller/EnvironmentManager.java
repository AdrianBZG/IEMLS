package controller;

import javafx.application.Platform;
import model.map.EnvironmentMap;
import model.object.agent.Agent;
import util.Directions;
import util.Tuple;
import view.EnvironmentView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rudy on 15/12/16.
 */
public class EnvironmentManager {


    private EnvironmentView mapView;
    private EnvironmentMap map;
    private static Timer timer = null;
    private int interval = 500;

    public EnvironmentManager (EnvironmentView mapView) {

        this.mapView = mapView;
        map = mapView.getEnvironmentMap();
    }


    public Timer getTimer () {
        if (timer == null)  {
            timer = new Timer ();
        }
        return timer;
    }

    /**
     * Execute the timer for the IEMLS world.
     */
    public void play () {
        getTimer().scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                Platform.runLater(() -> {
                    exploreMap();
                });
            }
        }, 0, interval);

    }


    public void stop () {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void exploreMap() {
        if(map.getAgents().size() > 0) {
            Agent agent = map.getAgents().get(0);
            System.out.println(map.getAgents().size());
            map.removeAt(agent.getPosition().getX(), agent.getPosition().getY());
            map.set(agent.getPosition().getX(), agent.getPosition().getY() + 1, agent);
            mapView.updateMap();
        }
        else {
            System.out.println("No agents on map");
        }
    }


}
