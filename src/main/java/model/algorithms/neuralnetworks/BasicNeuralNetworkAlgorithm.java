package model.algorithms.neuralnetworks;

/**
 * Created by adrian on 10/01/17.
 */
import controller.NeuralNetworkAgentConfigurationController;
import model.algorithms.Algorithm;
import model.object.agent.Agent;
import model.object.agent.NeuralAgent;

import java.util.ArrayList;

public class BasicNeuralNetworkAlgorithm extends Algorithm {

    private int numberOfAgentsToBeTrained = 20;
    private Agent agent = null;
    private Agent initialAgent = null;
    private ArrayList<NeuralAgent> neuralAgents = null;

    @Override
    public void start(Agent agent) {
        this.agent = agent;
        this.initialAgent = agent;
    }

    @Override
    public void update(Agent agent) {
        if(agent == null) {
            start(agent);
        }

        if (this.neuralAgents != null) {
            for(NeuralAgent na : neuralAgents) {
                if(na == NeuralNetworkAgentConfigurationController.bestNeuralAgent) {
                    System.out.println("The best trained agent has a value of: " + NeuralNetworkAgentConfigurationController.bestAgentScore);
                    this.agent.setPosition(na.getPosition());
                }
            }
        } else {
            neuralAgents = new ArrayList<>();
            for(int i = 0; i < numberOfAgentsToBeTrained; i++) {
                NeuralAgent toTrainAgent = NeuralAgentFactory.generateSmartAgent(initialAgent.getMap(), initialAgent.getPosition());
                neuralAgents.add(toTrainAgent);
            }
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
