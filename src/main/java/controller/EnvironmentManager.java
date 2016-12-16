package controller;

import java.util.Timer;

/**
 * Created by rudy on 15/12/16.
 */
public class EnvironmentManager {

    private static Timer timer = null;


    public Timer getTimer () {
        if (timer == null) timer = new Timer ();
        return timer;
    }


    /**
     * Execute the timer for the IEMLS world.
     */
    public void play () {

    }


    public void stop () {

    }

}
