package model.algorithms.geneticalgorithm;

/**
 * Created by adrian on 7/01/17.
 */
import java.util.Random;

public class Candidate implements Comparable<Candidate> {
    public static final int SIZE = 10;
    public boolean[] genotype;
    final Random rand = new Random();

    public Candidate() {
        genotype = new boolean[SIZE];
    }

    void random() {
        for (int i = 0; i < genotype.length; i++) {
            genotype[i] = 0.5 > rand.nextFloat();
        }
    }

    private String gene() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genotype.length; i++) {
            sb.append(genotype[i] == true ? 1 : 0);
        }
        return sb.toString();
    }

    int fitness() {
        int sum = 0;
        for (int i = 0; i < genotype.length; i++) {
            if (genotype[i])
                sum++;
        }
        return sum;
    }

    public int compareTo(Candidate o) {
        int f1 = this.fitness();
        int f2 = o.fitness();

        if (f1 < f2)
            return 1;
        else if (f1 > f2)
            return -1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return "gene=" + gene() + " fit=" + fitness();
    }
}
