package model.algorithms.neuralnetworks;

/**
 * Created by adrian on 10/01/17.
 */
import model.map.EnvironmentMap;
import org.encog.ml.data.MLData;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.networks.BasicNetwork;

public class NeuralAgent {

    private BasicNetwork brain;
    private EnvironmentMap environment;
    private int x;
    private int y;
    BasicNeuralData vision;

    public NeuralAgent(BasicNetwork brain, EnvironmentMap environment) {
        this.brain = brain;
        this.environment = environment;
        this.x = 0;
        this.y = 0;
        this.vision = new BasicNeuralData(NeuralConstants.VISION_POINTS);
    }

    public void updateVision() {
        // North-East Corner
        int xNE = x + 1;
        int yNE = y - 1;

        // North-West Corner
        int xNW = x - 1;
        int yNW = y - 1;

        // South-East Corner
        int xSE = x + 1;
        int ySE = y + 1;

        // South-West Corner
        int xSW = x - 1;
        int ySW = y + 1;

        // twelve o'clock
        this.vision.setData(NeuralConstants.VISION_POINT_12_OCLOCK, environment
                .isWall(x, y, Maze.NORTH) ? NeuralConstants.HI : NeuralConstants.LO);

        // three o'clock
        this.vision.setData(NeuralConstants.VISION_POINT_3_OCLOCK, environment
                .isWall(x, y, Maze.EAST) ? NeuralConstants.HI : NeuralConstants.LO);

        // six o'clock
        this.vision.setData(NeuralConstants.VISION_POINT_6_OCLOCK, environment
                .isWall(x, y, Maze.SOUTH) ? NeuralConstants.HI : NeuralConstants.LO);

        // nine o'clock
        this.vision.setData(NeuralConstants.VISION_POINT_9_OCLOCK, environment
                .isWall(x, y, Maze.WEST) ? NeuralConstants.HI : NeuralConstants.LO);
    }

    public boolean move(int direction) {

        if (this.environment.isWall(this.x, this.y, direction))
            return false;


        switch (direction) {
            case Maze.NORTH:
                y--;
                break;
            case Maze.SOUTH:
                y++;
                break;
            case Maze.EAST:
                x++;
                break;
            case Maze.WEST:
                x--;
                break;
        }

        return true;
    }

    private int directionFromIndex(int i) {
        if (i == NeuralConstants.MOTOR_NORTH)
            return Maze.NORTH;
        else if (i == NeuralConstants.MOTOR_SOUTH)
            return Maze.SOUTH;
        else if (i == NeuralConstants.MOTOR_EAST)
            return Maze.EAST;
        else
            return Maze.WEST;
    }

    public int autonomousMoveDirection() {
        updateVision();
        MLData result = this.brain.compute(this.vision);

        double winningOutput = Double.NEGATIVE_INFINITY;
        int winningDirection = 0;

        for (int i = 0; i < result.size(); i++) {
            // determine direction
            int direction = directionFromIndex(i);

            if (this.environment.isWall(this.x, this.y, direction))
                continue;

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
        int direction = autonomousMoveDirection();
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
}
