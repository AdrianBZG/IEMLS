package model.map.generator;

import java.util.Random;

public class DisplacementFractalNoise implements IGenerator {
    /** Source of entropy */
    private Random rand_;

    /** Amount of roughness */
    float roughness_;

    /** Plasma fractal grid */
    private float[][] grid_;

    private float[][] normalizedGrid_;

    private long seed;

    private boolean generated_ = false;

    public DisplacementFractalNoise() {
    }

    private void initialize() {
        //roughness_ = (float)0.5 / EnvironmentMap.getDimensions().get().getWidth();
        roughness_ = 0.8f;
        //grid_ = new float[EnvironmentMap.getDimensions().get().getWidth()][EnvironmentMap.getDimensions().get().getHeight()];
        grid_ = new float[1000][1000];
        rand_ = new Random();

        int xh = grid_.length - 1;
        int yh = grid_[0].length - 1;

        // set the corner points
        grid_[0][0] = rand_.nextFloat() - 0.05f;
        grid_[0][yh] = rand_.nextFloat() - 0.05f;
        grid_[xh][0] = rand_.nextFloat() - 0.05f;
        grid_[xh][yh] = rand_.nextFloat() - 0.05f;

        // generate the fractal
        generate(0, 0, xh, yh);

        normalizedGrid_ = this.toNormalized();

        generated_ = true;
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

    public float[][] toNormalized() {
        int w = grid_.length;
        int h = grid_[0].length;
        float[][] ret = new float[w][h];
        for(int i = 0;i < w;i++) {
            for(int j = 0;j < h;j++) {
                if(grid_[i][j] > 0 && grid_[i][j] < 2) {
                    ret[i][j] = (float)0.6;
                } else if ((grid_[i][j] > 2 && grid_[i][j] < 6) || grid_[i][j] < 0) {
                    ret[i][j] = (float)0.1;
                }
            }
        }
        return ret;
    }

    @Override
    public void newSeed() {
        seed = new Random().nextInt();
    }

    @Override
    public double generateAtPoint(double x, double y) {
        if(!generated_) initialize();
        //System.out.println("Retorno:" + grid_[(int)x][(int)y]);
        return normalizedGrid_[(int)x][(int)y];
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