/**
 * IEMLS - AgentsManager.java 3/1/16
 * <p>
 * Copyright 20XX IEMLS team. All rights reserved.
 */

package model;

import javafx.application.Platform;
import model.object.Resource;
import model.object.agent.Agent;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * The known resources manager
 */
public class ResourcesManager {
    /**
     * Resources
     */
    private static ArrayList<Resource> resources = new ArrayList<>();

    /**
     * Add resource to the picked resources.
     * @param res is the picked resource
     */
    public static void addResource (Resource res) {
        resources.add(res);
        System.out.println("Adding new resource. Size: " + resources.size());
    }

    /**
     * Get the array of resources
     * @return the resources.
     */
    public static ArrayList<Resource> getResources () {
        return resources;
    }

    /**
     * Get the last picked resource.
     * @return the last picked resource.
     */
    public static Resource getLastResource () {
        return resources.get(resources.size() - 1);
    }
}
