package model.algorithms.neuralnetworks;

/**
 * Created by adrian on 10/01/17.
 */
public class NeuralConstants {
    final public static int VISION_POINTS = 12;
    final public static int VISION_POINT_12_OCLOCK = 0;
    final public static int VISION_POINT_3_OCLOCK = 3;
    final public static int VISION_POINT_6_OCLOCK = 6;
    final public static int VISION_POINT_9_OCLOCK = 9;

    final public static int MOTOR_NORTH = 0;
    final public static int MOTOR_EAST = 1;
    final public static int MOTOR_SOUTH = 2;
    final public static int MOTOR_WEST = 3;
    public static final int INPUT_NEURON_COUNT = VISION_POINTS;
    public static final int OUTPUT_NEURON_COUNT = 4;

    final public static double HI = 0.5;
    final public static double LO = -0.5;
}
