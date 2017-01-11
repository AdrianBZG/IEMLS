package model.object.agent;

import controller.NeuralNetworkAgentConfigurationController;
import controller.SwarmNestConfigurationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import model.algorithms.neuralnetworks.NeuralConstants;
import model.map.EnvironmentMap;
import model.object.TypeObject;
import model.species.Specie;
import org.encog.ml.data.MLData;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.networks.BasicNetwork;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import util.Directions;
import util.Position;
import view.ErrorView;
import view.ObjectView.NeuralAgentView;
import view.ObjectView.ObjectView;
import view.ObjectView.SwarmAgentView;

import java.io.IOException;
import java.util.Optional;

public class NeuralAgent extends Agent {

    /**
     * The agent specie
     */
    private Specie specie;

    private BasicNetwork brain;
    BasicNeuralData vision;

    public NeuralAgent(BasicNetwork brain, EnvironmentMap map) {
        this.brain = brain;
        this.setMap(map);
        this.setPosition(0,0);
        this.vision = new BasicNeuralData(NeuralConstants.VISION_POINTS);
    }

    public NeuralAgent(NeuralAgent neuralAgent, BasicNetwork brain, EnvironmentMap map) {
        this.brain = neuralAgent.brain;
        this.setMap(map);
        if (neuralAgent.getAlgorithm() != null) {
            setAlgorithm(neuralAgent.getAlgorithm().clone());
        }
        setPosition(neuralAgent.getPosition().getX(), neuralAgent.getPosition().getY());
        this.vision = new BasicNeuralData(NeuralConstants.VISION_POINTS);
    }

    public NeuralAgent(NeuralAgent neuralAgent) {
        this.brain = neuralAgent.brain;
        this.setMap(neuralAgent.getMap());
        this.vision = neuralAgent.vision;
        if (neuralAgent.getAlgorithm() != null) {
            setAlgorithm(neuralAgent.getAlgorithm().clone());
        }
        setPosition(neuralAgent.getPosition().getX(), neuralAgent.getPosition().getY());
    }

    public NeuralAgent() {
        this.brain = null;
        this.setMap(null);
        this.setPosition(0,0);
        this.vision = new BasicNeuralData(NeuralConstants.VISION_POINTS);
    }

    public void updateVision() {
        // twelve o'clock
        boolean wallNorth = false;
        if(getMap().get(Position.getInDirection(this.getPosition(), Directions.UP)).isPresent()) {
            wallNorth = getMap().get(Position.getInDirection(this.getPosition(), Directions.UP)).get().getType().equals(TypeObject.Obstacle);
        }

        boolean wallEast = false;
        if(getMap().get(Position.getInDirection(this.getPosition(), Directions.RIGHT)).isPresent()) {
            wallEast = getMap().get(Position.getInDirection(this.getPosition(), Directions.RIGHT)).get().getType().equals(TypeObject.Obstacle);
        }

        boolean wallSouth = false;
        if(getMap().get(Position.getInDirection(this.getPosition(), Directions.DOWN)).isPresent()) {
            wallSouth = getMap().get(Position.getInDirection(this.getPosition(), Directions.DOWN)).get().getType().equals(TypeObject.Obstacle);
        }

        boolean wallWest = false;
        if(getMap().get(Position.getInDirection(this.getPosition(), Directions.LEFT)).isPresent()) {
            wallWest = getMap().get(Position.getInDirection(this.getPosition(), Directions.LEFT)).get().getType().equals(TypeObject.Obstacle);
        }

        this.vision.setData(NeuralConstants.VISION_POINT_12_OCLOCK, wallNorth ? NeuralConstants.HI : NeuralConstants.LO);

        // three o'clock
        this.vision.setData(NeuralConstants.VISION_POINT_3_OCLOCK, wallEast ? NeuralConstants.HI : NeuralConstants.LO);

        // six o'clock
        this.vision.setData(NeuralConstants.VISION_POINT_6_OCLOCK, wallSouth ? NeuralConstants.HI : NeuralConstants.LO);

        // nine o'clock
        this.vision.setData(NeuralConstants.VISION_POINT_9_OCLOCK, wallWest ? NeuralConstants.HI : NeuralConstants.LO);
    }

    public void checkMove(Directions direction) {
        if(getMap().get(Position.getInDirection(this.getPosition(), direction)).isPresent()) {
            if(getMap().get(Position.getInDirection(this.getPosition(), direction)).get().getType().equals(TypeObject.Obstacle)) {
                return;
            }
        }

        //System.out.println("Neural agent antes moverse: " + this.getPosition().getX() + ", " + this.getPosition().getY());
        move(direction);
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

            if(getMap().get(Position.getInDirection(this.getPosition(), direction)).isPresent()) {
                if(getMap().get(Position.getInDirection(this.getPosition(), direction)).get().getType().equals(TypeObject.Obstacle)) {
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

    public void performMove() {
        Directions direction = autonomousMoveDirection();
        checkMove(direction);
    }


    /**
     * Representation of object in 2D dimension
     *
     * @return
     */
    @Override
    public ObjectView getVisualObject() {
        return new NeuralAgentView();
    }

    /**
     * Name of object to user display identification
     *
     * @return
     */
    @Override
    public String getName() {
        return "Neural Network Agent";
    }

    /**
     * Is possible configure this object?
     *
     * @return boolean to question
     */
    @Override
    public boolean hasOptions() {
        return true;
    }

    @Override
    public void showOptions() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Neural Network Agent Configuration");
        dialog.setHeaderText("You can select the type of neural network and some properties");
        dialog.getDialogPane().setPrefSize(500, 600);

        FontIcon icon = new FontIcon(FontAwesome.COGS);
        icon.setIconSize(64);
        dialog.setGraphic(icon);
        ButtonType applyChanges = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyChanges, ButtonType.CANCEL);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("NeuralNetworkAgentConfiguration.fxml"));
            Parent root = fxmlLoader.load();
            dialog.getDialogPane().setContent(root);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == applyChanges) {
                    NeuralNetworkAgentConfigurationController configurationController = fxmlLoader.getController();
                    setAlgorithm(configurationController.getAlgorithm());
                    return null;
                }
                return null;
            });

        } catch (IOException e) {
            new ErrorView("No possible load agent configuration");
            e.printStackTrace();
        }

        Optional<String> s = dialog.showAndWait();
    }

    public BasicNetwork getBrain() {
        return brain;
    }

    public void setBrain(BasicNetwork brain) {
        this.brain = brain;
    }

    public BasicNeuralData getVision() {
        return vision;
    }

    public void setVision(BasicNeuralData vision) {
        this.vision = vision;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new NeuralAgent(this);
    }
}
