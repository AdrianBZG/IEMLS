package model.map.generator;

import java.util.Random;

public class NullNoise implements IGenerator {
    public NullNoise() {
    }

    @Override
    public double generateAtPoint(double x, double y) {
        return 0.0;
    }

    @Override
    public String getGeneratorName() {
        return "No noise";
    }

    @Override
    public String toString() {
        return getGeneratorName();
    }
}