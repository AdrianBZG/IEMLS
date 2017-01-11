package model.algorithms.neuralnetworks;

/**
 * Created by adrian on 10/01/17.
 */
import model.map.EnvironmentMap;
import model.object.agent.NeuralAgent;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import util.Tuple;

public class NeuralAgentFactory {

    public static NeuralAgent generateAgent(EnvironmentMap map)
    {
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(NeuralConstants.INPUT_NEURON_COUNT));
        network.addLayer(new BasicLayer(60));
        network.addLayer(new BasicLayer(30));
        network.addLayer(new BasicLayer(NeuralConstants.OUTPUT_NEURON_COUNT));
        network.getStructure().finalizeStructure();
        network.reset();

        NeuralAgent agent = new NeuralAgent(network, map);
        return agent;
    }

    public static NeuralAgent generateSmartAgent(EnvironmentMap map, Tuple<Integer,Integer> startPos)
    {
        NeuralAgent agent = NeuralAgentFactory.generateAgent(map);
        agent.setPosition(startPos);
        map.set(agent.getPosition().getX(), agent.getPosition().getY(), agent);
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
