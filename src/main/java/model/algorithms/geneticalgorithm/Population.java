package model.algorithms.geneticalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by adrian on 7/01/17.
 *
 * A population is an abstraction of a collection of individuals. The population
 * class is generally used to perform group-level operations on its individuals,
 * such as finding the strongest individuals, collecting stats on the population
 * as a whole, and selecting individuals to mutate or crossover.
 *
 */
public class Population {
    private ArrayList<Individual> population = new ArrayList<>();
    private double populationFitness = -1;

    /**
     * Initializes blank population of individuals
     */
    public Population(int populationSize) {
    }

    /**
     * Initializes population of individuals
     *
     * @param populationSize
     *            The number of individuals in the population
     * @param chromosomeLength
     *            The size of each individual's chromosome
     */
    public Population(int populationSize, int chromosomeLength) {
        // Initialize the population as an array of individuals
        this.population = new ArrayList<>();

        // Create each individual in turn
        for (int individualCount = 0; individualCount < populationSize; individualCount++) {
            // Create an individual, initializing its chromosome to the given
            // length
            Individual individual = new Individual(chromosomeLength);
            // Add individual to population
            this.population.add(individual);
        }
    }

    /**
     * Get individuals from the population
     *
     * @return individuals Individuals in population
     */
    public ArrayList<Individual> getPopulation() {
        return this.population;
    }

    /**
     * Find an individual in the population by its fitness
     *
     * This method lets you select an individual in order of its fitness. This
     * can be used to find the single strongest individual (eg, if you're
     * testing for a solution), but it can also be used to find weak individuals
     * (if you're looking to cull the population) or some of the strongest
     * individuals (if you're using "elitism").
     *
     * @param offset
     *            The offset of the individual you want, sorted by fitness. 0 is
     *            the strongest, population.length - 1 is the weakest.
     * @return individual Individual at offset
     */
    public Individual getFittest(int offset) {
        // Order population by fitness
        Collections.sort(this.population, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.getFitness() > o2.getFitness()) {
                    return -1;
                } else if (o1.getFitness() < o2.getFitness()) {
                    return 1;
                }
                return 0;
            }
        });

        // Return the fittest individual
        return population.get(offset);
    }

    /**
     * Set population's group fitness
     *
     * @param fitness
     *            The population's total fitness
     */
    public void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }

    /**
     * Get population's group fitness
     *
     * @return populationFitness The population's total fitness
     */
    public double getPopulationFitness() {
        return this.populationFitness;
    }

    /**
     * Get population's size
     *
     * @return size The population's size
     */
    public int size() {
        return this.population.size();
    }

    /**
     * Set individual at offset
     *
     * @param individual
     * @param offset
     * @return individual
     */
    public Individual setIndividual(int offset, Individual individual) {
        return population.set(offset, individual);
    }

    /**
     * Get individual at index
     *
     * @param index
     * @return individual
     */
    public Individual getIndividual(int index) {
        return population.get(index);
    }

    /**
     * Shuffles the population in-place
     *
     * @param void
     * @return void
     */
    public void shuffle() {
        Collections.shuffle(this.population);
    }
}
