package model.map.generator;

import model.map.EnvironmentMap;

import java.util.Random;

public class DisplacementFractalNoise implements IGenerator {
    /** Source of entropy */
    private Random rand_;

    /** Amount of roughness */
    float roughness_;

    /** Plasma fractal grid */
    private float[][] grid_;

    public DisplacementFractalNoise() {
        float roughness = (float) 0.5;
        roughness_ = roughness / EnvironmentMap.getDimensions().get().getWidth();
        grid_ = new float[EnvironmentMap.getDimensions().get().getWidth()][EnvironmentMap.getDimensions().get().getHeight()];
        rand_ = new Random();
        initialize();
    }

    private void initialize() {
        int xh = grid_.length - 1;
        int yh = grid_[0].length - 1;

        // set the corner points
        grid_[0][0] = rand_.nextFloat() - 0.5f;
        grid_[0][yh] = rand_.nextFloat() - 0.5f;
        grid_[xh][0] = rand_.nextFloat() - 0.5f;
        grid_[xh][yh] = rand_.nextFloat() - 0.5f;

        // generate the fractal
        generate(0, 0, xh, yh);
    }

    // Add a suitable amount of random displacement to a point
    private float roughen(float v, int l, int h) {
        return v + roughness_ * (float) (rand_.nextGaussian() * (h - l));
    }


    // generate the fractal
    private void generate(int xl, int yl, int xh, int yh) {
        int xm = (xl + xh) / 2;
        int ym = (yl + yh) / 2;
        if ((xl == xm) && (yl == ym)) return;

        grid_[xm][yl] = 0.5f * (grid_[xl][yl] + grid_[xh][yl]);
        grid_[xm][yh] = 0.5f * (grid_[xl][yh] + grid_[xh][yh]);
        grid_[xl][ym] = 0.5f * (grid_[xl][yl] + grid_[xl][yh]);
        grid_[xh][ym] = 0.5f * (grid_[xh][yl] + grid_[xh][yh]);

        float v = roughen(0.5f * (grid_[xm][yl] + grid_[xm][yh]), xl + yl, yh
                + xh);
        grid_[xm][ym] = v;
        grid_[xm][yl] = roughen(grid_[xm][yl], xl, xh);
        grid_[xm][yh] = roughen(grid_[xm][yh], xl, xh);
        grid_[xl][ym] = roughen(grid_[xl][ym], yl, yh);
        grid_[xh][ym] = roughen(grid_[xh][ym], yl, yh);

        generate(xl, yl, xm, ym);
        generate(xm, yl, xh, ym);
        generate(xl, ym, xm, yh);
        generate(xm, ym, xh, yh);
    }

    @Override
    public double generateAtPoint(double x, double y) {
        return grid_[(int)x][(int)y];
    }

    @Override
    public String getGeneratorName() {
        return "Displacement Fractal Noise";
    }

    @Override
    public String toString() {
        return getGeneratorName();
    }
}
