/**
 * IEMLS - Sector.java 5/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.map;


/**
 * Auxiliary class used to access to different sectors into EnvironmentMap
 *
 */
public class Sector {
    /**
     * X coordinate
     */
    private int x;
    /**
     * Y Coordinate
     */
    private int y;

    private Sector(int x, int y) {
        this.x = x; this.y = y;
    }

    /**
     * Make a sector position to find into map.
     * @param x
     * @param y
     * @return
     */
    public static Sector pos(int x, int y) {
        return new Sector(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sector sector = (Sector) o;

        if (x != sector.x) return false;
        return y == sector.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
