/**
 * IEMLS - EnvironmentMap.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.map;

import model.map.generator.IGenerator;
import model.object.IObject;

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
 */
public class EnvironmentMap<Obj extends IObject> {

    private static int CHUNK_SIZE = 32;

    /**
     * Partial Map with zones explored
     */
    HashMap<Sector, Chunk<Obj>> map = new HashMap<>();

    Optional<IGenerator> generator = Optional.empty();

    /**
     * Build a empty map
     */
    public EnvironmentMap() {
        getMap().put(Sector.pos(0,0), new Chunk(CHUNK_SIZE));
    }

    /**
     * Generate a procedural map
     * TODO: DO IT
     * TODO: Decide which procedural methods is use. In case of several methods pass through
     * TODO: arguments the object(function) that generate it in function of points
     * @param generator
     */
    public EnvironmentMap(IGenerator generator) {
        this.generator.of(generator);
    }

    /**
     * Retrieve object from map position
     * @param x coordinate
     * @param y coordinate
     * @return Object from map
     */
    public Optional<Obj> get(int x, int y) {
        Sector sector = Sector.pos(x / CHUNK_SIZE, y / CHUNK_SIZE);
        Chunk<Obj> chunk = getMap().get(sector);
        if (chunk == null) {
            if (getGenerator().isPresent()) {
                Chunk<Obj> chunkAux = new Chunk<>(getGenerator().get(), CHUNK_SIZE);
                getMap().put(sector, chunkAux);
                return chunkAux.get(x % CHUNK_SIZE, y % CHUNK_SIZE);
            }
            else {
                getMap().put(sector, new Chunk<>(CHUNK_SIZE));
                return Optional.empty();
            }
        }
        else {
            return chunk.get(x % CHUNK_SIZE, y % CHUNK_SIZE);
        }

    }

    /**
     * Set a object into map position, it ensure that not overlap other objects
     */
    public void set(int x, int y, Obj object) {
        //TODO: Generate chunk if not exist into hashmap the chunk. Here is strange but isn't a big penalization
        get(x, y);
        getMap().get(Sector.pos(x / CHUNK_SIZE, y / CHUNK_SIZE)).set(x % CHUNK_SIZE, y % CHUNK_SIZE, object);
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
    public HashMap<Sector, Chunk<Obj>> getMap() {
        return map;
    }

    public Optional<IGenerator> getGenerator() {
        return generator;
    }

    public void setGenerator(Optional<IGenerator> generator) {
        this.generator = generator;
    }
}
