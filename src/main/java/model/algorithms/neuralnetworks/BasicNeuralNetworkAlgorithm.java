package model.algorithms.neuralnetworks;

/**
 * Created by adrian on 10/01/17.
 */
import model.algorithms.Algorithm;
import model.map.EnvironmentMap;
import model.object.TypeObject;
import model.object.agent.Agent;
import model.object.agent.NeuralAgent;
import org.encog.ml.data.MLData;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.networks.BasicNetwork;
import util.Directions;
import util.Position;

public class BasicNeuralNetworkAlgorithm extends Algorithm {

    private NeuralAgent dummyNeuralAgent = null;
    private NeuralAgent realNeuralAgent = null;

    @Override
    public void start(Agent agent) {
        NeuralAgent castedAgent = (NeuralAgent)agent;
        this.dummyNeuralAgent = castedAgent;
    }

    @Override
    public void update(Agent agent) {
        if(dummyNeuralAgent == null) {
            start(agent);
        }

        if (this.realNeuralAgent != null) {
            this.realNeuralAgent.autonomousMoveDirection();
        } else {
            realNeuralAgent = NeuralAgentFactory.generateSmartAgent(dummyNeuralAgent.getMap());
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public String toString() {
        return "Basic Neural Network";
    }

    @Override
    public Algorithm clone() {
        return new BasicNeuralNetworkAlgorithm();
    }

    @Override
    public int getAlgorithmType() {
        return 2;
    }
}
