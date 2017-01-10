package model.algorithms.geneticalgorithm;

import model.algorithms.Algorithm;
import model.map.EnvironmentMap;
import model.object.TypeObject;
import model.object.agent.Agent;
import model.object.agent.ExplorerAgent;
import util.Directions;
import util.Position;
import util.Tuple;

import java.util.ArrayList;

/**
 * Created by adrian on 7/01/17.
 */
public class ExplorerGeneticAlgorithm extends Algorithm {
    public static int maxGenerations = 50;

    private Agent agent = null;
    Directions lastDirection = null;

    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    /**
     * Used for tournament selection in crossover.
     */
    protected int tournamentSize;

    public ExplorerGeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount,
                                    int tournamentSize) {

        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }

    /**
     * Initialize population
     *
     * @param chromosomeLength
     *            The length of the individuals chromosome
     * @return population The initial population generated
     */
    public Population initPopulation(int chromosomeLength) {
        // Initialize population
        Population population = new Population(this.populationSize, chromosomeLength);
        return population;
    }

    /**
     * Calculate fitness for an individual.
     *
     * This fitness calculation is a little more involved than chapter2's. In
     * this case we initialize a new Robot class, and evaluate its performance
     * in the given maze.
     *
     * @param individual
     *            the individual to evaluate
     * @return double The fitness value for individual
     */
    public double calcFitness(Individual individual, EnvironmentMap map, Agent agent) {
        int score = 0;

        // Loop over route and score each move
        for (Tuple<Integer,Integer> routeStep : agent.getVisitedPoints()) {
            if (!agent.getVisitedPoints().contains(map.get(routeStep.getX(), routeStep.getY()).get().getPosition())) {
                // Increase score for correct move
                score++;
                // If is a resource, another score point is added
                if (map.get(routeStep.getX(), routeStep.getY()).get().getType().equals(TypeObject.Resource)) {
                    score++;
                }
            }
        }

        // Store fitness
        individual.setFitness(score);

        return score;
    }

    /**
     * Evaluate the whole population
     *
     * Essentially, loop over the individuals in the population, calculate the
     * fitness for each, and then calculate the entire population's fitness. The
     * population's fitness may or may not be important, but what is important
     * here is making sure that each individual gets evaluated.
     *
     * @param population
     *            the population to evaluate
     */
    public void evalPopulation(Population population) {
        double populationFitness = 0;

        // Loop over population evaluating individuals and suming population
        // fitness
        for (Individual individual : population.getPopulation()) {
            populationFitness += this.calcFitness(individual, this.agent.getMap(), this.agent);
        }

        population.setPopulationFitness(populationFitness);
    }

    /**
     * Check if population has met termination condition
     *
     * Upper bound on the number of generations.
     *
     * @param generationsCount
     *            Number of generations passed
     * @return boolean True if termination condition met, otherwise, false
     */
    public boolean isTerminationConditionMet(int generationsCount) {
        return (generationsCount > ExplorerGeneticAlgorithm.maxGenerations);
    }

    /**
     * Selects parent for crossover using tournament selection
     *
     * Tournament selection works by choosing N random individuals, and then
     * choosing the best of those.
     *
     * @param population
     * @return The individual selected as a parent
     */
    public Individual selectParent(Population population) {
        // Create tournament
        Population tournament = new Population(this.tournamentSize);

        // Add random individuals to the tournament
        population.shuffle();
        for (int i = 0; i < this.tournamentSize; i++) {
            Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }

        // Return the best
        return tournament.getFittest(0);
    }

    /**
     * Apply mutation to population
     *
     * This method is the same as chapter2's version.
     *
     * @param population
     *            The population to apply mutation to
     * @return The mutated population
     */
    public Population mutatePopulation(Population population) {
        // Initialize new population
        Population newPopulation = new Population(this.populationSize);

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);

            // Loop over individual's genes
            for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                // Skip mutation if this is an elite individual
                if (populationIndex >= this.elitismCount) {
                    // Does this gene need mutation?
                    if (this.mutationRate > Math.random()) {
                        // Get new gene
                        int newGene = 1;
                        if (individual.getGene(geneIndex) == 1) {
                            newGene = 0;
                        }
                        // Mutate gene
                        individual.setGene(geneIndex, newGene);
                    }
                }
            }


            // Add individual to population
            newPopulation.setIndividual(populationIndex, individual);
        }

        // Return mutated population
        return newPopulation;
    }

    /**
     * Crossover population using single point crossover
     *
     * We want to select a contiguous region of the chromosome from
     * each parent.
     *
     * This might look like this:
     *
     * Parent1: AAAAAAAAAA
     * Parent2: BBBBBBBBBB
     * Child  : AAAABBBBBB
     *
     * @param population
     *            Population to crossover
     * @return Population The new population
     */
    public Population crossoverPopulation(Population population) {
        // Create new population
        Population newPopulation = new Population(population.size());

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            // Apply crossover to this individual?
            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
                // Initialize offspring
                Individual offspring = new Individual(parent1.getChromosomeLength());

                // Find second parent
                Individual parent2 = this.selectParent(population);

                // Get random swap point
                int swapPoint = (int) (Math.random() * (parent1.getChromosomeLength() + 1));

                // Loop over genome
                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    // Use half of parent1's genes and half of parent2's genes
                    if (geneIndex < swapPoint) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    }
                }

                // Add offspring to new population
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                // Add individual to new population without applying crossover
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }

        return newPopulation;
    }

    /**
     * Initialize algorithm, it could run in background
     */
    @Override
    public void start(Agent agent) {
        this.agent = agent;
    }

    /**
     * Get an update from algorithm, the environment uses ticks to update its "world" each unit of time its called this
     * function by all agents.
     */
    @Override
    public void update(Agent agent) {
        if (this.agent != null) {
            ExplorerAgent castedAgent = (ExplorerAgent)agent;
            boolean firstStep = lastDirection == null;
            boolean mustChangeAction = false;

            Directions nextAction = null;  // Its possible to don't have any allowed action, so the agent will be still.
            EnvironmentMap map = castedAgent.getMap();

            ArrayList<Directions> allowedActions = castedAgent.getAllowedActions();
            ArrayList<Directions> resources = new ArrayList<>();

            for (Directions dir : Directions.values())
                // Check if the agent is next to a resource in the given direction.
                map.get(Position.getInDirection(castedAgent.getPosition(), dir)).ifPresent(mapObject -> {
                            if (mapObject.getType().equals(TypeObject.Resource)) {
                                resources.add(dir);
                            }
                        }
                );

            if (resources.size() > 0) { // If there are resources near.
                //System.out.println("Next to resource");
                nextAction = resources.get((int) (Math.random() * resources.size()));
            } else {
                Integer action = (int) (Math.random() * allowedActions.size());
                if (!firstStep && action < ((0.75) * allowedActions.size())) {  // 75 % to take the same action as previous step.
                    if (castedAgent.checkAllowedPos(Position.getInDirection(castedAgent.getPosition(), lastDirection))) {
                        //System.out.println("Same action like before " + lastDirection);
                        nextAction = lastDirection;
                    } else {
                        mustChangeAction = true;
                    }
                } else {
                    mustChangeAction = true;
                }
            }
            if (mustChangeAction) {
                nextAction = allowedActions.get((int) (Math.random() * allowedActions.size()));
            }

            lastDirection = nextAction;
            castedAgent.move(nextAction);
            map.removeAt(agent);
        }
        else {
            start(agent);
        }
    }

    /**
     * Stop algorithm, especially when its used threads with ourselves resources.
     */
    @Override
    public void stop() {

    }

    @Override
    public String toString() {
        return "Explorer with Genetic Algorithm";
    }

    @Override
    public Algorithm clone() {
        return new ExplorerGeneticAlgorithm(this.populationSize,
                                             this.mutationRate,
                                             this.crossoverRate,
                                             this.elitismCount,
                                             this.tournamentSize);
    }

    public static int getMaxGenerations() {
        return maxGenerations;
    }

    public static void setMaxGenerations(int maxGenerations) {
        ExplorerGeneticAlgorithm.maxGenerations = maxGenerations;
    }

    @Override
    public int getAlgorithmType() {
        return 0;
    }
}
