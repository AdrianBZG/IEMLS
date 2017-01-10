package model.algorithms.neuralnetworks;

/**
 * Created by adrian on 10/01/17.
 */
import model.map.EnvironmentMap;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

public class NeuralAgentFactory {

    public static BasicNeuralNetworkAlgorithm generateMouse(EnvironmentMap map)
    {
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(NeuralConstants.INPUT_NEURON_COUNT));
        network.addLayer(new BasicLayer(60));
        //network.addLayer(new BasicLayer(30));
        network.addLayer(new BasicLayer(NeuralConstants.OUTPUT_NEURON_COUNT));
        network.getStructure().finalizeStructure();
        network.reset();

        BasicNeuralNetworkAlgorithm agent = new BasicNeuralNetworkAlgorithm(network);
        return agent;
    }

    public static BasicNeuralNetworkAlgorithm generateSmartMouse(EnvironmentMap map)
    {
        BasicNeuralNetworkAlgorithm agent = NeuralAgentFactory.generateMouse(map);
        EvaluateAgent eval = new EvaluateAgent(agent, 10);
        for(;;)
        {
            agent.getBrain().reset();
            int score = eval.evaluate(agent);
            if( score>50 )
                return agent;
        }
    }
}
