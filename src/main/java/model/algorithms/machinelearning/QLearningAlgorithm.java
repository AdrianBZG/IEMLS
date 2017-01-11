package model.algorithms.machinelearning;

/**
 * Created by adrian on 8/01/17.
 */

import model.ResourcesManager;
import model.algorithms.Algorithm;
import model.object.TypeObject;
import model.object.agent.Agent;
import util.Directions;
import util.Tuple;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class QLearningAlgorithm extends Algorithm {
    private final static int NUMBER_OF_TRAINING_EPISODES = 1000;
    private final static int RESOURCE_REWARD_VALUE = 100;

    int[][] R = new int[1][1]; // Reward Lookup
    double[][] Q = new double[1][1]; // Q Learning

    // Q-Learning parameters
    private double alpha = 0.1;
    private double gamma = 0.9;
    private boolean finished = false;
    private int trainingIteration = 1;
    private Random rand;
    private int collectedAmountOfResources = 0;

    // Time handling
    private long BEGIN_TIME = 0L;
    private long END_TIME = 0L;

    /* http://en.wikipedia.org/wiki/Q-learning

       Q(s,a)= Q(s,a) + alpha * (R(s,a) + gamma * Max(next state, all actions) - Q(s,a)) */
    private Tuple<Integer, Integer> initialPos;
    private Agent agent = null;

    public QLearningAlgorithm() {
    }

    @Override
    public void start(Agent agent) {
        this.agent = agent;
        this.finished = false;
        this.trainingIteration = 1;
        this.rand = new Random();
        this.initialPos = new Tuple<Integer, Integer>(agent.getPosition().getX(), agent.getPosition().getY());

        // Allocate memory for the R and Q matrix
        if(agent.getMap().getDimensions().isPresent()) {
            Tuple<Integer, Integer> mapDimensions = agent.getMap().getDimensions().get();
            R = new int[mapDimensions.getX()][mapDimensions.getY()]; // reward lookup
            Q = new double[mapDimensions.getX()][mapDimensions.getY()]; // Q learning
        }

        // Set reward matrix
        init();

        long BEGIN = System.currentTimeMillis();
    }

    @Override
    public void update(Agent agent) {
        if (this.agent == null) start(agent);

        if (!finished) {
            /*
         1. Set parameter , and environment reward matrix R
         2. Initialize matrix Q as zero matrix
         3. For each episode: Select random initial state
            Do while not reach goal state o
                Select one among all possible actions for the current state o
                Using this possible action, consider to go to the next state o
                Get maximum Q value of this next state based on all possible actions o
                Compute o Set the next state as the current state
         */
            // For each episode
            // Select random initial state
            this.trainingIteration++;
            Tuple<Integer,Integer> state = getRandomPositionToStart();
            agent.setPosition(state);

            while (!isGoalReached()) // goal state
            {
                // Select one among all possible actions for the current state
                ArrayList<Directions> allowedActionsFromState = agent.getAllowedActions();

                // Selection strategy is random in this example
                Directions action = allowedActionsFromState.get(rand.nextInt(allowedActionsFromState.size()));

                // Action outcome is set to deterministic in this example
                // Transition probability is 1

                agent.move(action);

                // Using this possible action, consider to go to the next state
                double q = Q(agent.getPosition());
                double maxQ = maxQ();
                int r = R(agent.getPosition());

                System.out.println("(Machine Learning) Pruebo de: " + state + " a " + action + ", Recompensa: " + r + ", Valor Q actual: " + q);

                double value = q + getAlpha() * (r + getGamma() * maxQ - q);
                setQ(state, value);

                // Set the next state as the current state
                state = agent.getPosition();
            }
            finished = checkTrainingFinished();
        } else {
            long END = System.currentTimeMillis();
            this.trainingIteration = 1;
            rand = new Random();
            System.out.println("Time: " + (this.END_TIME - this.BEGIN_TIME) / 1000.0 + " sec.");
        }
    }

    private Tuple<Integer,Integer> getRandomPositionToStart() {
        if(agent.getMap().getDimensions().isPresent()) {
            Tuple<Integer, Integer> mapDimensions = agent.getMap().getDimensions().get();
            while(true) {
                int randomPosX = new Random().nextInt(mapDimensions.getX());
                int randomPosY = new Random().nextInt(mapDimensions.getY());
                if(agent.getMap().get(randomPosX, randomPosY).isPresent()) {
                    if(!agent.getMap().get(randomPosX, randomPosY).get().getType().equals(TypeObject.Obstacle)) {
                        return new Tuple<Integer,Integer>(randomPosX, randomPosY);
                    }
                }
            }
        }

        // Not found random position (impossible)
        return null;
    }

    private boolean checkTrainingFinished() {
        return this.trainingIteration == QLearningAlgorithm.NUMBER_OF_TRAINING_EPISODES;
    }

    private boolean isGoalReached() {
        return (0.3 * collectedAmountOfResources) >= ResourcesManager.totalAmountOfResources;
    }

    @Override
    public void stop() {
    }

    @Override
    public String toString() {
        return "Q-Learning";
    }

    @Override
    public Algorithm clone() {
        return new QLearningAlgorithm();
    }

    @Override
    public int getAlgorithmType() {
        return 2;
    }

    private void init() {
        loopMapSettingRewards(QLearningAlgorithm.RESOURCE_REWARD_VALUE);
    }

    private void loopMapSettingRewards(int reward) {
        if(agent.getMap().getDimensions().isPresent()) {
            Tuple<Integer, Integer> mapDimensions = agent.getMap().getDimensions().get();
            for(int i = 0; i < mapDimensions.getX(); i++) {
                for(int j = 0; j < mapDimensions.getY(); j++) {
                    R[i][j] = 0;
                    Q[i][j] = 0;
                    if(agent.getMap().get(i, j).isPresent()) {
                        if(agent.getMap().get(i, j).get().getType().equals(TypeObject.Resource)) {
                            R[i][j] = reward;
                        }
                    }
                }
            }
        }
    }

    double maxQ() {
        ArrayList<Directions> actionsFromState = this.agent.getAllowedActions();
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < actionsFromState.size(); i++) {
            Directions nextState = actionsFromState.get(i);
            Tuple<Integer,Integer> wouldBeNextPos = getPositionFromDirectionWithoutMoving(nextState);
            double value = Q[wouldBeNextPos.getX()][wouldBeNextPos.getY()];

            if (value > maxValue)
                maxValue = value;
        }
        return maxValue;
    }

    private Tuple<Integer,Integer> getPositionFromDirectionWithoutMoving(Directions possibleState) {
        if(possibleState == Directions.UP) {
            return new Tuple<Integer,Integer>(agent.getPosition().getX(), agent.getPosition().getY() + 1);
        } else if(possibleState == Directions.DOWN) {
            return new Tuple<Integer,Integer>(agent.getPosition().getX(), agent.getPosition().getY() - 1);
        } else if(possibleState == Directions.RIGHT) {
            return new Tuple<Integer,Integer>(agent.getPosition().getX() + 1, agent.getPosition().getY());
        } else {
            return new Tuple<Integer,Integer>(agent.getPosition().getX() - 1, agent.getPosition().getY());
        }
    }

    double Q(Tuple<Integer,Integer> state) {
        return Q[state.getX()][state.getY()];
    }

    void setQ(Tuple<Integer,Integer> state, double value) {
        Q[state.getX()][state.getY()] = value;
    }

    int R(Tuple<Integer,Integer> state) {
        return R[state.getX()][state.getY()];
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

        /*void printResult() {
        System.out.println("Print result");
        for (int i = 0; i < Q.length; i++) {
            System.out.print("out from " + stateNames[i] + ":  ");
            for (int j = 0; j < Q[i].length; j++) {
                System.out.print(df.format(Q[i][j]) + " ");
            }
            System.out.println();
        }
    }*/

    // policy is maxQ(states)
    /*void showPolicy() {
        System.out.println("\nshowPolicy");
        for (int i = 0; i < states.length; i++) {
            int from = states[i];
            int to = policy(from);
            System.out.println("from " + stateNames[from] + " goto " + stateNames[to]);
        }
    }*/

    // get policy from state
    /*int policy(int state) {
        int[] actionsFromState = actions[state];
        double maxValue = Double.MIN_VALUE;
        int policyGotoState = state; // default goto self if not found
        for (int i = 0; i < actionsFromState.length; i++) {
            int nextState = actionsFromState[i];
            double value = Q[state][nextState];

            if (value > maxValue) {
                maxValue = value;
                policyGotoState = nextState;
            }
        }
        return policyGotoState;
    }*/
}