/**
 * IEMLS - Chunk.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.map;

import model.map.generator.IGenerator;
import model.object.IObject;

import java.util.HashMap;
import java.util.Optional;

/**
 * TODO: Commenta algo
 *
 */
public class Chunk<Obj extends IObject> {
    /**
     * Represent chunk size
     */
    private int size;

    /**
     * Represent information about contents in the chunk
     */
    HashMap<Integer, Obj> chunk = new HashMap<Integer, Obj>();

    /**
     * Generate a empty Chunk
     * @param chunkSize size of chunk
     */
    public Chunk(int chunkSize) {
        size = chunkSize;
    }

    /**
     * Generate a chunk with specified generator
     * TODO: implement a interface to generator
     * @param generator
     * @param chunkSize
     */
    public Chunk(IGenerator generator, int chunkSize) {

    }

    /**
     * Get a map object in the limited chunk
     * @param x
     * @param y
     * @return
     */
    public Optional<Obj> get(int x, int y) {
        if (x < getSize() && y < getSize()
            && x >= 0 && y >= 0) {
            return Optional.ofNullable(getUnsafe(x, y));
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * Unsafe version of get (faster)
     * @param x
     * @param y
     * @return
     */
    public Obj getUnsafe(int x, int y) {
        return getChunk().get(y * getSize() + x);
    }

    /**
     * Set a IObject into a position into chunk
     */
    public void set(int x, int y, Obj object) {
        getChunk().put(y * getSize() + x, object);
    }

    /**
     *
     */
    public int getSize() {
        return size;
    }

    /**
     *
     */
    public HashMap<Integer, Obj> getChunk() {
        return chunk;
    }

    public void removeAt (int x, int y) {
        chunk.remove(y * getSize() + x);
    }
}
