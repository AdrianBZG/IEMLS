package controller;

import javafx.application.Platform;
import model.map.EnvironmentMap;
import view.EnvironmentView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rudy on 15/12/16.
 */
public class EnvironmentManager {


    private EnvironmentView mapView;
    private EnvironmentMap map;
    private static Timer timer = null;
    private int interval = 50;

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
                    map.agentsStep(mapView);
                    //mapView.updateMap();
                });
            }
        }, 10, interval);

    }


    public void stop () {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }



}
