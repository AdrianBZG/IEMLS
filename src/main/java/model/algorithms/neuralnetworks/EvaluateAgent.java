package model.algorithms.neuralnetworks;

/**
 * Created by adrian on 10/01/17.
 */
import controller.NeuralNetworkAgentConfigurationController;
import model.map.EnvironmentMap;
import model.object.Resource;
import model.object.agent.NeuralAgent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EvaluateAgent {

    private List<EnvironmentMap> maps = new ArrayList<>();

    public EvaluateAgent(NeuralAgent agent, int size)
    {
        for(int i=0;i<size;i++)
        {
            EnvironmentMap map = agent.getMap().clone();
            this.maps.add(map);
        }
    }

    public int evaluate(NeuralAgent agent)
    {
        int score = 0;

        for(EnvironmentMap map: maps)
        {
            agent.setMap(map);

            Set<String> segmentScore = new HashSet<String>();
            for(int i=0;i<50;i++)
            {
                String key = agent.getPosition().getX()+":"+agent.getPosition().getY();
                segmentScore.add(key);
                agent.performMove();
            }

            score+=segmentScore.size();
        }

        System.out.println("Agent score: " + ((30*30) - score));
        if(NeuralNetworkAgentConfigurationController.bestAgentScore < ((30*30) - score)) {
            NeuralNetworkAgentConfigurationController.bestAgentScore = ((30*30) - score);
            NeuralNetworkAgentConfigurationController.bestNeuralAgent = agent;
        }
        return ((30*30) - score);
    }
}
