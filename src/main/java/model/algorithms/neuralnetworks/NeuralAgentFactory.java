package model.algorithms.neuralnetworks;

/**
 * Created by adrian on 10/01/17.
 */
import model.map.EnvironmentMap;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

public class NeuralAgentFactory {

    public static NeuralAgent generateMouse(EnvironmentMap map)
    {
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(NeuralConstants.INPUT_NEURON_COUNT));
        network.addLayer(new BasicLayer(60));
        //network.addLayer(new BasicLayer(30));
        network.addLayer(new BasicLayer(NeuralConstants.OUTPUT_NEURON_COUNT));
        network.getStructure().finalizeStructure();
        network.reset();

        NeuralAgent agent = new NeuralAgent(network,map);
        return agent;
    }

    public static NeuralAgent generateSmartMouse(EnvironmentMap map)
    {
        NeuralAgent agent = NeuralAgentFactory.generateMouse(map);
        EvaluateAgent eval = new EvaluateAgent(10);
        for(;;)
        {
            agent.getBrain().reset();
            int score = eval.evaluate(agent);
            if( score>50 )
                return agent;
        }
    }
}
