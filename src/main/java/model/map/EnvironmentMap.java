/**
 * IEMLS - EnvironmentMap.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.map;

import model.map.generator.IGenerator;
import model.object.Block;
import model.object.MapObject;
import model.object.Resource;
import model.object.TypeObject;
import model.object.agent.Agent;
import util.Directions;
import util.Position;
import util.Tuple;
import view.EnvironmentView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


/**
 * Represent full map extension
 *
 * TODO: Map size limitation is Int size
 * TODO: Use BigInteger?
 * TODO: Test which should be the chunk size to be more efficient in memory
 * TODO: Let save map in disk.
 *       TODO: specified format or serialize?
 * TODO: Unload unused Chunk from memory to disk
 *
 */
public class EnvironmentMap {

    private static int CHUNK_SIZE = 32;

    /**
     * Partial Map with zones explored
     */
    HashMap<Sector, Chunk> map = new HashMap<>();

    Optional<Tuple<Integer, Integer>> dimensions = Optional.empty();

    Optional<IGenerator> generator = Optional.empty();

    EnvironmentView mapView = null;

    /**
     * List of Agents
     */
    ArrayList<Agent> agents = new ArrayList<Agent>();

    ArrayList<Directions> lastActions = new ArrayList<>();

    /**
     * Build a empty map
     */
    public EnvironmentMap() {
        getMap().put(Sector.pos(0,0), new Chunk(CHUNK_SIZE));
    }

    /**
     * Generate a procedural map
     * @param generator
     */
    public EnvironmentMap(IGenerator generator) {
        this.generator = Optional.of(generator);
        getMap().put(Sector.pos(0,0), new Chunk(CHUNK_SIZE));
        generateChunkNoise(Sector.pos(0,0));
    }


    private void generateChunkNoise(Sector pos) {
        if (generator.isPresent()) {
            for (int i = 0; i < CHUNK_SIZE; i++) {
                for (int j = 0; j < CHUNK_SIZE; j++) {
                    double gen = generator.get().generateAtPoint((pos.getX() * CHUNK_SIZE + i) / 10.0, (pos.getY() * CHUNK_SIZE + j) / 10.0);
                    if (gen > 0.5) {
                        set(pos.getX() * CHUNK_SIZE + i, pos.getY() * CHUNK_SIZE + j, new Block());
                    }
                    else if (gen > 0) {
                        set(pos.getX() * CHUNK_SIZE + i, pos.getY() * CHUNK_SIZE + j, new Resource(50, "Food"));
                    }
                }
            }
        }
    }

    /**
     * Remove a agent
     */
    private void removeAgent(Agent agent) {
        int i = 0;
        while (agents.size() > i && !agent.getPosition().equals(agents.get(i).getPosition())) i++;
        agents.remove(i);
    }

    /**
     * Add agent
     * @param agent
     * @return
     */
    private void addAgent(Agent agent) {
        agents.add(agent);
    }


    public ArrayList<Agent> getAgents () {
        return agents;
    }

    public Agent getAgent (int inx) {
        return agents.get(inx);
    }

    public Optional<MapObject> get(Tuple <Integer, Integer> pos) {
        return get(pos.getX(), pos.getY());
    }

    /**
     * Retrieve object from map position
     * @param x coordinate
     * @param y coordinate
     * @return Object from map
     */
    public Optional<MapObject> get(int x, int y) {
        if (getDimensions().isPresent()) {
            if (getDimensions().get().getWidth() < x || getDimensions().get().getHeight() < y
                    || x < 0 || y < 0) {
                return Optional.empty();
            }
            if (getDimensions().get().getWidth() <= x || getDimensions().get().getHeight() <= y
                    || x <= 0 || y <= 0) {
                return Optional.of(new Block());
            }
        }

        Sector sector = makeSector(x, y);
        Chunk chunk = getMap().get(sector);

        if (chunk == null) {
            Chunk chunkAux = new Chunk(CHUNK_SIZE);
            getMap().put(sector, chunkAux);
            if (getGenerator().isPresent()) {
                generateChunkNoise(sector);
                return get(x, y);
            }
            else {
                return Optional.empty();
            }
        }
        else {
            return chunk.get(Math.abs(x % CHUNK_SIZE), Math.abs(y % CHUNK_SIZE));
        }
    }

    /**
     * Set a object into map position, it ensure that not overlap other objects, *the chunk should already create*
     */
    public void set(int x, int y, MapObject object) {
        if (getDimensions().isPresent()) {
            if (getDimensions().get().getWidth() < x || getDimensions().get().getHeight() < y
                    || x < 0 || y < 0) {
                return;
            }
        }
        Chunk chunk = getMap().get(makeSector(x, y));
        if (chunk != null) {
            removeAt(x,y);
            if (object.getType() == TypeObject.Agent) {
                Agent agent = (Agent)object;
                agent.setPosition(new Tuple<>(x,y));
                agent.setMap(this);
                addAgent(agent);
            }
            chunk.set(Math.abs(x % CHUNK_SIZE), Math.abs(y % CHUNK_SIZE), object);
            if (mapView != null)
                mapView.updateMap();
        }
    }

