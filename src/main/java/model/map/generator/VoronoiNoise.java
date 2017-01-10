package model.map.generator;

import java.util.Random;

public class VoronoiNoise implements IGenerator {
    //for speed, we can approximate the sqrt term in the distance funtions
    private static final double SQRT_2 = 1.4142135623730950488;
    private static final double SQRT_3 = 1.7320508075688772935;
    private static final double voronoiFrequency = 1.0;

    //You can either use the feature point height (for biomes or solid pillars), or the distance to the feature point
    private boolean useDistance = false;

    private long seed;
    private short distanceMethod;

    public VoronoiNoise() {
    }

    @Override
    public void newSeed() {
        seed = new Random().nextInt();
    }

    private double getDistance(double xDist, double zDist) {
        switch(distanceMethod) {
            case 0:
                return Math.sqrt(xDist * xDist + zDist * zDist) / SQRT_2;
            case 1:
                return xDist + zDist;
            default:
                return Double.NaN;
        }
    }

    public boolean isUseDistance() {
        return useDistance;
    }

    public void setUseDistance(boolean useDistance) {
        this.useDistance = useDistance;
    }

    public short getDistanceMethod() {
        return distanceMethod;
    }

    public long getSeed() {
        return seed;
    }

    public void setDistanceMethod(short distanceMethod) {
        this.distanceMethod = distanceMethod;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    /**
     * To avoid having to store the feature points, we use a hash function
     * of the coordinates and the seed instead. Those big scary numbers are
     * arbitrary primes.
     */
    public static double valueNoise2D (int x, int z, long seed) {
        long n = (1619 * x + 6971 * z + 1013 * seed) & 0x7fffffff;
        n = (n >> 13) ^ n;
        return 1.0 - ((double)((n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff) / 1073741824.0);
    }

    @Override
    public double generateAtPoint(double x, double y) {
        x *= voronoiFrequency;
        y *= voronoiFrequency;

        int xInt = (x > .0? (int)x: (int)x - 1);
        int zInt = (y > .0? (int)y: (int)y - 1);

        double minDist = 32000000.0;

        double xCandidate = 0;
        double zCandidate = 0;

        for(int zCur = zInt - 2; zCur <= zInt + 2; zCur++) {
            for(int xCur = xInt - 2; xCur <= xInt + 2; xCur++) {

                double xPos = xCur + valueNoise2D(xCur, zCur, seed);
                double zPos = zCur + valueNoise2D(xCur, zCur, new Random(seed).nextLong());
                double xDist = xPos - x;
                double zDist = zPos - y;
                double dist = xDist * xDist + zDist * zDist;

                if(dist < minDist) {
                    minDist = dist;
                    xCandidate = xPos;
                    zCandidate = zPos;
                }
            }
        }

        double returnValue = ((double)VoronoiNoise.valueNoise2D (
                (int)(Math.floor (xCandidate)),
                (int)(Math.floor (zCandidate)), seed));

        //System.out.println("Voronoi: " + returnValue);
        return returnValue;
    }

    @Override
    public String getGeneratorName() {
        return "Voronoi Noise";
    }

    @Override
    public String toString() {
        return getGeneratorName();
    }
}