package model.algorithms.neuralnetworks;

/**
 * Created by adrian on 10/01/17.
 */
import model.algorithms.Algorithm;
import model.map.EnvironmentMap;
import model.object.TypeObject;
import model.object.agent.Agent;
import org.encog.ml.data.MLData;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.networks.BasicNetwork;
import util.Directions;
import util.Position;

public class NeuralNetworkAlgorithm extends Algorithm {

    private BasicNetwork brain;
    private Agent agent;
    private EnvironmentMap environment;
    private int x;
    private int y;
    BasicNeuralData vision;

    public NeuralNetworkAlgorithm(BasicNetwork brain) {
        this.brain = brain;
        this.environment = agent.getMap();
        this.x = 0;
        this.y = 0;
        this.vision = new BasicNeuralData(NeuralConstants.VISION_POINTS);
    }

    public void updateVision() {
        // twelve o'clock
        boolean wallNorth = false;
        if(environment.get(Position.getInDirection(agent.getPosition(), Directions.UP)) != null) {
            wallNorth = environment.get(Position.getInDirection(agent.getPosition(), Directions.UP)).get().getType().equals(TypeObject.Obstacle);
        }

        boolean wallEast = false;
        if(environment.get(Position.getInDirection(agent.getPosition(), Directions.UP)) != null) {
            wallEast = environment.get(Position.getInDirection(agent.getPosition(), Directions.RIGHT)).get().getType().equals(TypeObject.Obstacle);
        }

        boolean wallSouth = false;
        if(environment.get(Position.getInDirection(agent.getPosition(), Directions.UP)) != null) {
            wallSouth = environment.get(Position.getInDirection(agent.getPosition(), Directions.DOWN)).get().getType().equals(TypeObject.Obstacle);
        }

        boolean wallWest = false;
        if(environment.get(Position.getInDirection(agent.getPosition(), Directions.UP)) != null) {
            wallWest = environment.get(Position.getInDirection(agent.getPosition(), Directions.LEFT)).get().getType().equals(TypeObject.Obstacle);
        }

        this.vision.setData(NeuralConstants.VISION_POINT_12_OCLOCK, wallNorth ? NeuralConstants.HI : NeuralConstants.LO);

        // three o'clock
        this.vision.setData(NeuralConstants.VISION_POINT_3_OCLOCK, wallEast ? NeuralConstants.HI : NeuralConstants.LO);

        // six o'clock
        this.vision.setData(NeuralConstants.VISION_POINT_6_OCLOCK, wallSouth ? NeuralConstants.HI : NeuralConstants.LO);

        // nine o'clock
        this.vision.setData(NeuralConstants.VISION_POINT_9_OCLOCK, wallWest ? NeuralConstants.HI : NeuralConstants.LO);
    }

    public boolean move(Directions direction) {
        if(environment.get(Position.getInDirection(agent.getPosition(), direction)) != null) {
            if(environment.get(Position.getInDirection(agent.getPosition(), direction)).get().getType().equals(TypeObject.Obstacle)) {
                return false;
            }
        }

        agent.move(direction);
        return true;
    }

    private Directions directionFromIndex(int i) {
        if (i == NeuralConstants.MOTOR_NORTH)
            return Directions.UP;
        else if (i == NeuralConstants.MOTOR_SOUTH)
            return Directions.DOWN;
        else if (i == NeuralConstants.MOTOR_EAST)
            return Directions.RIGHT;
        else
            return Directions.LEFT;
    }

    public Directions autonomousMoveDirection() {
        updateVision();
        MLData result = this.brain.compute(this.vision);

        double winningOutput = Double.NEGATIVE_INFINITY;
        Directions winningDirection = Directions.UP;

        for (int i = 0; i < result.size(); i++) {
            // determine direction
            Directions direction = directionFromIndex(i);

            if(environment.get(Position.getInDirection(agent.getPosition(), direction)) != null) {
                if(environment.get(Position.getInDirection(agent.getPosition(), direction)).get().getType().equals(TypeObject.Obstacle)) {
                    continue;
                }
            }

            // evaluate if this is a "winning" direction
            double thisOutput = result.getData(i);
            if (thisOutput > winningOutput) {
                winningOutput = thisOutput;
                winningDirection = direction;
            }
        }

        return winningDirection;
    }

    public boolean move() {
        Directions direction = autonomousMoveDirection();
        return move(direction);
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


    public BasicNetwork getBrain() {
        return brain;
    }

    public void setBrain(BasicNetwork brain) {
        this.brain = brain;
    }

    public EnvironmentMap getEnvironment() {
        return environment;
    }

    public void setEnvironment(EnvironmentMap environment) {
        this.environment = environment;
    }

    @Override
    public void start(Agent agent) {
        this.agent = agent;
        environment = agent.getMap();
    }

    @Override
    public void update(Agent agent) {
        if (this.agent != null) {
            autonomousMoveDirection();
        } else {
            start(agent);
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public String toString() {
        return "Neural Network";
    }

    @Override
    public Algorithm clone() {
        return new NeuralNetworkAlgorithm(this.brain);
    }

    @Override
    public int getAlgorithmType() {
        return 2;
    }
}