    private Sector makeSector(int x, int y) {
        return Sector.pos((int)Math.floor((float)x / CHUNK_SIZE), (int)Math.floor((float)y / CHUNK_SIZE));
    }


    /**
     * Remove a map object in a determined position
     * @param x
     * @param y
     */
    public void removeAt (int x, int y) {
        get(x, y).ifPresent(mapObject -> {
            if (mapObject.getType() == TypeObject.Agent) {
                removeAgent((Agent) mapObject);
            }
            getMap().get(makeSector(x, y)).removeAt(Math.abs(x % CHUNK_SIZE), Math.abs(y % CHUNK_SIZE));
        });
    }

    /**
     * Generate a image, where each point represent a object into map
     * TODO: Implement
     * @return
     */
    public ArrayList<Integer> minimap() {
        return new ArrayList<>();
    }

    /**
     *
     */
    public HashMap<Sector, Chunk> getMap() {
        return map;
    }

    public Optional<IGenerator> getGenerator() {
        return generator;
    }

    public Optional<Tuple<Integer, Integer>> getDimensions() {
        return dimensions;
    }

    public void setDimensions(int x, int y) {
        this.dimensions = Optional.of(new Tuple<>(x, y));
    }

    public void setGenerator(Optional<IGenerator> generator) {
        this.generator = generator;
    }

    public void agentsStep (EnvironmentView mapView) {
        if (this.mapView == null) this.mapView = mapView;
        // Check if this will be the first step of the agents.
        boolean firstAction = true;

        if (!agents.isEmpty())
            firstAction = agents.get(0).getLastAction() == null;

        for (Agent agent : agents) {
            Directions nextAction = null;
            boolean mustChangeAction = false;  // Check if the agent can perform same action as before.
            Tuple<Integer, Integer> pos = agent.getPosition();
            System.out.println("Agent : " + agent.toString());
            removeAt(pos.getX(), pos.getY());

            ArrayList<Directions> allowedActions = agent.getAllowedActions();
            ArrayList<Directions> resources = new ArrayList<>();

            for (Directions dir : Directions.values())
                // Check if the agent is next to a resource in the given direction.
                if (resourceAtPos(Position.getInDirection(pos, dir)))
                    resources.add(dir);


            if (resources.size() > 0) // If there are resources near.
                nextAction = resources.get((int) (Math.random() * resources.size()));
            else {
                Integer action = (int) (Math.random() * allowedActions.size());

                if (!firstAction && action > ((1 - 0.9) * allowedActions.size())) {  // 90 % to take the same action as previous step.
                    if (checkAllowedPos(Position.getInDirection(pos, agent.getLastAction()), agent))
                        nextAction = agent.getLastAction();
                    else
                        mustChangeAction = true;
                }
                else {
                    mustChangeAction = true;
                }
            }
            if (mustChangeAction)
                nextAction = allowedActions.get((int) (Math.random() * allowedActions.size()));

            if (nextAction != null)
                performAction(agent, nextAction);
        }
    }



    /**
     * This method makes agents explore the map randomly avoiding last position and obstacles.
     * When resource is next to the agent he pick up it.
     */


    private boolean checkAllowedPos (Tuple<Integer, Integer> nextPos, Agent agent) {
        return ((!get(nextPos).isPresent() ||
                (get(nextPos).get().getType() != TypeObject.Obstacle &&
                        get(nextPos).get().getType() != TypeObject.Agent)));
    }

    private void performAction (Agent agent, Directions dir) {
        performAction(agent, agent.getPosition(), dir);
    }

    private void performAction (Agent agent, Tuple<Integer, Integer> pos, Directions dir) {
        if (dir != null) {
            switch(dir) {
                case UP:
                    set(pos.getX(), pos.getY() - 1, agent);
                    break;
                case RIGHT:
                    set(pos.getX() + 1, pos.getY(), agent);
                    break;
                case LEFT:
                    set(pos.getX() - 1, pos.getY(), agent);
                    break;
                case DOWN:
                    set(pos.getX(), pos.getY() + 1, agent);
                    break;
            }
        }
    }

    private boolean resourceAtPos (Tuple<Integer, Integer> pos) {
        return get(pos).isPresent() && get(pos).get().getType() == TypeObject.Resource;
    }

    /**
     * This method saves the map to a file. Is connected with the menu item save map button
     */
    public void saveMap () {

    }

    /**
     * This method load a map from a file. Is connected with the menu item load map button
     */
    public void loadMap () {

    }


}
