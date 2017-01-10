package model.algorithms.neuralnetworks;

/**
 * Created by adrian on 10/01/17.
 */
import model.map.EnvironmentMap;
import model.object.Resource;
import model.object.TypeObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EvaluateAgent {

    private List<EnvironmentMap> maps = new ArrayList<>();

    public EvaluateAgent(NeuralNetworkAlgorithm agent, int size)
    {
        for(int i=0;i<size;i++)
        {
            EnvironmentMap map = agent.getEnvironment().clone();
            this.maps.add(map);
        }
    }

    public int evaluate(NeuralNetworkAlgorithm agent)
    {
        int score = 0;

        for(EnvironmentMap map: maps)
        {
            agent.setEnvironment(map);
            map.set(0,0, new Resource(1,"Dummy"));
            agent.setX(0);
            agent.setY(0);

            Set<String> segmentScore = new HashSet<String>();
            for(int i=0;i<100;i++)
            {
                String key = agent.getX()+":"+agent.getY();
                segmentScore.add(key);
                agent.move();
            }

            score+=segmentScore.size();
        }

        return ((30*30) - score);
    }
}
